package com.example.intervaltimer.activityElements;

import android.widget.Button;

public class ButtonSetTimer {

    private Button buttonTimerMinReduce;
    private Button buttonTimerMinIncrease;
    private Button buttonTimerSecReduce;
    private Button buttonTimerSecIncrease;

    public ButtonSetTimer() {}

    public Button getButtonTimerMinReduce() {
        return buttonTimerMinReduce;
    }

    public void setButtonTimerMinReduce(Button buttonTimerMinReduce) {
        this.buttonTimerMinReduce = buttonTimerMinReduce;
    }

    public Button getButtonTimerMinIncrease() {
        return buttonTimerMinIncrease;
    }

    public void setButtonTimerMinIncrease(Button buttonTimerMinIncrease) {
        this.buttonTimerMinIncrease = buttonTimerMinIncrease;
    }

    public Button getButtonTimerSecReduce() {
        return buttonTimerSecReduce;
    }

    public void setButtonTimerSecReduce(Button buttonTimerSecReduce) {
        this.buttonTimerSecReduce = buttonTimerSecReduce;
    }

    public Button getButtonTimerSecIncrease() {
        return buttonTimerSecIncrease;
    }

    public void setButtonTimerSecIncrease(Button buttonTimerSecIncrease) {
        this.buttonTimerSecIncrease = buttonTimerSecIncrease;
    }
}
