package com.example.intervaltimer.timer;

import android.widget.TextView;

class TextViewForTimer {

    private TextView textViewForTimer;

    TextViewForTimer(TextView textView) {
        textViewForTimer = textView;
    }

    void refreshTimerLengthInTextField(String newTimerLength) {
        textViewForTimer.setText(newTimerLength);
    }

    void changeTextView(TextView textView) {
       textViewForTimer = textView;
    }

    TextView getTextViewForTimer() {
        return textViewForTimer;
    }
}