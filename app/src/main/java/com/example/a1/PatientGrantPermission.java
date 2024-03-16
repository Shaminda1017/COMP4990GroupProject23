package com.example.a1;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

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
    TextView doc_name, doc_special, doc_hospital;

    private Button backBtn;
    private RecyclerView recyclerView;
    private DoctorAdapter doctorAdapter;
    private List<HelperClass> doctorList;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_give_permission);

        backBtn = findViewById(R.id.back_to_home_button);
        recyclerView = findViewById(R.id.recycler_view_doctors);
        doc_name = findViewById(R.id.doctor_name);
        doc_hospital = findViewById(R.id.et_doctor_hospital);
        doc_special = findViewById(R.id.et_doctor_specialization);

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        doctorList = new ArrayList<>();
        doctorAdapter = new DoctorAdapter(doctorList);
        recyclerView.setAdapter(doctorAdapter);

        // Retrieve doctors' data from Firebase
        retrieveDoctorsFromFirebase();
    }

    private void retrieveDoctorsFromFirebase() {
        DatabaseReference doctorsRef = FirebaseDatabase.getInstance().getReference().child("Doctors");

        doctorsRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
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
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });
    }

}
