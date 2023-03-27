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

public class Photo_Of_The_Day_Adapter extends RecyclerView.Adapter<Photo_Of_The_Day_Adapter.PhotoViewHolder> {

    private ArrayList<GalleryModel> galleryModelArrayList;
    private Context context;

    public Photo_Of_The_Day_Adapter(Context context,ArrayList<GalleryModel> galleryModelArrayList) {
        this.galleryModelArrayList = galleryModelArrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public PhotoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.photo_of_the_day_item,parent,false);
        return new PhotoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PhotoViewHolder holder, int position) {
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

    public class PhotoViewHolder extends RecyclerView.ViewHolder{
        private ImageView imageView;
        public PhotoViewHolder(View view){
            super(view);
            imageView = view.findViewById(R.id.photo_item_img);

        }
    }
}
