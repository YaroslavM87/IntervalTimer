package com.example.intervaltimer.timer;

public class Timer {

    private long timerLength;
    private TextViewForTimer textViewForTimer;
    private int timerId;
    private TimerType timerType;

    Timer() {}

    Timer(long timerLength, TimerType timerType, int timerID) {
        this.timerLength = timerLength;
        this.timerType = timerType;
        this.timerId = timerID;
    }

    void setId(int timerId) {
        this.timerId = timerId;
    }

    public int getId() {
        return timerId;
    }

    void updateId(int newTimerId) {
        timerId = newTimerId;
    }

    void setLength(long newTimerLength) {
        timerLength = Math.max(newTimerLength, 0L);
    }

    long getLengthAtTheMoment() {
        return this.timerLength;
    }

    void updateTimerLengthInCrtActvt(long newTimerLength) {
        timerLength = Math.max(newTimerLength, 0L);
        updateTimerValueInTextView(toString(1));
    }

    void updateTimerLengthInRunActvt(long newTimerLength) {
        timerLength = Math.max(newTimerLength, 0L);
        updateTimerValueInTextView(toString(2));
    }

    void setTextViewForTimer(TextViewForTimer textViewForTimer) {
        this.textViewForTimer = textViewForTimer;
    }

    void updateAllTimersParamsInTextView(String timerId, String timerType, String newTimerLength) {
        textViewForTimer.updateTimerParameters(timerId, timerType, newTimerLength);
    }

    void updateTypeInRunActvt(TimerType timerType) {
        if(timerType == TimerType.TIME_TO_START) {
            textViewForTimer.updateTimerTypeInTextField(
                    timerType.toString(true).concat("  from")
            );
        } else {
            textViewForTimer.updateTimerTypeInTextField(timerType.toString(true));
        }
    }

    void updateIdInTextView(int timerId) {
        textViewForTimer.updateTimerIdInTextField(getNumberOfGroupOfTimers(timerId));
    }

    void updateTimerValueInTextView(String newTimerLength) {
        textViewForTimer.updateTimerLength(newTimerLength);
    }

    public String getNumberOfGroupOfTimers(int timerId) {

        if(convertTimerIdToNumberOfGroupOfTimers(timerId) == -1) {
            return "";

        } else {
            return convertNumberOfGroupOfTimersToNumberOfLap(
                    convertTimerIdToNumberOfGroupOfTimers(timerId)
            );
        }
    }

    private int convertTimerIdToNumberOfGroupOfTimers(int timerId) {
        if(timerId == -1 || timerId == 0) {
            return -1;

        } else if(timerId % 2 == 0) {
            return timerId / 2;

        } else {
            return (timerId + 1) / 2;
        }
    }
    // ((timerId + 1) / 2) + ". "
    // timerId + ". "

    private String convertNumberOfGroupOfTimersToNumberOfLap(int numberOfGroup) {
        String result;
        switch ((numberOfGroup + 10) % 10) {
            case 1:
                result = numberOfGroup + "st lap";
                break;

            case 2:
                result = numberOfGroup + "nd lap";
                break;

            case 3:
                result = numberOfGroup + "rd lap";
                break;

            default:
                result = numberOfGroup + "th lap";
        }
        return result;
    }

    public TimerType getType() {
        return timerType;
    }

    public void setType(TimerType timerType) {
        this.timerType = timerType;
    }

    //@Override
    public String toString(int type) {
        switch (type) {
            case 1:
                return timeValueToFormattedRepresentationType_1(timerLength);
            default:
                return timeValueToFormattedRepresentationType_2(timerLength);
        }
    }

    String timeValueToFormattedRepresentationType_1(long timeValue) {

        if(timeValue < 60000L) {
            return String.format("%s s" , parseLongToString(convertMillToSec(timeValue)));

        } else {
            return String.format("%s m %s s" ,
                    parseLongToString(convertMillToMin(timeValue)),
                    parseLongToString(convertMillToSec(timeValue)));
        }
    }

    String timeValueToFormattedRepresentationType_2(long timeValue) {
            return String.format("%s : %s" ,
                    parseLongToStringAddZero(convertMillToMin(timeValue)),
                    parseLongToStringAddZero(convertMillToSec(timeValue)));
    }

    private long convertMillToMin(long timeValue) {
        return ((timeValue / 1000) - convertMillToSec(timeValue)) / 60;
    }

    private long convertMillToSec(long timeValue) {
        return (timeValue / 1000) % 60;
    }

    private String parseLongToString(long timeValue) {
        return Long.toString(timeValue);
    }

    private String parseLongToStringAddZero(long timeValue) {
        return timeValue < 10 ? ("0" + timeValue) : Long.toString(timeValue);
    }
}