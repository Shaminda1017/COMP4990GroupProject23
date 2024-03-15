package com.example.a1;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.a1.DoctorHome;
import com.example.a1.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

public class DoctorLogin extends AppCompatActivity {

    Button btn_sign_in, btn_register;
    EditText et_userName, et_doc_pw;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_login);

        btn_sign_in = findViewById(R.id.sign_in_btn_doctor);
        btn_register = findViewById(R.id.register_btn_doctor);
        et_userName = findViewById(R.id.et_doctor_username);
        et_doc_pw = findViewById(R.id.et_password);

        btn_sign_in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!validateUserName() || !validateUserPassword()) {

                } else {
                    checkUser();
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

    public Boolean validateUserName() {
        String val = et_userName.getText().toString();
        if (val.isEmpty()) {
            et_userName.setError("User cannot be empty");
            return false;
        } else {
            et_userName.setError(null);
            return true;
        }
    }

    public Boolean validateUserPassword() {
        String valP = et_doc_pw.getText().toString();
        if (valP.isEmpty()) {
            et_doc_pw.setError("Password cannot be empty");
            return false;
        } else {
            et_doc_pw.setError(null);
            return true;
        }
    }

    public void checkUser() {
        String username = et_userName.getText().toString().trim();
        String password = et_doc_pw.getText().toString().trim();

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Doctors");
        Query checkUserDB = reference.orderByChild("userName").equalTo(username);

        checkUserDB.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    for (DataSnapshot userSnapshot : snapshot.getChildren()) {
                        String pwFromDB = userSnapshot.child("password").getValue(String.class);
                        if (pwFromDB != null && pwFromDB.equals(password)) {
                            // Passwords match, login successful
                            Intent intent = new Intent(DoctorLogin.this, DoctorHome.class);
                            startActivity(intent);
                            return;
                        } else {
                            // Passwords don't match
                            et_doc_pw.setError("Invalid credentials");
                            et_doc_pw.requestFocus();
                            return;
                        }
                    }
                } else {
                    // User doesn't exist
                    et_userName.setError("User does not exist");
                    et_userName.requestFocus();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Handle database error
            }
        });
    }

}