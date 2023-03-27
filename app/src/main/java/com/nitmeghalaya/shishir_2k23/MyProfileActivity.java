package com.nitmeghalaya.shishir_2k23;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

public class MyProfileActivity extends AppCompatActivity {

    private Button mUploadBtn;
    private ImageView mImageView,mChooseImageBtn;
    private static final int PICK_IMAGE_REQUEST = 1;
    private Uri mImageUri;
    private ImageButton logoutBtn;
    private ProgressBar progressBar;

    private StorageReference mStorageRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_profile);
        mChooseImageBtn = findViewById(R.id.choose_image);
        mUploadBtn = findViewById(R.id.upload_image);
        mImageView = findViewById(R.id.uploaded_image);
        logoutBtn = findViewById(R.id.log_out_id);
        progressBar = findViewById(R.id.Myprofile_progressBar);

        mStorageRef = FirebaseStorage.getInstance().getReference("User_Uploads");

        mChooseImageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFileChooser();
            }
        });

        mUploadBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadFile();

            }
        });


        //LogOut
        logoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth mAuth = FirebaseAuth.getInstance();
                mAuth.signOut();
                Intent intent = new Intent(MyProfileActivity.this, loginActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
    private void openFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    private void uploadFile() {
        progressBar.setVisibility(View.VISIBLE);
        mUploadBtn.setEnabled(false);
        if (mImageUri != null) {
            StorageReference fileReference = mStorageRef.child(System.currentTimeMillis()
                    + "." + getFileExtension(mImageUri));

            fileReference.putFile(mImageUri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            progressBar.setVisibility(View.GONE);
                           // Toast.makeText(MyProfileActivity.this, "Upload successful", Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(MyProfileActivity.this, MyProfileActivity.class);
                            startActivity(intent);
                            finish();

                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            mUploadBtn.setEnabled(true);
                            progressBar.setVisibility(View.GONE);
                            Toast.makeText(MyProfileActivity.this, "Upload failed: " + e.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    });
        } else {
            progressBar.setVisibility(View.GONE);
            Toast.makeText(this, "No file selected", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK
                && data != null && data.getData() != null) {
            mImageUri = data.getData();

            Picasso.get().load(mImageUri).into(mImageView);
        }
    }

    private String getFileExtension(Uri uri) {
        ContentResolver cR = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cR.getType(uri));
    }
}