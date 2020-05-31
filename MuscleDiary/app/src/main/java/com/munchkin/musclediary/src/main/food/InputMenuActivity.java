package com.munchkin.musclediary.src.main.food;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.munchkin.musclediary.R;
import com.munchkin.musclediary.src.BaseActivity;
import com.munchkin.musclediary.src.main.food.adapter.MenuResultAdapter;
import com.munchkin.musclediary.src.main.food.interfaces.InputMenuActivityView;
import com.munchkin.musclediary.src.main.food.interfaces.ResultItemClickListener;
import com.munchkin.musclediary.src.main.food.models.FoodResult;
import com.munchkin.musclediary.src.main.food.models.MenuItem;
import com.munchkin.musclediary.src.main.food.services.InputMenuService;

import java.util.ArrayList;

public class InputMenuActivity extends BaseActivity implements InputMenuActivityView, View.OnClickListener, ResultItemClickListener {

    String mMealTitle;
    TextView mTvMealTitle;
    RecyclerView mMenuResultRecyclerView;
    MenuResultAdapter mMenuResultAdapter;
    ResultItemClickListener mResultItemClickListener = new ResultItemClickListener() {
        @Override
        public void onResultItemClicked(Intent intentSending) {
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

        //레이아웃 컴포넌트 설정
        mTvMealTitle = findViewById(R.id.input_menu_tv_meal_title);
        mMenuResultRecyclerView = findViewById(R.id.input_menu_rv_menu_result);

        //제목을 선택한 끼니로 바꿔줌
        mTvMealTitle.setText(mMealTitle);

        //더미데이터 생성
        addMealList();

        //리사이클러뷰 레이아웃 매니저 적용
        mMenuResultRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        mMenuResultAdapter = new MenuResultAdapter(getApplicationContext(),mMenuItems,mResultItemClickListener);
        mMenuResultRecyclerView.setAdapter(mMenuResultAdapter);

        //검색버튼 생성, 클릭이벤트 적용
        ImageButton btSearch = findViewById(R.id.input_menu_btn_search);
        btSearch.setOnClickListener(this);
    }

    private void addMealList(){
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
                Log.d("jooan","serving : "+serving);

                //취소
                if(serving != -1.0){
                    Log.d("jooan", "입력헸음");
                    return;
                }else{
                    Log.d("jooan", "취소헸음");
                    //추가한 음식에 추가
                }
            }
        }
    }

    private void tryGetFoodList(String keyword){
        showProgressDialog();
        InputMenuService inputMenuService = new InputMenuService(this);
        inputMenuService.getFoodList(keyword);
    }

    @Override
    public void validateSuccess(int code, String message, ArrayList<FoodResult> result) {
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

    @Override
    public void validateFailure(String message) {
        showCustomToast(message);
        hideProgressDialog();
    }

    private void addEditiorActionListener(){
        mEtSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if(actionId == EditorInfo.IME_ACTION_SEARCH){
                    String menu = mEtSearch.getText().toString();
                    Log.d("test", Integer.toString(menu.length()));
                    if(menu.length() == 0){
                        addMealList();
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
                    addMealList();
                    mMenuResultAdapter.notifyDataSetChanged();
                } else
                    tryGetFoodList(menu);
                break;

            default:
                break;
        }
    }

    @Override
    public void onResultItemClicked(Intent intentSending) {
        startActivityForResult(intentSending,3000);
    }
}
