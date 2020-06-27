package com.example.intervaltimer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.intervaltimer.activityElements.AdapterForTimerList;
import com.example.intervaltimer.timer.Timer;
import com.example.intervaltimer.timer.TimerCreateUtil;
import com.example.intervaltimer.timer.TimerRunUtil;

import java.util.ArrayList;

public class RunTimerActivity extends AppCompatActivity {

    TimerRunUtil timerRunUtil;
    TextView idAndTypeOfOperatedTimer;
    TextView timeOfOperatedTimer;
    Button buttonTimerStop;
    RecyclerView recyclerViewForTimer;
    AdapterForTimerList adapterForRecyclerView;
    LinearLayoutManager layoutManagerForRecyclerView;
    int layoutForRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_run_timer);

        initializeActivityElements();
        initializeObjects();
        setObjects();
        performActivityMainTask();
    }

    private void initializeActivityElements() {
        initializeTextViews();
        initializeButtons();
        recyclerViewForTimer = (RecyclerView) findViewById(R.id.runTimerRecyclerView);
        layoutForRecyclerView = R.layout.run_timer_recycler_view;
    }

    private void initializeObjects() {
        timerRunUtil = new TimerRunUtil(idAndTypeOfOperatedTimer, timeOfOperatedTimer);
        adapterForRecyclerView = new AdapterForTimerList(timerRunUtil.getTimerList(), layoutForRecyclerView);
        layoutManagerForRecyclerView = new LinearLayoutManager(this);
    }

    private void setObjects() {
        recyclerViewForTimer.setAdapter(adapterForRecyclerView);
        recyclerViewForTimer.setLayoutManager(layoutManagerForRecyclerView);
        timerRunUtil.setAdapter(adapterForRecyclerView);
        timerRunUtil.setRunTimerActivity(this);
    }

    private void initializeButtons() {
        buttonTimerStop = findViewById(R.id.stopTimer);
    }

    private void initializeTextViews() {
        timeOfOperatedTimer = (TextView) findViewById(R.id.timeOfOperatedTimer);
        idAndTypeOfOperatedTimer = (TextView) findViewById(R.id.idAndTypeOfOperatedTimer);
    }

    public void performActivityMainTask() {
        timerRunUtil.launchSetOfTimers();
    }

    public void timerStop(View buttonTimerStop) {
        timerRunUtil.stopTimerAndResetTimerList();
        Intent intent = new Intent(this, CreateTimerActivity.class);
        startActivity(intent);
    }

    public void timerOnFinishAction() {
        Intent intent = new Intent(this, CreateTimerActivity.class);
        startActivity(intent);
    }
}