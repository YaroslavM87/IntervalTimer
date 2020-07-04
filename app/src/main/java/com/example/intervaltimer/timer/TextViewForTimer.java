package com.example.intervaltimer.timer;

import android.widget.TextView;

class TextViewForTimer {

    private TextView textViewForTimerType;
    private TextView textViewForTimerId;
    private TextView textViewForTimerValue;

    TextViewForTimer(TextView type, TextView id, TextView timeValue) {
        textViewForTimerType = type;
        textViewForTimerId = id;
        textViewForTimerValue = timeValue;
    }

    void updateTimerTypeInTextField(String timerType) {
        textViewForTimerType.setText(timerType);
    }

    void updateTimerIdInTextField(String timerId) {
        textViewForTimerId.setText(timerId);
    }

    void updateTimerLength(String newTimerLength) {
        textViewForTimerValue.setText(newTimerLength);
    }

    void updateTimerParameters(String timerType, String timerId, String newTimerLength) {
        updateTimerTypeInTextField(timerType);
        updateTimerIdInTextField(timerId);
        updateTimerLength(newTimerLength);
    }
}