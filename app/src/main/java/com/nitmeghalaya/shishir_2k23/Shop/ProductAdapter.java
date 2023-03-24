package com.nitmeghalaya.shishir_2k23.Shop;

import android.app.Dialog;
import android.content.Context;
import android.os.Handler;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.nitmeghalaya.shishir_2k23.R;

import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductViewHolder> {

    private Context context;
    private List<ProductModelClass> productList;

    public ProductAdapter(Context context,List<ProductModelClass> productList) {
        this.context = context;
        this.productList = productList;
    }
    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.shop_style, parent, false);
        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        ProductModelClass product = productList.get(position);
        holder.name.setText(product.getName());
        holder.price.setText(product.getPrice());
        // use Glide or Picasso to load the images from the URLs
       // Glide.with(holder.itemView.getContext()).load(product.getImageUrls().get(0)).into(holder.imageView);
        List<String> imageUrls = product.getImageUrls();
        final int[] currentIndex = {0}; // Initialize with the first image index

        final Handler handler = new Handler();
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                currentIndex[0]++;
                if (currentIndex[0] >= imageUrls.size()) {
                    currentIndex[0] = 0; // Reset to the first image index if we reach the end
                }
                String imageUrl = imageUrls.get(currentIndex[0]);
                Glide.with(holder.itemView.getContext())
                        .load(imageUrl)
                        .placeholder(R.drawable.front_b)
                        .into(holder.imageView);

                // Schedule the next image update
                handler.postDelayed(this, 5000); // Update every 5 seconds
            }
        };

        handler.post(runnable); // Start the first image update

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handler.removeCallbacks(runnable); // Stop the automatic image updates when the user clicks on the item
                // Start an activity or perform an action when the user clicks on the item
            }
        });

        holder.buy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create a new BottomSheetDialog
                BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(v.getContext());

                // Inflate the registration form layout
                View registrationFormView = LayoutInflater.from(context).inflate(R.layout.buy_product_popup, null);

                // Find the views in the registration form
                EditText nameEditText = registrationFormView.findViewById(R.id.name_edit_text);
                EditText rollNumberEditText = registrationFormView.findViewById(R.id.roll_number_edit_text);
                EditText sizeOfClothEditText = registrationFormView.findViewById(R.id.size_of_cloth_edit_text);
                EditText emailEditText = registrationFormView.findViewById(R.id.email_edit_text);
                Button registerButton = registrationFormView.findViewById(R.id.register_button);

                // Set click listener on register button
                registerButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // Get the input values from the registration form
                        String name = nameEditText.getText().toString();
                        String rollNumber = rollNumberEditText.getText().toString();
                        String sizeOfCloth = sizeOfClothEditText.getText().toString();
                        String email = emailEditText.getText().toString();
                        // Validate the registration form fields here
                        if (name.isEmpty()) {
                            Toast.makeText(v.getContext(), "Please enter your name", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        if (rollNumber.isEmpty()) {
                            Toast.makeText(v.getContext(), "Please enter your roll number", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        if (sizeOfCloth.isEmpty()) {
                            Toast.makeText(v.getContext(), "Please enter your size of cloth", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        if (email.isEmpty()) {
                            Toast.makeText(v.getContext(), "Please enter your email", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        // Save the registration details to Firebase
                        // ...
                        // Submit the registration form here
                        FirebaseDatabase database = FirebaseDatabase.getInstance();
                        DatabaseReference registrationRef = database.getReference("buy_request");
                        BuyModelClass registration = new BuyModelClass(name, rollNumber, sizeOfCloth, email);
                        String key = registrationRef.push().getKey();
                        registrationRef.child(key).setValue(registration);

                        // Dismiss the dialog
                        bottomSheetDialog.dismiss();
                        // Inflate the popup_success layout
                        View popupView = LayoutInflater.from(context).inflate(R.layout.buy_successful_popup, null);

                        PopupWindow popupWindow = new PopupWindow(popupView, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);

// show the popup window
                        popupWindow.showAtLocation(v, Gravity.CENTER, 0, 0);

// create a handler to dismiss the popup after 2 seconds
                        Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                popupWindow.dismiss();
                            }
                        }, 2000); // 2 seconds delay


                    }
                });

                // Set the registration form as the content view of the dialog
                bottomSheetDialog.setContentView(registrationFormView);

                // Show the dialog
                bottomSheetDialog.show();
            }
        });

    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    public class ProductViewHolder extends RecyclerView.ViewHolder {
        public ImageView imageView;
        public TextView name;
        public TextView price;
        public Button buy;
        public ProductViewHolder(View view){
            super(view);
            imageView = view.findViewById(R.id.product_image);
            name = view.findViewById(R.id.product_name);
            price = view.findViewById(R.id.product_price);
            buy = view.findViewById(R.id.buy_product);

        }
    }
}
