package com.example.a1;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class DoctorRegister extends AppCompatActivity {
    Button btn_register;
    EditText et_doctor_name, et_doctor_email, et_doctor_username, phone_doctor, et_doctor_password, et_doctor_re_password, et_doctor_hos, et_doctor_special;

    FirebaseDatabase database;
    DatabaseReference reference;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_register);

        btn_register = findViewById(R.id.doctor_registration_button);
        et_doctor_name = findViewById(R.id.et_doctor_full_name);
        et_doctor_email = findViewById(R.id.et_doctor_email);
        et_doctor_username = findViewById(R.id.et_doctor_username);
        phone_doctor = findViewById(R.id.phone_doctor);
        et_doctor_password = findViewById(R.id.et_doctor_password);
        et_doctor_re_password = findViewById(R.id.et_doctor_re_password);
        et_doctor_hos = findViewById(R.id.et_doctor_hospital);
        et_doctor_special = findViewById(R.id.et_doctor_specialization);

        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("DoctorRegister", "Button Clicked - Starting UserHomePage");

                database = FirebaseDatabase.getInstance();
                reference = database.getReference("Doctors");

                String fullName = et_doctor_name.getText().toString().trim();
                String email = et_doctor_email.getText().toString().trim();
                String phone = phone_doctor.getText().toString().trim();
                String userName = et_doctor_username.getText().toString().trim();
                String hospital = et_doctor_hos.getText().toString().trim();
                String special = et_doctor_special.getText().toString().trim();
                String password = et_doctor_password.getText().toString().trim();
                String rePassword = et_doctor_re_password.getText().toString().trim();

                // Logging the values before saving to Firebase
                Log.d("DoctorRegister", "Full Name: " + fullName);
                Log.d("DoctorRegister", "Email: " + email);
                Log.d("DoctorRegister", "Phone: " + phone);
                Log.d("DoctorRegister", "Username: " + userName);
                Log.d("DoctorRegister", "Password: " + password);
                Log.d("DoctorRegister", "Re-entered Password: " + rePassword);

                if (fullName.isEmpty() || email.isEmpty() || phone.isEmpty() || hospital.isEmpty() || special.isEmpty() || userName.isEmpty() || password.isEmpty() || rePassword.isEmpty()) {
                    Toast.makeText(DoctorRegister.this, "Please enter all the fields", Toast.LENGTH_SHORT).show();
                } else if (!password.equals(rePassword)) {
                    Toast.makeText(DoctorRegister.this, "Passwords do not match", Toast.LENGTH_SHORT).show();
                } else {
                    HelperClass helperClass = new HelperClass(fullName, email, phone, hospital, userName, special, password);
                    //reference.child(userName).setValue(helperClass);
                    reference.child(String.valueOf(userName)).setValue(helperClass);


                    // Assuming 'users' node already exists in your Firebase Realtime Database
                    Toast.makeText(DoctorRegister.this, "Registration successful", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getApplicationContext(), DoctorLogin.class);
                    startActivity(intent);
                }
            }
        });
    }
}
