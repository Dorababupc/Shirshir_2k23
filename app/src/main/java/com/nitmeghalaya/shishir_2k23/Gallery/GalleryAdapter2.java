package com.nitmeghalaya.shishir_2k23.Gallery;

import static android.content.ContentValues.TAG;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextClock;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.nitmeghalaya.shishir_2k23.Home.GalleryModel;
import com.nitmeghalaya.shishir_2k23.R;
import com.nitmeghalaya.shishir_2k23.SignUpActivity;
import com.nitmeghalaya.shishir_2k23.loginActivity;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

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


        FirebaseUser firebaseUser= FirebaseAuth.getInstance().getCurrentUser();
//        String userid=firebaseUser.getUid();
//        Log.d("user is",""+userid);
        String outerString = model.getId(); // the outer string to check
        // Query the 'likes' collection with the outer string as a filter
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        db.collection("GalleryLikes")
                .whereEqualTo("photoId", outerString)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            // Get the array of strings for the current document
                            ArrayList<String> innerStrings = (ArrayList<String>) document.get("userIds");
                            holder.likeCount.setText(String.valueOf(innerStrings.size()));
                            holder.tempLikes=innerStrings.size();
                            // Check if the inner string is present in the array of strings
                            if(firebaseUser!=null){
                                if (innerStrings.contains(firebaseUser.getUid())) {
                                    holder.liked=true;
                                    holder.love.setImageResource(R.drawable.ic_baseline_favorite_24);
                                }
                            }
                        }
                    } else {
                        Log.d(TAG, "Error getting documents: ", task.getException());
                    }
                });

        holder.love.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(firebaseUser==null){
                    Toast.makeText(holder.love.getContext(),"You need to login to like",Toast.LENGTH_LONG).show();

                }
                else{
                    if(holder.liked){
                        holder.liked=false;
                        holder.love.setImageResource(R.drawable.ic_baseline_favorite_border_24);
                        holder.tempLikes= holder.tempLikes-1;
                        holder.likeCount.setText(String.valueOf(holder.tempLikes));
                        CollectionReference likesRef = db.collection("GalleryLikes");

                        likesRef.whereEqualTo("photoId", model.getId()).get()
                                .addOnSuccessListener(queryDocumentSnapshots -> {
                                    for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                                        ArrayList<String> innerStrings = (ArrayList<String>) documentSnapshot.get("userIds");
                                        if (innerStrings != null) {
                                            innerStrings.remove(firebaseUser.getUid());
                                            documentSnapshot.getReference().update("userIds", innerStrings);
                                        }
                                    }
                                });

                    }
                    else{
                        holder.liked=true;
                        holder.love.setImageResource(R.drawable.ic_baseline_favorite_24);
                        holder.tempLikes= holder.tempLikes+1;
                        holder.likeCount.setText(String.valueOf(holder.tempLikes));
                        Map<String, Object> updateMap = new HashMap<>();
                        updateMap.put("userIds", FieldValue.arrayUnion(firebaseUser.getUid()));
                        db.collection("GalleryLikes").whereEqualTo("photoId", model.getId()).get().addOnCompleteListener(task -> {
                            if (task.isSuccessful()) {
                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    // Update the document with the new array value
                                    db.collection("GalleryLikes").document(document.getId()).update(updateMap)
                                            .addOnSuccessListener(aVoid -> {
                                                // Update successful
                                                Log.d(TAG, "Document updated");
                                            })
                                            .addOnFailureListener(e -> {
                                                // Update failed
                                                Log.e(TAG, "Error updating document", e);
                                            });
                                }
                            } else {
                                Log.e(TAG, "Error getting documents: ", task.getException());
                            }
                        });

                    }
                }

            }
        });




    }

    @Override
    public int getItemCount() {
        return galleryModelArrayList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        private ImageView imageView;
        private  boolean liked;
        private ImageView love;
        private int tempLikes;
        private TextView likeCount;
        public MyViewHolder(View view){
            super(view);
            imageView = view.findViewById(R.id.gallery_img_id);
            liked=false;
            love=view.findViewById(R.id.love_gallery);
            tempLikes=0;
            likeCount=view.findViewById(R.id.like_count_gallery);
        }
    }
}
