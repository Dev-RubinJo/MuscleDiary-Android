package com.munchkin.musclediary.src.main.food;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.content.Intent;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.munchkin.musclediary.R;
import com.munchkin.musclediary.src.BaseFragment;
import com.munchkin.musclediary.src.main.food.adapter.MealAdapter;
import com.munchkin.musclediary.src.main.food.interfaces.InputMenuActivityView;
import com.munchkin.musclediary.src.main.food.interfaces.MenuItemClickListener;
import com.munchkin.musclediary.src.main.food.models.DeleteFoodRequest;
import com.munchkin.musclediary.src.main.food.models.FoodResult;
import com.munchkin.musclediary.src.main.food.models.MealItem;
import com.munchkin.musclediary.src.main.food.models.MenuItem;
import com.munchkin.musclediary.src.main.food.models.ReadFoodResult;
import com.munchkin.musclediary.src.main.food.services.InputMenuService;
import com.munchkin.musclediary.src.main.setting.models.GetNutritionResponse;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import static com.munchkin.musclediary.src.ApplicationClass.sSharedPreferences;

public class FoodFragment extends BaseFragment implements InputMenuActivityView, View.OnClickListener, OnDateSelectedListener, MenuItemClickListener {

    private ArrayList<MealItem> mMealitems;
    private MealAdapter mMealAdapter;

    private final int WEB_PROTEIN = 0;

    private Button mbtWebProtein;

    //목표영양
    private TextView mTvGoalKcal;
    private int mGoalKcal = 0;
    //섭취영양
    private TextView mTvEatenKcal;
    private Double mEatenKcal = 0.0;
    //남은영양
    private TextView mTvLeftKcal;
    private Double mLeftKcal;

    //목표탄단지 gram
    private int mGoalCarboGram = 0;
    private int mGoalProteinGram = 0;
    private int mGoalFatGram = 0;

    //섭취탄단지 gram
    private int mEatenCarboGram =0;
    private int mEatenProteinGram = 0;
    private int mEatenFatGram = 0;

    //탄단지 gram 텍스트와 프로그래스바
    private TextView mTvCarboGram;
    private TextView mTvProteinGram;
    private TextView mTvFatGram;

    //단백질 부족에 대한 홍보 문구
    private TextView mTvWarningProtein;

    private com.mikhaellopez.circularprogressbar.CircularProgressBar mPbCarbo;
    private com.mikhaellopez.circularprogressbar.CircularProgressBar mPbProtein;
    private com.mikhaellopez.circularprogressbar.CircularProgressBar mPbFat;

    //그램수 계산을 위한 shared preference
    SharedPreferences.Editor mEditor = sSharedPreferences.edit();

