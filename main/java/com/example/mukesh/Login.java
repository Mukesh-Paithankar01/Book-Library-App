package com.example.mukesh;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class Login extends AppCompatActivity {

    private EditText usernameEditText;
    private EditText passwordEditText;
    private Button loginButton;
    private Button signupButton; // Added signup button
    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        usernameEditText = findViewById(R.id.usernameEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        loginButton = findViewById(R.id.loginButton);
        signupButton = findViewById(R.id.signupButton); // Initialize signup button
        dbHelper = new DatabaseHelper(this);

        // Set up login button click listener
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = usernameEditText.getText().toString().trim();
                String password = passwordEditText.getText().toString().trim();

                if (username.equals("admin") && password.equals("admin")) {
                    // Go to Admin Dashboard
                    Intent intent = new Intent(Login.this, AdminDashboardActivity.class);
                    startActivity(intent);
                    Toast.makeText(Login.this, "Welcome, Admin!", Toast.LENGTH_SHORT).show();
                } else if (dbHelper.checkUser(username, password)) {
                    // Go to User Dashboard
                    Intent intent = new Intent(Login.this, UserDashBoardActivity.class);
                    startActivity(intent);
                    Toast.makeText(Login.this, "Welcome, User!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(Login.this, "Invalid Credentials", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // Set up signup button click listener to open SignupActivity
        signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Login.this, Signup.class);
                startActivity(intent);
            }
        });
    }
}
