package com.example.shishir_2k23;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.shishir_2k23.Event.EventFragment;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class EventRegisterActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {


    private EditText mNameField;
    private EditText mEmailField;
    private EditText mPhoneField;
    private EditText mRollNumberField;
    private EditText mCollegeName;
    private Spinner mYearSpinner;
    private Button mRegisterBtn;
    private Spinner mDepartmentSpinner;
    private RadioGroup mProgramRadioGroup;
    FirebaseFirestore mFirestore;
    private TextView eventNameTV;
    private String Eventname;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_register);

        eventNameTV = findViewById(R.id.event_name);
        eventNameTV.setText(getIntent().getStringExtra("event_name"));

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
        String name = mNameField.getText().toString();
        String email = mEmailField.getText().toString();
        String phone = mPhoneField.getText().toString();
        String rollNumber = mRollNumberField.getText().toString();
        String collegeName = mCollegeName.getText().toString();
        String year = mYearSpinner.getSelectedItem().toString();
        String department = mDepartmentSpinner.getSelectedItem().toString();
        String program = ((RadioButton)findViewById(mProgramRadioGroup.getCheckedRadioButtonId())).getText().toString();
        Eventname = eventNameTV.getText().toString();

        RegistrationModel registrationModel = new RegistrationModel(name, email, phone, rollNumber,collegeName, year, department, program);

        mFirestore.collection(Eventname).add(registrationModel)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Toast.makeText(EventRegisterActivity.this, "Registration successful!", Toast.LENGTH_SHORT).show();
                        EventFragment eventFragment = new EventFragment();

                        // Start a new transaction and replace the current Fragment with the Home Fragment
                        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                        fragmentTransaction.replace(R.id.frame_layout, eventFragment);
                        fragmentTransaction.addToBackStack(null);
                        fragmentTransaction.commit();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(EventRegisterActivity.this, "Registration failed: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
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