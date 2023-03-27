package com.nitmeghalaya.shishir_2k23.Shop;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.nitmeghalaya.shishir_2k23.R;
//import com.google.firebase.storage.FirebaseStorage;
//import com.google.firebase.storage.StorageReference;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ShopFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ShopFragment extends Fragment {
    private Handler handler = new Handler();
    private Runnable runnable;
    private ImageView imageView1,imageView2,imageView3,imageView4,imageView5;
    private String[] imageUrls1 = {"https://firebasestorage.googleapis.com/v0/b/shishir-2k23.appspot.com/o/Merchandise%2FHoodie%2FWhite%20hoodie%2Ffront.png?alt=media&token=116a7c4d-0483-495b-9a0b-448d82359d8e", "https://firebasestorage.googleapis.com/v0/b/shishir-2k23.appspot.com/o/Merchandise%2FHoodie%2FWhite%20hoodie%2Fback.png?alt=media&token=3d9c88a8-ff8a-48cb-9570-10ebef7cb604"};
    private String[] imageUrls2={"https://firebasestorage.googleapis.com/v0/b/shishir-2k23.appspot.com/o/Merchandise%2FHoodie%2FBlack%20hoodie%2Ffront_b.png?alt=media&token=2b71713a-3e3f-4e4f-8f9d-2a42018243fe","https://firebasestorage.googleapis.com/v0/b/shishir-2k23.appspot.com/o/Merchandise%2FHoodie%2FBlack%20hoodie%2Fback_b.png?alt=media&token=3c6c96d1-d64b-4bb2-a8c9-67507e1ef4d1"};
    private String[] imageUrls3={"https://firebasestorage.googleapis.com/v0/b/shishir-2k23.appspot.com/o/Merchandise%2FT-Shirt%2FBlack%20T%20shirt%2Ffront_b_w.png?alt=media&token=6766ad95-ef0f-4501-8fbd-51971fe716f7","https://firebasestorage.googleapis.com/v0/b/shishir-2k23.appspot.com/o/Merchandise%2FT-Shirt%2FBlack%20T%20shirt%2Fback_b_w.png?alt=media&token=50ce756a-cf5e-4017-8330-c68c47a62141"};
    private String[] imageUrls4={"https://firebasestorage.googleapis.com/v0/b/shishir-2k23.appspot.com/o/Merchandise%2FT-Shirt%2FBrugelerey%20T%20shirt%2Fwhite_b.png?alt=media&token=87905954-ffa6-4b2b-85a0-512163629dc4","https://firebasestorage.googleapis.com/v0/b/shishir-2k23.appspot.com/o/Merchandise%2FT-Shirt%2FBrugelerey%20T%20shirt%2Fback_B.png?alt=media&token=c19d32d3-e4f4-4a69-849f-6c1677684b43"};
    private String[] imageUrls5={"https://firebasestorage.googleapis.com/v0/b/shishir-2k23.appspot.com/o/Merchandise%2FT-Shirt%2FWhite%20T-Shirt%2Ffront_w.png?alt=media&token=9b24d6a5-3f8a-4387-9f46-032252c660d2","https://firebasestorage.googleapis.com/v0/b/shishir-2k23.appspot.com/o/Merchandise%2FT-Shirt%2FWhite%20T-Shirt%2Fback_w.png?alt=media&token=5c01b7e4-8851-4c17-9754-881b2acb1fd3"};
    private int currentImageIndex = 0,currentImageIndex2=1;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ShopFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ShopFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ShopFragment newInstance(String param1, String param2) {
        ShopFragment fragment = new ShopFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_shop, container, false);
    }
    @Override
    public void onViewCreated(@NonNull View v, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(v, savedInstanceState);
        imageView1 = v.findViewById(R.id.firstImage);
        imageView2=v.findViewById(R.id.secondImage);
        imageView3=v.findViewById(R.id.thirdImage);
        imageView4=v.findViewById(R.id.fourthImage);
        imageView5=v.findViewById(R.id.fifthImage);
        runnable = new Runnable() {
            @Override
            public void run() {
                // Load the next image using Glide
                Glide.with(v.getContext())
                        .load(imageUrls1[currentImageIndex])
                        .into(imageView1);
                Glide.with(v.getContext()).load(imageUrls2[currentImageIndex]).into(imageView2);
                Glide.with(v.getContext()).load(imageUrls3[currentImageIndex]).into(imageView3);
                Glide.with(v.getContext()).load(imageUrls4[currentImageIndex]).into(imageView4);
                Glide.with(v.getContext()).load(imageUrls5[currentImageIndex]).into(imageView5);
                // Increment the index and wrap around if necessary
                currentImageIndex = (currentImageIndex + 1) % imageUrls1.length;

                // Schedule the next change after 3 seconds
                handler.postDelayed(this, 4000);
            }
        };

        // Start the initial change after 3 seconds
        handler.postDelayed(runnable, 1000);
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        // Remove the callback to prevent memory leaks
        handler.removeCallbacks(runnable);
    }
}