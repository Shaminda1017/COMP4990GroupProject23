package com.example.a1;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
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

public class DoctorCheckReports extends AppCompatActivity implements DoctorAdapter.OnItemClickListener {

    private RecyclerView recyclerViewReports;
    private DoctorAdapter doctorAdapter;
    private List<HelperClass> reportItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_check_report);

        recyclerViewReports = findViewById(R.id.recycler_view_reports);

        reportItems = new ArrayList<>();
        doctorAdapter = new DoctorAdapter(reportItems);
        doctorAdapter.setOnItemClickListener(this); // Set item click listener
        recyclerViewReports.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewReports.setAdapter(doctorAdapter);

        Button backBtn = findViewById(R.id.back_to_home_button);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        retrievePatientReports();
    }

    private void retrievePatientReports() {
        DatabaseReference reportsRef = FirebaseDatabase.getInstance().getReference().child("Documents");

        reportsRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                reportItems.clear(); // Clear previous data
                for (DataSnapshot reportSnapshot : dataSnapshot.getChildren()) {
                    String patientName = reportSnapshot.child("patientName").getValue(String.class);
                    String fileName = reportSnapshot.child("fileName").getValue(String.class);
                    String fileUrl = reportSnapshot.child("fileUrl").getValue(String.class);

                    HelperClass reportItem = new HelperClass(patientName, fileName, fileUrl);
                    reportItems.add(reportItem);
                }
                doctorAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle onCancelled
            }
        });
    }

    // Implement onItemClick method to handle item click
    @Override
    public void onItemClick(int position) {
        // Get the clicked report item
        HelperClass clickedItem = reportItems.get(position);

        // Open the URL associated with the clicked report in a web browser
        String fileUrl = clickedItem.getFileUrl();
        if (fileUrl != null && !fileUrl.isEmpty()) {
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(fileUrl));
            startActivity(browserIntent);
        } else {
            Toast.makeText(this, "File URL is empty", Toast.LENGTH_SHORT).show();
        }
    }
}
