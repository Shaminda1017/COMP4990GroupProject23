package com.example.a1;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
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
    DBHelper DB;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_register);

        btn_register = findViewById(R.id.patient_registration_button);
        et_patient_name = findViewById(R.id.et_patient_full_name);
        et_patient_email = findViewById(R.id.et_patient_email);
        et_patient_reg = findViewById(R.id.et_patient_registration);
        et_patient_phone = findViewById(R.id.phone_patient);
        et_patient_pw = findViewById(R.id.et_patient_password);
        et_patient_re_pw = findViewById(R.id.et_patient_re_password);

        DB = new DBHelper(this);

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

                if (fullName.equals("") || email.equals("") || phone.equals("") || regNumber.equals("") || password.equals("") || rePassword.equals("")) {
                    Toast.makeText(PatientRegister.this, "Please enter all the fields", Toast.LENGTH_SHORT).show();
                } else if (!password.equals(rePassword)) {
                    Toast.makeText(PatientRegister.this, "Passwords do not match", Toast.LENGTH_SHORT).show();
                } else {
                    boolean checkUser = DB.checkUsername(email);
                    if (!checkUser) {
                        boolean insert = DB.insertData(email, password, "patient");
                        if (insert) {
                            Toast.makeText(PatientRegister.this, "Registration successful", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(getApplicationContext(), PatientLogin.class);
                            startActivity(intent);
                        } else {
                            Toast.makeText(PatientRegister.this, "Registration failed", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(PatientRegister.this, "User already exists", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }
}

