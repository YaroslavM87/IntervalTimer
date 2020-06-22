package com.example.intervaltimer.timer;

import java.util.ArrayList;

class TimerList {


    private ArrayList<Timer> timerList;


    TimerList() {
        timerList = new ArrayList<>();
    }


    ArrayList<Timer> getTimerList() {
        return timerList;
    }

    void setTimerList(ArrayList<Timer> list) {
        timerList = list;
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
        timerList.remove(index);
        updateIdOfTimersInList();
    }

    void removeTimerFromList(Timer timer) {
        timerList.remove(timer);
        updateIdOfTimersInList();
    }

    private void updateIdOfTimersInList() {
        for(Timer t : timerList) {
            t.setTimerId(timerList.indexOf(t));
        }
    }
}