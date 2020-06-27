package com.example.intervaltimer.activityElements;

import java.util.ArrayList;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.intervaltimer.R;
import com.example.intervaltimer.timer.Timer;



public class AdapterForTimerList extends RecyclerView.Adapter<AdapterForTimerList.TimerViewHolder> {

    class TimerViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView textViewForTimer;
        TextView textViewForTimerIdAndType;

        TimerViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewForTimer = (TextView) itemView.findViewById(R.id.timerValue);
            textViewForTimerIdAndType = (TextView) itemView.findViewById(R.id.timerIdAndType);
            textViewForTimer.setOnClickListener(this);
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

    public AdapterForTimerList(ArrayList<Timer> timerList, int layout) {
        this.timerList = timerList;
        this.layout = layout;
    }

    @NonNull
    @Override
    public AdapterForTimerList.TimerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(layout, parent, false);
        return new TimerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterForTimerList.TimerViewHolder holder, int position) {
        Timer userTimer = timerList.get(position);
        holder.textViewForTimer.setText(userTimer.toString());
        String timerIdAndType = userTimer.getNumberOfGroupOfTimersAsString().concat(userTimer.getType().toString());
        holder.textViewForTimerIdAndType.setText(timerIdAndType);
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
}