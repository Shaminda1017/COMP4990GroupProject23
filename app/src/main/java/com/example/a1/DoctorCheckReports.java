package com.example.a1;

import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.FileDownloadTask;

import java.io.File;
import java.io.IOException;

import com.google.firebase.FirebaseApp;

public class DoctorCheckReports extends AppCompatActivity {

    Button backBtn, doc_upload_btn, doc_download_btn;
    ImageView downloadedImageView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_check_report);

        // Initialize Firebase
        FirebaseApp.initializeApp(this);

        backBtn = findViewById(R.id.back_to_home_button);
        doc_upload_btn = findViewById(R.id.doctor_upload_file_btn);
        doc_download_btn = findViewById(R.id.doc_dawnload_file_btn);
        //downloadedImageView = findViewById(R.id.downloaded_image_view);

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        doc_download_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                downloadFile();
            }
        });
    }

    private void downloadFile() {
        // Create a reference to the file you want to download
        StorageReference storageRef = FirebaseStorage.getInstance().getReference().child("path/to/your/file");

        try {
            // Create a temporary file
            File localFile = File.createTempFile("file", "extension");

            // Download the file to local storage
            storageRef.getFile(localFile).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                    // Local file created successfully, display it
                    displayDownloadedFile(localFile);
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception exception) {
                    // Handle any errors
                    Toast.makeText(DoctorCheckReports.this, "Failed to download file", Toast.LENGTH_SHORT).show();
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
            // Handle file creation error
        }
    }

    private void displayDownloadedFile(File localFile) {
        // Check if the downloaded file is an image
        if (localFile != null && localFile.exists()) {
            // Set the image in the ImageView
            downloadedImageView.setImageURI(Uri.fromFile(localFile));
        } else {
            Toast.makeText(this, "Downloaded file not found", Toast.LENGTH_SHORT).show();
        }
    }
}


//import android.os.Bundle;
//import android.view.View;
//import android.widget.Button;
//
//import androidx.annotation.Nullable;
//import androidx.appcompat.app.AppCompatActivity;
//
//public class DoctorCheckReports extends AppCompatActivity {
//
//    Button backBtn, doc_upload_btn, doc_dawnload_btn;
//    @Override
//    protected void onCreate(@Nullable Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_doctor_check_report);
//
//        backBtn = findViewById(R.id.back_to_home_button);
//        doc_upload_btn = findViewById(R.id.doctor_upload_file_btn);
//        doc_dawnload_btn = findViewById(R.id.doc_dawnload_file_btn);
//        backBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                finish();
//            }
//        });
//    }
//}
