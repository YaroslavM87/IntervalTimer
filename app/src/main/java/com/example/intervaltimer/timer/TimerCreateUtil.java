package com.example.intervaltimer.timer;

import java.util.ArrayList;

import android.widget.TextView;

import com.example.intervaltimer.activityElements.AdapterForTimerList;

public class TimerCreateUtil {

    private TimerList timerList;

    private Timer timerUnderConfig;
    private TextView textViewForTimerUnderConfigIdAndType;
    private TextView textViewForTimerUnderConfigTimeValue;

    private long initialTime;

    private AdapterForTimerList adapter;

    {
        initialTime = 0L;
    }

    public TimerCreateUtil(TextView idAndType, TextView timeValue) {
        textViewForTimerUnderConfigIdAndType = idAndType;
        textViewForTimerUnderConfigTimeValue = timeValue;
        timerList = new TimerList(SharedResources.getCopyOfTimerList(idAndType, timeValue));
        setInitialConditions();
    }

    public void addTimerInList() {

        if (timerUnderConfig.getId() == -1) {
            if (timerUnderConfig.getLengthAtTheMoment() > 0L) {
                timerList.addTimerInList(timerList.getSizeOfTimerList(), timerUnderConfig);
                timerUnderConfig.updateId(timerList.getSizeOfTimerList() - 1);
                timerUnderConfig = createTimer(textViewForTimerUnderConfigIdAndType, textViewForTimerUnderConfigTimeValue);
                adapter.notifyDataSetChanged();
            }

        } else {
            if (timerUnderConfig.getLengthAtTheMoment() > 0L) {
                timerUnderConfig = createTimer(textViewForTimerUnderConfigIdAndType, textViewForTimerUnderConfigTimeValue);
            }
        }
    }

    public void chooseTimerForConfiguration(int index) {
        if (index != timerList.getIndexOfTimerInList(timerUnderConfig)) {
            timerUnderConfig = timerList.getTimerFromList(index);
            updateTimerValuesInTextViews(timerUnderConfig);
        }
    }

    public void configureTimer(long changeForTimerLength) {
        long newTimerLength = changeForTimerLength + timerUnderConfig.getLengthAtTheMoment();
        if (newTimerLength >= 1000L) {
            timerUnderConfig.setLength(newTimerLength);
        } else {
            if (timerUnderConfig.getLengthAtTheMoment() > 0L)
                timerUnderConfig.setLength(1000L);
        }
        updateTimerValues(timerUnderConfig, timerUnderConfig.getLengthAtTheMoment());
        adapter.notifyDataSetChanged();
    }

    public void deleteTimerUnderConfig() {

        if (timerList.getSizeOfTimerList() > 1) {

            if (timerList.getTimerFromList(timerList.getIndexOfTimerInList(timerUnderConfig)) != null) {
                int indexOfTimerToDelete = timerList.getIndexOfTimerInList(timerUnderConfig);

                if (timerUnderConfig.getType() == TimerType.WORK_TIME) {
                    timerList.removeTimerFromList(indexOfTimerToDelete);
                    timerUnderConfig = createTimer(textViewForTimerUnderConfigIdAndType, textViewForTimerUnderConfigTimeValue);

                    if (timerList.getSizeOfTimerList() >= indexOfTimerToDelete + 1) {
                        timerList.removeTimerFromList(indexOfTimerToDelete);
                    }

                    adapter.notifyDataSetChanged();

                } else {
                    /* TODO: notify(); */
                }
            }
        }
    }

    public void resetTimerList() {
        updateTimerValues(timerUnderConfig, 0L);
        timerList.clearTimerList();
    }

    public void updateTimerListInSharedResources() {
        SharedResources.setTimerList(timerList.getTimerList());
    }

    public ArrayList<Timer> getTimerList() {
        return timerList.getTimerList();
    }

    public void setAdapter(AdapterForTimerList adapter) {
        this.adapter = adapter;
    }

    public boolean canTimerBeRun() {
        return timerList.getTimerFromList(timerList.getSizeOfTimerList() - 1).getType() == TimerType.WORK_TIME;
    }

    private void setInitialConditions() {
        if(timerList.getTimerList().isEmpty()) {
            addInitialTimerInList(textViewForTimerUnderConfigIdAndType, textViewForTimerUnderConfigTimeValue);
        }
        timerUnderConfig = createTimer(textViewForTimerUnderConfigIdAndType, textViewForTimerUnderConfigTimeValue);
    }

    private void addInitialTimerInList(TextView idAndType, TextView timeValue) {
        Timer newTimer = new Timer(5000L, TimerType.TIME_TO_START, 0);
        newTimer.setTextViewForTimer(new TextViewForTimer(idAndType, timeValue));
        timerList.addTimerInList(0, newTimer);
    }

    private Timer createTimer(TextView idAndType, TextView timeValue) {
        Timer newTimer;

        if (
                timerList.getTimerFromList(timerList.getSizeOfTimerList() - 1).getType() == TimerType.TIME_TO_START ||
                        timerList.getTimerFromList(timerList.getSizeOfTimerList() - 1).getType() == TimerType.REST_TIME
        ) {
            newTimer = new Timer(initialTime, TimerType.WORK_TIME, -1);

        } else {
            newTimer = new Timer(initialTime, TimerType.REST_TIME, -1);
        }

        newTimer.setTextViewForTimer(new TextViewForTimer(idAndType, timeValue));
        updateTimerValues(newTimer, initialTime);

        return newTimer;
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
}