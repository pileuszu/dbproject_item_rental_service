package com.example.dbproject.reservationApp;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dbproject.R;
import com.example.dbproject.proposalApp.WriteActivity;
import com.example.dbproject.rentalApp.ITEM_CATEGORY;
import com.example.dbproject.tabLayer.DBHelper;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class ReservationAdapter extends RecyclerView.Adapter<ReservationAdapter.ViewHolder> {

    private Integer student_id = 190012345;
    private ArrayList<ITEM_CATEGORY> reservationItems;
    private Context mContext;
    private DBHelper mDBHelper;



    public ReservationAdapter(ArrayList<ITEM_CATEGORY> reservationItems, Context mContext) {
        this.reservationItems = reservationItems;
        this.mContext = mContext;
        this.mDBHelper = new DBHelper(mContext);
    }

    @NonNull
    @Override
    public ReservationAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View holder = LayoutInflater.from(parent.getContext()).inflate(R.layout.reservation_list, parent, false);
        return new ViewHolder(holder);
    }

    @Override
    public void onBindViewHolder(@NonNull ReservationAdapter.ViewHolder holder, int position) {
        holder.tv_reservation_category_name.setText(reservationItems.get(position).getItem_category());
        final int _position = position;
        holder.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), ScheduleActivity.class);
                intent.putExtra("item_category", reservationItems.get(_position).getItem_category());
                intent.putExtra("item_location", reservationItems.get(_position).getItem_location());
                v.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return reservationItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView tv_reservation_category_name;
        private Button button;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_reservation_category_name = itemView.findViewById(R.id.tv_reservation_category_name);
            button = itemView.findViewById(R.id.reservation_borrow_button);
        }
    }
}
