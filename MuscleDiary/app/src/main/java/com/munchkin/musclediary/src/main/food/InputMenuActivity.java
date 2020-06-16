package com.munchkin.musclediary.src.main.food;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.munchkin.musclediary.R;
import com.munchkin.musclediary.src.BaseActivity;
import com.munchkin.musclediary.src.main.food.adapter.MenuResultAdapter;
import com.munchkin.musclediary.src.main.food.adapter.SelectedMenuAdapter;
import com.munchkin.musclediary.src.main.food.interfaces.InputMenuActivityView;
import com.munchkin.musclediary.src.main.food.interfaces.ResultMenuItemClickListener;
import com.munchkin.musclediary.src.main.food.models.FoodAddRequest;
import com.munchkin.musclediary.src.main.food.models.FoodResult;
import com.munchkin.musclediary.src.main.food.models.MenuItem;
import com.munchkin.musclediary.src.main.food.models.ReadFoodResult;
import com.munchkin.musclediary.src.main.food.services.InputMenuService;

import java.util.ArrayList;

import static com.munchkin.musclediary.src.ApplicationClass.sSharedPreferences;

public abstract class InputMenuActivity extends BaseActivity implements InputMenuActivityView, View.OnClickListener, ResultMenuItemClickListener {

    String mMealTitle;
    TextView mTvMealTitle;
    RecyclerView mMenuResultRecyclerView;
    MenuResultAdapter mMenuResultAdapter;

    //선택한 메뉴들 보여주는 리사이클러뷰
    RecyclerView mSelectedMenuRecyclerView;
    SelectedMenuAdapter mSelectedMenuAdapter;

    //선택한 메뉴를 담을 리스트
    ArrayList<MenuItem> mClickedMenuItem;
    MenuItem mSelectedItem;

    //입력할 날짜와 끼니타입변수
    String mRecordDate;
    int mMealType;

    ResultMenuItemClickListener mResultMenuItemClickListener = new ResultMenuItemClickListener() {
        @Override
        public void onResultItemClicked(Intent intentSending, MenuItem clickedItem) {
            mSelectedItem = clickedItem;
            startActivityForResult(intentSending,3000);
        }
    };

