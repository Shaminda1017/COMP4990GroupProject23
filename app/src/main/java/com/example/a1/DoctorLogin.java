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
    EditText et_doc_email, et_doc_pw;
    DBHelper DB;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_login);

        btn_sign_in = findViewById(R.id.sign_in_btn_doctor);
        btn_register = findViewById(R.id.register_btn_doctor);
        et_doc_email = findViewById(R.id.et_doctor_email);
        et_doc_pw = findViewById(R.id.et_password);

        DB = new DBHelper(this);


        btn_sign_in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("UserLogin", "Button Clicked - Starting UserHomePage");
                String username = et_doc_email.getText().toString();
                String password = et_doc_pw.getText().toString();

                if(username.equals("") || password.equals(""))
                    Toast.makeText(DoctorLogin.this, "Please enter all the fields", Toast.LENGTH_SHORT).show();
                else {
                    Boolean checkuserpass = DB.checkUsernamePassword(username, password, "doctor");
                    if(checkuserpass) {
                        Toast.makeText(DoctorLogin.this, "Sign in successful", Toast.LENGTH_SHORT).show();
                        Intent intent_patient_log_in = new Intent(DoctorLogin.this, DoctorHome.class);
                        startActivity(intent_patient_log_in);
                    } else {
                        Toast.makeText(DoctorLogin.this, "Failed credentials", Toast.LENGTH_SHORT).show();
                    }
                }

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
