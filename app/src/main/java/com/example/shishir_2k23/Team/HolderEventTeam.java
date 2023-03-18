package com.example.shishir_2k23.Team;

import android.view.View;
import android.widget.TextView;
import com.example.shishir_2k23.R;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class HolderEventTeam extends RecyclerView.ViewHolder {
    TextView eventName;
    RecyclerView teamMembers;
    public HolderEventTeam(@NonNull View itemView) {
        super(itemView);
        eventName=itemView.findViewById(R.id.eventNameTextView);
        teamMembers=itemView.findViewById(R.id.teamMembersRecyclerView);
    }
}
