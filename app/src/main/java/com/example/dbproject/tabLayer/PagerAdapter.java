package com.example.dbproject.tabLayer;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.dbproject.historyApp.Fragment_History;
import com.example.dbproject.proposalApp.Fragment_Proposal;

import com.example.dbproject.homeApp.Fragment_Home;
import com.example.dbproject.rentalApp.Fragment_Rental;
import com.example.dbproject.reservationApp.Fragment_Reservation;

public class PagerAdapter extends FragmentStateAdapter {



    private static final int NUM_TABS = 5;
    private static final String[] TAB_NAMES= {"조회", "대여", "홈", "예약", "문의"};
    private DBHelper dbHelper;
    public PagerAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle, DBHelper dbHelper) {
        super(fragmentManager, lifecycle);
        this.dbHelper = dbHelper;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        if (position == 0) {
            Fragment fragment = new Fragment_History();
            if (fragment != null) {
                Bundle args = new Bundle();
                args.putString(Fragment_History.TITLE, TAB_NAMES[position]);
                fragment.setArguments(args);
                ((Fragment_History) fragment).setDbHelper(dbHelper);
                return fragment;
            } else {
                // 예외 처리 또는 로그 출력

                return null;
            }
        } else if (position == 1) {
            Fragment fragment = new Fragment_Rental();
            if (fragment != null) {
                Bundle args = new Bundle();
                args.putString(Fragment_History.TITLE, TAB_NAMES[position]);
                fragment.setArguments(args);
                ((Fragment_Rental) fragment).setDbHelper(dbHelper);
                return fragment;
            } else {
                // 예외 처리 또는 로그 출력

                return null;
            }
        } else if (position == 2) {
            Fragment fragment = new Fragment_Home();
            if (fragment != null) {
                Bundle args = new Bundle();
                args.putString(Fragment_History.TITLE, TAB_NAMES[position]);
                fragment.setArguments(args);
                ((Fragment_Home) fragment).setDbHelper(dbHelper);
                return fragment;
            } else {
                // 예외 처리 또는 로그 출력

                return null;
            }
        } else if (position == 3) {
            Fragment fragment = new Fragment_Reservation();
            if (fragment != null) {
                Bundle args = new Bundle();
                args.putString(Fragment_History.TITLE, TAB_NAMES[position]);
                fragment.setArguments(args);
                ((Fragment_Reservation) fragment).setDbHelper(dbHelper);
                return fragment;
            } else {
                // 예외 처리 또는 로그 출력

                return null;
            }
        } else if (position == 4) {
            Fragment fragment = new Fragment_Proposal();
            if (fragment != null) {
                Bundle args = new Bundle();
                args.putString(Fragment_History.TITLE, TAB_NAMES[position]);
                fragment.setArguments(args);
                ((Fragment_Proposal) fragment).setDbHelper(dbHelper);
                return fragment;
            } else {
                // 예외 처리 또는 로그 출력

                return null;
            }
        } else {
            // 예외 처리 또는 로그 출력

            return null;
        }
    }

    @Override
    public int getItemCount() {
        return NUM_TABS;
    }
}
