package com.example.a1;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.a1.HelperClass;
import com.example.a1.PatientHome;
import com.example.a1.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.Cipher;
import java.security.NoSuchAlgorithmException;
import android.util.Base64;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PatientCheckInbox extends AppCompatActivity {

    Button backBtn, msgSendBtn, encryptBtn;
    EditText et_patient_msg;
    Spinner doc_select;

    // Firebase variables
    FirebaseDatabase database;
    DatabaseReference messagesRef, doctorsRef;

    String patientId;
    SecretKey secretKey; // Declare SecretKey variable to store the generated key

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_check_inbox);

        // Initialize Firebase Database reference
        messagesRef = FirebaseDatabase.getInstance().getReference().child("Messages");

        // Initialize Firebase database references
        database = FirebaseDatabase.getInstance();
        doctorsRef = database.getReference("Doctors");

        backBtn = findViewById(R.id.back_to_home_button);
        msgSendBtn = findViewById(R.id.btn_msg_submit);
        et_patient_msg = findViewById(R.id.patient_msg_to_doctor);
        doc_select = findViewById(R.id.select_doctor);
        encryptBtn = findViewById(R.id.btn_encrypt);

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        msgSendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendMessageToDoctor();
            }
        });

        encryptBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                encryptMessageContent();
            }
        });

        // Obtain the patientId from Firebase Authentication
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            patientId = user.getUid();
        } else {
            // Handle the case where the user is not logged in
        }

        // Populate the spinner with list of doctors
        populateDoctorSpinner();

        // Generate Secret Key
        try {
            secretKey = generateSecretKey();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }
    @Override
    protected void onStart() {
        super.onStart();

        // Check if the user is signed in (non-null) and update UI accordingly
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if (currentUser == null) {
            // User is not signed in, show a toast message
            Toast.makeText(this, "Please log in to access this feature", Toast.LENGTH_SHORT).show();
        }
    }

    private void populateDoctorSpinner() {
        doctorsRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                List<String> doctorNames = new ArrayList<>();
                for (DataSnapshot doctorSnapshot : dataSnapshot.getChildren()) {
                    String doctorName = doctorSnapshot.child("fullName").getValue(String.class);
                    doctorNames.add(doctorName);
                }
                ArrayAdapter<String> adapter = new ArrayAdapter<>(PatientCheckInbox.this, android.R.layout.simple_spinner_item, doctorNames);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                doc_select.setAdapter(adapter);

                messagesRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            HelperClass message = snapshot.getValue(HelperClass.class);
                            if (message != null) {
                                String formattedTimestamp = message.getFormattedTimestamp();
                                // Now you can use the formatted timestamp in your app
                                Log.d("Formatted Timestamp", formattedTimestamp);
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        // Handle error
                    }
                });

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle error
            }
        });
    }

    private void sendMessageToDoctor() {
        Object selectedItem = doc_select.getSelectedItem();
        if (selectedItem != null) {
            String doctorName = selectedItem.toString(); // Get selected doctor's name from Spinner
            final String messageContent = et_patient_msg.getText().toString().trim();

            // Get the currently logged-in user
            FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
            String senderName = currentUser != null ? currentUser.getDisplayName() : "Unknown"; // Provide a default name if display name is null
            if (!TextUtils.isEmpty(messageContent)) {
                // Get the current timestamp
                long timestamp = System.currentTimeMillis();

                // Create an instance of HelperClass with senderName, receiverName, messageContent, and timestamp
                HelperClass message = new HelperClass(senderName, doctorName, messageContent);
                message.setTimestamp(timestamp);
                messagesRef.child(doctorName).setValue(message);


                // Save the message to Firebase under the "Messages" node
               // DatabaseReference messagesRef = database.getReference("Messages");
                messagesRef.push().setValue(message)

                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(PatientCheckInbox.this, "Message sent successfully", Toast.LENGTH_SHORT).show();
                                et_patient_msg.setText(""); // Clear message input field after sending
                                startActivity(new Intent(PatientCheckInbox.this, PatientHome.class)); // Navigate to patient home page after successful message sending
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(PatientCheckInbox.this, "Failed to send message: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
            } else {
                Toast.makeText(PatientCheckInbox.this, "Please enter a message", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(PatientCheckInbox.this, "Please select a doctor", Toast.LENGTH_SHORT).show();
        }
    }


    private void encryptMessageContent() {
        String messageContent = et_patient_msg.getText().toString().trim();
        if (!TextUtils.isEmpty(messageContent)) {
            String encryptedMessage = encryptMessage(messageContent);
            if (encryptedMessage != null) {
                et_patient_msg.setText(encryptedMessage);
            } else {
                Toast.makeText(this, "Encryption failed", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "Please enter a message", Toast.LENGTH_SHORT).show();
        }
    }

    private String encryptMessage(String message) {
        try {
            if (secretKey != null) { // Check if secretKey is not null
                Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
                cipher.init(Cipher.ENCRYPT_MODE, secretKey);
                byte[] encryptedBytes = cipher.doFinal(message.getBytes("UTF-8"));
                return Base64.encodeToString(encryptedBytes, Base64.DEFAULT);
            } else {
                Log.e("Encryption Error", "Secret key is null");
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private SecretKey generateSecretKey() throws NoSuchAlgorithmException {
        KeyGenerator keyGen = KeyGenerator.getInstance("AES");
        keyGen.init(256); // You can adjust the key size as needed (128, 192, or 256)
        return keyGen.generateKey();
    }
}
