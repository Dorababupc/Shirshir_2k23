package com.nitmeghalaya.shishir_2k23.Sponsors;

import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.nitmeghalaya.shishir_2k23.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

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
//            Toast.makeText(holder.img.getContext(),""+ m.getWebsite(),Toast.LENGTH_LONG).show();
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(m.getWebsite()));
            holder.img.getContext().startActivity(intent);
        });

    }

    @NonNull
    @Override
    public HolderSponsor onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.singlerow_sponsor,parent,false);
        return new HolderSponsor(view);
    }

    public FirebaseRecyclerOptions<ModelSponsor> getOptions() {
        return options;
    }
}
