package com.example.dbproject.rentalApp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dbproject.R;
import com.example.dbproject.proposalApp.PROPOSAL;
import com.example.dbproject.tabLayer.DBHelper;

import java.util.ArrayList;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ViewHolder> {


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
        this.categoryItems = categoryItems;
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
                String.valueOf(categoryItems.get(position).getItem_total_amount()) +
                " / " +
                String.valueOf(categoryItems.get(position).getItem_left_amount()));
    }

    @Override
    public int getItemCount() {
        return categoryItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView tv_rental_category_name;
        private TextView tv_rental_amount;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_rental_category_name = itemView.findViewById(R.id.tv_rental_category_name);
            tv_rental_amount = itemView.findViewById(R.id.tv_rental_amount);

        }
    }
}
