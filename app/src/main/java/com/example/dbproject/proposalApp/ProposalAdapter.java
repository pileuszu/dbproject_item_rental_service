package com.example.dbproject.proposalApp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dbproject.DB_TABLE.PROPOSAL;
import com.example.dbproject.R;
import com.example.dbproject.tabLayer.DBHelper;

import java.util.ArrayList;

public class ProposalAdapter extends RecyclerView.Adapter<ProposalAdapter.ViewHolder> {


    private ArrayList<PROPOSAL> proposalItems;
    private Context mContext;
    private DBHelper mProposalDBHelper;

    public ProposalAdapter(ArrayList<PROPOSAL> proposalItems, Context mContext) {
        this.proposalItems = proposalItems;
        this.mContext = mContext;
        this.mProposalDBHelper = new DBHelper(mContext);
    }


    // Method to update the data in the adapter
    public void setProposalItems(ArrayList<PROPOSAL> proposalItems) {
        this.proposalItems = proposalItems;
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public ProposalAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View holder = LayoutInflater.from(parent.getContext()).inflate(R.layout.proposal_list, parent, false);
        return new ViewHolder(holder);
    }

    @Override
    public void onBindViewHolder(@NonNull ProposalAdapter.ViewHolder holder, int position) {
        holder.tv_proposal_id.setText(String.valueOf(proposalItems.get(position).getId()));
        holder.tv_proposal_content.setText(proposalItems.get(position).getContent());
        holder.tv_proposal_time.setText(proposalItems.get(position).getWrite_date() + " " + proposalItems.get(position).getWrite_time());
    }

    @Override
    public int getItemCount() {
        return proposalItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView tv_proposal_id;
        private TextView tv_proposal_content;
        private TextView tv_proposal_date;
        private TextView tv_proposal_time;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_proposal_id = itemView.findViewById(R.id.tv_proposal_id);
            tv_proposal_content = itemView.findViewById(R.id.tv_proposal_content);
            tv_proposal_time = itemView.findViewById(R.id.tv_proposal_time);

        }
    }
}
