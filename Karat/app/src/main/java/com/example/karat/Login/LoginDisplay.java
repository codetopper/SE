package com.example.karat.Login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Switch;
import android.widget.Toast;

import com.example.karat.Customer.CHome.CHomeDisplay;
import com.example.karat.R;
import com.example.karat.Staff.SHome.SHomeDisplay;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginDisplay extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;
    private EditText usernameEditText, passwordEditText;
    private Button loginBtn;
    private Button rgnBtn;
    private Button resetBtn;
    private Switch domain;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_display);
        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();

        ///////////////////////////////////////////////////////////////////
        //Login process
        initializeUI();

        rgnBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent regIntent = new Intent(getApplicationContext(), RegisterDisplay.class);
                startActivity(regIntent);
            }
        });

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginUserAccount();
            }
        });

        resetBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent resetPwIntent = new Intent(getApplicationContext(), ResetPwDisplay.class);
                startActivity(resetPwIntent);
            }
        });
    }

    private void loginUserAccount() {

        final String user, password;
        final boolean isStaff = domain.isChecked();

        user = usernameEditText.getText().toString();
        password = passwordEditText.getText().toString();

        if (TextUtils.isEmpty(user)) {
            Toast.makeText(getApplicationContext(), "Please enter email...", Toast.LENGTH_LONG).show();
            return;
        }
        if (TextUtils.isEmpty(password)) {
            Toast.makeText(getApplicationContext(), "Please enter password!", Toast.LENGTH_LONG).show();
            return;
        }

        mAuth.signInWithEmailAndPassword(user, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success
                            mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                    String email = user.replace("@", "");
                                    email = email.replace(".", "");
                                    int check = dataSnapshot.child("UserDatabase").child(email).child("isStaff").getValue(Integer.class);
                                    boolean userType;
                                    if (check == 1){
                                        userType = true;
                                    } else {
                                        userType = false;
                                    }

                                    if (userType==isStaff){
                                        if(isStaff){
                                            Intent SHomeIntent = new Intent(getApplicationContext(), SHomeDisplay.class);
                                            startActivity(SHomeIntent);
                                        } else {
                                            Intent CHomeIntent = new Intent(getApplicationContext(), CHomeDisplay.class);
                                            startActivity(CHomeIntent);
                                        }
                                        passwordEditText.setText((""));
                                    } else {
                                        Toast.makeText(LoginDisplay.this, "Authentication failed.",
                                                Toast.LENGTH_SHORT).show();
                                    }
                                }
                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {
                                    Toast.makeText(LoginDisplay.this, "Database Error",
                                            Toast.LENGTH_SHORT).show();
                                }
                            });
                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(LoginDisplay.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });

    }

    private void initializeUI() {
        usernameEditText = findViewById(R.id.username);
        passwordEditText = findViewById(R.id.password_login);
        resetBtn = findViewById(R.id.ResetPw);
        rgnBtn = findViewById(R.id.Register);
        loginBtn = findViewById(R.id.login);
        domain = findViewById(R.id.Domain);
    }
}