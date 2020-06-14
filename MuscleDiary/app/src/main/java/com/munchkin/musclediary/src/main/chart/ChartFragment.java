package com.munchkin.musclediary.src.main.chart;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.github.mikephil.charting.charts.Chart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.interfaces.dataprovider.ChartInterface;
import com.munchkin.musclediary.R;
import com.munchkin.musclediary.src.BaseFragment;
import com.munchkin.musclediary.src.main.chart.dialog.TermActivity;
import com.munchkin.musclediary.src.main.chart.dialog.TypeActivity;
import com.munchkin.musclediary.src.main.chart.interfaces.ChartFragmentView;
import com.munchkin.musclediary.src.main.chart.models.WeightResult;
import com.munchkin.musclediary.src.main.chart.services.ChartService;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import static android.app.Activity.RESULT_OK;
import static android.content.Context.POWER_SERVICE;

public class ChartFragment extends BaseFragment implements View.OnClickListener, ChartFragmentView {
    //startActivityForResult requestCode
    private final int CHANGE_TYPE = 0;
    private final int CHANGE_TERM = 1;
    private final int ADD_CHART = 2;

    private LineChart mLineChart;
//    private LineChart lineChart;
    //리사이클러뷰 아이템 리스트
    private ArrayList<ChartItem> mWeightItems;
    private ArrayList<ChartItem> mFatItems;
    private ImageButton mBtnAddData;

    //그래프 생성
    List<Entry> mWeightList;
    List<Entry> mFatList;

