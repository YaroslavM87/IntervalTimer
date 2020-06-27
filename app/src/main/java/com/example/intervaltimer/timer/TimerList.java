package com.example.intervaltimer.timer;

import java.util.*;
import java.util.List;

class TimerList {

    private ArrayList<Timer> timerList;

    TimerList(ArrayList<Timer> timerList) {
        this.timerList = timerList;
    }

    ArrayList<Timer> getTimerList() {
        return timerList;
    }

    int getSizeOfTimerList() {
        return timerList.size();
    }

    void clearTimerList() {
        timerList.clear();
    }

    boolean isNotEmpty() {
        return !timerList.isEmpty();
    }

    void addTimerInList(int index, Timer timer) {
        timerList.add(index, timer);
    }

    Timer getTimerFromList(int index) {
        return timerList.get(index);
    }

    int getIndexOfTimerInList(Timer timer) {
        return timerList.indexOf(timer);
    }

    void removeTimerFromList(int index) {
        if(timerList.get(index) != null) timerList.remove(index);
        updateIdOfTimersInList();
    }

    void removeTimerFromList(Timer timer) {
        timerList.remove(timer);
        //updateIdOfTimersInList();
    }

    private void updateIdOfTimersInList() {
        for(Timer t : timerList) {
            t.setTimerId(timerList.indexOf(t));
        }
    }
}