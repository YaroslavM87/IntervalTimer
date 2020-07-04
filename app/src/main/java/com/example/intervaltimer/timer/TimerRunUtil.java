package com.example.intervaltimer.timer;

import android.media.AudioManager;
import android.media.ToneGenerator;
import android.os.CountDownTimer;
import android.widget.TextView;

import com.example.intervaltimer.RunTimerActivity;
import com.example.intervaltimer.activityElements.AdptForTimListInRunActvt;

import java.util.ArrayList;

public class TimerRunUtil {

    private TimerList timerList;
    private ArrayList<Timer> copyOfTimerList;

    private Timer operatedTimer;
    private TextView textViewForTimerUnderConfigType;
    private TextView textViewForTimerUnderConfigId;
    private TextView textViewForOperatedTimerTimeValue;
    private CountDownTimer countDownTimer;
    private long countDownInterval;
    private AdptForTimListInRunActvt adapter;
    private RunTimerActivity runTimerActivity;
    private ToneGenerator toneGenerator;

    {
        countDownInterval = 1000L;
    }

    public TimerRunUtil(TextView type, TextView id, TextView timeValue) {
        textViewForTimerUnderConfigType = type;
        textViewForTimerUnderConfigId = id;
        textViewForOperatedTimerTimeValue = timeValue;
        timerList = new TimerList(SharedResources.getCopyOfTimerList(type, id, timeValue));
        toneGenerator = new ToneGenerator(AudioManager.STREAM_SYSTEM, 100);
    }

    public void launchSetOfTimers() {
        setNextTimerAsOperated(0);
        runTimer();
    }

    public void stopTimerAndResetTimerList() {
        if (countDownTimer != null) countDownTimer.cancel();
        timerList.clearTimerList();
        runTimerActivity.timerOnFinishAction();
    }

    public void setAdapter(AdptForTimListInRunActvt adapter) {
        this.adapter = adapter;
    }

    public void setRunTimerActivity(RunTimerActivity activity) {
        runTimerActivity = activity;
    }

    public void setTextViewsForOperatedTimer(TextView type, TextView id, TextView timeValue) {
        textViewForTimerUnderConfigType = type;
        textViewForTimerUnderConfigId = id;
        textViewForOperatedTimerTimeValue = timeValue;
    }

    public ArrayList<Timer> getTimerList() {
        return timerList.getTimerList();
    }


    private void setNextTimerAsOperated(int index) {
        operatedTimer = timerList.getTimerFromList(index);
        updateTimerTypeAndIdInTextView(operatedTimer);

    }

    private void runTimer() {
        deleteTimerFromList(operatedTimer);
        adapter.notifyDataSetChanged();
        countDownTimer = new CountDownTimer(operatedTimer.getLengthAtTheMoment(), countDownInterval) {

            public void onTick(long millisUntilFinished) {
                operatedTimer.updateTimerLengthInRunActvt(millisUntilFinished);
                produceSoundOnTick(millisUntilFinished);
            }

            public void onFinish() {
                operatedTimer.updateTimerLengthInRunActvt(0L);

                if (isLastTimerInList(operatedTimer)) {
                    countDownTimer.cancel();
                    runTimerActivity.timerOnFinishAction();

                } else {
                    setNextTimerAsOperated(0);
                    runTimer();
                }
            }
        }.start();
    }

    private void updateTimerTypeAndIdInTextView(Timer timer) {
        timer.updateTypeInRunActvt(timer.getType());
        timer.updateIdInTextView(timer.getId());
    }

    private void deleteTimerFromList(Timer timer) {
        int indexOfTimerToDelete = timerList.getIndexOfTimerInList(timer);
        timerList.removeTimerFromListWhileRunning(timer);
        adapter.notifyItemRemoved(indexOfTimerToDelete);
    }

    private boolean isLastTimerInList(Timer timer) {
        return timerList.getSizeOfTimerList() - 1 == timerList.getIndexOfTimerInList(timer);
    }

    private ArrayList<Timer> makeCopyOfTimerList(ArrayList<Timer> source) {
        return new ArrayList<Timer>(source);
    }

    private void produceSoundOnTick(long millisUntilFinished) {
        if(millisUntilFinished < 5500L & millisUntilFinished > 1700L) {
            toneGenerator.startTone(ToneGenerator.TONE_PROP_PROMPT, 50);
            toneGenerator.startTone(ToneGenerator.TONE_PROP_PROMPT, 50);
        }
        if(millisUntilFinished < 1500L & millisUntilFinished > 200L) {
            toneGenerator.startTone(ToneGenerator.TONE_PROP_ACK, 500);
        }
    }
}