package com.example.a1;

import android.content.Intent;
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

    DBHelper DB;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_register);

        btn_register = findViewById(R.id.doctor_registration_button);
        et_doctor_name = findViewById(R.id.et_doctor_full_name);
        et_doctor_email = findViewById(R.id.et_doctor_email);
        et_doctor_reg = findViewById(R.id.et_doctor_registration);
        et_doctor_phone = findViewById(R.id.phone_doctor);
        et_doctor_pw = findViewById(R.id.et_doctor_password);
        et_doctor_re_pw = findViewById(R.id.et_doctor_re_password);

        DB = new DBHelper(this);

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

                if (fullName.equals("") || email.equals("") || phone.equals("") || regNumber.equals("") || password.equals("") || rePassword.equals("")) {
                    Toast.makeText(DoctorRegister.this, "Please enter all the fields", Toast.LENGTH_SHORT).show();
                } else if (!password.equals(rePassword)) {
                    Toast.makeText(DoctorRegister.this, "Passwords do not match", Toast.LENGTH_SHORT).show();
                } else {
                    boolean checkUser = DB.checkUsername(email);
                    if (!checkUser) {
                        boolean insert = DB.insertData(email, password, "doctor");
                        if (insert) {
                            Toast.makeText(DoctorRegister.this, "Registration successful", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(getApplicationContext(), DoctorLogin.class);
                            startActivity(intent);
                        } else {
                            Toast.makeText(DoctorRegister.this, "Registration failed", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(DoctorRegister.this, "User already exists", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

    }
}
