package com.example.mukesh;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class AdminDashboardActivity extends AppCompatActivity {

    private EditText etTitle, etAuthor;
    private Button btnSelectImage, btnSelectPdf, btnUploadBook;
    private Uri imageUri, pdfUri;
    private DatabaseHelper dbHelper;

    private static final int REQUEST_IMAGE = 1;
    private static final int REQUEST_PDF = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_dashboard);

        dbHelper = new DatabaseHelper(this);
        etTitle = findViewById(R.id.etTitle);
        etAuthor = findViewById(R.id.etAuthor);
        btnSelectImage = findViewById(R.id.btnSelectImage);
        btnSelectPdf = findViewById(R.id.btnSelectPdf);
        btnUploadBook = findViewById(R.id.btnUploadBook);

        btnSelectImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectImage();
            }
        });

        btnSelectPdf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectPdf();
            }
        });

        btnUploadBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadBook();
            }
        });
    }

    private void selectImage() {
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        intent.setType("image/*");
        startActivityForResult(intent, REQUEST_IMAGE);
    }

    private void selectPdf() {
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        intent.setType("application/pdf");
        startActivityForResult(intent, REQUEST_PDF);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && data != null) {
            Uri uri = data.getData();
            if (requestCode == REQUEST_IMAGE) {
                imageUri = uri;
                Toast.makeText(this, "Image selected", Toast.LENGTH_SHORT).show();
            } else if (requestCode == REQUEST_PDF) {
                pdfUri = uri;
                Toast.makeText(this, "PDF selected", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void uploadBook() {
        String title = etTitle.getText().toString().trim();
        String author = etAuthor.getText().toString().trim();

        if (title.isEmpty() || author.isEmpty() || imageUri == null || pdfUri == null) {
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        boolean isInserted = dbHelper.addBook(title, author, imageUri.toString(), pdfUri.toString());
        if (isInserted) {
            Toast.makeText(this, "Book uploaded successfully", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Failed to upload book", Toast.LENGTH_SHORT).show();
        }
    }
}
