package com.example.intervaltimer.timer;

public class Timer {

    private long timerLength;
    private TextViewForTimer textViewForTimer; // when move Timer obj to the TimerList keeping hold
    private int timerId;
    private TimerType timerType;

    Timer() {}

    Timer(long timerLength, TimerType timerType, int timerID) {
        this.timerLength = timerLength;
        this.timerType = timerType;
        this.timerId = timerID;
    }

    void setTimerLength(long newTimerLength) {

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

    void updateTimerParamInTextView(String timerId, String timerType, String newTimerLength) {
        textViewForTimer.updateTimerParameters(timerId, timerType, newTimerLength);
    }

    void updateTimerId(int timerId) {
        this.timerId = timerId;
        textViewForTimer.updateTimerIdAndTypeInTextField(getNumberOfGroupOfTimersAsString(), timerType.toString());
    }

    void setTimerId(int timerId) {
        this.timerId = timerId;
    }

    public int getTimerId() {
        return timerId;
    }

    public String getTimerIdAsString() {
        if(timerId == -1 || timerId == 0) {
            return "";
        } else {
            return timerId + ". ";
        }
    }

    public String getNumberOfGroupOfTimersAsString() {
        if(timerId == -1 || timerId == 0 || timerId % 2 == 0) {
            return "";
        } else {
            return ((timerId + 1) / 2) + ". ";
        }
    }

    public TimerType getTimerType() {
        return timerType;
    }

    public void setTimerType(TimerType timerType) {
        this.timerType = timerType;
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