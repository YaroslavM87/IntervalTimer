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
    TextView timeOfTimerUnderConfig;
    TextView idAndTypeOfTimerUnderConfig;
    ButtonSetTimer buttonSetTimer;
    Button buttonAddNewTimer;
    Button buttonTimerStart;
    Button buttonTimerStop;
    Button buttonTimerDelete;
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
                //adapterForRecyclerView.notifyDataSetChanged();
            }
        });

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
        recyclerViewForTimer = (RecyclerView) findViewById(R.id.recyclerView);
    }

    private void initializeObjects() {
        timerUtil = new TimerUtil(idAndTypeOfTimerUnderConfig, timeOfTimerUnderConfig);
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
        buttonTimerDelete = findViewById(R.id.deleteTimer);
    }

    private void initializeTextViews() {
        timeOfTimerUnderConfig = (TextView) findViewById(R.id.timeOfTimerUnderConfig);
        idAndTypeOfTimerUnderConfig = (TextView) findViewById(R.id.idAndTypeOfTimerUnderConfig);
    }

    private void setObjects() {
        recyclerViewForTimer.setAdapter(adapterForRecyclerView);
        recyclerViewForTimer.setLayoutManager(layoutManagerForRecyclerView);
        timerUtil.setAdapter(adapterForRecyclerView);
    }

    public void addTimerToList(View buttonAddNewTimer) {
        if(!timerUtil.isTimerRunning()) {
            timerUtil.addTimerToList();
        }
    }

    //@RequiresApi(api = Build.VERSION_CODES.M)
    public void timerStart(View buttonTimerStart) {
        timerUtil.launchSetOfTimers();
    }

    public void timerStop(View buttonTimerStop) {
        timerUtil.stopTimerAndResetTimerList();
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

    public void timerDelete(View buttonTimerDelete) {
        timerUtil.deleteTimerUnderConfig();
    }
}