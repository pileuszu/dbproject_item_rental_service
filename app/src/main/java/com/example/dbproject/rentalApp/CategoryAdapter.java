package com.example.dbproject.rentalApp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dbproject.R;
import com.example.dbproject.tabLayer.DBHelper;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ViewHolder> {

    private Integer student_id = 190012345;
    private ArrayList<ITEM_CATEGORY> categoryItems;
    private Context mContext;
    private DBHelper mCategoryDBHelper;

    public CategoryAdapter(ArrayList<ITEM_CATEGORY> categoryItems, Context mContext) {
        this.categoryItems = categoryItems;
        this.mContext = mContext;
        this.mCategoryDBHelper = new DBHelper(mContext);
    }

    // Method to update the data in the adapter
    public void setCategoryItems(ArrayList<ITEM_CATEGORY> categoryItems) {
        this.categoryItems.clear();
        this.categoryItems.addAll(categoryItems);
        notifyDataSetChanged();
    }



    @NonNull
    @Override
    public CategoryAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View holder = LayoutInflater.from(parent.getContext()).inflate(R.layout.rental_list, parent, false);
        return new ViewHolder(holder);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryAdapter.ViewHolder holder, int position) {
        holder.tv_rental_category_name.setText(categoryItems.get(position).getItem_category());
        holder.tv_rental_amount.setText("총 개수 / 남은 개수 : " +
                categoryItems.get(position).getItem_total_amount() +
                " / " +
                categoryItems.get(position).getItem_left_amount());

        final int _position = position;
        holder.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(categoryItems.get(_position).getItem_left_amount() > 0) {
                    // 대여 작업을 위한 DB 업데이트 등의 코드 실행
                    // 예시로 DBHelper를 사용한 DB 업데이트
                    Integer itemId = mCategoryDBHelper.selectItemId_For_RentalItem(categoryItems.get(_position).getItem_category(), categoryItems.get(_position).getItem_location());
                    mCategoryDBHelper.updateItem("대여중", itemId);
                    mCategoryDBHelper.InsertRental(student_id, itemId, getCurrentDate(), getNextDate(), null, 0, "대여중");
                    Toast.makeText(mContext, "대여가 완료되었습니다.", Toast.LENGTH_SHORT).show();
                    notifyDataSetChanged();
                } else {
                    Toast.makeText(mContext, "대여 가능한 개수를 초과하였습니다.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return categoryItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView tv_rental_category_name;
        private TextView tv_rental_amount;
        private Button button;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_rental_category_name = itemView.findViewById(R.id.tv_rental_category_name);
            tv_rental_amount = itemView.findViewById(R.id.tv_rental_amount);
            button = itemView.findViewById(R.id.button);
        }
    }

    private String getCurrentDate() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        Date currentDate = new Date();
        return dateFormat.format(currentDate);
    }
    private String getNextDate() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_YEAR, 1);  // 현재 날짜에 1일을 더합니다.
        Date nextDate = calendar.getTime();
        return dateFormat.format(nextDate);
    }


    private String getCurrentTime() {
        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm", Locale.getDefault());
        Date currentTime = new Date();
        return timeFormat.format(currentTime);
    }
}
