package com.example.shishir_2k23.Sponsors;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.shishir_2k23.R;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class HolderSponsor extends RecyclerView.ViewHolder {
    TextView txt;
    ImageView img;
    public HolderSponsor(@NonNull View itemView) {
        super(itemView);
        txt=itemView.findViewById(R.id.title_sponsor);
        img=itemView.findViewById(R.id.image_sponsor);
    }
}
