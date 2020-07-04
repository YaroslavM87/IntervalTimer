package com.example.intervaltimer.activityElements;

import java.util.ArrayList;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.intervaltimer.R;
import com.example.intervaltimer.timer.Timer;
import com.example.intervaltimer.timer.TimerType;


public class AdptForTimListInCrtActvt extends RecyclerView.Adapter<AdptForTimListInCrtActvt.TimerViewHolder> {

    class TimerViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView textViewForTimerValue;
        TextView textViewForTimerType;


        TimerViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewForTimerValue = (TextView) itemView.findViewById(R.id.timerValueInCreate);
            textViewForTimerType = (TextView) itemView.findViewById(R.id.timerTypeInCreate);
            textViewForTimerValue.setOnClickListener(this);
            textViewForTimerType.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (onEntryClickListener != null) {
                onEntryClickListener.onEntryClick(v, getLayoutPosition());
            }
        }
    }

    private ArrayList<Timer> timerList;
    private OnEntryClickListener onEntryClickListener;
    private int layout;
    private int displayWidth;

    public AdptForTimListInCrtActvt(ArrayList<Timer> timerList, int layout) {
        this.timerList = timerList;
        this.layout = layout;
    }

    @NonNull
    @Override
    public AdptForTimListInCrtActvt.TimerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(layout, parent, false);
        return new TimerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdptForTimListInCrtActvt.TimerViewHolder holder, int position) {
        Timer userTimer = timerList.get(position);

        setAppropriateLayoutParams(userTimer, holder.textViewForTimerValue);
        setAppropriateLayoutParams(userTimer, holder.textViewForTimerType);

        if(userTimer.getType() == TimerType.REST_TIME) {
            holder.textViewForTimerType.setText(R.string.forViewInRVAdapterInActivityCreate);

        } else {

            if(userTimer.getType() == TimerType.TIME_TO_START) {
                holder.textViewForTimerType.setText(userTimer.getType().toString(true));

            } else {
                holder.textViewForTimerType.setText(userTimer.getNumberOfGroupOfTimers(userTimer.getId()));
            }
        }

        holder.textViewForTimerValue.setText(userTimer.toString(1));
    }

    @Override
    public int getItemCount() {
        return timerList.size();
    }

    public interface OnEntryClickListener {
        void onEntryClick(View view, int position);
    }

    public void setOnEntryClickListener(OnEntryClickListener onEntryClickListener) {
        this.onEntryClickListener = onEntryClickListener;
    }

    public void setTimerList(ArrayList<Timer> timerList) {
        this.timerList = timerList;
    }

    private void setAppropriateLayoutParams(Timer timer, TextView textView) {

        int layoutWidth = (displayWidth / 34 * 20) / 2;

        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                layoutWidth, LinearLayout.LayoutParams.WRAP_CONTENT
        );

        int left;
        int top;
        int right;
        int bottom;

        switch(timer.getType()) {

            case TIME_TO_START:
                top = 0;
                bottom = 0;

                if(textView.getId() == R.id.timerTypeInCreate) {
                    left = 7;
                    right = 0;
                    layoutParams.setMargins(left, top , right, bottom);
                    textView.setLayoutParams(layoutParams);
                    textView.setBackgroundResource(R.drawable.rounded_corner_left);
                    textView.setTextAppearance(R.style.TextViewForTimerStartAndWorkTypes);

                } else {
                    left = 0;
                    right = 7;
                    layoutParams.setMargins(left, top , right, bottom);
                    textView.setLayoutParams(layoutParams);
                    textView.setBackgroundResource(R.drawable.rounded_corner_right);
                    textView.setTextAppearance(R.style.TextViewForTimerStartAndWorkTypes);
                }
                break;

            case WORK_TIME:
                top = 25;
                bottom = 0;

                if(textView.getId() == R.id.timerTypeInCreate) {
                    left = 7;
                    right = 0;
                    layoutParams.setMargins(left, top , right, bottom);
                    textView.setLayoutParams(layoutParams);
                    textView.setBackgroundResource(R.drawable.rounded_corner_top_left);
                    textView.setTextAppearance(R.style.TextViewForTimerStartAndWorkTypes);

                } else {
                    left = 0;
                    right = 7;
                    layoutParams.setMargins(left, top , right, bottom);
                    textView.setLayoutParams(layoutParams);
                    textView.setBackgroundResource(R.drawable.rounded_corner_top_right);
                    textView.setTextAppearance(R.style.TextViewForTimerStartAndWorkTypes);
                }
                break;

            case REST_TIME:
                top = 0;
                bottom = 0;

                if(textView.getId() == R.id.timerTypeInCreate) {
                    left = 7;
                    right = 0;
                    layoutParams.setMargins(left, top , right, bottom);
                    textView.setLayoutParams(layoutParams);
                    textView.setBackgroundResource(R.drawable.rounded_corner_bottom_left);
                    textView.setTextAppearance(R.style.TextViewForTimerRestType);

                } else {
                    left = 0;
                    right = 7;
                    layoutParams.setMargins(left, top , right, bottom);
                    textView.setLayoutParams(layoutParams);
                    textView.setBackgroundResource(R.drawable.rounded_corner_bottom_right);
                    textView.setTextAppearance(R.style.TextViewForTimerRestType);
                }
                break;
        }
    }

    public void setDisplayWidth(int displayWidth) {
        this.displayWidth = displayWidth;
    }
}