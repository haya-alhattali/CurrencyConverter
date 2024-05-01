package com.haya.currencyconverter1;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class RegisterActivity extends AppCompatActivity {

    // Declare variables
    private EditText usernameEditText, emailEditText, passwordEditText, repasswordEditText;
    private Button signupButton, signinButton;
    private FirebaseAuth firebaseAuth;
    private ProgressDialog progressDialog;
    private String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        // Initialize variables
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Establishing The Account");

        usernameEditText = findViewById(R.id.username);
        emailEditText = findViewById(R.id.email);
        passwordEditText = findViewById(R.id.password);
        repasswordEditText = findViewById(R.id.repassword);
        signupButton = findViewById(R.id.signupbutton);
        signinButton = findViewById(R.id.signinbutton);

        firebaseAuth = FirebaseAuth.getInstance();

        // Set onClickListener for signupButton
        signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get user input
                String username = usernameEditText.getText().toString().trim();
                String email = emailEditText.getText().toString().trim();
                String password = passwordEditText.getText().toString().trim();
                String repassword = repasswordEditText.getText().toString().trim();

                // Validate user input
                if (TextUtils.isEmpty(username) || TextUtils.isEmpty(email) ||
                        TextUtils.isEmpty(password) || TextUtils.isEmpty(repassword)) {
                    progressDialog.dismiss();
                    Toast.makeText(RegisterActivity.this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
                } else if (!email.matches(emailPattern)) {
                    progressDialog.dismiss();
                    emailEditText.setError("Type A Valid Email Here");
                } else if (password.length() < 6) {
                    progressDialog.dismiss();
                    passwordEditText.setError("Password Must Be 6 Characters Or More");
                } else if (!password.equals(repassword)) {
                    progressDialog.dismiss();
                    passwordEditText.setError("The Passwords Don't Match");
                } else {
                    // Create user account
                    firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener() {
                        @Override
                        public void onComplete(@NonNull Task task) {
                            if (task.isSuccessful()) {
                                progressDialog.show();
                                Toast.makeText(RegisterActivity.this, "Registration successful", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(RegisterActivity.this, HomepageActivity.class);
                                startActivity(intent);
                                finish();
                            } else {
                                Toast.makeText(RegisterActivity.this, "Registration failed", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });

        // Set onClickListener for signinButton
        signinButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate to login activity
                Intent intent = new Intent(RegisterActivity.this, login.class);
                startActivity(intent);
            }
        });
    }
}