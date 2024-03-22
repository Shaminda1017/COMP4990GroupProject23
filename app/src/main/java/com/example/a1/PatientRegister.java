package com.example.a1;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class PatientRegister extends AppCompatActivity {
    Button btn_register;
    EditText et_patient_name, et_patient_email, et_userName, et_patient_phone, et_patient_pw;
    DatabaseReference databaseReference;
    FirebaseDatabase databasep;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_register);

        btn_register = findViewById(R.id.patient_registration_button);
        et_patient_name = findViewById(R.id.et_patient_full_name);
        et_patient_email = findViewById(R.id.et_patient_email);
        et_userName = findViewById(R.id.et_patient_username);
        et_patient_phone = findViewById(R.id.phone_patient);
        et_patient_pw = findViewById(R.id.et_patient_password);

        // Initialize Firebase Database reference
        databaseReference = FirebaseDatabase.getInstance().getReference().child("Patients");

        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                databasep = FirebaseDatabase.getInstance();
                databaseReference = databasep.getReference("Patients");

                String fullName = et_patient_name.getText().toString().trim();
                String email = et_patient_email.getText().toString().trim();
                String userName = et_userName.getText().toString().trim();
                String phone = et_patient_phone.getText().toString().trim();
                String password = et_patient_pw.getText().toString().trim();


                if (!fullName.isEmpty() && !email.isEmpty() && !userName.isEmpty() && !phone.isEmpty() && !password.isEmpty()) {
                    // Create a new instance of HelperClass with the provided data
                    HelperClass patient = new HelperClass(fullName, email, userName, phone, password);
                    databaseReference.child(userName).setValue(patient);

                    // Get a reference to the "Patients" node in your Firebase database
                    DatabaseReference patientsRef = FirebaseDatabase.getInstance().getReference().child("Patients");

                    patientsRef.push().setValue(patient);

                    Toast.makeText(PatientRegister.this, "Registration successful", Toast.LENGTH_SHORT).show();

                    // Navigate to the login activity
                    Intent intent = new Intent(getApplicationContext(), PatientLogin.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(PatientRegister.this, "Please enter all the fields", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