    //캘린더 설정, 날짜 형식 설정
    private MaterialCalendarView mCalendarView;
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");
    //현재 날짜 가져오기
    private Date currentTime = Calendar.getInstance().getTime();

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_web_protein:
                Intent webproteinIntent = new Intent(getActivity(), WebProteinActivity.class);
                startActivity(webproteinIntent);
                break;
        }

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //처음시작할때만 날짜 읽어옴
        final String todayText = String.format(DATE_FORMAT.format(currentTime));
        mEditor.putString("recordDate", todayText);
        mEditor.apply();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        final View view = inflater.inflate(R.layout.fragment_food, container, false);
        setComponentView(view);
        Log.d("life","food onCreatedView enter");

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        //NOTE 내생각엔 처음에 완전 아무것도 없을때 뭔가 하려고 했는데 없어도 무방
        initMealList();
        tryReadMeal(1);
        tryReadMeal(2);
        tryReadMeal(3);
        tryReadMeal(4);
        tryGetNutrition();
        //kcalUpdate();
        //progressBarUpdate();
    }

    // 생성될 음식 프레그먼트에 대한 컴포넌트 세팅
    @Override
    public void setComponentView(View v) {
        //더미데이터 생성
        initMealList();

        //웹뷰 보여주기 위한 설정
        mbtWebProtein = v.findViewById(R.id.bt_web_protein);
        mbtWebProtein.setOnClickListener(this);

        //리사이클러뷰 생성, 레이아웃 매니저 적용
        RecyclerView mealRecyclerView = v.findViewById(R.id.fragment_food_rv_meal);
        mealRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        //리사이클러뷰 어뎁터 생성, 적용
        mMealAdapter = new MealAdapter(getContext(),mMealitems,this);
        mealRecyclerView.setAdapter(mMealAdapter);

        //달력 xml과 연결
        mCalendarView = v.findViewById(R.id.fragment_food_calendarView);
        mCalendarView.setOnDateChangedListener(this);
        mCalendarView.setDateSelected(currentTime,true);

        //목표 칼로리 텍스트뷰 생성
        mTvGoalKcal = v.findViewById(R.id.fragment_food_tv_goal_cal);


        //섭취칼로리 텍스트뷰 생성
        mTvEatenKcal = v.findViewById(R.id.fragment_food_tv_eaten_cal);
        mTvLeftKcal = v.findViewById(R.id.fragment_food_tv_left_cal);

        //프로그레스 바 등록
        mPbCarbo = v.findViewById(R.id.fragment_food_pb_carbo);
        mPbProtein = v.findViewById(R.id.fragment_food_pb_protein);
        mPbFat = v.findViewById(R.id.fragment_food_pb_fat);

        mTvCarboGram = v.findViewById(R.id.fragment_food_tv_str_carbo_left);
        mTvProteinGram = v.findViewById(R.id.fragment_food_tv_str_protein_left);
        mTvFatGram = v.findViewById(R.id.fragment_food_tv_str_fat_left);

        //문구 등록
        mTvWarningProtein = v.findViewById(R.id.fragment_food_tv_str_protein_warning);
    }

    //메뉴추가시, mealItem 업데이트
    public void onCompleteMenuSelect(ArrayList<MenuItem> menuAddList, String mealTitle){
        for(int i=0; i<4; i++){
            if(mMealitems.get(i).getMealTitle().equals(mealTitle)){
                for(int j=0; j<menuAddList.size(); j++){
                    mMealitems.get(i).getMenuItemList().add(menuAddList.get(j));
                }
            }
        }

        kcalUpdate();
        progressBarUpdate();
        mMealAdapter.changeDataset(mMealitems);
    }

    //프로그레스바 업데이트
    public void progressBarUpdate(){
        //progressbar 초기화
        mPbCarbo.setProgressWithAnimation(0,(long) 100);
        mPbProtein.setProgressWithAnimation(0,(long) 100);
        mPbFat.setProgressWithAnimation(0,(long) 100);

        //기존에 저장되어있는 목표 그램수 받아오기
        mGoalCarboGram = sSharedPreferences.getInt("carbohydrateGram",0);
        mGoalProteinGram = sSharedPreferences.getInt("proteinGram",0);
        mGoalFatGram = sSharedPreferences.getInt("fatGram",0);

        //먹은 그램수 초기화
        mEatenCarboGram = 0;
        mEatenProteinGram = 0;
        mEatenFatGram = 0;

        //먹은 그램수 새로 누적
        for (int i = 0; i<mMealitems.size(); i++){
            for (int j = 0; j<mMealitems.get(i).getMenuItemList().size(); j++) {
                MenuItem thisItem = mMealitems.get(i).getMenuItemList().get(j);
                mEatenCarboGram += thisItem.getCarbohydrate()*thisItem.getServing();
                mEatenProteinGram += thisItem.getProtein()*thisItem.getServing();
                mEatenFatGram += thisItem.getFat()*thisItem.getServing();
            }

        }

        //text 설정
        mTvCarboGram.setText(mEatenCarboGram+"/"+mGoalCarboGram+"g");
        mTvProteinGram.setText(mEatenProteinGram+"/"+mGoalProteinGram+"g");
        mTvFatGram.setText(mEatenFatGram+"/"+ mGoalFatGram +"g");

        //progressbar 퍼센트에 따른 변경
        if(mGoalFatGram!=0){
            float percent = ((float)mEatenFatGram/ (float)mGoalFatGram *100);
            mPbFat.setProgressWithAnimation(percent, (long) 500);
        }

        if(mGoalProteinGram!=0) {
            float percent = ((float)mEatenProteinGram/(float)mGoalProteinGram*100);
            mPbProtein.setProgressWithAnimation(percent,(long) 500);
        }

        if(mGoalCarboGram!=0){
            float percent = ((float)mEatenCarboGram/(float)mGoalCarboGram*100);
            mPbCarbo.setProgressWithAnimation(percent,(long) 500);
        }

        if(mGoalFatGram<mEatenFatGram){
            mTvFatGram.setTextColor(Color.RED);
        }else{
            mTvFatGram.setTextColor(Color.rgb(43,50,82));
        }
        if(mGoalProteinGram<mEatenProteinGram){
            mTvProteinGram.setTextColor(Color.RED);
        }else{
            mTvProteinGram.setTextColor(Color.rgb(43,50,82));
        }
        if(mGoalCarboGram<mEatenCarboGram){
            mTvCarboGram.setTextColor(Color.RED);
        }else{
            mTvCarboGram.setTextColor(Color.rgb(43,50,82));
        }

        mTvWarningProtein.setText("일일 목표 단백질 섭취까지 "+(mGoalProteinGram-mEatenProteinGram)+"g 남았습니다.\n단백질 쉐이크로 간편하게 채워보세요");
    }

    //총 칼로리 업데이트트
   public void kcalUpdate(){
        mEatenKcal = 0.0;
        mLeftKcal = 0.0;

        for (int i = 0; i<mMealitems.size(); i++){
            //식단별 칼로리 초기화
            mMealitems.get(i).setMealTotalCalories(0.0);

            for (int j = 0; j<mMealitems.get(i).getMenuItemList().size(); j++) {
                Double eatingKcal = mMealitems.get(i).getMenuItemList().get(j).getCalorie()*
                        mMealitems.get(i).getMenuItemList().get(j).getServing();
                mEatenKcal += eatingKcal;

                //식단별 칼로리 누적
                mMealitems.get(i).setMealTotalCalories(mMealitems.get(i).getMealTotalCalories()+eatingKcal);
            }

        }

        mGoalKcal = sSharedPreferences.getInt("kcal", 0);
        mTvGoalKcal.setText(String.format("%.2f", (double)mGoalKcal));
        mLeftKcal = mGoalKcal - mEatenKcal;
        mTvEatenKcal.setText(String.format("%.2f",mEatenKcal));
        mTvLeftKcal.setText(String.format("%.2f",mLeftKcal)+" kcal");
    }

    //아침 점심 저녁 기타 끼니란을 입력
    private void initMealList(){
        String titleList[] = {"아침","점심","저녁","기타"};
        double kcalList[] = {0.0,0.0,0.0,0.0};
        ArrayList<MenuItem> menuItemsEmptyOne = new ArrayList<>();
        ArrayList<MenuItem> menuItemsEmptyTwo = new ArrayList<>();
        ArrayList<MenuItem> menuItemsEmptyThree = new ArrayList<>();
        ArrayList<MenuItem> menuItemsEmptyFour = new ArrayList<>();

        mMealitems = new ArrayList<>();

        for(int i = 0; i<titleList.length; i++){
            MealItem mealItem = new MealItem();
            mealItem.setMealTitle(titleList[i]);
            mealItem.setMealTotalCalories(kcalList[i]);
            mMealitems.add(mealItem);
            if(i==0){
                mMealitems.get(i).setMenuItemList(menuItemsEmptyOne);
            }else if (i==1){
                mMealitems.get(i).setMenuItemList(menuItemsEmptyTwo);
            }else if (i==2){
                mMealitems.get(i).setMenuItemList(menuItemsEmptyThree);
            }else{
                mMealitems.get(i).setMenuItemList(menuItemsEmptyFour);
            }

        }

    }

    //캘린더
    @Override
    public void onDateSelected(@NonNull MaterialCalendarView widget, @NonNull CalendarDay date, boolean selected) {
        final String selectedDay = String.format(DATE_FORMAT.format(date.getDate()));
        mEditor.putString("recordDate", selectedDay);
        mEditor.apply();
        initMealList();
        tryReadMeal(1);
        tryReadMeal(2);
        tryReadMeal(3);
        tryReadMeal(4);
        //Toast.makeText(getContext(), text, Toast.LENGTH_SHORT).show();
    }

    public void tryReadMeal(int mealType){
        InputMenuService inputMenuService = new InputMenuService(this);
        String recordDate = sSharedPreferences.getString("recordDate","1999-12-31");
        inputMenuService.readFoodList(mealType,recordDate);
    }

    public void tryDeleteMenu(int menuNo){
        InputMenuService inputMenuService = new InputMenuService(this);
        DeleteFoodRequest deleteFoodRequest = new DeleteFoodRequest(menuNo);
        inputMenuService.deleteFood(deleteFoodRequest);
    }

    private void tryGetNutrition(){
        showProgressDialog(getActivity());
        final InputMenuService inputMenuService = new InputMenuService(this);
        inputMenuService.getNutrition();
    }

    @Override
    public void readMenuSuccess(int code, String message, ArrayList<ReadFoodResult> readFoodResults, int mealType) {
        if(readFoodResults==null){
            mMealitems.get(mealType-1).getMenuItemList().clear();

            kcalUpdate();
            progressBarUpdate();
            mMealAdapter.changeDataset(mMealitems);
            return;
        }
        ArrayList<MenuItem> menuItems = new ArrayList<>();
        for(int i =0; i<readFoodResults.size();i++){
            ReadFoodResult menuResult = readFoodResults.get(i);
            mMealitems.get(mealType-1).getMenuItemList().add(new MenuItem(menuResult));
//            menuItems.add(menuItem);
        }

        kcalUpdate();
        progressBarUpdate();
        mMealAdapter.changeDataset(mMealitems);

//        String mealTitle;
//        if(mealType==1){
//            mealTitle = "아침";
//        }else if(mealType==2){
//            mealTitle = "점심";
//        }else if(mealType==3){
//            mealTitle = "저녁";
//        }else{
//            mealTitle = "기타";
//        }
//        onCompleteMenuSelect(menuItems,mealTitle);
    }

    @Override
    public void deleteFoodSuccess(int code, String message) {
        initMealList();
        tryReadMeal(1);
        tryReadMeal(2);
        tryReadMeal(3);
        tryReadMeal(4);

        kcalUpdate();
        progressBarUpdate();
        mMealAdapter.changeDataset(mMealitems);
    }

    @Override
    public void getNutritionSuccess(int code, String message, GetNutritionResponse.NutritionResult result) {
        SharedPreferences.Editor editor = sSharedPreferences.edit();
        editor.putInt("kcal", result.getGoalCalorie());
        editor.putInt("carbohydrateGram", result.getCarboRate());
        editor.putInt("proteinGram", result.getProteinRate());
        editor.putInt("fatGram", result.getFatRate());
        Log.d("testLog", "isSuccess");

        editor.apply();
        kcalUpdate();
        progressBarUpdate();
        hideProgressDialog();
    }

    @Override
    public void getGoalWeightFailure(String message) {
        SharedPreferences.Editor editor = sSharedPreferences.edit();
        editor.putInt("kcal", 0);
        editor.putInt("carbohydrateGram", 0);
        editor.putInt("proteinGram", 0);
        editor.putInt("fatGram", 0);
        editor.apply();
        Log.d("testLog", "isFail");
        kcalUpdate();
        progressBarUpdate();
        hideProgressDialog();
    }

    @Override
    public void onMenuClickListener(View view, int position) { }

    @Override
    public void onMenuDeleteClicked(int menuNo) {
        tryDeleteMenu(menuNo);
    }

    @Override
    public void validateFailure(String message) { }

    //Note 사용하지 않음
    @Override
    public void searchFoodListSuccess(int code, String message, ArrayList<FoodResult> result) { }
    @Override
    public void addFoodSuccess(int code, String message) { }


}
