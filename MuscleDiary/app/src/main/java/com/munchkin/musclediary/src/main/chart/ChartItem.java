package com.munchkin.musclediary.src.main.chart;

public class ChartItem {
    private float level;
    private int date;
    private int month;
    private int year;

    public ChartItem(float level, int year, int month, int date){
        this.level = level;
        this.date = date;
        this.month = month;
        this.year = year;
    }

    public float getLevel() {
        return level;
    }

    public int getDate() {
        return date;
    }

    public int getMonth() {
        return month;
    }

    public int getYear() {
        return year;
    }
}
