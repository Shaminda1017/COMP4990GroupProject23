package com.example.a1;
import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.a1.HelperClass;
import com.example.a1.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class PatientUploadDoc extends AppCompatActivity {
    Button backBtn, pUploadBtn;
    EditText etPatientFile;

    StorageReference storageReference;
    DatabaseReference databaseReference;

    Uri fileUri;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_upload_doc);

        backBtn = findViewById(R.id.back_to_home_button);
        pUploadBtn = findViewById(R.id.p_upload_btn);
        etPatientFile = findViewById(R.id.patient_upload_doc);

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        storageReference = FirebaseStorage.getInstance().getReference();
        databaseReference = FirebaseDatabase.getInstance().getReference().child("UploadedDocuments");

        pUploadBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadFile();
            }
        });

        etPatientFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectFile();
            }
        });
    }

    private void selectFile() {
        Intent intent = new Intent();
        intent.setType("*/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select File"), 12);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 12 && resultCode == RESULT_OK && data != null && data.getData() != null) {
            fileUri = data.getData();
            etPatientFile.setText(fileUri.getLastPathSegment());
        }
    }

    private void uploadFile() {
        if (fileUri != null) {
            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setTitle("Uploading File...");
            progressDialog.show();

            // Get the file extension from the URI
            String fileExtension = getFileExtension(fileUri);

            // Generate a unique file name with the current timestamp and file extension
            String fileName = System.currentTimeMillis() + "." + fileExtension;

            // Reference to the file in Firebase Storage
            StorageReference fileReference = storageReference.child("uploads/" + fileName);

            fileReference.putFile(fileUri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            progressDialog.dismiss();
                            Toast.makeText(PatientUploadDoc.this, "Upload successful", Toast.LENGTH_SHORT).show();

                            // Get the download URL of the uploaded file
                            Task<Uri> downloadUriTask = taskSnapshot.getStorage().getDownloadUrl();
                            downloadUriTask.addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    String downloadUrl = uri.toString();
                                    String fileName = etPatientFile.getText().toString().trim();
                                    uploadToDatabase(fileName, downloadUrl);
                                }
                            });
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            progressDialog.dismiss();
                            Toast.makeText(PatientUploadDoc.this, "Upload failed: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                            double progress = (100.0 * snapshot.getBytesTransferred() / snapshot.getTotalByteCount());
                            progressDialog.setMessage("Uploaded " + (int) progress + "%");
                        }
                    });
        } else {
            Toast.makeText(this, "No file selected", Toast.LENGTH_SHORT).show();
        }
    }

    private void uploadToDatabase(String fileName, String downloadUrl) {
        String uploadId = databaseReference.push().getKey();
        HelperClass helperClass = new HelperClass();
        helperClass.setFileName(fileName);
        helperClass.setFileUrl(downloadUrl);

        if (uploadId != null) {
            databaseReference.child(uploadId).setValue(helperClass)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            // Successfully uploaded to database
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            // Failed to upload to database
                            Toast.makeText(PatientUploadDoc.this, "Failed to upload to database: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
        }
    }

    private String getFileExtension(Uri uri) {
        String extension = null;
        String[] projection = {MediaStore.MediaColumns.MIME_TYPE};
        Cursor cursor = getContentResolver().query(uri, projection, null, null, null);
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                String mimeType = cursor.getString(0);
                extension = MimeTypeMap.getSingleton().getExtensionFromMimeType(mimeType);
            }
            cursor.close();
        }
        if (extension == null) {
            extension = uri.getPath();
            int lastDotPosition = extension.lastIndexOf('.');
            if (lastDotPosition != -1) {
                extension = extension.substring(lastDotPosition + 1);
            } else {
                extension = "";
            }
        }
        return extension.toLowerCase();
    }
}
