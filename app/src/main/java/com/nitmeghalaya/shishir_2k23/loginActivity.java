package com.nitmeghalaya.shishir_2k23;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class loginActivity extends AppCompatActivity {
    private EditText username;
    private EditText password;
    private TextView skipTV, signupTV;
    private MaterialButton button;
    private TextView forgot;
    private ProgressBar progressBar;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.acivity_login);
        username=findViewById(R.id.username);
        password=findViewById(R.id.password);
        button=findViewById(R.id.loginbtn);
        skipTV = findViewById(R.id.skip);
        signupTV = findViewById(R.id.signup_id);
        progressBar = findViewById(R.id.login_progress);
        forgot=findViewById(R.id.forgotIn);
        forgot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String usernameText=username.getText().toString();
                if (TextUtils.isEmpty(usernameText)) {
                    // Email is empty, show error message
                    Toast.makeText(getApplicationContext(), "Enter email for sending the reset link", Toast.LENGTH_SHORT).show();
                    return;
                }

                FirebaseAuth.getInstance().sendPasswordResetEmail(usernameText)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    // Password reset email sent successfully
                                    Toast.makeText(loginActivity.this, "Password reset email sent", Toast.LENGTH_SHORT).show();
                                } else {
                                    // Error sending password reset email
                                    Toast.makeText(loginActivity.this, "Error sending password reset email", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });

            }
        });
        button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);
                final String usernameText=username.getText().toString();
                final String passwordText=password.getText().toString();
//                Toast.makeText(loginActivity.this,usernameText, Toast.LENGTH_LONG).show();
                if (TextUtils.isEmpty(usernameText)) {
                    // Email is empty, show error message
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(getApplicationContext(), "Email is required.", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(passwordText)) {
                    // Password is empty, show error message
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(getApplicationContext(), "Password is required.", Toast.LENGTH_SHORT).show();
                    return;
                }

//                     Sign in user with email and password
                FirebaseAuth.getInstance().signInWithEmailAndPassword(usernameText, passwordText)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    progressBar.setVisibility(View.GONE);
                                    Intent intent = new Intent(loginActivity.this, MainActivity.class);
                                    startActivity(intent);
                                    finish();
                                } else {
                                    progressBar.setVisibility(View.GONE);
                                    // Sign in failed, show error message
                                    Toast.makeText(getApplicationContext(), "Login failed. Please check your credentials.", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });
        //Skip is clicked
        skipTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(loginActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        //directing to SignUp activity
        signupTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(loginActivity.this,SignUpActivity.class);
                startActivity(intent);
            }
        });


    }
}
