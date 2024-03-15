package com.example.a1;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.a1.PatientHome;
import com.example.a1.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class PatientLogin extends AppCompatActivity {

    Button btn_patient_sign_in, btn_patient_register;
    EditText et_username, et_password;
    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_login);

        btn_patient_sign_in = findViewById(R.id.patient_sign_in_btn);
        btn_patient_register = findViewById(R.id.patient_register_btn);
        et_username = findViewById(R.id.patient_username);
        et_password = findViewById(R.id.patient_password);

        firebaseAuth = FirebaseAuth.getInstance();

        btn_patient_sign_in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkUser();
            }
        });

        btn_patient_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(PatientLogin.this, PatientRegister.class));
            }
        });
    }

    public void checkUser() {
        String username = et_username.getText().toString().trim();
        String password = et_password.getText().toString().trim();

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Patients");
        Query checkUserDB = reference.orderByChild("userName").equalTo(username);

        checkUserDB.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    for (DataSnapshot userSnapshot : snapshot.getChildren()) {
                        String pwFromDB = userSnapshot.child("password").getValue(String.class);
                        if (pwFromDB != null && pwFromDB.equals(password)) {
                            // Passwords match, login successful
                            Intent intent = new Intent(PatientLogin.this, PatientHome.class);
                            startActivity(intent);
                            return;
                        } else {
                            // Passwords don't match
                            et_password.setError("Invalid credentials");
                            et_password.requestFocus();
                            return;
                        }
                    }
                } else {
                    // User doesn't exist
                    et_username.setError("User does not exist");
                    et_username.requestFocus();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Handle database error
            }
        });
    }


    // Method to authenticate user using Firebase Auth
    private boolean authenticateUser(String username, String password) {
        // Your Firebase Auth logic to authenticate user
        // Replace this with your Firebase Auth code
        // For simplicity, I'm returning true here
        return true;
    }

    private void rememberUsername(String username) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("rememberedUsername", username);
        editor.apply();
    }

    private void rememberPassword(String password) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("rememberedPassword", password);
        editor.apply();
    }
}
