package com.example.shishir_2k23.Team;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.example.shishir_2k23.R;

import java.util.ArrayList;

public class EventAdapterTeam extends FirebaseRecyclerAdapter<String,HolderEventTeam> {
    FirebaseRecyclerOptions<String> options;
    DatabaseReference mbase;
    TeamAdapterTeam adapter;
    //private ProgressBar progressBar;

    Context context;
    public EventAdapterTeam(@NonNull FirebaseRecyclerOptions<String> options,Context context) {
        super(options);
        this.options=options;
        this.context=context;
//        progressBar = ((Activity) context).findViewById(R.id.team_progressBar);
//        progressBar.setVisibility(View.VISIBLE);

    }


    @Override
    protected void onBindViewHolder(@NonNull HolderEventTeam holder, int position, @NonNull String model) {
        holder.eventName.setText(model);
        LinearLayoutManager layoutManager = new LinearLayoutManager(holder.teamMembers.getContext(), RecyclerView.VERTICAL,false);
        holder.teamMembers.setLayoutManager(layoutManager);
        holder.progressBar.setVisibility(View.VISIBLE);
//        FirebaseDatabase.getInstance("https://shishir-2k23-default-rtdb.asia-southeast1.firebasedatabase.app").setPersistenceEnabled(true);

        FirebaseDatabase database = FirebaseDatabase.getInstance("https://shishir-2k23-default-rtdb.asia-southeast1.firebasedatabase.app");
        DatabaseReference ref = database.getReference(model);

        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                holder.progressBar.setVisibility(View.GONE);

                ArrayList<ModelTeamTeam> dataArray = new ArrayList<>();
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    ModelTeamTeam data = ds.getValue(ModelTeamTeam.class);
                    dataArray.add(data);
                }
                TeamAdapterTeam adapter = new TeamAdapterTeam(dataArray);
                holder.teamMembers.setAdapter(adapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Handle database error
                holder.progressBar.setVisibility(View.GONE);

            }
        });

    }

    @NonNull
    @Override
    public HolderEventTeam onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.singlerow_event_team,parent,false);
        return new HolderEventTeam(view);
    }

}
