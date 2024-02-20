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

import java.net.PasswordAuthentication;

public class PatientLogin extends AppCompatActivity {

    Button btn_patient_sign_in, btn_patient_register;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_login);

        btn_patient_sign_in = findViewById(R.id.patient_sign_in_btn);
        btn_patient_register = findViewById(R.id.patient_register_btn);


        btn_patient_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("Register", "Button Clicked - Starting UserHomePage");

                Intent intent_patient_reg = new Intent(PatientLogin.this, PatientRegister.class);
                startActivity(intent_patient_reg);

            }
        });

        btn_patient_sign_in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("Sign in", "Button Clicked - Starting UserHomePage");

                Intent intent_patient_log_in = new Intent(PatientLogin.this, PatientHome.class);
                startActivity(intent_patient_log_in);
            }
        });
//
//        button_patient.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Log.d("UserLogin", "Button Clicked - Starting UserHomePage");
//
//                // Redirect to UserHomePage or perform other actions for sign-in
//                Intent intent_sign_in = new Intent(PatientLogin.this, UserHomePage.class);
//                startActivity(intent_sign_in);
//            }
//        });
    }
}
