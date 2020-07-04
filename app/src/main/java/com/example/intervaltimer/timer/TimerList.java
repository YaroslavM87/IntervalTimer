package com.example.intervaltimer.timer;

import java.util.*;

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

    void removeTimerFromListWhileRunning(Timer timer) {
        timerList.remove(timer);
    }

    private void updateIdOfTimersInList() {
        for(Timer t : timerList) {
            t.setId(timerList.indexOf(t));
        }
    }
}