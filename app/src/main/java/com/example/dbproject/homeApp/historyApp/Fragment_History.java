package com.example.dbproject.homeApp.historyApp;

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
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        loadRecentDB(studentID);
    }

    private void loadRecentDB(Integer _studentID) {
        try {
            if (mDBHelper != null) {
//                mDBHelper.InsertStudent(190012345, "123", "asdfwe", "software", "college", "1999/12/31", "nope");
//                mDBHelper.InsertStudent(190054321, "122343", "asdfw1234e", "software", "college", "1999/12/32", "nope1231");
//                mDBHelper.InsertItem(12345, "Charger", "asdfasdf", "software", "1231242", "123123");
//                mDBHelper.InsertItem(23622, "wefwdf", "asdfasdf", "charger", "1223442", "12");
//                mDBHelper.InsertItem(12362, "qwe", "asdfasdf", "software", "1212242", "efawe");
//                mDBHelper.InsertItem(85435, "zxcvw", "asdfasdf", "charger", "1235", "awefa");
//                mDBHelper.InsertRental(23129, 190012345, 12345, "1235123","!2345123m","2341241", "123124","1234");
//                mDBHelper.InsertRental(1, 190012345, 12345, "2023-06-06", "2023-06-13", "2023-06-13", "no", "no");

                mHistoryItems = mDBHelper.getHistoryList(_studentID);
                if (historyAdapter == null) {
                    historyAdapter = new HistoryAdapter(mHistoryItems, requireContext());
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
