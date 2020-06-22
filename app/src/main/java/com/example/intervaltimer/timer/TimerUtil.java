package com.example.intervaltimer.timer;

import java.util.ArrayList;
import android.os.CountDownTimer;
import android.widget.TextView;

import com.example.intervaltimer.activityElements.AdapterForTimerList;

public class TimerUtil {

    private TimerList timerList;
    private ArrayList<Timer> copyOfTimerList;

    private Timer timerUnderConfig;
    private Timer operatedTimer;
    private TextView textViewForTimerUnderConfigIdAndType;
    private TextView textViewForTimerUnderConfigTimeValue;

    private CountDownTimer countDownTimer;
    private long initialTime;
    private long countDownInterval;
    private boolean isFirstLaunch;
    private boolean timerIsRunning;

    private AdapterForTimerList adapter;

    {
        initialTime = 0L;
        countDownInterval = 1000L;
    }

    public TimerUtil(TextView idAndType, TextView timeValue) {
        timerList = new TimerList();
        textViewForTimerUnderConfigIdAndType = idAndType;
        textViewForTimerUnderConfigTimeValue = timeValue;
        setInitialConditions();
    }

    /**
     * Условной конструкцией 1го ур. влож. реализован фильтр для обхода ситуации, когда таймер,
     * находящийся в списке, после вызова на редактирование {@link #chooseTimerForConfiguration(int index)}
     * добавляют вызовом {@link #addTimerToList()} в тот же список, что приводит к 2м ссылкам
     * на один и тот же объект
     */
    public void addTimerToList() {

        if(timerUnderConfig.getTimerId() == -1) {
            if(timerUnderConfig.getTimerLengthAtTheMoment() > 0L) {
                timerList.addTimerInList(timerList.getSizeOfTimerList(), timerUnderConfig);
                timerUnderConfig.updateTimerId(timerList.getSizeOfTimerList() - 1);
                timerUnderConfig = createTimer(textViewForTimerUnderConfigIdAndType, textViewForTimerUnderConfigTimeValue);
                adapter.notifyDataSetChanged();
            }

        } else {
            if(timerUnderConfig.getTimerLengthAtTheMoment() > 0L) {
                timerUnderConfig = createTimer(textViewForTimerUnderConfigIdAndType, textViewForTimerUnderConfigTimeValue);
            }
        }
    }

    public void chooseTimerForConfiguration(int index) {
        if(index != timerList.getIndexOfTimerInList(timerUnderConfig)) {
            timerUnderConfig = timerList.getTimerFromList(index);
            updateTimerValuesInTextViews(timerUnderConfig);
        }
    }

    public void configureTimer(long changeForTimerLength) {
        long newTimerLength = changeForTimerLength + timerUnderConfig.getTimerLengthAtTheMoment();
        if(newTimerLength >= 1000L) {
            timerUnderConfig.setTimerLength(newTimerLength);
        } else {
            if(timerUnderConfig.getTimerLengthAtTheMoment() > 0L) timerUnderConfig.setTimerLength(1000L);
        }
        updateTimerValues(timerUnderConfig, timerUnderConfig.getTimerLengthAtTheMoment());
        adapter.notifyDataSetChanged();
    }

    public void deleteTimer(Timer timer) {
        int indexOfTimerToDelete = timerList.getIndexOfTimerInList(timer);
        timerList.removeTimerFromList(timer);
        adapter.notifyItemRemoved(indexOfTimerToDelete);
    }

    public void deleteTimer(int index) {
        timerList.removeTimerFromList(index);
        adapter.notifyItemRemoved(index);
    }

    public void launchSetOfTimers() {
        if(!timerIsRunning) {
            if (timerList.getTimerFromList(timerList.getSizeOfTimerList() - 1).getTimerType() != TimerType.REST_TIME & isFirstLaunch) {
                setNextTimerAsOperated(0);
                isFirstLaunch = false;
                timerIsRunning = true;
                runTimer();
            }
        }
    }

