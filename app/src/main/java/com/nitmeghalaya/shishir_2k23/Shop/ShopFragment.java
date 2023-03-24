package com.nitmeghalaya.shishir_2k23.Shop;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.nitmeghalaya.shishir_2k23.R;

import java.util.ArrayList;
import java.util.List;
//import com.google.firebase.storage.FirebaseStorage;
//import com.google.firebase.storage.StorageReference;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ShopFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ShopFragment extends Fragment {
    private Handler handler = new Handler();
    private RecyclerView recyclerView;
    private List<ProductModelClass> productList;
    private DatabaseReference databaseReference;
    private ProgressBar shopPregressbar;

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

        recyclerView = v.findViewById(R.id.shop_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        shopPregressbar = v.findViewById(R.id.shop_progress);
        shopPregressbar.setVisibility(View.VISIBLE);

        productList = new ArrayList<>();
        databaseReference = FirebaseDatabase.getInstance().getReference("products");

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                productList.clear();
                shopPregressbar.setVisibility(View.GONE);
                for (DataSnapshot productSnapshot : dataSnapshot.getChildren()) {

                    String name = productSnapshot.child("name").getValue(String.class);
                    String price = productSnapshot.child("price").getValue(String.class);
                    List<String> imageUrls = new ArrayList<>();
                    for (DataSnapshot imageUrlSnapshot : productSnapshot.child("imageUrls").getChildren()) {
                        String imageUrl = imageUrlSnapshot.getValue(String.class);
                        imageUrls.add(imageUrl);
                    }
                    ProductModelClass product = new ProductModelClass(name, price, imageUrls);
                    productList.add(product);
                }
                ProductAdapter adapter = new ProductAdapter(v.getContext(),productList);
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                shopPregressbar.setVisibility(View.GONE);
                Toast.makeText(v.getContext(), "Failed to load products.", Toast.LENGTH_SHORT).show();
            }
        });

    }

}