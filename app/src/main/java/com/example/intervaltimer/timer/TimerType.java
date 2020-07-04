package com.example.intervaltimer.timer;

public enum TimerType {
    TIME_TO_START ("start", "START"),
    WORK_TIME ("work", "WORK"),
    REST_TIME ("rest", "REST");

    private String title;
    private String titleCapital;

    TimerType(String title, String titleCapital) {
        this.title = title;
        this.titleCapital = titleCapital;
    }

    //@Override
    public String toString(boolean needTittleInCapital) {
        return needTittleInCapital ? titleCapital : title;
    }
}
