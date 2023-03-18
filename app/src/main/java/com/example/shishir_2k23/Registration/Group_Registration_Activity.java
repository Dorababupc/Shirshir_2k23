package com.example.shishir_2k23.Registration;

import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.TextUtils;
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

import com.example.shishir_2k23.Event.EventFragment;
import com.example.shishir_2k23.EventRegisterActivity;
import com.example.shishir_2k23.R;
import com.example.shishir_2k23.RegistrationModel;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

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
        String leader_name = mleaderNameField.getText().toString();
        String email = mEmailField.getText().toString();
        String phone = mPhoneField.getText().toString();
        String rollNumber = mRollNumberField.getText().toString();
        String collegeName = mCollegeName.getText().toString();
        String year = mYearSpinner.getSelectedItem().toString();
        String department = mDepartmentSpinner.getSelectedItem().toString();
        String program = ((RadioButton)findViewById(mProgramRadioGroup.getCheckedRadioButtonId())).getText().toString();
        Eventname = eventNameTV.getText().toString();


        GroupRegistrationModel registrationModel = new GroupRegistrationModel(Teamname,leader_name, email, phone, rollNumber,collegeName, year, department, program,teamMembers);

        mFirestore.collection(Eventname+"_Group").add(registrationModel)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Toast.makeText(Group_Registration_Activity.this, "Registration successful!", Toast.LENGTH_SHORT).show();
                        //HomeFragment homeFragment = new HomeFragment();
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