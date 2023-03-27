package com.nitmeghalaya.shishir_2k23;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class SignUpActivity extends AppCompatActivity {
    private EditText name,email,password,re_password;
    private TextView skip,signin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        name = findViewById(R.id.username);
        email = findViewById(R.id.email_id);
        password = findViewById(R.id.password);
        re_password = findViewById(R.id.retype_password);
        skip = findViewById(R.id.skip_id);
        signin = findViewById(R.id.signin_id);

        //Skip
        skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SignUpActivity.this,MainActivity.class);
                startActivity(intent);
                finish();
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