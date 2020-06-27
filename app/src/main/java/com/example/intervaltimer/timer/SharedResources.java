package com.example.intervaltimer.timer;

import android.widget.TextView;

import java.util.ArrayList;

public class SharedResources {

    private static ArrayList<Timer> timerList;
    private static boolean canTimerBeRun;

    static {
        timerList = new ArrayList<>();
        canTimerBeRun = false;
    }

    private SharedResources() {}

    static ArrayList<Timer> getCopyOfTimerList(TextView idAndType, TextView timeValue) {
        ArrayList<Timer> copyOfTimerList = new ArrayList<>();
        if(!timerList.isEmpty()) {
            for(Timer t : timerList) {
                Timer newTimer = new Timer (t.getLengthAtTheMoment(), t.getType(), t.getId());
                newTimer.setTextViewForTimer(new TextViewForTimer(idAndType, timeValue));
                copyOfTimerList.add(timerList.indexOf(t), newTimer);

            }
        }
        return copyOfTimerList;
    }

    static void setTimerList(ArrayList<Timer> list) {
        timerList.clear();
        timerList.addAll(list);
    }

     static void setCanTimerBeRun(boolean value) {
        canTimerBeRun = value;
    }

    public static boolean canTimerBeRun() {
        return canTimerBeRun;
    }
}
