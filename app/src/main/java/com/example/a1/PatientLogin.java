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
    EditText et_email, et_password;
    DBHelper DB;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_login);

        btn_patient_sign_in = findViewById(R.id.patient_sign_in_btn);
        btn_patient_register = findViewById(R.id.patient_register_btn);
        et_email = findViewById(R.id.patient_email);
        et_password = findViewById(R.id.patient_password);

        DB = new DBHelper(this);


        btn_patient_sign_in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("Sign in", "Button Clicked - Starting UserHomePage");
                String username = et_email.getText().toString();
                String password = et_password.getText().toString();

                if(username.equals("") || password.equals(""))
                    Toast.makeText(PatientLogin.this, "Please enter all the fields", Toast.LENGTH_SHORT).show();
                else {
                    Boolean checkuserpass = DB.checkUsernamePassword(username, password, "patient");
                    if(checkuserpass) {
                        Toast.makeText(PatientLogin.this, "Sign in successful", Toast.LENGTH_SHORT).show();
                        Intent intent_patient_log_in = new Intent(PatientLogin.this, PatientHome.class);
                        startActivity(intent_patient_log_in);
                    } else {
                        Toast.makeText(PatientLogin.this, "Failed credentials", Toast.LENGTH_SHORT).show();
                    }
                }


            }
        });

        btn_patient_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent_patient_reg = new Intent(PatientLogin.this, PatientRegister.class);
                startActivity(intent_patient_reg);
            }
        });

    }
}