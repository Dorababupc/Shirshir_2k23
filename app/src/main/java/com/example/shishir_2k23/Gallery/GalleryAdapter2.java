package com.example.shishir_2k23.Gallery;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shishir_2k23.Home.GalleryModel;
import com.example.shishir_2k23.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class GalleryAdapter2 extends RecyclerView.Adapter<GalleryAdapter2.MyViewHolder> {

    private ArrayList<GalleryModel> galleryModelArrayList;
    private Context context;

    //constructor for the adapter
    public GalleryAdapter2( Context context,ArrayList<GalleryModel> galleryModelArrayList) {
        this.galleryModelArrayList = galleryModelArrayList;
        this.context = context;
    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
      View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.gallery_item_view,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        final GalleryModel model = galleryModelArrayList.get(position);
        Picasso.get().load(model.getImgUrl()).into(holder.imageView);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(context, "Image is Clicked", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return galleryModelArrayList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        private ImageView imageView;

        public MyViewHolder(View view){
            super(view);
            imageView = view.findViewById(R.id.gallery_img_id);
        }
    }
}
