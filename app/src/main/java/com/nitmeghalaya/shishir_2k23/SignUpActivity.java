package com.nitmeghalaya.shishir_2k23;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;
//import com.google.android.gms.tasks.OnCompleteListener;

public class SignUpActivity extends AppCompatActivity {
    private EditText name,email,password,repassword;
    private TextView skip,signin;
    private MaterialButton signup;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        name = findViewById(R.id.usernameUp);
        email = findViewById(R.id.emailUp);
        password = findViewById(R.id.passwordUp);
        repassword = findViewById(R.id.repasswordUp);
        skip = findViewById(R.id.skip_id);
        signin = findViewById(R.id.signin_id);
        signup=findViewById(R.id.signup);
        progressBar = findViewById(R.id.signup_progress);
        //Skip
        skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SignUpActivity.this,MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressBar.setVisibility(View.VISIBLE);
                String usernameText=name.getText().toString();
                String emailText=email.getText().toString();
                String passwordText=password.getText().toString();
                String repasswordText=repassword.getText().toString();
                if (TextUtils.isEmpty(usernameText)) {
                    // Email is empty, show error message
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(getApplicationContext(), "Username is required.", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(emailText)) {
                    // Email is empty, show error message
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(getApplicationContext(), "Email is required.", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(TextUtils.isEmpty(passwordText)){
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(getApplicationContext(),"Password is required",Toast.LENGTH_LONG).show();
                    return;
                }
                if(TextUtils.isEmpty(repasswordText)){
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(getApplicationContext(),"Password reentry is required",Toast.LENGTH_LONG).show();
                    return;
                }
                if(passwordText.equals(repasswordText)){
                    FirebaseAuth mAuth = FirebaseAuth.getInstance();
                    mAuth.createUserWithEmailAndPassword(emailText, passwordText)
                            .addOnCompleteListener(SignUpActivity.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        progressBar.setVisibility(View.GONE);
                                        // Sign-up success, update UI with the signed-in user's information
                                        FirebaseUser user = mAuth.getCurrentUser();
                                        String uid = user.getUid();
//                                        DocumentReference userRef = FirebaseFirestore.getInstance().collection("users").document(uid);
// or
                                        DatabaseReference userRef = FirebaseDatabase.getInstance().getReference().child("users").child(uid);
                                        Map<String, Object> userMap = new HashMap<>();
                                        userMap.put("username", usernameText);
                                        userMap.put("email", emailText);
                                        userMap.put("password",passwordText);
                                        userRef.setValue(userMap)
                                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                    @Override
                                                    public void onSuccess(Void aVoid) {
                                                        progressBar.setVisibility(View.GONE);
                                                          //Toast.makeText(SignUpActivity.this,"Signup successful,login to continue",Toast.LENGTH_LONG).show();
                                                        Intent intent = new Intent(SignUpActivity.this,loginActivity.class);
                                                        startActivity(intent);
                                                        finish();
                                                    }
                                                })
                                                .addOnFailureListener(new OnFailureListener() {
                                                    @Override
                                                    public void onFailure(@NonNull Exception e) {
                                                        progressBar.setVisibility(View.GONE);
                                                    }
                                                });



// add any other additional user information


                                    } else {
                                        progressBar.setVisibility(View.GONE);
                                        // If sign-up fails, display a message to the user
                                        Toast.makeText(SignUpActivity.this, "Authentication failed.",
                                                Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                }
                else{
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(getApplicationContext(),"passwords not matching",Toast.LENGTH_LONG).show();
                }

            }
        });

        //Directing to sign in page
        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SignUpActivity.this,loginActivity.class);
                startActivity(intent);
                finish();
            }
        });


    }
}