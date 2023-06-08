package com.example.dbproject.reservationApp;

import android.content.IntentFilter;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ScrollView;
import android.widget.TextView;

import com.example.dbproject.R;
import com.example.dbproject.rentalApp.ITEM_CATEGORY;
import com.example.dbproject.tabLayer.DBHelper;

import java.util.ArrayList;

public class  Fragment_Reservation extends Fragment {
    public static final String TITLE = "title";
    private DBHelper mDBHelper;
    private ScrollView reservation_scrollview;
    private RecyclerView recyclerView_reservation_title1;
    private RecyclerView recyclerView_reservation_title2;
    private RecyclerView recyclerView_reservation_title3;
    private ArrayList<ITEM_CATEGORY> mItem_ReservationItems_1;
    private ArrayList<ITEM_CATEGORY> mItem_ReservationItems_2;
    private ArrayList<ITEM_CATEGORY> mItem_ReservationItems_3;
    private ReservationAdapter reservationAdapter_1;
    private ReservationAdapter reservationAdapter_2;
    private ReservationAdapter reservationAdapter_3;
    private String location_1 = "총학생회";
    private String location_2 = "소프트웨어학부";
    private String location_3 = "소프트웨어학과";
    private Button reservation_re_button;
    public Fragment_Reservation() {
        // Required empty public constructor
    }

    public void setDbHelper(DBHelper dbHelper) {
        mDBHelper = dbHelper;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_reservation, container, false);
        recyclerView_reservation_title1 = view.findViewById(R.id.recyclerView_reservation_title1);
        recyclerView_reservation_title2 = view.findViewById(R.id.recyclerView_reservation_title2);
        recyclerView_reservation_title3 = view.findViewById(R.id.recyclerView_reservation_title3);
        mItem_ReservationItems_1 = new ArrayList<>();
        mItem_ReservationItems_2 = new ArrayList<>();
        mItem_ReservationItems_3 = new ArrayList<>();
        reservation_scrollview = view.findViewById(R.id.reservation_scrollview);
        reservation_scrollview.setSmoothScrollingEnabled(true);
        loadRecentDB();
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        reservation_re_button = view.findViewById(R.id.reservation_re_button);
        reservation_re_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadRecentDB();
            }
        });
    }

    protected void loadRecentDB() {
        try {
            if (mDBHelper != null) {
                mItem_ReservationItems_1 = mDBHelper.getReservationItem_Category_List(location_1);
                mItem_ReservationItems_2 = mDBHelper.getReservationItem_Category_List(location_2);
                mItem_ReservationItems_3 = mDBHelper.getReservationItem_Category_List(location_3);
                if (reservationAdapter_1 == null) {
                    reservationAdapter_1 = new ReservationAdapter(mItem_ReservationItems_1, getActivity().getApplicationContext());
                    recyclerView_reservation_title1.setHasFixedSize(true);
                    recyclerView_reservation_title1.setAdapter(reservationAdapter_1);
                }
                if (reservationAdapter_2 == null) {
                    reservationAdapter_2 = new ReservationAdapter(mItem_ReservationItems_2, getActivity().getApplicationContext());
                    recyclerView_reservation_title2.setHasFixedSize(true);
                    recyclerView_reservation_title2.setAdapter(reservationAdapter_2);
                }
                if (reservationAdapter_3 == null) {
                    reservationAdapter_3 = new ReservationAdapter(mItem_ReservationItems_3, getActivity().getApplicationContext());
                    recyclerView_reservation_title3.setHasFixedSize(true);
                    recyclerView_reservation_title3.setAdapter(reservationAdapter_3);
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
