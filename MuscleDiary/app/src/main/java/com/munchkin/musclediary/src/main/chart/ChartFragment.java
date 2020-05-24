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

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.munchkin.musclediary.R;
import com.munchkin.musclediary.src.BaseFragment;
import com.munchkin.musclediary.src.main.chart.dialog.TermActivity;
import com.munchkin.musclediary.src.main.chart.dialog.TypeActivity;

import java.util.ArrayList;
import java.util.List;

import static android.app.Activity.RESULT_OK;
import static android.content.Context.POWER_SERVICE;

public class ChartFragment extends BaseFragment implements View.OnClickListener {
    //startActivityForResult requestCode
    private final int CHANGE_TYPE = 0;

    private LineChart mLineChart;
//    private LineChart lineChart;
    //리사이클러뷰 아이템 리스트
    private ArrayList<ChartItem> items;
    private ImageButton mBtnAddData;

    //그래프 생성
    List<Entry> mEntries;

    //분석종류 1 = 체지방, 2 = 체중
    private int mType = 1;

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
        addRecyclerList();

        //리사이클러뷰 생성, 레이아웃 매니저 적용
        RecyclerView chartRecyclerView = v.findViewById(R.id.recycler_chart);
        chartRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        //리사이클러뷰 어뎁터 생성, 적용
        ChartAdapter adapter = new ChartAdapter(getContext(), items);
        chartRecyclerView.setAdapter(adapter);

        //예시용 그래프 라이브러리
//        mLineChart = (LineChart)view.findViewById(R.id.line_chart);

        // 맨 아래 주석에 적어둔 대로 메서드 만들어서 리스트 생성하고 리턴받아서 넣는 플로우 생성하기
        mEntries = new ArrayList<>();
        mEntries.add(new Entry(1, 1));
        mEntries.add(new Entry(2, 2));
        mEntries.add(new Entry(3, 0));
        mEntries.add(new Entry(4, 4));
        mEntries.add(new Entry(5, 3));

        setmLineChart(mEntries, "체지방률 변화");
    }

    // 그래프 세팅 메서드
    private void setmLineChart(List<Entry> list, String label) {
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

        XAxis xAxis = mLineChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setTextColor(Color.BLACK);
        xAxis.enableGridDashedLine(8, 24, 0);

        YAxis yLAxis = mLineChart.getAxisLeft();
        yLAxis.setTextColor(Color.BLACK);

        YAxis yRAxis = mLineChart.getAxisRight();
        yRAxis.setDrawLabels(false);
        yRAxis.setDrawAxisLine(false);
        yRAxis.setDrawGridLines(false);

        Description description = new Description();
        description.setText("");

        mLineChart.setDoubleTapToZoomEnabled(false);
        mLineChart.setDrawGridBackground(false);
        mLineChart.setDescription(description);
        //lineChart.animateY(2000, Easing.EasingOption.EaseInCubic);
        mLineChart.invalidate();
    }

    // 분석 종류, 기간에 따라 리스트 만들고 리턴해주기

    //리사이클러뷰 리스트 아이템 채우는 함수
    private void addRecyclerList(){
        //더미데이터들
        String levelList[] = {"75.5Kg", "74.5Kg","75.7Kg","74.9Kg","75.1Kg","74.5Kg"};
        String dateList[] = {"2020년 04월 9일", "2020년 04월 10일","2020년 04월 11일","2020년 04월 12일","2020년 04월 13일","2020년 04월 14일"};
        items = new ArrayList<>();

        for(int i = 0; i < levelList.length; i++){
            ChartItem item = new ChartItem();
            item.setLevel(levelList[i]);
            item.setDate(dateList[i]);
            items.add(item);
        }
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.bt_add_list_chart:
                Intent inputChartIntent = new Intent(getContext(), InputChartActivity.class);
                startActivity(inputChartIntent);
                break;

            case R.id.bt_type_chart:
                Intent typeIntent = new Intent(getContext(), TypeActivity.class);
                typeIntent.putExtra("type", mType);
                startActivityForResult(typeIntent, CHANGE_TYPE);
                break;

            case R.id.bt_term_chart:
                Intent termIntent = new Intent(getContext(), TermActivity.class);
                startActivityForResult(termIntent, CHANGE_TYPE);
                break;

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

        switch(requestCode){
            case CHANGE_TYPE:
                mType = data.getIntExtra("type", 0);
                Log.d("test", Integer.toString(mType));
                if(mType == 1){
                    setmLineChart(mEntries, "체지방률 변화");
                } else if(mType == 2){
                    setmLineChart(mEntries, "체중 변화");
                }
                break;

            default:
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
