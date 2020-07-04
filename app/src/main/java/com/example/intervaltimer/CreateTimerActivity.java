package com.example.intervaltimer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import com.example.intervaltimer.activityElements.ButtonSetTimer;
import com.example.intervaltimer.activityElements.AdptForTimListInCrtActvt;
import com.example.intervaltimer.timer.TimerCreateUtil;

import java.util.Objects;

public class CreateTimerActivity extends AppCompatActivity {

    TextView typeOfTimerUnderConfig;
    TextView idOfTimerUnderConfig;
    TextView timeOfTimerUnderConfig;
    ButtonSetTimer buttonSetTimer;
    Button buttonAddTimerApplyChange;
    Button buttonTimerDelete;
    Button buttonResetList;
    Button buttonTimerStart;
    TimerCreateUtil timerCreateUtil;
    RecyclerView recyclerViewForTimer;
    AdptForTimListInCrtActvt adapterForRecyclerView;
    LinearLayoutManager layoutManagerForRecyclerView;
    int layoutForRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_timer);
        Objects.requireNonNull(getSupportActionBar()).hide();

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
        timerCreateUtil = new TimerCreateUtil(typeOfTimerUnderConfig, idOfTimerUnderConfig, timeOfTimerUnderConfig);
        adapterForRecyclerView = new AdptForTimListInCrtActvt(timerCreateUtil.getTimerList(), layoutForRecyclerView);
        layoutManagerForRecyclerView = new LinearLayoutManager(this);
    }

    private void initializeButtons() {
        buttonSetTimer = new ButtonSetTimer();
        buttonSetTimer.setButtonTimerMinIncrease((Button) findViewById(R.id.minIncrease));
        buttonSetTimer.setButtonTimerMinReduce((Button) findViewById(R.id.minReduce));
        buttonSetTimer.setButtonTimerSecIncrease((Button) findViewById(R.id.secIncrease));
        buttonSetTimer.setButtonTimerSecReduce((Button) findViewById(R.id.secReduce));
        buttonAddTimerApplyChange = findViewById(R.id.addTimerApplyChange);
        buttonTimerStart = findViewById(R.id.startTimer);
        buttonResetList = findViewById(R.id.resetList);
        buttonTimerDelete = findViewById(R.id.deleteTimer);
    }

    private void initializeTextViews() {
        timeOfTimerUnderConfig = (TextView) findViewById(R.id.timeOfTimerUnderConfig);
        typeOfTimerUnderConfig = (TextView) findViewById(R.id.typeOfTimerUnderConfig);
        idOfTimerUnderConfig = (TextView) findViewById(R.id.idOfTimerUnderConfig);
    }

    private void setObjects() {
        adapterForRecyclerView.setDisplayWidth(getDisplayWidth(this));
        recyclerViewForTimer.setAdapter(adapterForRecyclerView);
        recyclerViewForTimer.setLayoutManager(layoutManagerForRecyclerView);
        timerCreateUtil.setAdapter(adapterForRecyclerView);
        buttonAddTimerApplyChange.setText(R.string.addTimer);
    }

    public void addTimerToList(View buttonAddNewTimer) {
        timerCreateUtil.addTimerInList();
        if(buttonAddTimerApplyChange.getText().toString().contentEquals(getResources().getText(R.string.applyChange))) {
            buttonAddTimerApplyChange.setText(R.string.addTimer);
        }
    }

    public void timerStart(View buttonTimerStart) {
        if(timerCreateUtil.isReadyToStartTimerSet()) {
            Intent intent = new Intent(this, RunTimerActivity.class);
            startActivity(intent);
        }
    }

    public void timerDelete(View buttonTimerDelete) {
        timerCreateUtil.deletePairOfTimers();
        if(buttonAddTimerApplyChange.getText().toString().contentEquals(getResources().getText(R.string.applyChange))) {
            buttonAddTimerApplyChange.setText(R.string.addTimer);
        }
    }

    public void resetList(View buttonResetList) {
       timerCreateUtil.resetTimerList();
        if(buttonAddTimerApplyChange.getText().toString().contentEquals(getResources().getText(R.string.applyChange))) {
            buttonAddTimerApplyChange.setText(R.string.addTimer);
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
            adapterForRecyclerView.setOnEntryClickListener(new AdptForTimListInCrtActvt.OnEntryClickListener(){
                @Override
                public void onEntryClick(View view, int position) {
                    timerCreateUtil.chooseTimerForConfiguration(position);
                    buttonAddTimerApplyChange.setText(R.string.applyChange);
                }
            });
        }
    }

    public int getDisplayWidth(Activity activity) {
        int width = 0;
        if (activity != null && activity.getWindowManager() != null && activity.getWindowManager().getDefaultDisplay() != null) {
            Point point=new Point();
            activity.getWindowManager().getDefaultDisplay().getSize(point);
            width = point.x;
        }
        return width;
    }
}

//buttonResetList.setEnabled(false);