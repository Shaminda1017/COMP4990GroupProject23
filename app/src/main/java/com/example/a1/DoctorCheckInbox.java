package com.example.a1;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Base64;
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

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

public class DoctorCheckInbox extends AppCompatActivity {

    Button backBtn, msgSendBtn, encryptBtn;
    EditText et_doctor_msg;
    Spinner patientSpinner;

    // Firebase variables
    FirebaseDatabase database;
    DatabaseReference messagesRef, patientsRef;

    // Declare the secret key used for encryption
    SecretKey secretKey;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_check_inbox);

        backBtn = findViewById(R.id.back_to_home_button);
        msgSendBtn = findViewById(R.id.btn_msg_submit);
        et_doctor_msg = findViewById(R.id.doctor_msg_to_patient);
        encryptBtn = findViewById(R.id.btn_encrypt);
        patientSpinner = findViewById(R.id.select_patient);

        // Initialize Firebase Database reference
        messagesRef = FirebaseDatabase.getInstance().getReference().child("Messages");

        // Initialize Firebase database references
        database = FirebaseDatabase.getInstance();
        patientsRef = database.getReference("Patients");

        // Generate or obtain the secret key used for encryption
        try {
            secretKey = generateSecretKey();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        msgSendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendMessageToPatient();
            }
        });

        encryptBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                encryptMessageContent();
            }
        });

        // Populate the spinner with list of patients
        populatePatientSpinner();
    }

    private void populatePatientSpinner() {
        patientsRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                List<String> patientNames = new ArrayList<>();
                for (DataSnapshot patientSnapshot : dataSnapshot.getChildren()) {
                    String patientName = patientSnapshot.child("fullName").getValue(String.class);
                    patientNames.add(patientName);
                }
                ArrayAdapter<String> adapter = new ArrayAdapter<>(DoctorCheckInbox.this, android.R.layout.simple_spinner_item, patientNames);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                patientSpinner.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle error
                Toast.makeText(DoctorCheckInbox.this, "Failed to retrieve patient names: " + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void sendMessageToPatient() {
        String patientName = patientSpinner.getSelectedItem().toString(); // Get selected patient's name from Spinner

        // Retrieve patient's ID based on the selected name (assuming it's stored in the database)
        // For demonstration purposes, let's assume the ID is "123".
       // String patientId = "123";
        String doctorName = "Doctor"; // Set the sender's name as the doctor's name

        String messageContent = et_doctor_msg.getText().toString().trim();
        if (!TextUtils.isEmpty(messageContent)) {
            // Encrypt the message
            String encryptedMessage = encryptMessage(messageContent);
            if (encryptedMessage != null) {
                // Create a new message object with sender and receiver names
                HelperClass message = new HelperClass(doctorName, patientName, encryptedMessage);
                messagesRef.child(patientName).setValue(message);

                // Save the message to Firebase
                messagesRef.child(patientName).setValue(message, new DatabaseReference.CompletionListener() {
                    @Override
                    public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                        if (error == null) {
                            // Message sent successfully
                            Toast.makeText(DoctorCheckInbox.this, "Message sent successfully", Toast.LENGTH_SHORT).show();
                            et_doctor_msg.setText(""); // Clear message input field after sending
                            startActivity(new Intent(DoctorCheckInbox.this, DoctorHome.class)); // Navigate to doctor home page after successful message sending
                        } else {
                            // Failed to send message
                            Toast.makeText(DoctorCheckInbox.this, "Failed to send message: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            } else {
                Toast.makeText(this, "Encryption failed", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "Please enter a message", Toast.LENGTH_SHORT).show();
        }
    }

    private void displayMessagesFromFirebase() {
        // Listen for changes in the message data
        messagesRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // Handle data changes
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    HelperClass message = snapshot.getValue(HelperClass.class);
                    if (message != null) {
                        String senderName = message.getSenderName();
                        String receiverName = message.getReceiverName();
                        String encryptedMessage = message.getMessageContent();

                        // Decrypt the message
                        String decryptedMessage = decrypt(encryptedMessage);

                        // Display the sender's name, receiver's name, and decrypted message
                        Log.d("Message", "Sender: " + senderName + ", Receiver: " + receiverName + ", Message: " + decryptedMessage);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle database error
                Toast.makeText(DoctorCheckInbox.this, "Failed to retrieve messages: " + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
    private String decrypt(String encryptedMessage) {
        try {
            if (secretKey != null) {
                Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
                cipher.init(Cipher.DECRYPT_MODE, secretKey);
                byte[] decryptedBytes = cipher.doFinal(Base64.decode(encryptedMessage, Base64.DEFAULT));
                return new String(decryptedBytes);
            } else {
                Toast.makeText(this, "Secret key is null", Toast.LENGTH_SHORT).show();
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    private void encryptMessageContent() {
        String messageContent = et_doctor_msg.getText().toString().trim();
        if (!TextUtils.isEmpty(messageContent)) {
            String encryptedMessage = encryptMessage(messageContent);
            if (encryptedMessage != null) {
                et_doctor_msg.setText(encryptedMessage);
            } else {
                Toast.makeText(this, "Encryption failed", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "Please enter a message", Toast.LENGTH_SHORT).show();
        }
    }

    private String encryptMessage(String message) {
        try {
            if (secretKey != null) {
                Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
                cipher.init(Cipher.ENCRYPT_MODE, secretKey);
                byte[] encryptedBytes = cipher.doFinal(message.getBytes());
                return Base64.encodeToString(encryptedBytes, Base64.DEFAULT);
            } else {
                Toast.makeText(this, "Secret key is null", Toast.LENGTH_SHORT).show();
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private SecretKey generateSecretKey() throws NoSuchAlgorithmException {
        KeyGenerator keyGen = KeyGenerator.getInstance("AES");
        keyGen.init(256);
        return keyGen.generateKey();
    }
}
