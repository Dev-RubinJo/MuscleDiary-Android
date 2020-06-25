package com.munchkin.musclediary.src.main.setting;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.analytics.FirebaseAnalytics;
import com.munchkin.musclediary.R;
import com.munchkin.musclediary.src.BaseActivity;
import com.munchkin.musclediary.src.BaseFragment;
import com.munchkin.musclediary.src.main.MainActivity;
import com.munchkin.musclediary.src.main.MainViewPagerAdapter;
import com.munchkin.musclediary.src.main.setting.dialog.ActivityLevelActivity;
import com.munchkin.musclediary.src.main.setting.dialog.AgeActivity;
import com.munchkin.musclediary.src.main.setting.dialog.GenderActivity;
import com.munchkin.musclediary.src.main.setting.dialog.HeightActivity;
import com.munchkin.musclediary.src.main.setting.dialog.KcalGoalActivity;
import com.munchkin.musclediary.src.main.setting.dialog.RatioGoalActivity;
import com.munchkin.musclediary.src.main.setting.dialog.WeeklyWeightGoalActivity;
import com.munchkin.musclediary.src.main.setting.dialog.WeightActivity;
import com.munchkin.musclediary.src.main.setting.dialog.WeightGoalActivity;
import com.munchkin.musclediary.src.main.setting.interfaces.SettingFragmentView;
import com.munchkin.musclediary.src.main.setting.models.GetGoalWeightResponse;
import com.munchkin.musclediary.src.main.setting.models.GetNutritionResponse;
import com.munchkin.musclediary.src.main.setting.models.ProfileResult;
import com.munchkin.musclediary.src.main.setting.services.SettingService;
import com.munchkin.musclediary.src.signin.SignInActivity;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import static android.app.Activity.RESULT_OK;
import static com.munchkin.musclediary.src.ApplicationClass.X_ACCESS_TOKEN;
import static com.munchkin.musclediary.src.ApplicationClass.sSharedPreferences;


public class SettingFragment extends BaseFragment implements View.OnClickListener, SettingFragmentView {

    //startActivityForResult requestCode
    private final int ADD_MEAL = 0;
    private final int CHANGE_GENDER = 1;
    private final int CHANGE_AGE = 2;
    private final int CHANGE_HEIGHT = 3;
    private final int CHANGE_WEIGHT = 4;
    private final int LOGOUT = 5;
    private final int DELETE_MEAL = 6;
    private final int CHANGE_RATIO = 7;
    private final int CHANGE_KCAL = 8;
    private final int CHANGE_WEIGHT_GOAL = 9;
    private final int CHANGE_ACTIVITY_LEVEL = 10;
    private final int CHANGE_GOAL_WEIGHT = 11;

    //프로필 변경 버튼
    private Button mBtGender;
    private Button mBtAge;
    private Button mBtHeight;
    private Button mBtWeight;
    private Button mBtLogout;
    private Button mBtActivity;
    private Button mBtWeekWeight;

    //주간 체중목표버튼
    private Button mBtWeightGoal;
    private TextView mTvStartWeight;
    private TextView mTvNowWeight;

    //영양목표 변경 버튼
    private Button mBtRatio;
    private Button mBtKcal;

    //끼니 리스트 어뎁터, 아이템
    private SettingAdapter mSettintAdapter;
    private ArrayList<SettingItem> mItems;

    //임시 프로필 저장 변수
    private double mGoalWeight = 0.0;
    private double mStartWeight = 0.0;
    private double mNowWeight = 0.0;
    private double mHeight = 175.2;
    private double mWeight = 65.1;
    private String mBirth = "1996-06-03";
    private int mYear = 0;
    private int mMonth = 0;
    private int mDay = 0;
    private int mGender = 1;
    private int mWeightGoal = 3;
    private int mActivityLevel = 3;
    SharedPreferences.Editor editor = sSharedPreferences.edit();

    //목표영양 비율 임시 리스트
    private int[] mRatio = {50,30,20};

    //목표영양 임시 칼로리
    private int mKcal = 2024;

