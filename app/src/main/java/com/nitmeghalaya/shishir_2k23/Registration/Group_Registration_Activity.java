package com.nitmeghalaya.shishir_2k23.Registration;

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
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.nitmeghalaya.shishir_2k23.MainActivity;
import com.nitmeghalaya.shishir_2k23.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Group_Registration_Activity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private EditText mTeamNameField;
    private EditText mleaderNameField;
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
    private EditText teamMemberEditText;
    private Button addTeamMemberButton;
    private ListView teamMembersList;
    private String Eventname;
    private List<String> teamMembers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_registration);

        eventNameTV = findViewById(R.id.event_name);
        eventNameTV.setText(getIntent().getStringExtra("event_name"));

        mTeamNameField = findViewById(R.id.Team_name_field);
        mleaderNameField = findViewById(R.id.name_field);
        mEmailField = findViewById(R.id.email_field);
        mPhoneField = findViewById(R.id.phone_field);
        mRollNumberField = findViewById(R.id.roll_number);
        mCollegeName = findViewById(R.id.college_name);
        mRegisterBtn = findViewById(R.id.register_button);
        teamMemberEditText = findViewById(R.id.team_member_edit_text);
        addTeamMemberButton = findViewById(R.id.add_team_member_button);
        teamMembersList = findViewById(R.id.team_members_list);

        //year
        mYearSpinner = findViewById(R.id.year_spinner);
        ArrayAdapter<CharSequence> yearAdapter = ArrayAdapter.createFromResource(this, R.array.year_options, android.R.layout.simple_spinner_item);
        yearAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mYearSpinner.setAdapter(yearAdapter);
        mYearSpinner.setOnItemSelectedListener(this);
        //Department
        mDepartmentSpinner = findViewById(R.id.department_spinner);
        ArrayAdapter<CharSequence> departmentAdapter = ArrayAdapter.createFromResource(this, R.array.department_options, android.R.layout.simple_spinner_item);
        departmentAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mDepartmentSpinner.setAdapter(departmentAdapter);
        mDepartmentSpinner.setOnItemSelectedListener(this);

        mProgramRadioGroup = findViewById(R.id.program_radio_group);

        //Add members
        teamMembers = new ArrayList<>();
        ArrayAdapter<String> teamMembersAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, teamMembers);
        teamMembersList.setAdapter(teamMembersAdapter);


        addTeamMemberButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String teamMemberName = teamMemberEditText.getText().toString();
                if (!TextUtils.isEmpty(teamMemberName)){
                    teamMembers.add(teamMemberName);
                    teamMemberEditText.setText("");
                    teamMembersAdapter.notifyDataSetChanged();
                }
            }
        });

        mFirestore = FirebaseFirestore.getInstance();

        mRegisterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onSubmitButtonClicked(view);
            }
        });
    }

    public void onSubmitButtonClicked(View view) {
        String Teamname = mTeamNameField.getText().toString();
        if (TextUtils.isEmpty(Teamname)) {
            Toast.makeText(this, "Please enter your Team name", Toast.LENGTH_SHORT).show();
            return;
        }
        String leader_name = mleaderNameField.getText().toString();
        if (TextUtils.isEmpty(leader_name)) {
            Toast.makeText(this, "Please enter your Leader name", Toast.LENGTH_SHORT).show();
            return;
        }
        String email = mEmailField.getText().toString();
        if (TextUtils.isEmpty(email)) {
            Toast.makeText(this, "Please enter your email", Toast.LENGTH_SHORT).show();
            return;
        }
        String phone = mPhoneField.getText().toString().trim();
        // Check if phone number is valid
        Pattern phonePattern = Pattern.compile("^[+]?[0-9]{10,13}$");
        Matcher phoneMatcher = phonePattern.matcher(phone);
        if (!phoneMatcher.matches()) {
            Toast.makeText(this, "Please enter a valid phone number", Toast.LENGTH_SHORT).show();
            return;
        }
        String rollNumber = mRollNumberField.getText().toString();
        if (TextUtils.isEmpty(rollNumber)) {
            Toast.makeText(this, "Please enter your roll number", Toast.LENGTH_SHORT).show();
            return;
        }
        String collegeName = mCollegeName.getText().toString();
        if (TextUtils.isEmpty(collegeName)) {
            Toast.makeText(this, "Please enter your college name", Toast.LENGTH_SHORT).show();
            return;
        }
        String year = mYearSpinner.getSelectedItem().toString();
        if (TextUtils.isEmpty(year)) {
            Toast.makeText(this, "Please select your year", Toast.LENGTH_SHORT).show();
            return;
        }
        String department = mDepartmentSpinner.getSelectedItem().toString();
        if (TextUtils.isEmpty(department)) {
            Toast.makeText(this, "Please select your department", Toast.LENGTH_SHORT).show();
            return;
        }
        String program = ((RadioButton)findViewById(mProgramRadioGroup.getCheckedRadioButtonId())).getText().toString();
        if (TextUtils.isEmpty(program)) {
            Toast.makeText(this, "Please select your program", Toast.LENGTH_SHORT).show();
            return;
        }
        Eventname = eventNameTV.getText().toString();


        GroupRegistrationModel registrationModel = new GroupRegistrationModel(Teamname,leader_name, email, phone, rollNumber,collegeName, year, department, program,teamMembers);

        mFirestore.collection(Eventname+"_Group").add(registrationModel)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        //Toast.makeText(Group_Registration_Activity.this, "Registration successful!", Toast.LENGTH_SHORT).show();
                        // Create a new dialog object and set its content view to the popup layout
                        Dialog popupDialog = new Dialog(Group_Registration_Activity.this);
                        View popupView = LayoutInflater.from(Group_Registration_Activity.this).inflate(R.layout.success_registration_popup, null);
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
                                    Intent intent = new Intent(Group_Registration_Activity.this, MainActivity.class);
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
                        Toast.makeText(Group_Registration_Activity.this, "Registration failed: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }



    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {
        super.onPointerCaptureChanged(hasCapture);
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}