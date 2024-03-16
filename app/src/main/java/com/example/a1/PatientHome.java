package com.example.a1;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;


public class PatientHome extends AppCompatActivity implements MyAdapter.OnItemClickListener {
    ArrayList<MyDataSet> dataSets = new ArrayList<>();
    RecyclerView recyclerView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_home_page);

        recyclerView = findViewById(R.id.recycler_view);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);

        dataSets.add(new MyDataSet("Book Your Appointment", R.drawable.book_your_appt));
        dataSets.add(new MyDataSet("Check Your Inbox", R.drawable.notification));
        dataSets.add(new MyDataSet("Grant Your Permission", R.drawable.access_control));
        dataSets.add(new MyDataSet("Upload Your Document", R.drawable.upload));
        dataSets.add(new MyDataSet("Check Your Past Medication", R.drawable.medical_history));

        MyAdapter myAdapter = new MyAdapter(dataSets, this);
        recyclerView.setAdapter(myAdapter);
    }

    @Override
    public void onItemClick(int position) {
        // Open appropriate activity based on clicked item
        switch (position) {
            case 0:
                // Open Schedule Appointment activity
                startActivity(new Intent(PatientHome.this, PatientBookApt.class));
                break;
            case 1:
                // Open Check Your Inbox activity
                startActivity(new Intent(PatientHome.this, PatientCheckInbox.class));
                break;
            case 2:
                startActivity(new Intent(PatientHome.this, PatientGrantPermission.class));
                break;
            case 3:
                startActivity(new Intent(PatientHome.this, PatientUploadDoc.class));
                break;
            case 4:
                startActivity(new Intent(PatientHome.this, PatientMedHistory.class));
                break;
        }
    }
}
