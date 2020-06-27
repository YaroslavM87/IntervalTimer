package com.example.intervaltimer.timer;

import android.os.CountDownTimer;
import android.widget.TextView;

import com.example.intervaltimer.RunTimerActivity;
import com.example.intervaltimer.activityElements.AdapterForTimerList;

import java.util.ArrayList;

public class TimerRunUtil {

    private TimerList timerList;
    private ArrayList<Timer> copyOfTimerList;

    private Timer operatedTimer;
    private TextView textViewForOperatedTimerIdAndType;
    private TextView textViewForOperatedTimerTimeValue;


    private CountDownTimer countDownTimer;
    private long countDownInterval;

    private AdapterForTimerList adapter;
    private RunTimerActivity runTimerActivity;

    {
        countDownInterval = 1000L;
    }

    public TimerRunUtil(TextView idAndType, TextView timeValue) {
        textViewForOperatedTimerIdAndType = idAndType;
        textViewForOperatedTimerTimeValue = timeValue;
        copyOfTimerList = SharedResources.getCopyOfTimerList(textViewForOperatedTimerIdAndType, textViewForOperatedTimerTimeValue);
        timerList = new TimerList(copyOfTimerList);
    }

    public void launchSetOfTimers() {
        setNextTimerAsOperated(0);
        runTimer();
    }

    public void stopTimerAndResetTimerList() {
        updateTimerValues(operatedTimer, 0L);
        if (countDownTimer != null) countDownTimer.cancel();
        timerList.clearTimerList();
        runTimerActivity.timerOnFinishAction();
    }

    public void setAdapter(AdapterForTimerList adapter) {
        this.adapter = adapter;
    }

    public void setRunTimerActivity(RunTimerActivity activity) {
        runTimerActivity = activity;
    }

    public void setTextViewsForOperatedTimer(TextView timeOfOperatedTimer, TextView idAndTypeOfOperatedTimer) {
        this.textViewForOperatedTimerTimeValue = timeOfOperatedTimer;
        this.textViewForOperatedTimerIdAndType = idAndTypeOfOperatedTimer;
    }

    public ArrayList<Timer> getTimerList() {
        return copyOfTimerList;
    }


    private void runTimer() {
        deleteTimerFromList(operatedTimer);
        countDownTimer = new CountDownTimer(operatedTimer.getLengthAtTheMoment(), countDownInterval) {

            public void onTick(long millisUntilFinished) {
                updateTimerValues(operatedTimer, millisUntilFinished);
            }

            public void onFinish() {
                updateTimerValues(operatedTimer, 0L);

                if (isLastTimerInList(operatedTimer)) {
                    timerList.clearTimerList();
                    countDownTimer.cancel();
                    runTimerActivity.timerOnFinishAction();

                } else {
                    setNextTimerAsOperated(0);
                    runTimer();
                }
            }
        }.start();
    }

    private void setNextTimerAsOperated(int index) {
        operatedTimer = timerList.getTimerFromList(index);
       // operatedTimer.setTextViewForTimer(new TextViewForTimer(timeOfOperatedTimer, idAndTypeOfOperatedTimer));
    }

    private void updateTimerValues(Timer timer, long currentTimeValue) {
        if (timer != null) {
            timer.setLength(currentTimeValue);
            updateTimerValuesInTextViews(timer);
        }
    }

    private void updateTimerValuesInTextViews(Timer timer) {
        String id = timer.getNumberOfGroupOfTimersAsString();
        String type = timer.getType().toString();
        String timeValue = timer.timeValueToFormattedRepresentation(timer.getLengthAtTheMoment());
        timer.updateTimerParamInTextView(id, type, timeValue);
    }

    private void deleteTimerFromList(Timer timer) {
        int indexOfTimerToDelete = timerList.getIndexOfTimerInList(timer);
        timerList.removeTimerFromList(timer);
        adapter.notifyItemRemoved(indexOfTimerToDelete);
    }

    private boolean isLastTimerInList(Timer timer) {
        return timerList.getSizeOfTimerList() - 1 == timerList.getIndexOfTimerInList(timer);
    }

    private ArrayList<Timer> makeCopyOfTimerList(ArrayList<Timer> source) {
        return new ArrayList<Timer>(source);
    }
}