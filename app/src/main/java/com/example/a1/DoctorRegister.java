package com.example.a1;

import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class DoctorRegister extends AppCompatActivity {
    Button btn_register;
    EditText et_doctor_name, et_doctor_email, et_doctor_reg, et_doctor_phone, et_doctor_pw, et_doctor_re_pw;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_register);

        btn_register = findViewById(R.id.doctor_registration_button);

        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("UserLogin", "Button Clicked - Starting UserHomePage");

                String fullName = et_doctor_name.getText().toString().trim();
                String email = et_doctor_email.getText().toString().trim();
                String phone = et_doctor_phone.getText().toString().trim();
                String regNumber = et_doctor_reg.getText().toString().trim();
                String password = et_doctor_pw.getText().toString().trim();
                String rePassword = et_doctor_re_pw.getText().toString().trim();

                // Perform registration logic (example: check if fields are not empty)
                if (!fullName.isEmpty() && !email.isEmpty() && !phone.isEmpty() && !regNumber.isEmpty() && !password.isEmpty() && password.equals(rePassword)) {
                    // Registration successful
                    Log.d("UserLogin", "Registration Successful");

                    // Show a toast message
                    Toast.makeText(DoctorRegister.this, "Registration Successful", Toast.LENGTH_SHORT).show();

                } else {
                    // Registration failed
                    Log.d("UserLogin", "Registration Failed");

                    // Show a toast message
                    Toast.makeText(DoctorRegister.this, "Registration Failed. Please check your input.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
