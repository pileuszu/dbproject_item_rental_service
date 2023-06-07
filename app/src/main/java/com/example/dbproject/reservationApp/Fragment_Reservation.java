package com.example.dbproject.reservationApp;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.dbproject.R;
import com.example.dbproject.tabLayer.DBHelper;

public class Fragment_Reservation extends Fragment {
    public static final String TITLE = "title";
    private DBHelper mDBHelper;
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
        return inflater.inflate(R.layout.fragment_reservation, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ((TextView)view.findViewById(R.id.textView)).setText(getArguments().getString(TITLE));

    }
}