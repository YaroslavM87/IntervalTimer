package com.example.intervaltimer.timer;

import java.util.ArrayList;
import android.os.CountDownTimer;
import android.widget.TextView;

import com.example.intervaltimer.activityElements.AdapterForTimerList;

public class TimerUtil {

    private Timer timerUnderConfig;
    private Timer operatedTimer;
    private TextView textViewForTimerUnderConfig;
    private TimerList timerList;
    private CountDownTimer countDownTimer;
    private long initialTime;
    private long countDownInterval;
    private boolean isFirstLaunch;
    private ArrayList<Timer> copyOfTimerList;
    private AdapterForTimerList adapter;

    {
        initialTime = 0L;
        countDownInterval = 1000L;
        isFirstLaunch = true;
    }

    public TimerUtil(TextView textView) {
        textViewForTimerUnderConfig = textView;
        timerList = new TimerList();
        timerUnderConfig = createTimer(textViewForTimerUnderConfig);
    }

    /**
     * Условной конструкцией 1го ур. влож. реализован фильтр для обхода ситуации, когда таймер,
     * находящийся в списке, после вызова на редактирование {@link #chooseTimerForConfiguration(int index)}
     * добавляют вызовом {@link #addTimerToList()} в тот же список, что приводит к 2м ссылкам
     * на один и тот же объект
     */
    public void addTimerToList() {

        if(timerUnderConfig.getTimerID() == -1) {
            if(timerUnderConfig.getTimerLengthAtTheMoment() > 0L) {
                timerList.addTimerInList(timerList.getSizeOfTimerList(), timerUnderConfig);
                timerUnderConfig.setTimerID(timerList.getSizeOfTimerList());
                timerUnderConfig = createTimer(textViewForTimerUnderConfig);
            }

        } else {
            if(timerUnderConfig.getTimerLengthAtTheMoment() > 0L) {
                timerUnderConfig = createTimer(textViewForTimerUnderConfig);
            }
        }
    }

    public void deleteTimer(Timer timer) {
        timerList.removeTimerFromList(timer);
    }

    public void deleteTimer(int index) {
        timerList.removeTimerFromList(index);
    }

    public void runTimer() {

        if(timerList.timerListIsNotEmpty()) {

            if(isFirstLaunch) {
                setNextTimerAsOperated();
                isFirstLaunch = false;
            }

            timerList.removeTimerFromList(operatedTimer);
            adapter.notifyDataSetChanged();

            countDownTimer = new CountDownTimer(operatedTimer.getTimerLengthAtTheMoment(), countDownInterval) {

                public void onTick(long millisUntilFinished) {
                    refreshTimeValue(millisUntilFinished);
                }

                public void onFinish() {

                    refreshTimeValue(0L);

                    if(timerList.getSizeOfTimerList() - 1 == timerList.getIndexOfTimer(operatedTimer)) {
                        timerList.clearTimerList();
                        isFirstLaunch = true;
                        this.cancel();

                    } else {
                        setNextTimerAsOperated();
                        runTimer();
                    }
                }
            }.start();

        } // else ( notify() )
    }

    public void stopTimer() {
        if(timerList.timerListIsNotEmpty()) {
            //refreshTimeValue(0L);
            if(countDownTimer != null) {countDownTimer.cancel();}
            isFirstLaunch = true;
            timerList.clearTimerList();
            adapter.notifyDataSetChanged();
        } // else ( notify() )
    }

    public void chooseTimerForConfiguration(int index) {
        timerUnderConfig = timerList.getTimer(index);
        timerUnderConfig.getTextViewForTimer().refreshTimerLengthInTextField(
                timerUnderConfig.timeValueToFormattedRepresentation(
                        timerUnderConfig.getTimerLengthAtTheMoment()
                )
        );
    }

    public void configureTimer(long changeForTimerLength) {

        timerUnderConfig.setTimerLength(changeForTimerLength);

        timerUnderConfig.getTextViewForTimer().refreshTimerLengthInTextField(
                timerUnderConfig.timeValueToFormattedRepresentation(
                        timerUnderConfig.getTimerLengthAtTheMoment()
                )
        );
    }

    // method for recycler view adapter at MainActivity
    public ArrayList<Timer> getTimerList() {
        return timerList.getTimerList();
    }

    public void setTimerAdapter(AdapterForTimerList adapter) {
        this.adapter = adapter;
    }


    /**
     * вызов {@code timer.setTimerID(-1)} необходим для работы фильтра в {@link #addTimerToList()}
     */
    private Timer createTimer(TextView textView) {
        Timer timer = new Timer();
        timer.setTextViewForTimer(new TextViewForTimer(textView));
        timer.setTimerLength(initialTime);
        timer.getTextViewForTimer().
                refreshTimerLengthInTextField(timer.timeValueToFormattedRepresentation(initialTime));
        timer.setTimerID(-1);
        return timer;
    }

    private void setNextTimerAsOperated() {
        operatedTimer = timerList.getTimer(0);
    }

    private void refreshTimeValue(long currentTimeValue) {
        if(operatedTimer != null) {
            operatedTimer.setTimerLength(currentTimeValue);
            operatedTimer.getTextViewForTimer().refreshTimerLengthInTextField(
                    operatedTimer.timeValueToFormattedRepresentation(currentTimeValue)
            );
        }
    }
}

//copyOfTimerList = (ArrayList<Timer>) timerList.getTimerList().clone();

// if(timerList.getTimerList() != copyOfTimerList & !timerList.getTimerList().equals(copyOfTimerList)) {
//         timerList.setTimerList((ArrayList<Timer>) copyOfTimerList.clone());
//        adapter.notifyDataSetChanged();
//        }