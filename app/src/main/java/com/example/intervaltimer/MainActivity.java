package com.example.intervaltimer;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import com.example.intervaltimer.activityElements.ButtonSetTimer;
import com.example.intervaltimer.activityElements.AdapterForTimerList;
import com.example.intervaltimer.timer.TimerUtil;

public class MainActivity extends AppCompatActivity {

    TimerUtil timerUtil;
    TextView textViewForTimerUnderConfig;
    ButtonSetTimer buttonSetTimer;
    Button buttonAddNewTimer;
    Button buttonTimerStart;
    Button buttonTimerStop;
    RecyclerView recyclerViewForTimer;
    AdapterForTimerList adapterForRecyclerView;
    LinearLayoutManager layoutManagerForRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initializeActivityElements();
        initializeObjects();
        setObjects();

        adapterForRecyclerView.setOnEntryClickListener(new AdapterForTimerList.OnEntryClickListener(){
            @Override
            public void onEntryClick(View view, int position) {
                timerUtil.chooseTimerForConfiguration(position);
                adapterForRecyclerView.notifyDataSetChanged();
            }
        });
    }

    private void initializeActivityElements() {
        initializeButtons();
        initializeTextViews();
        recyclerViewForTimer = (RecyclerView) findViewById(R.id.recyclerView);
    }

    private void initializeObjects() {
        timerUtil = new TimerUtil(textViewForTimerUnderConfig);
        adapterForRecyclerView = new AdapterForTimerList(timerUtil.getTimerList());
        layoutManagerForRecyclerView = new LinearLayoutManager(this);
    }

    private void initializeButtons() {
        buttonSetTimer = new ButtonSetTimer();
        buttonSetTimer.setButtonTimerMinIncrease((Button) findViewById(R.id.minIncrease));
        buttonSetTimer.setButtonTimerMinReduce((Button) findViewById(R.id.minReduce));
        buttonSetTimer.setButtonTimerSecIncrease((Button) findViewById(R.id.secIncrease));
        buttonSetTimer.setButtonTimerSecReduce((Button) findViewById(R.id.secReduce));
        buttonAddNewTimer = findViewById(R.id.addTimer);
        buttonTimerStart = findViewById(R.id.startTimer);
        buttonTimerStop = findViewById(R.id.stopTimer);
    }

    private void initializeTextViews() {
        textViewForTimerUnderConfig = (TextView) findViewById(R.id.textViewForTimerUnderConfig);
    }

    private void setObjects() {
        recyclerViewForTimer.setAdapter(adapterForRecyclerView);
        recyclerViewForTimer.setLayoutManager(layoutManagerForRecyclerView);
        timerUtil.setTimerAdapter(adapterForRecyclerView);
    }

    public void addTimerToList(View buttonAddNewTimer) {
        timerUtil.addTimerToList();
        adapterForRecyclerView.notifyDataSetChanged();
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public void timerStart(View buttonTimerStart) {
        timerUtil.runTimer();
    }

    public void timerStop(View buttonTimerStop) {
        timerUtil.stopTimer();
    }

    public void timerMinReduce(View buttonTimerSet) {
        timerUtil.configureTimer(-60000L);
    }

    public void timerMinIncrease(View buttonTimerStart) {
        timerUtil.configureTimer(60000L);
    }

    public void timerSecReduce(View buttonTimerSet) {
        timerUtil.configureTimer(-1000L);
    }

    public void timerSecIncrease(View buttonTimerStart) {
        timerUtil.configureTimer(1000L);
    }
}