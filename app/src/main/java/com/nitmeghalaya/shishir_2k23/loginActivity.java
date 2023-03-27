package com.nitmeghalaya.shishir_2k23;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
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
    private MaterialButton button;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.acivity_login);
        username=findViewById(R.id.username);
        password=findViewById(R.id.password);
        button=findViewById(R.id.loginbtn);

        button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
//
                final String usernameText=username.getText().toString();
                final String passwordText=password.getText().toString();
//                Toast.makeText(loginActivity.this,usernameText, Toast.LENGTH_LONG).show();
                if (TextUtils.isEmpty(usernameText)) {
                    // Email is empty, show error message
                    Toast.makeText(getApplicationContext(), "Email is required.", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(passwordText)) {
                    // Password is empty, show error message
                    Toast.makeText(getApplicationContext(), "Password is required.", Toast.LENGTH_SHORT).show();
                    return;
                }

//                     Sign in user with email and password
                FirebaseAuth.getInstance().signInWithEmailAndPassword(usernameText, passwordText)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    Intent intent = new Intent(loginActivity.this, MainActivity.class);
                                    startActivity(intent);
                                    finish();
                                } else {
                                    // Sign in failed, show error message
                                    Toast.makeText(getApplicationContext(), "Login failed. Please check your credentials.", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });


    }
}