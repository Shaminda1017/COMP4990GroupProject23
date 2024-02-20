package com.example.a1;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    Button btn_gerStarted_doctor, btn_gerStarted_patient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn_gerStarted_doctor = findViewById(R.id.get_started_btn_doctor);
        btn_gerStarted_patient = findViewById(R.id.get_started_btn_patient);



        btn_gerStarted_patient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent_patient_login = new Intent(MainActivity.this, PatientLogin.class);
                startActivity(intent_patient_login);
            }
        });

        btn_gerStarted_doctor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent_doctor_login = new Intent(MainActivity.this, DoctorLogin.class);
                startActivity(intent_doctor_login);
            }
        });

    }
}