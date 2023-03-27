package com.nitmeghalaya.shishir_2k23.Gallery;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.nitmeghalaya.shishir_2k23.Home.GalleryModel;
import com.nitmeghalaya.shishir_2k23.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class GalleryActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ArrayList<GalleryModel> galleryDataArrayList;
    private GalleryAdapter2 galleryAdapter;
    private ProgressBar progressBar;
    FirebaseFirestore db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);

        progressBar = findViewById(R.id.images_progressBar);
        progressBar.setVisibility(View.VISIBLE);
        galleryDataArrayList = new ArrayList<>();
        recyclerView = findViewById(R.id.gallery_rv);

        db = FirebaseFirestore.getInstance();

        recyclerView.setHasFixedSize(true);
        // adding horizontal layout manager for our recycler view.
        recyclerView.setLayoutManager(new LinearLayoutManager(GalleryActivity.this,LinearLayoutManager.VERTICAL, false));
        // adding our array list to our recycler view adapter class.
        galleryAdapter = new GalleryAdapter2(GalleryActivity.this,galleryDataArrayList);
        recyclerView.setAdapter(galleryAdapter);
        loadGalleryImage();
    }

    private void loadGalleryImage(){
        db.collection("Gallery").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                progressBar.setVisibility(View.GONE);
                for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots){
                    GalleryModel galleryDataModel = documentSnapshot.toObject(GalleryModel.class);
                    GalleryModel model = new GalleryModel();
                    model.setId(documentSnapshot.getId());
                    // below line is use for setting our
                    // image url for our modal class.
                    model.setImgUrl(galleryDataModel.getImgUrl());
//                    model.setLikes(galleryDataModel.getLikes());
                    // after that we are adding that
                    // data inside our array list.
                    galleryDataArrayList.add(model);

                    // after adding data to our array list we are passing
                    // that array list inside our adapter class.
                    galleryAdapter = new GalleryAdapter2(GalleryActivity.this,galleryDataArrayList);

                    recyclerView.setAdapter(galleryAdapter);
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(GalleryActivity.this, "Fail to load slider data.", Toast.LENGTH_SHORT).show();
            }
        });
    }
}