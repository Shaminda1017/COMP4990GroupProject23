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

public class DoctorLogin extends AppCompatActivity{

    Button btn_sign_in, btn_register;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_login);

        btn_sign_in = findViewById(R.id.sign_in_btn_doctor);
        btn_register = findViewById(R.id.register_btn_doctor);


        btn_sign_in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("UserLogin", "Button Clicked - Starting UserHomePage");

                // Redirect to UserHomePage or perform other actions for sign-in
                Intent intent_sign_in = new Intent(DoctorLogin.this, DoctorHome.class);
                startActivity(intent_sign_in);
            }
        });

        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent_doctor_reg = new Intent(DoctorLogin.this, DoctorRegister.class);
                startActivity(intent_doctor_reg);
            }
        });

    }
}
