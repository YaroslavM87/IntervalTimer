package com.example.intervaltimer.timer;

import java.util.ArrayList;

import android.widget.TextView;

import com.example.intervaltimer.activityElements.AdptForTimListInCrtActvt;

public class TimerCreateUtil {

    private TimerList timerList;

    private Timer timerUnderConfig;
    private TextView textViewForTimerUnderConfigType;
    private TextView textViewForTimerUnderConfigId;
    private TextView textViewForTimerUnderConfigTimeValue;

    private long initialTime;

    private AdptForTimListInCrtActvt adapter;

    {
        initialTime = 0L;
    }

    public TimerCreateUtil(TextView type, TextView id, TextView timeValue) {
        textViewForTimerUnderConfigType = type;
        textViewForTimerUnderConfigId = id;
        textViewForTimerUnderConfigTimeValue = timeValue;
        timerList = new TimerList(SharedResources.getCopyOfTimerList(type, id, timeValue));
        setInitialConditions();
    }

    public void addTimerInList() {

        if (timerUnderConfig.getId() == -1) {
            if (timerUnderConfig.getLengthAtTheMoment() > 0L) {
                timerList.addTimerInList(timerList.getSizeOfTimerList(), timerUnderConfig);
                timerUnderConfig.updateId(timerList.getSizeOfTimerList() - 1);
                timerUnderConfig = createTimer(textViewForTimerUnderConfigType, textViewForTimerUnderConfigId, textViewForTimerUnderConfigTimeValue);
                adapter.notifyDataSetChanged();
            }

        } else {
            if (timerUnderConfig.getLengthAtTheMoment() > 0L) {
                timerUnderConfig = createTimer(textViewForTimerUnderConfigType, textViewForTimerUnderConfigId, textViewForTimerUnderConfigTimeValue);
            }
        }
        timerUnderConfig.updateIdInTextView(timerList.getSizeOfTimerList());
    }

    public void chooseTimerForConfiguration(int index) {
        if (index != timerList.getIndexOfTimerInList(timerUnderConfig)) {
            timerUnderConfig = timerList.getTimerFromList(index);
            updateAllTimersParamsInTextView(timerUnderConfig);
        }
    }

    public void configureTimer(long changeForTimerLength) {
        long newTimerLength = changeForTimerLength + timerUnderConfig.getLengthAtTheMoment();
        if (newTimerLength >= 1000L) {
            timerUnderConfig.updateTimerLengthInCrtActvt(newTimerLength);
        } else {
            if (timerUnderConfig.getLengthAtTheMoment() > 0L)
                timerUnderConfig.updateTimerLengthInCrtActvt(1000L);
        }
        timerUnderConfig.updateTimerValueInTextView(timerUnderConfig.toString(1));
        adapter.notifyDataSetChanged();
    }

    public void deletePairOfTimers() {

        if (timerList.getSizeOfTimerList() > 1) {

            if (timerList.getTimerList().contains(timerUnderConfig)) {
                int indexOfTimerToDelete = timerList.getIndexOfTimerInList(timerUnderConfig);

                if (timerUnderConfig.getType() == TimerType.WORK_TIME) {
                    timerList.removeTimerFromList(indexOfTimerToDelete);

                    if (timerList.getSizeOfTimerList() >= indexOfTimerToDelete + 1) {
                        timerList.removeTimerFromList(indexOfTimerToDelete);
                    }

                } else if(timerUnderConfig.getType() == TimerType.REST_TIME) {
                    timerList.removeTimerFromList(indexOfTimerToDelete - 1);
                    timerList.removeTimerFromList(indexOfTimerToDelete - 1);
                }

                timerUnderConfig = createTimer(textViewForTimerUnderConfigType, textViewForTimerUnderConfigId, textViewForTimerUnderConfigTimeValue);
                timerUnderConfig.updateIdInTextView(timerList.getSizeOfTimerList());
                adapter.notifyDataSetChanged();
            }
        }
    }

    public void resetTimerList() {
        timerList = new TimerList(SharedResources.getNewTimerList());
        setInitialConditions();
        adapter.setTimerList(timerList.getTimerList());
        adapter.notifyDataSetChanged();
    }

    public void restoreTimerList() {
        timerList = new TimerList(SharedResources.getCopyOfTimerList(
                textViewForTimerUnderConfigType,
                textViewForTimerUnderConfigId,
                textViewForTimerUnderConfigTimeValue
        ));
        adapter.setTimerList(timerList.getTimerList());
        adapter.notifyDataSetChanged();
    }

    public ArrayList<Timer> getTimerList() {
        return timerList.getTimerList();
    }

    public void setAdapter(AdptForTimListInCrtActvt adapter) {
        this.adapter = adapter;
    }

    public boolean isReadyToStartTimerSet() {
        return updateTimerListInSharedResources() &
               timerList.getTimerFromList(timerList.getSizeOfTimerList() - 1).getType() == TimerType.WORK_TIME &
               !SharedResources.getTimerList().isEmpty();
    }

    private boolean updateTimerListInSharedResources() {
        return SharedResources.setTimerList(timerList.getTimerList());
    }

    private void setInitialConditions() {
        if(timerList.getTimerList().isEmpty()) {
            addInitialTimerInList(textViewForTimerUnderConfigType, textViewForTimerUnderConfigId, textViewForTimerUnderConfigTimeValue);
        }
        timerUnderConfig = createTimer(textViewForTimerUnderConfigType, textViewForTimerUnderConfigId, textViewForTimerUnderConfigTimeValue);
        timerUnderConfig.updateIdInTextView(timerList.getSizeOfTimerList());
    }

    private void addInitialTimerInList(TextView type, TextView id, TextView timeValue) {
        Timer newTimer = new Timer(5000L, TimerType.TIME_TO_START, 0);
        newTimer.setTextViewForTimer(new TextViewForTimer(type, id, timeValue));
        timerList.addTimerInList(0, newTimer);
    }

    private Timer createTimer(TextView type, TextView id, TextView timeValue) {
        Timer newTimer;

        if (
                timerList.getTimerFromList(timerList.getSizeOfTimerList() - 1).getType() == TimerType.TIME_TO_START ||
                        timerList.getTimerFromList(timerList.getSizeOfTimerList() - 1).getType() == TimerType.REST_TIME
        ) {
            newTimer = new Timer(initialTime, TimerType.WORK_TIME, -1);

        } else {
            newTimer = new Timer(initialTime, TimerType.REST_TIME, -1);
        }

        newTimer.setTextViewForTimer(new TextViewForTimer(type, id, timeValue));
        updateAllTimersParamsInTextView(newTimer);
        return newTimer;
    }

    private void updateAllTimersParamsInTextView(Timer timer) {
        timer.updateAllTimersParamsInTextView(
                timer.getType().toString(true),
                timer.getNumberOfGroupOfTimers(timer.getId()),
                timer.timeValueToFormattedRepresentationType_1(timer.getLengthAtTheMoment())
        );
    }
}