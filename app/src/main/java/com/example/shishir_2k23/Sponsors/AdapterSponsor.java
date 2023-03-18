package com.example.shishir_2k23.Sponsors;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.EmojiCompatConfigurationView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.shishir_2k23.R;
import com.example.shishir_2k23.Team.HolderEventTeam;
import com.example.shishir_2k23.Team.ModelTeamTeam;
import com.example.shishir_2k23.Team.TeamAdapterTeam;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.ConcurrentModificationException;

public class AdapterSponsor extends FirebaseRecyclerAdapter<ModelSponsor,HolderSponsor> {

    FirebaseRecyclerOptions<ModelSponsor> options;
    public AdapterSponsor(@NonNull FirebaseRecyclerOptions<ModelSponsor> options) {
        super(options);
        this.options=options;
    }

    @Override
    protected void onBindViewHolder(@NonNull HolderSponsor holder, int position, @NonNull ModelSponsor model) {
        holder.txt.setText(model.getTitle());
        Glide.with(holder.img.getContext()).load(model.getLogo()).into(holder.img);
        ModelSponsor m=model;
        holder.img.setOnClickListener(view -> {
            Toast.makeText(holder.img.getContext(),""+ model.getWebsite(),Toast.LENGTH_LONG);
        });

    }

    @NonNull
    @Override
    public HolderSponsor onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.singlerow_sponsor,parent,false);
        return new HolderSponsor(view);
    }
}
