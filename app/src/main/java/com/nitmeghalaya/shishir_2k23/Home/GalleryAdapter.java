package com.nitmeghalaya.shishir_2k23.Home;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.nitmeghalaya.shishir_2k23.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class GalleryAdapter extends RecyclerView.Adapter<GalleryAdapter.MyViewHolder> {

private ArrayList<GalleryModel> galleryModelArrayList;
private Context context;

//constructor for the adapter
    public GalleryAdapter( Context context,ArrayList<GalleryModel> galleryModelArrayList) {
        this.galleryModelArrayList = galleryModelArrayList;
        this.context = context;
    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.gallery_item,parent,false);
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

    public class MyViewHolder extends RecyclerView.ViewHolder{
        private ImageView imageView;

        public MyViewHolder(View itemView){
            super(itemView);

            imageView = itemView.findViewById(R.id.gallery_item_img);
        }
    }
}
