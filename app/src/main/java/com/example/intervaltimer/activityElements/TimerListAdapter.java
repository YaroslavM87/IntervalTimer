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



public class TimerListAdapter extends RecyclerView.Adapter<TimerListAdapter.TimerViewHolder> {

    private ArrayList<Timer> timerList;

    public  TimerListAdapter(ArrayList<Timer> timerList) {
        this.timerList = timerList;
    }

    static class TimerViewHolder extends RecyclerView.ViewHolder {

        TextView textViewForTimer;

        TimerViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewForTimer = (TextView) itemView.findViewById(R.id.item);
        }
    }

    @NonNull
    @Override
    public TimerListAdapter.TimerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.main_recycler_view, parent, false);
        return new TimerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TimerListAdapter.TimerViewHolder holder, int position) {
        Timer userTimer = timerList.get(position);
        holder.textViewForTimer.setText(userTimer.toString());
    }

    @Override
    public int getItemCount() {
        return timerList.size();
    }
}