    public void stopTimerAndResetTimerList() {
        if(timerIsRunning) {
            updateTimerValues(operatedTimer, 0L);
            if (countDownTimer != null) countDownTimer.cancel();
            timerList.clearTimerList();
            setInitialConditions();
            adapter.notifyDataSetChanged();
        }
    }

    // method for recycler view adapter at MainActivity
    public ArrayList<Timer> getTimerList() {
        return timerList.getTimerList();
    }

    public void setAdapter(AdapterForTimerList adapter) {
        this.adapter = adapter;
    }

    public boolean isTimerRunning() {
        return timerIsRunning;
    }


    private void setInitialConditions() {
        addInitialTimerInList(textViewForTimerUnderConfigIdAndType, textViewForTimerUnderConfigTimeValue);
        timerUnderConfig = createTimer(textViewForTimerUnderConfigIdAndType, textViewForTimerUnderConfigTimeValue);
        isFirstLaunch = true;
        timerIsRunning = false;
    }

    private void addInitialTimerInList(TextView idAndType, TextView timeValue) {
        Timer newTimer = new Timer(5000L, TimerType.TIME_TO_START, 0);
        newTimer.setTextViewForTimer(new TextViewForTimer(idAndType, timeValue));
        timerList.addTimerInList(0, newTimer);
    }

    private Timer createTimer(TextView idAndType, TextView timeValue) {
        Timer newTimer;

        if(
                timerList.getTimerFromList(timerList.getSizeOfTimerList() - 1).getTimerType() == TimerType.TIME_TO_START ||
                timerList.getTimerFromList(timerList.getSizeOfTimerList() - 1).getTimerType() == TimerType.REST_TIME
        ) {
            newTimer = new Timer(initialTime, TimerType.WORK_TIME, -1);

        } else {
            newTimer = new Timer(initialTime, TimerType.REST_TIME, -1);
        }

        newTimer.setTextViewForTimer(new TextViewForTimer(idAndType, timeValue));
        updateTimerValues(newTimer, initialTime);

        return newTimer;
    }

    private void runTimer() {
        deleteTimer(operatedTimer);
        countDownTimer = new CountDownTimer(operatedTimer.getTimerLengthAtTheMoment(), countDownInterval) {

            public void onTick(long millisUntilFinished) {
                updateTimerValues(operatedTimer, millisUntilFinished);
            }

            public void onFinish() {
                updateTimerValues(operatedTimer, 0L);

                if(isLastTimerInList(operatedTimer)) {
                    timerList.clearTimerList();
                    setInitialConditions();
                    adapter.notifyDataSetChanged();
                    this.cancel();

                } else {
                    setNextTimerAsOperated(0);
                    runTimer();
                }
            }
        }.start();
    }

    private void setNextTimerAsOperated(int index) {
        operatedTimer = timerList.getTimerFromList(index);
    }

    private void updateTimerValues(Timer timer, long currentTimeValue) {
        if(timer != null) {
            timer.setTimerLength(currentTimeValue);
            updateTimerValuesInTextViews(timer);
        }
    }

    private void updateTimerValuesInTextViews(Timer timer) {
            String id = timer.getTimerIdAsString();
            String type = timer.getTimerType().toString();
            String timeValue = timer.timeValueToFormattedRepresentation(timer.getTimerLengthAtTheMoment());
            timer.updateTimerParamInTextView(id, type, timeValue);
    }

    private boolean isLastTimerInList(Timer timer) {
        return timerList.getSizeOfTimerList() - 1 == timerList.getIndexOfTimerInList(timer);
    }
}




//copyOfTimerList = (ArrayList<Timer>) timerList.getTimerList().clone();

// if(timerList.getTimerList() != copyOfTimerList & !timerList.getTimerList().equals(copyOfTimerList)) {
//         timerList.setTimerList((ArrayList<Timer>) copyOfTimerList.clone());
//        adapter.notifyDataSetChanged();
//        }