package com.example.dbproject.historyApp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dbproject.tabLayer.DBHelper;
import com.example.dbproject.R;

import java.util.ArrayList;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.ViewHolder> {

    private ArrayList<HISTORY> historyItems;
    private Context mContext;
    private DBHelper mHistoryDBHelper;

    public HistoryAdapter(ArrayList<HISTORY> historyItems, Context mContext) {
        this.historyItems = historyItems;
        this.mContext = mContext;
        this.mHistoryDBHelper = new DBHelper(mContext);
    }

    @NonNull
    @Override
    public HistoryAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View holder = LayoutInflater.from(parent.getContext()).inflate(R.layout.history_list, parent, false);
        return new ViewHolder(holder);
    }

    @Override
    public void onBindViewHolder(@NonNull HistoryAdapter.ViewHolder holder, int position) {
        holder.tv_history_id.setText(String.valueOf(historyItems.get(position).getHistory_id()));
        holder.tv_history_item_id.setText(String.valueOf(historyItems.get(position).getHistory_item_id()));
        holder.tv_history_item_name.setText(historyItems.get(position).getHistory_item_name());
        holder.tv_history_rental_start_date.setText(historyItems.get(position).getHistory_start_date());
        holder.tv_history_rental_return_date.setText(historyItems.get(position).getHistory_return_date());
        holder.tv_history_rental_state.setText(historyItems.get(position).getHistory_return_state());
    }

    @Override
    public int getItemCount() {
        return historyItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView tv_history_id;
        private TextView tv_history_item_id;
        private TextView tv_history_item_name;
        private TextView tv_history_rental_start_date;
        private TextView tv_history_rental_return_date;
        private TextView tv_history_rental_state;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_history_id = itemView.findViewById(R.id.tv_history_id);
            tv_history_item_id = itemView.findViewById(R.id.tv_history_item_id);
            tv_history_item_name = itemView.findViewById(R.id.tv_history_item_name);
            tv_history_rental_start_date = itemView.findViewById(R.id.tv_history_rental_start_date);
            tv_history_rental_return_date = itemView.findViewById(R.id.tv_history_rental_return_date);
            tv_history_rental_state = itemView.findViewById(R.id.tv_history_rental_state);
        }
    }
}
