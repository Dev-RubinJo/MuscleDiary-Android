package com.munchkin.musclediary.src.main.food;

import android.content.SharedPreferences;
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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.munchkin.musclediary.R;
import com.munchkin.musclediary.src.BaseFragment;
import com.munchkin.musclediary.src.main.food.adapter.MealAdapter;
import com.munchkin.musclediary.src.main.food.models.MealItem;
import com.munchkin.musclediary.src.main.food.models.MenuItem;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import static com.munchkin.musclediary.src.ApplicationClass.sSharedPreferences;

public class FoodFragment extends BaseFragment implements View.OnClickListener, OnDateSelectedListener{

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

    //캘린더 설정, 날짜 형식 설정
    private MaterialCalendarView mCalendarView;
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");
    //현재 날짜 가져오기
    private Date currentTime = Calendar.getInstance().getTime();

    //그램수 계산을 위한 shared preference
    SharedPreferences.Editor mEditor = sSharedPreferences.edit();

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_web_protein:
                Intent webproteinIntent = new Intent(getActivity(), WebProteinActivity.class);
                startActivity(webproteinIntent);
                break;
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        final View view = inflater.inflate(R.layout.fragment_food, container, false);
        setComponentView(view);

        return view;
    }

    @Override
    public void onDestroyView(){
        super.onDestroyView();
        //Log.d("life","onDestroyView enter");
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
        mMealAdapter = new MealAdapter(getContext(),mMealitems);
        mealRecyclerView.setAdapter(mMealAdapter);

        //달력 xml과 연결
        mCalendarView = v.findViewById(R.id.fragment_food_calendarView);
        mCalendarView.setOnDateChangedListener(this);
        mCalendarView.setDateSelected(currentTime,true);

        //목표 칼로리 텍스트뷰 생성
        mTvGoalKcal = v.findViewById(R.id.fragment_food_tv_goal_cal);
        mGoalKcal = sSharedPreferences.getInt("kcal", 0);
        if(mGoalKcal != 0){
            mTvGoalKcal.setText(String.format("%d", mGoalKcal));
        }

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

        progressBarUpdate();
    }

    @Override
    public void onStart() {
        if(sSharedPreferences.getInt("kcal", 0) != mGoalKcal && mGoalKcal != 0){
            mGoalKcal = sSharedPreferences.getInt("kcal", 0);
            mTvGoalKcal.setText(mGoalKcal);
        }
        if(sSharedPreferences.getInt("carbohydrateGram",0) != mGoalCarboGram && mGoalCarboGram != 0 &&
                sSharedPreferences.getInt("proteinGram",0) != mGoalCarboGram && mGoalCarboGram != 0 &&
                sSharedPreferences.getInt("fatGram",0) != mGoalCarboGram && mGoalCarboGram != 0){
            mGoalCarboGram = sSharedPreferences.getInt("carbohydrateGram",0);
            mGoalProteinGram = sSharedPreferences.getInt("proteinGram",0);
            mGoalFatGram = sSharedPreferences.getInt("fatGram",0);

            progressBarUpdate();
        }
        super.onStart();
    }

    public void onCompleteMenuSelect(ArrayList<MenuItem> menuAddList, String mealTitle){

        for(int i=0; i<4; i++){
            if(mMealitems.get(i).getMealTitle().equals(mealTitle)){
                for(int j=0; j<menuAddList.size(); j++){
                    mMealitems.get(i).getMenuItemList().add(menuAddList.get(j));
                }
                //mMealitems.get(i).setMenuItemList();
            }
        }
        kcalUpdate();
        progressBarUpdate();
        mMealAdapter.notifyDataSetChanged();
    }

    public void progressBarUpdate(){
        //progressbar 초기화
        mPbCarbo.setProgress(0);
        mPbProtein.setProgress(0);
        mPbFat.setProgress(0);

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
            mPbFat.setProgress(percent);
        }

        if(mGoalProteinGram!=0) {
            float percent = ((float)mEatenProteinGram/(float)mGoalProteinGram*100);
            mPbProtein.setProgress(percent);
        }

        if(mGoalCarboGram!=0){
            float percent = ((float)mEatenCarboGram/(float)mGoalCarboGram*100);
            mPbCarbo.setProgress(percent);
        }

        mTvWarningProtein.setText("일일 목표 단백질 섭취까지 "+(mGoalProteinGram-mEatenProteinGram)+"g 남았습니다.\n단백질 쉐이크로 간편하게 채워보세요");

    }

    public void kcalUpdate(){
        //총 칼로리 수정
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

        mLeftKcal = mGoalKcal - mEatenKcal;
        mTvEatenKcal.setText(String.format("%.2f",mEatenKcal));
        mTvLeftKcal.setText(String.format("%.2f",mLeftKcal)+" kcal");

    }

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
        final String text = String.format("Date"+DATE_FORMAT.format(date.getDate()));
        Toast.makeText(getContext(), text, Toast.LENGTH_SHORT).show();
    }
}
