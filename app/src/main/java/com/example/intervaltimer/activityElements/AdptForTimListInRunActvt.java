package com.example.intervaltimer.activityElements;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.intervaltimer.R;
import com.example.intervaltimer.timer.Timer;
import java.util.ArrayList;

public class AdptForTimListInRunActvt extends RecyclerView.Adapter<AdptForTimListInRunActvt.TimerViewHolder> {

    static class TimerViewHolder extends RecyclerView.ViewHolder {

        TextView textViewForTimerType;
        TextView textViewForTimerId;
        TextView textViewForTimerValue;

        TimerViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewForTimerType = (TextView) itemView.findViewById(R.id.timerTypeInRun);
            textViewForTimerId = (TextView) itemView.findViewById(R.id.timerIdInRun);
            textViewForTimerValue = (TextView) itemView.findViewById(R.id.timerValueInRun);
        }
    }

    private ArrayList<Timer> timerList;
    private int layout;

    public AdptForTimListInRunActvt(ArrayList<Timer> timerList, int layout) {
        this.timerList = timerList;
        this.layout = layout;
    }

    @NonNull
    @Override
    public AdptForTimListInRunActvt.TimerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(layout, parent, false);
        return new TimerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdptForTimListInRunActvt.TimerViewHolder holder, int position) {
        Timer userTimer = timerList.get(position);
        holder.textViewForTimerType.setText(userTimer.getType().toString(true));
        holder.textViewForTimerId.setText(userTimer.getNumberOfGroupOfTimers(userTimer.getId()));
        holder.textViewForTimerValue.setText(userTimer.toString(2));
    }

    @Override
    public int getItemCount() {
        return timerList.size();
    }
}