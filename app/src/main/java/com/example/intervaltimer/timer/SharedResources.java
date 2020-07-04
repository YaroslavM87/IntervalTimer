package com.example.intervaltimer.timer;

import android.widget.TextView;

import java.util.ArrayList;

class SharedResources {

    private static ArrayList<Timer> timerList;

    static {
        timerList = new ArrayList<>();
    }

    private SharedResources() {}

    static ArrayList<Timer> getCopyOfTimerList(TextView type, TextView id, TextView timeValue) {
        ArrayList<Timer> copyOfTimerList = new ArrayList<>();
        if(!timerList.isEmpty()) {
            for(Timer t : timerList) {
                Timer newTimer = new Timer (t.getLengthAtTheMoment(), t.getType(), t.getId());
                newTimer.setTextViewForTimer(new TextViewForTimer(type, id, timeValue));
                copyOfTimerList.add(timerList.indexOf(t), newTimer);

            }  return copyOfTimerList;

        } else {
            return  getNewTimerList();
        }
    }

    static ArrayList<Timer> getTimerList() {
        return timerList;
    }

    static ArrayList<Timer> getNewTimerList() {
        return new ArrayList<>();
    }

    static boolean setTimerList(ArrayList<Timer> list) {
        timerList.clear();
        return timerList.addAll(list);
    }
}
