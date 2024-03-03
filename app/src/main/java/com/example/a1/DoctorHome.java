package com.example.a1;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class DoctorHome extends AppCompatActivity implements MyAdapter.OnItemClickListener {
    ArrayList<MyDataSet> dataSets_doctor = new ArrayList<>();

    RecyclerView recyclerVie_doctor;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_home_page);

        recyclerVie_doctor = findViewById(R.id.recycler_view_doctor);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerVie_doctor.setLayoutManager(linearLayoutManager);

        dataSets_doctor.add(new MyDataSet("Schedule Appointment", R.drawable.doc_appt));
        dataSets_doctor.add(new MyDataSet("Check Your Inbox", R.drawable.notification));
        dataSets_doctor.add(new MyDataSet("Patient Visit", R.drawable.doc_visit));
        dataSets_doctor.add(new MyDataSet("Check Reports", R.drawable.patient_report));
        //dataSets_doctor.add(new MyDataSet("Check Your Past Medication", R.drawable.medical_history));

        MyAdapter myAdapter = new MyAdapter(dataSets_doctor, this);
        recyclerVie_doctor.setAdapter(myAdapter);
    }

    @Override
    public void onItemClick(int position) {
        // Open appropriate activity based on clicked item
        switch (position) {
            case 0:
                // Open Schedule Appointment activity
                startActivity(new Intent(DoctorHome.this, DoctorScheduleAppointment.class));
                break;
            case 1:
                // Open Check Your Inbox activity
                startActivity(new Intent(DoctorHome.this, DoctorCheckInbox.class));
                break;
            case 2:
                startActivity(new Intent(DoctorHome.this, DoctorPatientVisit.class));
                break;
            case 3:
                startActivity(new Intent(DoctorHome.this, DoctorCheckReports.class));
                break;
        }
    }
}