    EditText mEtSearch;
    private ArrayList<MenuItem> mMenuItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_menu);

        mMenuItems = new ArrayList<>();
        //검색창 생성
        mEtSearch = findViewById(R.id.input_menu_et_search);
        //키보드에 있는 검색버튼 클릭 적용
        addEditiorActionListener();

        //인텐트 받아오기 (끼니 제목)
        Intent getIntent = getIntent();
        mMealTitle = getIntent.getStringExtra("mealTitle");
        mRecordDate = sSharedPreferences.getString("recordDate","1999-12-31");

        switch (mMealTitle){
            case "아침":
                mMealType=1;
                break;
            case "점심":
                mMealType=2;
                break;
            case "저녁":
                mMealType=3;
                break;
            case "기타":
                mMealType=4;
                break;
        }

        //선택한 아이템을 담을 리스트와 객체 생성
        mClickedMenuItem = new ArrayList<MenuItem>();

        //레이아웃 컴포넌트 설정
        mTvMealTitle = findViewById(R.id.input_menu_tv_meal_title);
        mMenuResultRecyclerView = findViewById(R.id.input_menu_rv_menu_result);
        mSelectedMenuRecyclerView = findViewById(R.id.input_menu_rv_menu_add_list);

        //제목을 선택한 끼니로 바꿔줌
        mTvMealTitle.setText(mMealTitle);

        //더미데이터 생성 -> 더이상 필요 없음
        clearMealList();

        //리사이클러뷰 레이아웃 매니저 적용
        mMenuResultRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        mMenuResultAdapter = new MenuResultAdapter(getApplicationContext(),mMenuItems, mResultMenuItemClickListener);
        mMenuResultRecyclerView.setAdapter(mMenuResultAdapter);

        //추가 메뉴 리사이클러뷰 레이아웃 매니저 적용
        mSelectedMenuRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext(),LinearLayoutManager.HORIZONTAL,false));
        mSelectedMenuAdapter = new SelectedMenuAdapter(getApplicationContext(),mClickedMenuItem,this);
        mSelectedMenuRecyclerView.setAdapter(mSelectedMenuAdapter);

        //검색버튼 생성, 클릭이벤트 적용
        ImageButton btSearch = findViewById(R.id.input_menu_btn_search);
        Button btComplete = findViewById(R.id.input_menu_btn_complete);
        btComplete.setOnClickListener(this);
        btSearch.setOnClickListener(this);
    }

    private void clearMealList(){
        mMenuItems.clear();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode != RESULT_OK){
            return;
        }

        switch (requestCode){
            case 3000: {
                //입력완료
                double serving = data.getDoubleExtra("serving",1);

                //취소
                if(serving != -1.0){
                    mSelectedItem.setServing(serving);
                    mClickedMenuItem.add(mSelectedItem);
                    mSelectedMenuAdapter.notifyDataSetChanged();
                }else{
                    return;
                }
            }
        }
    }

    private void tryGetFoodList(String keyword){
        showProgressDialog();
        InputMenuService inputMenuService = new InputMenuService(this);
        inputMenuService.getFoodList(keyword);
    }

    private void tryAddFood(){
        InputMenuService inputMenuService = new InputMenuService(this);
        for(int i=0;i<mClickedMenuItem.size();i++){
            MenuItem menuItem = mClickedMenuItem.get(i);
            FoodAddRequest foodAddRequest = new FoodAddRequest(menuItem.getFoodName(),
                    menuItem.getCalorie(),menuItem.getCarbohydrate(),menuItem.getProtein(),
                    menuItem.getFat(),mRecordDate,mMealType,menuItem.getServing(),menuItem.getFoodRegion());
            inputMenuService.postAddFood(foodAddRequest);
        }
        Toast.makeText(getApplicationContext(),"음식이 추가 되었습니다",Toast.LENGTH_SHORT).show();
    }

    private void addEditiorActionListener(){
        mEtSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if(actionId == EditorInfo.IME_ACTION_SEARCH){
                    String menu = mEtSearch.getText().toString();
                    Log.d("test", Integer.toString(menu.length()));
                    if(menu.length() == 0){
                        clearMealList();
                        mMenuResultAdapter.notifyDataSetChanged();
                    } else {
                        tryGetFoodList(menu);
                    }
                    return true;
                }
                return false;
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.input_menu_btn_search:
                //검색버튼 누를 때 editText에 있는 검색어로 api연결
                String menu = mEtSearch.getText().toString();
                if(menu.length() == 0) {
                    clearMealList();
                    mMenuResultAdapter.notifyDataSetChanged();
                } else
                    tryGetFoodList(menu);
                break;

            case R.id.input_menu_btn_complete:
                tryAddFood();
                Intent backToMainActivity = new Intent();
                backToMainActivity.putExtra("selectedMenu",mClickedMenuItem);
                backToMainActivity.putExtra("mealTitle",mMealTitle);
                setResult(Activity.RESULT_OK,backToMainActivity);
                finish();
                break;

            default:
                break;
        }
    }

    @Override
    public void onResultItemClicked(Intent intentSending, MenuItem clickedItem) {
        mClickedMenuItem.add(clickedItem);
        startActivityForResult(intentSending,3000);
    }

    @Override
    public void searchFoodListSuccess(int code, String message, ArrayList<FoodResult> result) {
        //성공했을 때만 리스트에 적용(code == 102)
        if(code == 102){
            mMenuItems.clear();
            for(FoodResult item : result){
                MenuItem menuItem = new MenuItem(item);
                mMenuItems.add(menuItem);
            }
            mMenuResultAdapter.notifyDataSetChanged();
        }
        hideProgressDialog();
    }

    public void onSelectedItemDelete(int position){
        mClickedMenuItem.remove(position);
        mSelectedMenuAdapter.changeDataset(mClickedMenuItem);
    }

    @Override
    public void addFoodSuccess(int code, String message) {
        Log.d("jooan", message);
    }

    @Override
    public void validateFailure(String message) {
        showCustomToast(message);
        hideProgressDialog();
    }

    @Override
    public void readMenuSuccess(int code, String message, ArrayList<ReadFoodResult> readFoodResults, int mealType) { }

    @Override
    public void deleteFoodSuccess(int code, String message) { }

}
