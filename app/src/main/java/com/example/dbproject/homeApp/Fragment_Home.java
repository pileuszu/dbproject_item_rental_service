package com.example.dbproject.homeApp;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ScrollView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;


import com.example.dbproject.R;
import com.example.dbproject.tabLayer.DBHelper;

import java.util.ArrayList;

public class Fragment_Home extends Fragment {
    public static final String TITLE = "title";
    private RecyclerView mRv_notice;
    private ArrayList<NOTICE> mNoticeItems;
    private DBHelper mDBHelper;
    private NoticeAdapter noticeAdapter;
    private ScrollView scrollView;

    public Fragment_Home() {
        // Required empty public constructor
    }

    public void setDbHelper(DBHelper dbHelper) {
        mDBHelper = dbHelper;
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        mRv_notice = view.findViewById(R.id.recyclerView_home);
        mNoticeItems = new ArrayList<>();
        scrollView = view.findViewById(R.id.notice_scrollview);
        scrollView.setSmoothScrollingEnabled(true);

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
//                mDBHelper.InsertNotice(123, "123", "123", "123", "!24124");
                mNoticeItems = mDBHelper.getNoticeList();
                if (noticeAdapter == null) {
                    noticeAdapter = new NoticeAdapter(mNoticeItems, getActivity().getApplicationContext());
                    mRv_notice.setHasFixedSize(true);
                    mRv_notice.setAdapter(noticeAdapter);
                }
            }
        } catch (Exception e) {
            // 예외 발생 시 로그 출력
            Log.e("Fragment_Home", "Error in loadRecentDB(): " + e.getMessage());
        }
    }

    @Override
    public void onDestroyView() {
        // Fragment가 더 이상 보이지 않을 때 DBHelper 객체 해제
        mDBHelper = null;
        super.onDestroyView();
    }
}
