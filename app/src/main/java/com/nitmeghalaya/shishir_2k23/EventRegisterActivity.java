package com.nitmeghalaya.shishir_2k23;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Timer;
import java.util.TimerTask;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EventRegisterActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {


    private EditText mNameField;
    private EditText mEmailField;
    private EditText mPhoneField;
    private EditText mRollNumberField;
    private EditText mCollegeName;
    private Spinner mYearSpinner;
    private Button mRegisterBtn;
    private Spinner mDepartmentSpinner;
    private ProgressBar submitprogressBar;
    private RadioGroup mProgramRadioGroup;
    FirebaseFirestore mFirestore;
    private TextView eventNameTV;
    private String Eventname;
    private boolean isSubmitting = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_register);

        eventNameTV = findViewById(R.id.event_name);
        eventNameTV.setText(getIntent().getStringExtra("event_name"));
        submitprogressBar = findViewById(R.id.submit_button_progress);

        mNameField = findViewById(R.id.name_field);
        mEmailField = findViewById(R.id.email_field);
        mPhoneField = findViewById(R.id.phone_field);
        mRollNumberField = findViewById(R.id.roll_number);
        mCollegeName = findViewById(R.id.college_name);
        mRegisterBtn = findViewById(R.id.register_button);

        mYearSpinner = findViewById(R.id.year_spinner);
        ArrayAdapter<CharSequence> yearAdapter = ArrayAdapter.createFromResource(this, R.array.year_options, android.R.layout.simple_spinner_item);
        yearAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mYearSpinner.setAdapter(yearAdapter);
        mYearSpinner.setOnItemSelectedListener(this);

        mDepartmentSpinner = findViewById(R.id.department_spinner);
        ArrayAdapter<CharSequence> departmentAdapter = ArrayAdapter.createFromResource(this, R.array.department_options, android.R.layout.simple_spinner_item);
        departmentAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mDepartmentSpinner.setAdapter(departmentAdapter);
        mDepartmentSpinner.setOnItemSelectedListener(this);

        mProgramRadioGroup = findViewById(R.id.program_radio_group);

        mFirestore = FirebaseFirestore.getInstance();

        mRegisterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onSubmitButtonClicked(view);
            }
        });
    }

    public void onSubmitButtonClicked(View view) {
        // Save data to Firebase here
        mRegisterBtn.setEnabled(false);
        if (isSubmitting) {
            Toast.makeText(this, "Please wait for the previous submission to complete", Toast.LENGTH_SHORT).show();
            return;
        }
        String name = mNameField.getText().toString();
        submitprogressBar.setVisibility(View.VISIBLE);

        if (TextUtils.isEmpty(name)) {
            submitprogressBar.setVisibility(View.GONE);
            mRegisterBtn.setEnabled(true);
            Toast.makeText(this, "Please enter your name", Toast.LENGTH_SHORT).show();
            return;
        }
        String email = mEmailField.getText().toString();
        if (TextUtils.isEmpty(email)) {
            submitprogressBar.setVisibility(View.GONE);
            mRegisterBtn.setEnabled(true);
            Toast.makeText(this, "Please enter your email", Toast.LENGTH_SHORT).show();
            return;
        }
        String phone = mPhoneField.getText().toString().trim();
        // Check if phone number is valid
        Pattern phonePattern = Pattern.compile("^[+]?[0-9]{10,13}$");
        Matcher phoneMatcher = phonePattern.matcher(phone);
        if (!phoneMatcher.matches()) {
            submitprogressBar.setVisibility(View.GONE);
            mRegisterBtn.setEnabled(true);
            Toast.makeText(this, "Please enter a valid phone number", Toast.LENGTH_SHORT).show();
            return;
        }
        String rollNumber = mRollNumberField.getText().toString();
        if (TextUtils.isEmpty(rollNumber)) {
            submitprogressBar.setVisibility(View.GONE);
            mRegisterBtn.setEnabled(true);
            Toast.makeText(this, "Please enter your roll number", Toast.LENGTH_SHORT).show();
            return;
        }
        String collegeName = mCollegeName.getText().toString();
        if (TextUtils.isEmpty(collegeName)) {
            submitprogressBar.setVisibility(View.GONE);
            mRegisterBtn.setEnabled(true);
            Toast.makeText(this, "Please enter your college name", Toast.LENGTH_SHORT).show();
            return;
        }
        String year = mYearSpinner.getSelectedItem().toString();
        if (TextUtils.isEmpty(year)) {
            submitprogressBar.setVisibility(View.GONE);
            mRegisterBtn.setEnabled(true);
            Toast.makeText(this, "Please select your year", Toast.LENGTH_SHORT).show();
            return;
        }
        String department = mDepartmentSpinner.getSelectedItem().toString();
        if (TextUtils.isEmpty(department)) {
            submitprogressBar.setVisibility(View.GONE);
            mRegisterBtn.setEnabled(true);
            Toast.makeText(this, "Please select your department", Toast.LENGTH_SHORT).show();
            return;
        }
        String program = ((RadioButton)findViewById(mProgramRadioGroup.getCheckedRadioButtonId())).getText().toString();
        if (TextUtils.isEmpty(program)) {
            submitprogressBar.setVisibility(View.GONE);
            mRegisterBtn.setEnabled(true);
            Toast.makeText(this, "Please select your program", Toast.LENGTH_SHORT).show();
            return;
        }
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                isSubmitting = true; // Set state to submitting
                Eventname = eventNameTV.getText().toString();

                RegistrationModel registrationModel = new RegistrationModel(name, email, phone, rollNumber,collegeName, year, department, program);

                mFirestore.collection(Eventname).add(registrationModel)
                        .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                            @Override
                            public void onSuccess(DocumentReference documentReference) {
                                isSubmitting = false;
                                submitprogressBar.setVisibility(View.GONE);

                                Toast.makeText(EventRegisterActivity.this, "Registration successful!", Toast.LENGTH_SHORT).show();

                                Dialog popupDialog = new Dialog(EventRegisterActivity.this);
                                View popupView = LayoutInflater.from(EventRegisterActivity.this).inflate(R.layout.success_registration_popup, null);
                                popupDialog.setContentView(popupView);

                                // Set up the UI components of the popup layout
                                TextView popupTitle = popupView.findViewById(R.id.success_reg);
                                popupTitle.setText("Registration successful!");

                                // Show the popup screen
                                popupDialog.show();
                                // Dismiss the dialog after 3 seconds
                                new Handler().postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        if (popupDialog != null && popupDialog.isShowing()) {
                                            popupDialog.dismiss();
                                            String someValue = "success";
                                            Intent intent = new Intent(EventRegisterActivity.this, MainActivity.class);
                                            intent.putExtra("someKey", someValue); // add any necessary data
                                            startActivity(intent);

                                        }
                                    }
                                }, 1000); // Delay for 3 seconds (3000 milliseconds)


                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                isSubmitting = false;
                                submitprogressBar.setVisibility(View.GONE);
                                Toast.makeText(EventRegisterActivity.this, "Try Again Registration failed: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                                Dialog popupDialog = new Dialog(EventRegisterActivity.this);
                                View popupView = LayoutInflater.from(EventRegisterActivity.this).inflate(R.layout.failure_registration_popup, null);
                                popupDialog.setContentView(popupView);

                                // Set up the UI components of the popup layout
                                TextView popupTitle = popupView.findViewById(R.id.fail_reg);
                                popupTitle.setText("Registration Failed");

                                // Show the popup screen
                                popupDialog.show();
                                // Dismiss the dialog after 3 seconds
                                new Handler().postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        if (popupDialog != null && popupDialog.isShowing()) {
                                            popupDialog.dismiss();
                                            String someValue = "failed";
                                            Intent intent = new Intent(EventRegisterActivity.this, MainActivity.class);
                                            intent.putExtra("someKey", someValue); // add any necessary data
                                            startActivity(intent);

                                        }
                                    }
                                }, 1000); // Delay for 3 seconds (3000 milliseconds)
                            }
                        });
            }
        }, 5000); // Set timer for 30 seconds


    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        // Not needed for this implementation
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        // Not needed for this implementation
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {
        super.onPointerCaptureChanged(hasCapture);
    }
}