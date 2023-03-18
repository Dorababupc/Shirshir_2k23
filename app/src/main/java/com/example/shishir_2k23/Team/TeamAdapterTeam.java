package com.example.shishir_2k23.Team;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import com.example.shishir_2k23.R;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class TeamAdapterTeam extends RecyclerView.Adapter<HolderTeamTeam> {
    ArrayList<ModelTeamTeam> options;
    public TeamAdapterTeam(ArrayList<ModelTeamTeam> options) {
        this.options=options;
    }

    @NonNull
    @Override
    public HolderTeamTeam onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.teammembers_team,parent,false);
        return new HolderTeamTeam(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HolderTeamTeam holder, int position) {
        holder.position.setText(options.get(position).getPosition());
        holder.name.setText(options.get(position).getName());
        Glide.with(holder.img.getContext()).load(options.get(position).getPurl()).into(holder.img);
        ModelTeamTeam m=options.get(position);
//        holder.contact.setOnClickListener(v -> {
//            Toast toast = Toast.makeText(holder.contact.getContext(),""+m.getContact(), Toast.LENGTH_LONG);
//            toast.show();
//        });
        holder.email.setOnClickListener(v -> {
            Toast toast = Toast.makeText(holder.email.getContext(),m.getEmail(), Toast.LENGTH_LONG);
            toast.show();
        });

    }

    @Override
    public int getItemCount() {
        return options.size();
    }

}
