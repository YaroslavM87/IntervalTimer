package com.example.intervaltimer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.widget.Button;
import android.widget.TextView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.intervaltimer.activityElements.AdptForTimListInCrtActvt;
import com.example.intervaltimer.activityElements.AdptForTimListInRunActvt;
import com.example.intervaltimer.timer.TimerRunUtil;

import java.util.Objects;

public class RunTimerActivity extends AppCompatActivity {

    TimerRunUtil timerRunUtil;

    TextView typeOfOperatedTimer;
    TextView idOfOperatedTimer;
    TextView timeOfOperatedTimer;
    Button buttonTimerStop;

    RecyclerView recyclerViewForTimer;
    AdptForTimListInRunActvt adapterForRecyclerView;
    LinearLayoutManager layoutManagerForRecyclerView;
    int layoutForRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_run_timer);
        Objects.requireNonNull(getSupportActionBar()).hide();

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
        timerRunUtil = new TimerRunUtil(typeOfOperatedTimer, idOfOperatedTimer, timeOfOperatedTimer);
        adapterForRecyclerView = new AdptForTimListInRunActvt(timerRunUtil.getTimerList(), layoutForRecyclerView);
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

        typeOfOperatedTimer = (TextView) findViewById(R.id.typeOfOperatedTimer);
        idOfOperatedTimer = (TextView) findViewById(R.id.idOfOperatedTimer);
        timeOfOperatedTimer = (TextView) findViewById(R.id.timeOfOperatedTimer);
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