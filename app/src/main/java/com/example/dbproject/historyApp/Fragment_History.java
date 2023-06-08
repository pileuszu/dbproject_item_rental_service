package com.example.dbproject.historyApp;

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

import com.example.dbproject.tabLayer.DBHelper;
import com.example.dbproject.R;

import java.util.ArrayList;

public class Fragment_History extends Fragment {
    public static final String TITLE = "title";
    public Integer studentID = 190012345;
    private RecyclerView mRv_history;
    private ArrayList<HISTORY> mHistoryItems;
    private DBHelper mDBHelper;
    private HistoryAdapter historyAdapter;
    private ScrollView scrollView;
    private Button history_re_button;

    public Fragment_History() {
        // Required empty public constructor
    }

    public void setDbHelper(DBHelper dbHelper) {
        mDBHelper = dbHelper;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_history, container, false);
        mRv_history = view.findViewById(R.id.recyclerView_history);
        mHistoryItems = new ArrayList<>();
        scrollView = view.findViewById(R.id.history_scrollview);
        scrollView.setSmoothScrollingEnabled(true);
        loadRecentDB(studentID);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        history_re_button = view.findViewById(R.id.history_re_button);
        history_re_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadRecentDB(studentID);
            }
        });
    }

    private void loadRecentDB(Integer _studentID) {
        try {
            if (mDBHelper != null) {
                mHistoryItems = mDBHelper.getHistoryList(_studentID);
                if (historyAdapter == null) {
                    historyAdapter = new HistoryAdapter(mHistoryItems, getContext().getApplicationContext());
                    mRv_history.setHasFixedSize(true);
                    mRv_history.setAdapter(historyAdapter);
                }
            }
        } catch (Exception e) {
            // 예외 발생 시 로그 출력
            Log.e("Fragment_History", "Error in loadRecentDB(): " + e.getMessage());
        }
    }

    @Override
    public void onDestroyView() {
        // Fragment가 더 이상 보이지 않을 때 DBHelper 객체 해제
        mDBHelper = null;
        super.onDestroyView();
    }
}