    //분석종류
    //1 = 체지방, 2 = 체중
    private int mType = 2;
    //0 = 주간, 1 = 월간, 3 = 년간
    private int mTerm = 0;
    private ChartAdapter mAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        final View view = inflater.inflate(R.layout.fragment_chart, container, false);
        setComponentView(view);
        return view;
    }
    // 생성될 차트 프레그먼트에 대한 컴포넌트 세팅
    @Override
    public void setComponentView(View v) {
        mLineChart = (LineChart)v.findViewById(R.id.line_chart);
        mBtnAddData =  v.findViewById(R.id.bt_add_list_chart);

        //리스트 추가 버튼 리스너 등록
        mBtnAddData.setOnClickListener(this);

        //분석종류 변경 버튼 생성 및 리스너 적용
        Button btType = v.findViewById(R.id.bt_type_chart);
        btType.setOnClickListener(this);

        //기간설정 버튼 생성 및 리스너 적용용
        Button btTerm = v.findViewById(R.id.bt_term_chart);
        btTerm.setOnClickListener(this);

        //더미데이터 넣는 함수 실행
        addWeightRecyclerList();
        addFatRecyclerList();

        //리사이클러뷰 생성, 레이아웃 매니저 적용
        RecyclerView chartRecyclerView = v.findViewById(R.id.recycler_chart);
        chartRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        //리사이클러뷰 어뎁터 생성, 적용
        mAdapter = new ChartAdapter(getContext(), mWeightItems);
        chartRecyclerView.setAdapter(mAdapter);

        //예시용 그래프 라이브러리
//        mLineChart = (LineChart)view.findViewById(R.id.line_chart);

        // 맨 아래 주석에 적어둔 대로 메서드 만들어서 리스트 생성하고 리턴받아서 넣는 플로우 생성하기
        mWeightList = new ArrayList<>();
        mFatList = new ArrayList<>();
        addWeightData(0, mWeightItems);
    }

    // 그래프 세팅 메서드
    private void setmLineChart(List<Entry> list, String label, final String[] xLabel) {
        LineDataSet lineDataSet = new LineDataSet(list, label);
        lineDataSet.setLineWidth(2);
        lineDataSet.setCircleRadius(6);
        lineDataSet.setCircleColor(Color.parseColor("#FFA1B4DC"));
        lineDataSet.setColor(Color.parseColor("#FFA1B4DC"));
        lineDataSet.setDrawCircleHole(true);
        lineDataSet.setDrawCircles(true);
        lineDataSet.setDrawHorizontalHighlightIndicator(false);
        lineDataSet.setDrawHighlightIndicators(false);
        lineDataSet.setDrawValues(false);

        LineData lineData = new LineData(lineDataSet);
        mLineChart.setData(lineData);

        ValueFormatter formatter = new ValueFormatter() {
            @Override
            public String getAxisLabel(float value, AxisBase axis) {
                if(value >= 0){
                    return xLabel[(int)value];
                }else {
                    return "";
                }
            }
        };

        XAxis xAxis = mLineChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setTextColor(Color.BLACK);
        xAxis.enableGridDashedLine(8, 24, 0);
        xAxis.removeAllLimitLines();
        xAxis.setGranularity(1f);
        xAxis.setValueFormatter(formatter);

        YAxis yLAxis = mLineChart.getAxisLeft();
        yLAxis.setTextColor(Color.BLACK);

        YAxis yRAxis = mLineChart.getAxisRight();
        yRAxis.setDrawLabels(false);
        yRAxis.setDrawAxisLine(false);
        yRAxis.setDrawGridLines(false);

        mLineChart.getAxisRight().setEnabled(false);
        mLineChart.getAxisLeft().setEnabled(false);

        Description description = new Description();
        description.setText("");

        mLineChart.setDoubleTapToZoomEnabled(false);
        mLineChart.setDrawGridBackground(false);
        mLineChart.setDragEnabled(true);
        mLineChart.setScaleEnabled(true);
        mLineChart.setVisibleXRangeMaximum(5);
        mLineChart.setDragDecelerationEnabled(true);
        mLineChart.setDescription(description);
        //lineChart.animateY(2000, Easing.EasingOption.EaseInCubic);
        mLineChart.invalidate();
    }

    // 분석 종류, 기간에 따라 리스트 만들고 리턴해주기

    //리사이클러뷰 리스트 아이템 채우는 함수

    private void addFatRecyclerList(){
        mFatItems = new ArrayList<>();
        float fatList[] = {17.0f, 17.5f, 17.4f, 17.3f, 17.1f};
        int fatYear[] = {2020,2020,2020,2020,2020};
        int fatMonth[] = {4,4,4,4,4};
        int fatDate[] = {20,21,22,23,24};

        for(int i = 0; i < fatList.length; i++){
            float level = fatList[i];
            int year = fatYear[i];
            int month = fatMonth[i];
            int date = fatDate[i];
            ChartItem item = new ChartItem(level, year, month, date);
            mFatItems.add(item);
        }
    }
    private void addWeightRecyclerList(){
        //더미데이터들
        mWeightItems = new ArrayList<>();
        float levelList[] = {75.5f, 74.5f, 75.7f, 74.9f, 75.1f, 74.5f, 74.5f, 74.5f, 74.5f, 74.5f, 74.5f, 74.5f, 74.5f, 74.5f, 74.5f, 74.5f};
        int yearList[] = {2020,2020,2020,2020,2020, 2020, 2020, 2020, 2020, 2020, 2020, 2020, 2020, 2020, 2020, 2020};
        int monthList[] = {4,4,4,4,4,4,4,4,4,4,4,4,4,4,4, 4};
        int dateList[] = {9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26, 27};

        for(int i = 0; i < levelList.length; i++){
            float level = levelList[i];
            int year = yearList[i];
            int month = monthList[i];
            int date = dateList[i];
            ChartItem item = new ChartItem(level, year, month, date);
            mWeightItems.add(item);
        }
    }

    private void addFatData(ArrayList<ChartItem> items){
        mFatList.clear();
        int count = 0;
        String[] labelList = new String[items.size()+1];
        for(ChartItem item : items){
            Log.d("testLog", count+"");
            labelList[count] = item.getDate()+"";
            mFatList.add(new Entry(count++, item.getLevel()));
        }
        setmLineChart(mFatList, "체지방률 변화", labelList);
    }

    private void addWeightData(int term, ArrayList<ChartItem> items){
        mWeightList.clear();
        int date = 0;
        int count = 0;
        String[] labelList = new String[items.size()+1];
        switch (term){
            case 0:
                count = 0;
                for(ChartItem item : items){
                    labelList[count] = item.getDate()+"";
                    mWeightList.add(new Entry(count++, item.getLevel()));
                }
                setmLineChart(mWeightList, "체중 변화", labelList);
                break;

            case 1:
                count = 0;
                for(int i = 0; i < items.size(); i++){
                    if(i == 0){
                        date = items.get(i).getDate();
                        labelList[count] = date+"";
                        mWeightList.add(new Entry(count++, items.get(i).getLevel()));
                    } else if(date + 7 <= items.get(i).getDate()){
                        date = items.get(i).getDate();
                        labelList[count] = date+"";
                        mWeightList.add(new Entry(count++, items.get(i).getLevel()));
                    }
                }
                setmLineChart(mWeightList, "체중 변화", labelList);
                break;

            case 2:
                date = 0;
                count = 0;
                for(int i = 0; i < items.size(); i++){
                    if(i == 0){
                        date = items.get(i).getDate();
                        labelList[count] = date+"";
                        mWeightList.add(new Entry(count++, items.get(i).getLevel()));
                    } else if(date + 30 <= items.get(i).getDate()){
                        date = items.get(i).getDate();
                        labelList[count] = date+"";
                        mWeightList.add(new Entry(count++, items.get(i).getLevel()));
                    }
                }
                setmLineChart(mWeightList, "체중 변화", labelList);
                break;

            default:
                break;
        }

    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.bt_add_list_chart:
                Intent inputChartIntent = new Intent(getContext(), InputChartActivity.class);
                startActivityForResult(inputChartIntent, ADD_CHART);
                break;

            case R.id.bt_type_chart:
                /*
                Intent typeIntent = new Intent(getContext(), TypeActivity.class);
                typeIntent.putExtra("type", mType);
                startActivityForResult(typeIntent, CHANGE_TYPE);
                 */
                showCustomToast("서비스 준비중입니다.");
                break;

            case R.id.bt_term_chart:
                if(mType == 2){
                    Intent termIntent = new Intent(getContext(), TermActivity.class);
                    startActivityForResult(termIntent, CHANGE_TERM);
                } else {
                    showCustomToast("체지방률은 기간설정을 할 수 없습니다. ");
                }
            default:
                break;
        }
    }

    //팝업창 결과
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(resultCode != RESULT_OK){
            return;
        }
        Calendar calendar = Calendar.getInstance();
        int today = calendar.get(Calendar.DATE);
        switch(requestCode){
            case CHANGE_TYPE:
                mType = data.getIntExtra("type", 0);
                if(mType == 1){
                    //setmLineChart(mFatList, "체지방률 변화");
                    addFatData(mFatItems);
                    mAdapter.setWeight(false);
                    mAdapter.setmItems(mFatItems);

                } else if(mType == 2){
                    addWeightData(0, mWeightItems);
                    mAdapter.setWeight(true);
                    mAdapter.setmItems(mWeightItems);
                }
                break;

            case CHANGE_TERM:
                mTerm = data.getIntExtra("term", 0);
                showCustomToast(Integer.toString(mTerm));
                switch(mTerm){
                    case 0:
                        int reordDate = getRecordDate(today);
                        addWeightData(0, mWeightItems);
                        break;
                    case 1:
                        addWeightData(1, mWeightItems);
                        break;
                    case 2:
                        addWeightData(2, mWeightItems);
                        break;
                    default:
                        break;
                }
                break;

            case ADD_CHART:
                int year = data.getIntExtra("year", 0);
                int month = data.getIntExtra("month", 0);
                int date = data.getIntExtra("date", 0);
                float level = data.getFloatExtra("weight", 0);
                Calendar c = Calendar.getInstance();
                c.set(year, month-1, date);
                ChartItem item = new ChartItem(level, year, month, date);
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                switch (mType){
                    case 1:
                        mFatItems.add(item);
                        mAdapter.notifyDataSetChanged();
                        addFatData(mFatItems);
                        break;
                    case 2:
                        tryPostWeight(level, simpleDateFormat.format(c.getTime()));
                        //tryGetWeight(getRecordDate(today));
                        addWeightData(0, mWeightItems);
                        break;
                    default:
                        break;
                }
                break;

            default:
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private int getRecordDate(int today){
        switch (mTerm){
            case 0:
                if(today > 0 && today < 8){
                    return 1;
                } else if(today >=8 && today < 15){
                    return 8;
                } else if(today >= 15 && today < 22){
                    return 15;
                } else if(today >= 22 && today < 29){
                    return 22;
                }else if(today >= 29){
                    return 29;
                }
                break;
            case 1:
                return 1;
            case 2:
                return 1;
            default:
                break;
        }
        return 0;
    }

    private void tryPostWeight(float weight, String recordDate){
        showProgressDialog(getActivity());
        final ChartService chartService = new ChartService(this);
        chartService.postWeight(weight, recordDate);
    }

    private void tryGetWeight(int date){
        showProgressDialog(getActivity());
        final ChartService chartService = new ChartService(this);
        switch (mTerm){
            case 0:
                chartService.getWeekWeight(date);
                break;
            case 1:
                chartService.getMonthWeight(date);
                break;
            case 2:
                chartService.getYearWeight(date);
                break;
            default:
                break;
        }
    }

    @Override
    public void getWeightSuccess(int code, String message, ArrayList<WeightResult> weightResults) {
        mWeightItems.clear();
        if (weightResults.size() != 0) {
            for(WeightResult item : weightResults) {
                float weight = item.getWeight();
                String recordDate = item.getRecordDate();
                String[] strings = recordDate.split("-");
                int year = Integer.getInteger(strings[0]);
                int month = Integer.getInteger(strings[1]);
                int date = Integer.getInteger(strings[2]);
                ChartItem chartItem = new ChartItem(weight, year, month, date);
                mWeightItems.add(chartItem);
            }
            mAdapter.notifyDataSetChanged();
            addWeightData(mTerm, mWeightItems);
        }

        hideProgressDialog();
    }

    @Override
    public void postWeightSuccess(int code, String message) {
        hideProgressDialog();
    }

    @Override
    public void validateFailure(String message) {
        hideProgressDialog();
        showCustomToast("네트워크와의 연결이 불안정합니다.");
    }
}
