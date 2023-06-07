package com.example.dbproject.homeApp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dbproject.R;
import com.example.dbproject.tabLayer.DBHelper;

import java.util.ArrayList;

public class NoticeAdapter extends RecyclerView.Adapter<NoticeAdapter.ViewHolder> {

    private ArrayList<NOTICE> noticeItems;
    private Context mContext;
    private DBHelper mDBHelper;

    public NoticeAdapter(ArrayList<NOTICE> noticeItems, Context mContext) {
        this.noticeItems = noticeItems;
        this.mContext = mContext;
        this.mDBHelper = new DBHelper(mContext);
    }

    @NonNull
    @Override
    public NoticeAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View holder = LayoutInflater.from(parent.getContext()).inflate(R.layout.notice_list, parent, false);
        return new ViewHolder(holder);
    }

    @Override
    public void onBindViewHolder(@NonNull NoticeAdapter.ViewHolder holder, int position) {
        holder.tv_title.setText(noticeItems.get(position).getTitle());
        holder.tv_content.setText(noticeItems.get(position).getContent());
        holder.tv_writer.setText(noticeItems.get(position).getWriter());
        holder.tv_date.setText(noticeItems.get(position).getDate());
    }

    @Override
    public int getItemCount() {
        return noticeItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView tv_title;
        private TextView tv_content;
        private TextView tv_writer;
        private TextView tv_date;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_title = itemView.findViewById(R.id.tv_history_id);
            tv_content = itemView.findViewById(R.id.tv_content);
            tv_writer = itemView.findViewById(R.id.tv_writer);
            tv_date = itemView.findViewById(R.id.tv_date);


        }
    }
}
