package com.nitmeghalaya.shishir_2k23.Team;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.nitmeghalaya.shishir_2k23.R;
import de.hdodenhof.circleimageview.CircleImageView;

public class HolderTeamTeam extends RecyclerView.ViewHolder {
    CircleImageView img;
    TextView name;
    TextView position;
    ImageView contact;
    ImageView email;
    public HolderTeamTeam(@NonNull View itemView) {
        super(itemView);
        img=itemView.findViewById(R.id.member_image);
        name=itemView.findViewById(R.id.member_name);
        position=itemView.findViewById(R.id.member_position);
        contact=itemView.findViewById(R.id.member_contact);
        email=itemView.findViewById(R.id.member_email);
    }
}
