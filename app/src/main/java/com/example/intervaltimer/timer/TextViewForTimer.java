package com.example.intervaltimer.timer;

import android.widget.TextView;

class TextViewForTimer {

    private TextView textViewForTimerIdAndType;
    private TextView textViewForTimerValue;

    TextViewForTimer(TextView idAndType, TextView timeValue) {
        textViewForTimerIdAndType = idAndType;
        textViewForTimerValue = timeValue;
    }

    void updateTimerLengthInTextField(String newTimerLength) {
        textViewForTimerValue.setText(newTimerLength);
    }

    void updateTimerIdAndTypeInTextField(String timerId, String timerType) {
        String timerIdAndType = timerId.concat(" ").concat(timerType);
        textViewForTimerIdAndType.setText(timerIdAndType);
    }

    void updateTimerParameters(String timerId, String timerType, String newTimerLength) {
        updateTimerIdAndTypeInTextField(timerId, timerType);
        textViewForTimerValue.setText(newTimerLength);
    }

    void changeTextView(TextView idAndType, TextView timeValue) {
        textViewForTimerIdAndType = idAndType;
        textViewForTimerValue = timeValue;
    }

    TextView getTextViewForTimerIdAndType() {
        return textViewForTimerIdAndType;
    }

    TextView getTextViewForTimerValue() {
        return textViewForTimerValue;
    }
}