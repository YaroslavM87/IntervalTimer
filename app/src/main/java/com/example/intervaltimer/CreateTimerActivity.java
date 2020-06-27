package com.example.intervaltimer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import com.example.intervaltimer.activityElements.ButtonSetTimer;
import com.example.intervaltimer.activityElements.AdapterForTimerList;
import com.example.intervaltimer.timer.SharedResources;
import com.example.intervaltimer.timer.TimerCreateUtil;

public class CreateTimerActivity extends AppCompatActivity {

    TimerCreateUtil timerCreateUtil;
    TextView idAndTypeOfTimerUnderConfig;
    TextView timeOfTimerUnderConfig;
    ButtonSetTimer buttonSetTimer;
    Button buttonAddNewTimer;
    Button buttonTimerStart;
    Button buttonTimerStop;
    Button buttonTimerDelete;
    RecyclerView recyclerViewForTimer;
    AdapterForTimerList adapterForRecyclerView;
    LinearLayoutManager layoutManagerForRecyclerView;
    int layoutForRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_timer);

        initializeActivityElements();
        initializeObjects();
        setObjects();

        performActionWhenClickRecyclerViewItem();

        buttonSetTimer.getButtonTimerMinIncrease().setOnLongClickListener(new View.OnLongClickListener(){
            @Override
            public boolean onLongClick(View v) {
                return false;
            }
        });

    }

    private void initializeActivityElements() {
        initializeButtons();
        initializeTextViews();
        recyclerViewForTimer = (RecyclerView) findViewById(R.id.createTimerRecyclerView);
        layoutForRecyclerView = R.layout.create_timer_recycler_view;
    }

    private void initializeObjects() {
        timerCreateUtil = new TimerCreateUtil(idAndTypeOfTimerUnderConfig, timeOfTimerUnderConfig);
        adapterForRecyclerView = new AdapterForTimerList(timerCreateUtil.getTimerList(), layoutForRecyclerView);
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
        buttonTimerDelete = findViewById(R.id.deleteTimer);
    }

    private void initializeTextViews() {
        timeOfTimerUnderConfig = (TextView) findViewById(R.id.timeOfTimerUnderConfig);
        idAndTypeOfTimerUnderConfig = (TextView) findViewById(R.id.idAndTypeOfTimerUnderConfig);
    }

    private void setObjects() {
        recyclerViewForTimer.setAdapter(adapterForRecyclerView);
        recyclerViewForTimer.setLayoutManager(layoutManagerForRecyclerView);
        timerCreateUtil.setAdapter(adapterForRecyclerView);
    }

    public void addTimerToList(View buttonAddNewTimer) {
        timerCreateUtil.addTimerInList();

//        if(!timerCreateUtil.canTimerBeRun()) {
//            timerCreateUtil.addTimerInList();
//        }
    }

    public void timerStart(View buttonTimerStart) {
        if(timerCreateUtil.canTimerBeRun()) {
            timerCreateUtil.updateTimerListInSharedResources();
            Intent intent = new Intent(this, RunTimerActivity.class);
            startActivity(intent);
        }
    }

    public void timerMinReduce(View buttonTimerSet) {
        timerCreateUtil.configureTimer(-60000L);
    }

    public void timerMinIncrease(View buttonTimerStart) {
        timerCreateUtil.configureTimer(60000L);
    }

    public void timerSecReduce(View buttonTimerSet) {
        timerCreateUtil.configureTimer(-1000L);
    }

    public void timerSecIncrease(View buttonTimerStart) {
        timerCreateUtil.configureTimer(1000L);
    }

    public void performActionWhenClickRecyclerViewItem() {
        if(this.getClass().getSimpleName().equals("CreateTimerActivity")) {
            adapterForRecyclerView.setOnEntryClickListener(new AdapterForTimerList.OnEntryClickListener(){
                @Override
                public void onEntryClick(View view, int position) {
                    timerCreateUtil.chooseTimerForConfiguration(position);
                }
            });
        }
    }

    public void timerDelete(View buttonTimerDelete) {
        timerCreateUtil.deleteTimerUnderConfig();
    }
}