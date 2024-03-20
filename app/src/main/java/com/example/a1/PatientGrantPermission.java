package com.example.a1;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class PatientGrantPermission extends AppCompatActivity {

    private static final String TAG = "PatientGrantPermission";

    private Button backBtn;
    private Button grantPermissionBtn;
    private RecyclerView recyclerView;
    private DoctorAdapter doctorAdapter;
    private List<HelperClass> doctorList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_give_permission);

        // Initialize views
        backBtn = findViewById(R.id.back_to_home_button);
        grantPermissionBtn = findViewById(R.id.grant_permission_button);
        recyclerView = findViewById(R.id.recycler_view_doctors);

        // Set click listener for the "Back" button
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        // Set click listener for the "Grant Permission" button
        grantPermissionBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                grantPermissionToSelectedDoctors();
            }
        });

        // Setup RecyclerView
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Initialize doctor list and adapter
        doctorList = new ArrayList<>();
        doctorAdapter = new DoctorAdapter(doctorList);
        recyclerView.setAdapter(doctorAdapter);

        // Retrieve doctors' data from Firebase
        retrieveDoctorsFromFirebase();
    }

    // Method to retrieve doctors' data from Firebase
    private void retrieveDoctorsFromFirebase() {
        DatabaseReference doctorsRef = FirebaseDatabase.getInstance().getReference().child("Doctors");

        doctorsRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                doctorList.clear(); // Clear previous data
                for (DataSnapshot doctorSnapshot : dataSnapshot.getChildren()) {
                    String doctorId = doctorSnapshot.getKey();
                    // Now you have the doctorId (e.g., "doctor1")
                    // You can retrieve specific data for each doctor if needed
                    // For example:
                    String doctorName = doctorSnapshot.child("fullName").getValue(String.class); // Assuming you stored doctor's full name
                    String doctorSpecialization = doctorSnapshot.child("specialization").getValue(String.class);
                    String doctorHospital = doctorSnapshot.child("hospital").getValue(String.class);

                    Log.d(TAG, "Doctor ID: " + doctorId + ", Doctor Name: " + doctorName +
                            ", Specialization: " + doctorSpecialization + ", Hospital: " + doctorHospital);

                    // Create a HelperClass object with retrieved data
                    HelperClass doctor = new HelperClass();
                    doctor.setId(doctorId); // Set doctor's ID
                    doctor.setFullName(doctorName);
                    doctor.setSpecialization(doctorSpecialization);
                    doctor.setHospital(doctorHospital);

                    // Add the doctor to the list
                    doctorList.add(doctor);
                }
                // Notify adapter about the data change
                doctorAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });
    }

    private void grantPermissionToSelectedDoctors() {
        // Initialize an ArrayList to store allowed patient IDs
        ArrayList<String> allowedPatientIds = new ArrayList<>();

        // Iterate through the list of doctors in the adapter
        for (HelperClass doctor : doctorList) {
            // Check if the doctor is selected (you need to implement this logic in your adapter)
            if (doctor.isSelected()) {
                // If selected, add the doctor's ID to the list of allowed patient IDs
                allowedPatientIds.add(doctor.getId());

                // Save the permission data to the database
                savePermissionData(doctor.getId(), "PatientID"); // Replace "PatientID" with the actual patient ID
            }
        }

        // Once you have the list of allowed patient IDs, start the DoctorCheckReports activity
        Intent intent = new Intent(PatientGrantPermission.this, DoctorCheckReports.class);
        intent.putStringArrayListExtra("allowedPatientIds", allowedPatientIds);
        startActivity(intent);
    }

    private void savePermissionData(String doctorId, String patientId) {
        // Get reference to the permissions table in the database
        DatabaseReference permissionsRef = FirebaseDatabase.getInstance().getReference().child("Permissions");

        // Create a unique key for this permission entry (e.g., combining doctorId and patientId)
        String permissionId = permissionsRef.push().getKey();

        // Set the data to be saved
        permissionsRef.child(permissionId).child("doctorId").setValue(doctorId);
        permissionsRef.child(permissionId).child("patientId").setValue(patientId);
        // You can set additional data like permission date, etc.
        // permissionsRef.child(permissionId).child("date").setValue(ServerValue.TIMESTAMP);
    }

}
