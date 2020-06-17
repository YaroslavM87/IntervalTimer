package com.example.intervaltimer.timer;

import android.widget.TextView;

public class Timer {

    private long timerLength;
    private TextViewForTimer textViewForTimer; // when move Timer obj to the TimerList keeping hold
    private int timerID;

    Timer() {}

//    public Timer(long timerLength, int timerID) {
//        this.timerLength = timerLength;
//        this.timerID = timerID;
//    }

    void setTimerLength(long changeForTimerLength) {

        long newTimerLength = changeForTimerLength + timerLength;

        if(newTimerLength <= 0L) {
            timerLength = 0L;
        } else {
            timerLength = newTimerLength;
        }
    }

    long getTimerLengthAtTheMoment() {
        return this.timerLength;
    }

    void setTextViewForTimer(TextViewForTimer textViewForTimer) {
        this.textViewForTimer = textViewForTimer;
    }

    TextViewForTimer getTextViewForTimer() {
        return this.textViewForTimer;
    }

    void setTimerID(int timerID) {
        this.timerID = timerID;
    }

    int getTimerID() {
        return timerID;
    }

    @Override
    public String toString() {
        return timeValueToFormattedRepresentation(timerLength);
    }

    String timeValueToFormattedRepresentation(long timeValue) {
        return String.format("%s : %s",
                parseLongToString(convertMillToMin(timeValue)),
                parseLongToString(convertMillToSec(timeValue)));
    }

    private long convertMillToMin(long timeValue) {
        return ((timeValue / 1000) - convertMillToSec(timeValue)) / 60;
    }

    private long convertMillToSec(long timeValue) {
        return (timeValue / 1000) % 60;
    }

    private String parseLongToString(long timeValue) {
        return timeValue < 10 ? ("0" + timeValue) : Long.toString(timeValue);
    }
}