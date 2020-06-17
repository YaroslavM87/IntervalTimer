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
import com.example.intervaltimer.activityElements.TimerListAdapter;
import com.example.intervaltimer.timer.TimerUtil;

public class MainActivity extends AppCompatActivity {

    TimerUtil timerUtil;

    TextView textViewForTimerUnderConfig;

    ButtonSetTimer buttonSetTimer;
    Button buttonAddNewTimer;
    Button buttonTimerStart;
    Button buttonTimerStop;

    RecyclerView rvForTimer;
    TimerListAdapter adapter;
    LinearLayoutManager llm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textViewForTimerUnderConfig = (TextView) findViewById(R.id.textViewForTimerUnderConfig);

        timerUtil = new TimerUtil(textViewForTimerUnderConfig);

        rvForTimer = (RecyclerView) findViewById(R.id.recyclerView);
        adapter = new TimerListAdapter(timerUtil.getTimerList());
        rvForTimer.setAdapter(adapter);
        timerUtil.setTimerAdapter(adapter);
        llm = new LinearLayoutManager(this);
        rvForTimer.setLayoutManager(llm);

        buttonSetTimer = new ButtonSetTimer();
        buttonSetTimer.setButtonTimerMinIncrease((Button) findViewById(R.id.minIncrease));
        buttonSetTimer.setButtonTimerMinReduce((Button) findViewById(R.id.minReduce));
        buttonSetTimer.setButtonTimerSecIncrease((Button) findViewById(R.id.secIncrease));
        buttonSetTimer.setButtonTimerSecReduce((Button) findViewById(R.id.secReduce));

        buttonAddNewTimer = findViewById(R.id.addTimer);

        buttonTimerStart = findViewById(R.id.startButton);

        buttonTimerStop = findViewById(R.id.stopTimer);

    }

    public void addTimerToList(View buttonAddNewTimer) {
        timerUtil.addTimerToList();
        adapter.notifyDataSetChanged();
        //startButton.setClickable(false);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public void timerStart(View buttonTimerStart) {
        timerUtil.runTimer();
        //startButton.setClickable(false);
    }

    public void timerStop(View buttonTimerStop) {
        timerUtil.stopTimer();
        //startButton.setClickable(false);
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
