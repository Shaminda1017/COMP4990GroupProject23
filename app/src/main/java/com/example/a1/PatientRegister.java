package com.example.a1;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class PatientRegister extends AppCompatActivity {
    Button btn_register;
    EditText et_patient_name, et_patient_email, et_patient_reg, et_patient_phone, et_patient_pw, et_patient_re_pw;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_register);

        btn_register = findViewById(R.id.patient_registration_button);

        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("UserLogin", "Button Clicked - Starting UserHomePage");

                String fullName = et_patient_name.getText().toString().trim();
                String email = et_patient_email.getText().toString().trim();
                String phone = et_patient_phone.getText().toString().trim();
                String regNumber = et_patient_reg.getText().toString().trim();
                String password = et_patient_pw.getText().toString().trim();
                String rePassword = et_patient_re_pw.getText().toString().trim();

                // Perform registration logic (example: check if fields are not empty)
                if (!fullName.isEmpty() && !email.isEmpty() && !phone.isEmpty() && !regNumber.isEmpty() && !password.isEmpty() && password.equals(rePassword)) {
                    // Registration successful
                    Log.d("UserLogin", "Registration Successful");

                    // Show a toast message
                    Toast.makeText(PatientRegister.this, "Registration Successful", Toast.LENGTH_SHORT).show();

                } else {
                    // Registration failed
                    Log.d("UserLogin", "Registration Failed");

                    // Show a toast message
                    Toast.makeText(PatientRegister.this, "Registration Failed. Please check your input.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
