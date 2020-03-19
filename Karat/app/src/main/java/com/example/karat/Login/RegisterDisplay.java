package com.example.karat.Login;

import androidx.annotation.IntegerRes;
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
import android.widget.Toast;

import com.example.karat.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class RegisterDisplay extends AppCompatActivity {

    private static final String TAG = RegisterDisplay.class.getSimpleName();
    private EditText userTV, passwordTV;
    private Button regBtn;
    private ProgressBar progressBar;
    private DatabaseReference mDatabase;
    private FirebaseAuth mAuth;
    private EditText firstNameTV, lastNameTV, mobileNumTV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_display);
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();

        initializeUI();

        regBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerNewUser();
            }
        });
    }

    private void registerNewUser() {
        progressBar.setVisibility(View.VISIBLE);
        final String user, password, firstName, lastName;
        final Integer mobileNum;
        user = "Customer-".concat(userTV.getText().toString());
        password = passwordTV.getText().toString();
        firstName = firstNameTV.getText().toString();
        lastName = lastNameTV.getText().toString();
        mobileNum = Integer.parseInt(mobileNumTV.getText().toString());
        if (TextUtils.isEmpty(user)) {
            Toast.makeText(getApplicationContext(), "Please enter email...", Toast.LENGTH_LONG).show();
            return;
        }
        if (TextUtils.isEmpty(password)) {
            Toast.makeText(getApplicationContext(), "Please enter password!", Toast.LENGTH_LONG).show();
            return;
        }

        mAuth.createUserWithEmailAndPassword(user, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d(TAG, "createUser:onComplete:" + task.isSuccessful());

                        if (task.isSuccessful()) {
                            writeUserToDatabase(task.getResult().getUser(), firstName, lastName, mobileNum);
                            Intent LoginIntent = new Intent(getApplicationContext(), LoginDisplay.class);
                            startActivity(LoginIntent);
                        } else {
                            Toast.makeText(RegisterDisplay.this, "Sign Up Failed",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void writeUserToDatabase(FirebaseUser user, String firstName, String lastName, Integer mobileNum){
        String email = user.getEmail().substring(9);
        mDatabase.child("UserDatabase").child(user.getUid()).child("Email").setValue(email);
        mDatabase.child("UserDatabase").child(user.getUid()).child("FirstName").setValue(firstName);
        mDatabase.child("UserDatabase").child(user.getUid()).child("LastName").setValue(lastName);
        mDatabase.child("UserDatabase").child(user.getUid()).child("MobileNo.").setValue((int)mobileNum);
    }

    private void initializeUI() {
        userTV = findViewById(R.id.username);
        passwordTV = findViewById(R.id.password_login);
        regBtn = findViewById(R.id.register);
        progressBar = findViewById(R.id.progressBar);
        firstNameTV = findViewById(R.id.firstName);
        lastNameTV = findViewById(R.id.lastName);
        mobileNumTV = findViewById(R.id.mobileNum);
    }
}