    FirebaseAnalytics mFirebaseAnalytics;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        mFirebaseAnalytics = FirebaseAnalytics.getInstance(context);
    }

    //현재년도를 받아오기
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy");
    //현재 날짜 가져오기
    private Date currentTime = Calendar.getInstance().getTime();
    String mCurrentYear = DATE_FORMAT.format(currentTime);

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public void onStart() {

        tryGetProfile();
        tryGetNutrition();
        switch (sSharedPreferences.getInt("goalWeightState",3)){
            case 1:
                mBtWeightGoal.setText("주간목표 - 주당 0.5kg 감량");
                break;
            case 2:
                mBtWeightGoal.setText("주간목표 - 주당 0.2kg 감량");
                break;
            case 3:
                mBtWeightGoal.setText("주간목표 - 주당 체중 유지");
                break;
            case 4:
                mBtWeightGoal.setText("주간목표 - 주당 0.2kg 증량");
                break;
            case 5:
                mBtWeightGoal.setText("주간목표 - 주당 0.5kg 증량");
                break;
        }

        tryGetGoalWeight();
        super.onStart();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        final View view = inflater.inflate(R.layout.fragment_setting, container, false);
        setComponentView(view);
        return view;
    }

    // 생성될 세팅 프레그먼트에 대한 컴포넌트 세팅
    @Override
    public void setComponentView(View v) {

        //성별 변경 버튼
        mBtGender = v.findViewById(R.id.bt_gender_setting);
        mBtGender.setOnClickListener(this);

        //생년월일 변경 버튼
        mBtAge = v.findViewById(R.id.bt_age_setting);
        mBtAge.setOnClickListener(this);

        //키 변경 버튼
        mBtHeight = v.findViewById(R.id.bt_height_setting);
        mBtHeight.setOnClickListener(this);

        //몸무게 변경 버튼
        mBtWeight = v.findViewById(R.id.bt_weight_setting);
        mBtWeight.setOnClickListener(this);

        //운동량 및 활동량 변경 버튼
        mBtActivity = v.findViewById(R.id.bt_activity_level_setting);
        mBtActivity.setOnClickListener(this);

        //주간 목표 체중 버튼
        mBtWeightGoal = v.findViewById(R.id.bt_week_weight);
        mBtWeightGoal.setOnClickListener(this);

        //목표 체중 버튼
        mBtWeekWeight = v.findViewById(R.id.bt_goal_weight);
        mBtWeekWeight.setOnClickListener(this);

        //시작체중, 현재 체중 TextView생성
        mTvNowWeight = v.findViewById(R.id.tv_now_weight);
        mTvStartWeight = v.findViewById(R.id.tv_start_weight);

        //영양목표 비율 버튼
        mBtRatio = v.findViewById(R.id.bt_ntRatio_setting);
        mBtRatio.setOnClickListener(this);
        //changeRatio();

        //영양목표 칼로리 버튼
        mBtKcal = v.findViewById(R.id.bt_kcal_setting);
        mBtKcal.setOnClickListener(this);
        //mKcal = sSharedPreferences.getInt("kcal", 0);
        //mBtKcal.setText(mKcal + "kcal");

        //로그아웃 버튼
        mBtLogout = v.findViewById(R.id.bt_logout);
        mBtLogout.setOnClickListener(this);

        //끼니 추가 버튼
        Button btAdd = v.findViewById(R.id.bt_add_setting);
        btAdd.setOnClickListener(this);

        //끼니 삭제 버튼
        Button btDelete = v.findViewById(R.id.bt_delete_setting);
        btDelete.setOnClickListener(this);

        //더미데이터 넣는 함수 실행
        addRecyclerList();

        //리사이클러뷰 생성, 레이아웃 매니저 적용
        RecyclerView chartRecyclerView = v.findViewById(R.id.recycler_setting);
        chartRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        //리사이클러뷰 어뎁터 생성, 적용
        mSettintAdapter = new SettingAdapter(getContext(), mItems);
        chartRecyclerView.setAdapter(mSettintAdapter);
    }



    private void setGoalWeight(double startWeight, double nowWeight, double goalWeight){
        if(startWeight >= 0.0){
            mTvStartWeight.setText(String.format("시작체중 : %.1fkg",startWeight));
        }
        if(nowWeight >= 0.0){
            mTvNowWeight.setText(String.format("현재체중 : %.1fkg", nowWeight));
        }
        mBtWeekWeight.setText(String.format("목표체중 : %.1fkg", goalWeight));
        mGoalWeight = goalWeight;
    }

    private void setProfile(ProfileResult profile){
        mHeight = profile.getHeight();
        mWeight = profile.getWeight();

        /*NOTE 나중에 회원가입 때 받아서 입력 되면 수정
        Date birth = profile.getBirth();
        SimpleDateFormat dateToStringForm = new SimpleDateFormat("yyyy-MM-dd");
        String dateToString = dateToStringForm.format(birth); */
        mGender = profile.getGender();

        mBtHeight.setText(String.format("%.1fcm", mHeight));
        mBtWeight.setText(String.format("%.1fkg", mWeight));

        mYear = Integer.parseInt(profile.getBirth().substring(0,4));
        mMonth = Integer.parseInt(profile.getBirth().substring(5,7));
        mDay = Integer.parseInt(profile.getBirth().substring(8,10));

        mBtAge.setText(String.format("%d년 %d월 %d일",mYear,mMonth,mDay));
        mBtGender.setText(mGender == 1 ? "남성" : "여성");
        editor.putLong("height",Double.doubleToRawLongBits(mHeight));
        editor.putLong("weight",Double.doubleToRawLongBits(mWeight));
        editor.putString("birth",profile.getBirth());
        editor.putInt("gender",mGender);
        editor.apply();
    }

    private void setGoalNutrition(int kcal, int carboRate, int proteinRate, int fatRate){
        mKcal = kcal;
        mBtKcal.setText(String.format("일일목표섭취칼로리\n%dkcal", kcal));

        mRatio[0] = carboRate;
        mRatio[1] = proteinRate;
        mRatio[2] = fatRate;
        mBtRatio.setText(String.format("목표영양비율 (탄:단:지)\n%d : %d : %d", carboRate, proteinRate, fatRate));
    }

    //한번에 실행시키기 위한 함수
    private void updateProfile(){
//        Double newHeight = Double.valueOf(sSharedPreferences.getLong("height",0));
//        Double newWeight = Double.valueOf(sSharedPreferences.getLong("weight",0));
//        Date newBirth = Date.valueOf(sSharedPreferences.getString("birth","1997-12-10"));
//        int newGender = sSharedPreferences.getInt("gender",1);
        tryPostUpdateProfile(mHeight,mWeight,mGender, mBirth);
    }

    //리사이클러뷰 리스트 아이템 채우는 함수
    private void addRecyclerList(){
        //더미데이터들
        String[] mealList = {"아침", "점심", "저녁"};
        mItems = new ArrayList<>();

        for (String meal : mealList) {
            SettingItem item = new SettingItem();
            item.setMeal(meal);
            mItems.add(item);
        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        SharedPreferences.Editor editor = sSharedPreferences.edit();
        if(resultCode != RESULT_OK){
            return;
        }
        switch(requestCode){
            case ADD_MEAL:
                //추가했을 때 리스트에 추가하는 코드
                if(data.getStringExtra("input") != null){
                    SettingItem item = new SettingItem();
                    item.setMeal(data.getStringExtra("input"));
                    mItems.add(item);
                    mSettintAdapter.notifyDataSetChanged();
                }
                break;

            case DELETE_MEAL:
                //삭제했을 때 리스트에 삭제하는 코드 필요
                break;

            case CHANGE_GENDER:
                //성별 변경했을 때 코드
                if(data.getIntExtra("gender", 0) != 0){
                    mGender = data.getIntExtra("gender", 0);
                    editor.putInt("gender",mGender);//만약 apply가 늦어서 서버에 입력이 기존 값으로 되면 commit으로 바꿀 것
                    editor.apply();

                    if(mGender == 1){
                        mBtGender.setText("남성");
                    } else if(mGender == 2){
                        mBtGender.setText("여성");
                    }

                    updateProfile();
                }
                break;

            case CHANGE_AGE:
                //생년월일 변경했을 때 코드
                String newBirth = data.getStringExtra("age");
                mYear = data.getIntExtra("ageYear",1997);
                mMonth = data.getIntExtra("ageMonth",12);
                mDay = data.getIntExtra("ageDay",10);
                mBirth = newBirth;
                mBtAge.setText(String.format("%d년 %d월 %d일",mYear,mMonth,mDay));
                editor.putString("birth",newBirth);

                updateProfile();
                break;

            case CHANGE_HEIGHT:
                //키 변경했을 때 코드
                mHeight = data.getDoubleExtra("height", 0);
                editor.putLong("height",Double.doubleToRawLongBits(mHeight));
                editor.apply(); //만약 apply가 늦어서 서버에 입력이 기존 값으로 되면 commit으로 바꿀 것

                mBtHeight.setText(String.format("%.1fcm", mHeight));
                updateProfile();
                break;


            case CHANGE_WEIGHT:
                //몸무게 변경했을 때 코드
                mWeight = data.getDoubleExtra("weight", 0);
                editor.putLong("weight",Double.doubleToRawLongBits(mWeight));
                editor.apply(); //만약 apply가 늦어서 서버에 입력이 기존 값으로 되면 commit으로 바꿀 것

                mBtWeight.setText(String.format("%.1fkg", mWeight));
                updateProfile();
                break;


                //주간 체중목표 변경시 코드
            case CHANGE_WEIGHT_GOAL:
                if(data.getIntExtra("weight_goal", 0) != 0){
                    mWeightGoal = data.getIntExtra("weight_goal", 0);
                    if(mWeightGoal == 1){
                        mBtWeightGoal.setText("주간목표 - 주당 0.5kg 감량");
                        editor.putInt("goalWeightState",1);
                        setGoalNutrition(sSharedPreferences.getInt("goalWeightState1Done",mKcal),mRatio[0],mRatio[1],mRatio[2]);
                        tryPostNutrition(mRatio[0],mRatio[1],mRatio[2],sSharedPreferences.getInt("goalWeightState1Done",mKcal));
                    }else if(mWeightGoal == 2){
                        mBtWeightGoal.setText("주간목표 - 주당 0.2kg 감량");
                        editor.putInt("goalWeightState",2);
                        setGoalNutrition(sSharedPreferences.getInt("goalWeightState2Done",mKcal),mRatio[0],mRatio[1],mRatio[2]);
                        tryPostNutrition(mRatio[0],mRatio[1],mRatio[2],sSharedPreferences.getInt("goalWeightState2Done",mKcal));
                    }else if(mWeightGoal == 3){
                        mBtWeightGoal.setText("주간목표 - 주당 체중 유지");
                        editor.putInt("goalWeightState",3);
                        setGoalNutrition(sSharedPreferences.getInt("goalWeightState3Done",mKcal),mRatio[0],mRatio[1],mRatio[2]);
                        tryPostNutrition(mRatio[0],mRatio[1],mRatio[2],sSharedPreferences.getInt("goalWeightState3Done",mKcal));
                    }else if(mWeightGoal == 4){
                        mBtWeightGoal.setText("주간목표 - 주당 0.2kg 증량");
                        editor.putInt("goalWeightState",4);
                        setGoalNutrition(sSharedPreferences.getInt("goalWeightState4Done",mKcal),mRatio[0],mRatio[1],mRatio[2]);
                        tryPostNutrition(mRatio[0],mRatio[1],mRatio[2],sSharedPreferences.getInt("goalWeightState4Done",mKcal));
                    }else if(mWeightGoal == 5){
                        mBtWeightGoal.setText("주간목표 - 주당 0.5kg 증량");
                        editor.putInt("goalWeightState",5);
                        setGoalNutrition(sSharedPreferences.getInt("goalWeightState5Done",mKcal),mRatio[0],mRatio[1],mRatio[2]);
                        tryPostNutrition(mRatio[0],mRatio[1],mRatio[2],sSharedPreferences.getInt("goalWeightState5Done",mKcal));
                    }
                    editor.apply();
                }
                break;

            case CHANGE_GOAL_WEIGHT:
                double goalWeight = data.getDoubleExtra("goalWeight", 0);
                tryPostGoalWeight(goalWeight);
                break;

            case CHANGE_ACTIVITY_LEVEL:
                if(data.getIntExtra("activity_level", 0) != 0){
                    mActivityLevel = data.getIntExtra("activity_level", 0);
                    switch (mActivityLevel) {
                        case 1:
                            mBtActivity.setText("사무직(아주 약간의 운동)");
                            break;
                        case 2:
                            mBtActivity.setText("가벼운 활동성(주당 1-3일 가벼운 운동)");
                            break;
                        case 3:
                            mBtActivity.setText("평범한 활동성(주당 3-5일 운동)");
                            break;
                        case 4:
                            mBtActivity.setText("높은 활동성(주당 6-7일 고강도 운동)");
                            break;
                        case 5:
                            mBtActivity.setText("매우 높은 활동성(초고강도의 운동, 고강도 노동)");
                            break;

                    }

                }
                break;

            case CHANGE_RATIO:
                //영양목표 변경했을 때
                mRatio[0] = data.getIntExtra("carbohydrate", mRatio[0]);
                mRatio[1] = data.getIntExtra("protein", mRatio[1]);
                mRatio[2] = data.getIntExtra("fat", mRatio[2]);
                /*
                editor.putInt("carbohydrate", mRatio[0]);
                editor.putInt("protein", mRatio[1]);
                editor.putInt("fat", mRatio[2]);
                editor.apply();
                 */
                tryPostNutrition(mRatio[0], mRatio[1], mRatio[2], mKcal);
                break;

            case CHANGE_KCAL:
                mKcal = data.getIntExtra("kcal", mKcal);
                editor.putInt("kcal", mKcal);
                editor.apply();
                tryPostNutrition(mRatio[0], mRatio[1], mRatio[2], mKcal);
                break;

            //로그아웃했을때
            case LOGOUT:
                mBtLogout.setText(data.getStringExtra("logout"));
                break;

            default:
                return;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            //끼니 추가 버튼 클릭이벤트
            case R.id.bt_add_setting:
                Intent inputIntent = new Intent(getActivity(), InputSettingActivity.class);
                startActivityForResult(inputIntent, ADD_MEAL);
                break;

                //끼니 삭제 버튼
            case R.id.bt_delete_setting:
                Intent deleteIntent = new Intent(getActivity(), DeleteSettingActivity.class);
                startActivityForResult(deleteIntent, DELETE_MEAL);
                break;

            //성별 변경 버튼 클릭이벤트
            case R.id.bt_gender_setting:
                Intent genderIntent = new Intent(getActivity(), GenderActivity.class);
                genderIntent.putExtra("gender", mGender);
                startActivityForResult(genderIntent, CHANGE_GENDER);
                break;

            //생일 변경 버튼 클릭이벤트
            case R.id.bt_age_setting:
                Intent ageIntent = new Intent(getActivity(), AgeActivity.class);
                ageIntent.putExtra("year", mYear);
                ageIntent.putExtra("month", mMonth);
                ageIntent.putExtra("date", mDay);
                startActivityForResult(ageIntent, CHANGE_AGE);
                break;

            //키 변경 버튼 클릭이벤트
            case R.id.bt_height_setting:
                Intent heightIntent = new Intent(getActivity(), HeightActivity.class);
                mBtHeight.getText();
                String height = mBtHeight.getText().toString();
                if(height.length() == 7){
                    //100cm이상일때
                    int heightInteger = Integer.parseInt(height.substring(0,3));
                    int heightDot = Integer.parseInt(height.substring(4,5));
                    heightIntent.putExtra("integer", heightInteger);
                    heightIntent.putExtra("dot", heightDot);
                } else if(height.length() == 6){
                    //10cm이상 100cm미만일때
                    int heightInteger = Integer.parseInt(height.substring(0,2));
                    int heightDot = Integer.parseInt(height.substring(3,4));
                    heightIntent.putExtra("integer", heightInteger);
                    heightIntent.putExtra("dot", heightDot);
                } else if(height.length() == 5){
                    //10cm미만일때
                    int heightInteger = Integer.parseInt(height.substring(0,1));
                    int heightDot = Integer.parseInt(height.substring(2,3));
                    heightIntent.putExtra("integer", heightInteger);
                    heightIntent.putExtra("dot", heightDot);
                }
                heightIntent.putExtra("height", mHeight);
                startActivityForResult(heightIntent, CHANGE_HEIGHT);
                break;

            case R.id.bt_weight_setting:
                Intent weightIntent = new Intent(getActivity(), WeightActivity.class);
                String weight = mBtWeight.getText().toString();
                if(weight.length() == 7){
                    //100kg이상일때
                    int heightInteger = Integer.parseInt(weight.substring(0,3));
                    int heightDot = Integer.parseInt(weight.substring(4,5));
                    weightIntent.putExtra("integer", heightInteger);
                    weightIntent.putExtra("dot", heightDot);
                } else if(weight.length() == 6){
                    //10kg이상 100kg 미만일때
                    int heightInteger = Integer.parseInt(weight.substring(0,2));
                    int heightDot = Integer.parseInt(weight.substring(3,4));
                    weightIntent.putExtra("integer", heightInteger);
                    weightIntent.putExtra("dot", heightDot);
                } else if(weight.length() == 5){
                    //10kg미만일때
                    int heightInteger = Integer.parseInt(weight.substring(0,1));
                    int heightDot = Integer.parseInt(weight.substring(2,3));
                    weightIntent.putExtra("integer", heightInteger);
                    weightIntent.putExtra("dot", heightDot);
                }
                weightIntent.putExtra("weight", mWeight);
                startActivityForResult(weightIntent, CHANGE_WEIGHT);
                break;

            case R.id.bt_goal_weight:
                Intent goalWeightIntent = new Intent(getActivity(), WeightGoalActivity.class);
                goalWeightIntent.putExtra("goalWeight", mGoalWeight);
                startActivityForResult(goalWeightIntent, CHANGE_GOAL_WEIGHT);
                break;

            case R.id.bt_week_weight:
                Intent weightgoalIntent = new Intent(getActivity(), WeeklyWeightGoalActivity.class);
                weightgoalIntent.putExtra("weight_goal", mWeightGoal);
                startActivityForResult(weightgoalIntent, CHANGE_WEIGHT_GOAL);
                break;

            case R.id.bt_activity_level_setting:
                Intent activitylevelIntent = new Intent(getActivity(), ActivityLevelActivity.class);
                activitylevelIntent.putExtra("activity_level", mActivityLevel);
                startActivityForResult(activitylevelIntent, CHANGE_ACTIVITY_LEVEL);
                break;

            case R.id.bt_ntRatio_setting:
                //영양목표 비율 번경 이벤트
                Intent ratioIntent = new Intent(getActivity(), RatioGoalActivity.class);
                ratioIntent.putExtra("kcal", mKcal);
                ratioIntent.putExtra("carbohydrate", mRatio[0]);
                ratioIntent.putExtra("protein", mRatio[1]);
                ratioIntent.putExtra("fat", mRatio[2]);
                startActivityForResult(ratioIntent, CHANGE_RATIO);
                break;

            case R.id.bt_kcal_setting:
                Intent kcalIntent = new Intent(getActivity(), KcalGoalActivity.class);
                kcalIntent.putExtra("kcal", mKcal);
                startActivityForResult(kcalIntent, CHANGE_KCAL);
                break;

            case R.id.bt_logout:
                X_ACCESS_TOKEN = "X_ACCESS_TOKEN";
                editor.clear();
                editor.commit();
                Intent i = new Intent( getContext() , SignInActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(i);
                //Intent logoutIntent = new Intent(getActivity(), SignInActivity.class);
                //startActivityForResult(logoutIntent, LOGOUT);
                //Intent signInIntent = new Intent(getActivity(), SignInActivity.class);
                //startActivity(signInIntent);

                break;

            default:
                break;
        }
    }

    private void tryPostUpdateProfile(Double height, Double weight, int gender, String birth){
        showProgressDialog(getActivity());
        SettingService settingService = new SettingService(this);
        settingService.postUpdateProfile(height, weight, gender, birth);
    }

    private void tryGetProfile(){
        showProgressDialog(getActivity());
        SettingService settingService = new SettingService(this);
        settingService.getProfile();
    }

    private void tryGetNutrition(){
        showProgressDialog(getActivity());
        final SettingService settingService = new SettingService(this);
        settingService.getNutrition();
    }

    private void tryPostNutrition(int carboRate, int proteinRate, int fatRate, int goalCalorie){
        showProgressDialog(getActivity());
        final SettingService settingService = new SettingService(this);
        settingService.postNutrition(carboRate, proteinRate, fatRate, goalCalorie);
    }

    private void tryPostGoalWeight(Double weight){
        showProgressDialog(getActivity());
        final SettingService settingService = new SettingService(this);
        settingService.postGoalWeight(weight);
    }

    private void tryGetGoalWeight(){
        showProgressDialog(getActivity());
        final SettingService settingService = new SettingService(this);
        settingService.getGoalWeight();
    }

    @Override
    public void updateProfileSuccess(int code, String message) {
        showCustomToast("프로필 변경에 성공했습니다.");
        updateStandardCalorie();
        hideProgressDialog();
    }

    private void updateStandardCalorie() {
        int calories;
        int birthYear = Integer.parseInt(mBirth.substring(0,4));
        int age = Integer.parseInt(mCurrentYear) - birthYear;
        int kcal=mKcal;

        if(mGender == 1){
            calories = (int)((66 + (13.7 * mWeight) + (5 * mHeight) - (6.8 * age))*1.55);
            editor.putInt("standardCalorie",calories);
        }else{
            calories = (int)((655 + (9.6 * mWeight) + (1.8 * mHeight) - (4.7 * age))*1.55);
            editor.putInt("standardCalorie",calories);
        }

        editor.putInt("goalWeightState1Done",(int)(calories*0.8));
        editor.putInt("goalWeightState2Done",(int)(calories*0.9));
        editor.putInt("goalWeightState3Done",calories);
        editor.putInt("goalWeightState4Done",(int)(calories*1.1));
        editor.putInt("goalWeightState5Done",(int)(calories*1.2));
        editor.apply();

        switch (sSharedPreferences.getInt("goalWeightState",3)){
            case 1:
                kcal = sSharedPreferences.getInt("goalWeightState1Done",mKcal);
                break;
            case 2:
                kcal = sSharedPreferences.getInt("goalWeightState2Done",mKcal);
                break;
            case 3:
                kcal = sSharedPreferences.getInt("goalWeightState3Done",mKcal);
                break;
            case 4:
                kcal = sSharedPreferences.getInt("goalWeightState4Done",mKcal);
                break;
            case 5:
                kcal = sSharedPreferences.getInt("goalWeightState5Done",mKcal);
                break;
        }
        mKcal = kcal;
        tryPostNutrition(mRatio[0],mRatio[1],mRatio[2],kcal);
    }

    @Override
    public void profileSuccess(int code, String message, ArrayList<ProfileResult> profileResult) {
        //showCustomToast("프로필 불러오기에 성공했습니다.");
        setProfile(profileResult.get(0));
        hideProgressDialog();
    }

    @Override
    public void postNutritionSuccess(int code, String message) {
        hideProgressDialog();
        if(code == 104){
            setGoalNutrition(mKcal, mRatio[0], mRatio[1], mRatio[2]);
        }
    }

    @Override
    public void getNutritionSuccess(int code, String message, GetNutritionResponse.NutritionResult result) {
        if(code == 102){
            setGoalNutrition(result.getGoalCalorie(), result.getCarboRate(), result.getProteinRate(), result.getFatRate());
        } else {
            setGoalNutrition(0, 0, 0, 0);
        }
        hideProgressDialog();
    }

    @Override
    public void getGoalWeightSuccess(int code, String message, GetGoalWeightResponse.Result result) {
        setGoalWeight(result.getStartWeight(), result.getCurrentWeight(), result.getGoalWeight());
        //showCustomToast("체중목표 성공");
        hideProgressDialog();
    }

    @Override
    public void postGoalWeightSuccess(int code, String message, double goalWeight) {
        setGoalWeight(-1.0, -1.0, goalWeight);
        hideProgressDialog();
    }

    @Override
    public void getGoalWeightFailure(String message) {
        showCustomToast(message);
        setGoalNutrition(0, 0, 0, 0);
        hideProgressDialog();
    }

    @Override
    public void validateFailure(String message) {
        showCustomToast("프로필 변경에 실패했습니다.");
        hideProgressDialog();
    }
}
