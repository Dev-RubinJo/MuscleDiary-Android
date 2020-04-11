package com.munchkin.musclediary.src.main.chart;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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

import java.util.ArrayList;
import java.util.List;

public class ChartFragment extends BaseFragment {

    private LineChart lineChart;
    //리사이클러뷰 아이템 리스트
    private ArrayList<ChartItem> items;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        final View view = inflater.inflate(R.layout.fragment_chart, container, false);
        setComponentView(view);

        //더미데이터 넣는 함수 실행
        addRecyclerList();

        //리사이클러뷰 생성, 레이아웃 매니저 적용
        RecyclerView chartRecyclerView = view.findViewById(R.id.recycler_chart);
        chartRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        //리사이클러뷰 어뎁터 생성, 적용
        ChartAdapter adapter = new ChartAdapter(getContext(), items);
        chartRecyclerView.setAdapter(adapter);

        //예시용 그래프 라이브러리
        lineChart = (LineChart)view.findViewById(R.id.line_chart);

        List<Entry> entries = new ArrayList<>();
        entries.add(new Entry(1, 1));
        entries.add(new Entry(2, 2));
        entries.add(new Entry(3, 0));
        entries.add(new Entry(4, 4));
        entries.add(new Entry(5, 3));

        LineDataSet lineDataSet = new LineDataSet(entries, "칼로리 소모량");
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
        lineChart.setData(lineData);

        XAxis xAxis = lineChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setTextColor(Color.BLACK);
        xAxis.enableGridDashedLine(8, 24, 0);

        YAxis yLAxis = lineChart.getAxisLeft();
        yLAxis.setTextColor(Color.BLACK);

        YAxis yRAxis = lineChart.getAxisRight();
        yRAxis.setDrawLabels(false);
        yRAxis.setDrawAxisLine(false);
        yRAxis.setDrawGridLines(false);

        Description description = new Description();
        description.setText("");

        lineChart.setDoubleTapToZoomEnabled(false);
        lineChart.setDrawGridBackground(false);
        lineChart.setDescription(description);
        //lineChart.animateY(2000, Easing.EasingOption.EaseInCubic);
        lineChart.invalidate();
        return view;
    }

    // 생성될 차트 프레그먼트에 대한 컴포넌트 세팅
    @Override
    public void setComponentView(View v) {

    }

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
}
