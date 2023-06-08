package com.example.dbproject.proposalApp;

import android.content.Intent;
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

import com.example.dbproject.DB_TABLE.PROPOSAL;
import com.example.dbproject.tabLayer.DBHelper;
import com.example.dbproject.R;

import java.util.ArrayList;

public class Fragment_Proposal extends Fragment {
    public static final String TITLE = "title";
    public Integer studentID = 190012345;
    private RecyclerView mRv_proposal;
    private ArrayList<PROPOSAL> mProposalItems;
    private DBHelper mDBHelper;
    private ProposalAdapter proposalAdapter;
    private Button inquireButton;
    private ScrollView scrollView;

    private static final int REQUEST_WRITE_ACTIVITY = 1;


    public Fragment_Proposal() {
        // Required empty public constructor
    }

    public void setDbHelper(DBHelper dbHelper) {
        mDBHelper = dbHelper;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_proposal, container, false);
        mRv_proposal = view.findViewById(R.id.recyclerView_proposal);
        mProposalItems = new ArrayList<>();
        scrollView = view.findViewById(R.id.proposal_scrollview);
        scrollView.setSmoothScrollingEnabled(true);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        inquireButton = view.findViewById(R.id.inquiry_button);
        inquireButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(requireActivity(), WriteActivity.class);
                startActivityForResult(intent, REQUEST_WRITE_ACTIVITY);
            }
        });
        loadRecentDB(studentID);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_WRITE_ACTIVITY && resultCode == getActivity().RESULT_OK) {
            // Refresh the list and load the updated data
            loadRecentDB(studentID);
        }
    }

    private void loadRecentDB(Integer _studentID) {
        try {
            if (mDBHelper != null) {

                mProposalItems = mDBHelper.getProposalList(_studentID);
                if (proposalAdapter == null) {
                    proposalAdapter = new ProposalAdapter(mProposalItems, requireContext());
                    mRv_proposal.setHasFixedSize(true);
                    mRv_proposal.setAdapter(proposalAdapter);
                } else {
                    proposalAdapter.setProposalItems(mProposalItems); // Update the data in the adapter

                }
            }
        } catch (Exception e) {
            // 예외 발생 시 로그 출력
            Log.e("Fragment_Proposal", "Error in loadRecentDB(): " + e.getMessage());
        }
    }

    @Override
    public void onDestroyView() {
        // Fragment가 더 이상 보이지 않을 때 DBHelper 객체 해제
        mDBHelper = null;
        super.onDestroyView();
    }
}






