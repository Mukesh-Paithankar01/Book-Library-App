package com.example.mukesh;

import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class UserDashBoardActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private DatabaseHelper dbHelper;
    private ArrayList<String> bookList;
    private ArrayList<Uri> pdfUris;
    private BookAdapter bookAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_dash_board);

        dbHelper = new DatabaseHelper(this);
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        bookList = new ArrayList<>();
        pdfUris = new ArrayList<>();

        loadBooks();

        bookAdapter = new BookAdapter(this, bookList, pdfUris);
        recyclerView.setAdapter(bookAdapter);
    }

    private void loadBooks() {
        Cursor cursor = dbHelper.getAllBooks();
        if (cursor != null && cursor.moveToFirst()) {
            do {
                int titleIndex = cursor.getColumnIndex("title");
                int authorIndex = cursor.getColumnIndex("author");
                int pdfUriIndex = cursor.getColumnIndex("pdf_uri");

                if (titleIndex != -1 && authorIndex != -1 && pdfUriIndex != -1) {
                    String title = cursor.getString(titleIndex);
                    String author = cursor.getString(authorIndex);
                    Uri pdfUri = Uri.parse(cursor.getString(pdfUriIndex));

                    bookList.add("Title: " + title + "\nAuthor: " + author);
                    pdfUris.add(pdfUri);
                } else {
                    Toast.makeText(this, "Error: One or more columns not found in database.", Toast.LENGTH_SHORT).show();
                    break;
                }
            } while (cursor.moveToNext());
        }

        if (cursor != null) {
            cursor.close();
        }
    }
}
