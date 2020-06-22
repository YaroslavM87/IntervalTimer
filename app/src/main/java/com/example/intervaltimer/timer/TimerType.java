package com.example.intervaltimer.timer;

public enum TimerType {
    TIME_TO_START ("Time to start"),
    WORK_TIME ("Work"),
    REST_TIME ("Rest");

    private String title;

    TimerType(String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        return title;
    }
}
