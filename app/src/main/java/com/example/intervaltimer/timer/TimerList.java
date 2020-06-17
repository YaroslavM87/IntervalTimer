package com.example.intervaltimer.timer;

import java.util.ArrayList;

class TimerList {

    private ArrayList<Timer> timerList;

    TimerList() {
        timerList = new ArrayList<Timer>();
    }

    ArrayList<Timer> getTimerList() {
        return timerList;
    }

    void setTimerList(ArrayList<Timer> list) {
        timerList = list;
    }

    void addTimerInList(int index, Timer timer) {
        timerList.add(index, timer);
    }

    Timer getTimer(int index) {
        return timerList.get(index);
    }

    int getIndexOfTimer(Timer timer) {
        return timerList.indexOf(timer);
    }

    void removeTimerFromList(int index) {
        timerList.remove(index);
    }

    void removeTimerFromList(Timer timer) {
        timerList.remove(timer);
    }

    int getSizeOfTimerList() {
        return timerList.size();
    }

    void clearTimerList() {
        timerList.clear();
    }

    boolean timerListIsNotEmpty() {
        return !timerList.isEmpty();
    }
}