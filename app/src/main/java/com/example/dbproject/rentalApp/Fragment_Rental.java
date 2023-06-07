package com.example.dbproject.rentalApp;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ScrollView;
import android.widget.TextView;

import com.example.dbproject.R;
import com.example.dbproject.tabLayer.DBHelper;

import java.util.ArrayList;

public class Fragment_Rental extends Fragment {
    public static final String TITLE = "title";
    private DBHelper mDBHelper;
    private ScrollView rental_scrollview;
    private RecyclerView recyclerView_rental_title1;
    private RecyclerView recyclerView_rental_title2;
    private RecyclerView recyclerView_rental_title3;
    private ArrayList<ITEM_CATEGORY> mItem_CategoryItems_1;
    private ArrayList<ITEM_CATEGORY> mItem_CategoryItems_2;
    private ArrayList<ITEM_CATEGORY> mItem_CategoryItems_3;
    private CategoryAdapter categoryAdapter_1;
    private CategoryAdapter categoryAdapter_2;
    private CategoryAdapter categoryAdapter_3;
    private String location_1 = "총학생회";
    private String location_2 = "소프트웨어학부";
    private String location_3 = "소프트웨어학과";



    public Fragment_Rental() {
        // Required empty public constructor
    }

    public void setDbHelper(DBHelper dbHelper) {
        mDBHelper = dbHelper;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_rental, container, false);
        recyclerView_rental_title1 = view.findViewById(R.id.recyclerView_rental_title1);
        recyclerView_rental_title2 = view.findViewById(R.id.recyclerView_rental_title2);
        recyclerView_rental_title3 = view.findViewById(R.id.recyclerView_rental_title3);
        mItem_CategoryItems_1 = new ArrayList<>();
        mItem_CategoryItems_2 = new ArrayList<>();
        mItem_CategoryItems_3 = new ArrayList<>();
        rental_scrollview = view.findViewById(R.id.rental_scrollview);
        rental_scrollview.setSmoothScrollingEnabled(true);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        loadRecentDB();
    }

    private void loadRecentDB() {
        try {
            if (mDBHelper != null) {
                mItem_CategoryItems_1 = mDBHelper.getItem_Category_List(location_1);
                mItem_CategoryItems_2 = mDBHelper.getItem_Category_List(location_2);
                mItem_CategoryItems_3 = mDBHelper.getItem_Category_List(location_3);
                if (categoryAdapter_1 == null) {
                    categoryAdapter_1 = new CategoryAdapter(mItem_CategoryItems_1, getActivity().getApplicationContext());
                    recyclerView_rental_title1.setHasFixedSize(true);
                    recyclerView_rental_title1.setAdapter(categoryAdapter_1);
                }
                if (categoryAdapter_2 == null) {
                    categoryAdapter_2 = new CategoryAdapter(mItem_CategoryItems_2, getActivity().getApplicationContext());
                    recyclerView_rental_title2.setHasFixedSize(true);
                    recyclerView_rental_title2.setAdapter(categoryAdapter_2);
                }
                if (categoryAdapter_3 == null) {
                    categoryAdapter_3 = new CategoryAdapter(mItem_CategoryItems_3, getActivity().getApplicationContext());
                    recyclerView_rental_title3.setHasFixedSize(true);
                    recyclerView_rental_title3.setAdapter(categoryAdapter_3);
                }
            }
        } catch (Exception e) {
            // 예외 발생시 로그 출력
            Log.e("Fragment_Rental", "Error in loadRecentDB(): " + e.getMessage());
        }
    }


    @Override
    public void onDestroyView() {
        // Fragment가 더 이상 보이지 않을 때 DBHelper 객체 해제
        mDBHelper = null;
        super.onDestroyView();
    }
}
