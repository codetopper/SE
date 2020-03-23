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

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class RegisterDisplay extends AppCompatActivity {

    private EditText userTV, passwordTV;
    private Button regBtn;
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
        final String user, password, firstName, lastName, mobileNum;
        int firstDigit;
        user = userTV.getText().toString();
        password = passwordTV.getText().toString();
        firstName = firstNameTV.getText().toString();
        lastName = lastNameTV.getText().toString();
        mobileNum = mobileNumTV.getText().toString();

        if (TextUtils.isEmpty(user)) {
            Toast.makeText(getApplicationContext(), "Please enter email...", Toast.LENGTH_LONG).show();
            return;
        }
        if (TextUtils.isEmpty(password)) {
            Toast.makeText(getApplicationContext(), "Please enter password!", Toast.LENGTH_LONG).show();
            return;
        }
        if (TextUtils.isEmpty(firstName)) {
            Toast.makeText(getApplicationContext(), "Please enter first name!", Toast.LENGTH_LONG).show();
            return;
        }
        if (TextUtils.isEmpty(lastName)) {
            Toast.makeText(getApplicationContext(), "Please enter last name!", Toast.LENGTH_LONG).show();
            return;
        }
        if (TextUtils.isEmpty(mobileNum)) {
            Toast.makeText(getApplicationContext(), "Please enter mobile number!", Toast.LENGTH_LONG).show();
            return;
        } else {
            firstDigit = Integer.parseInt(String.valueOf(mobileNum.toCharArray()[0]));
            if (mobileNum.length() != 8) {
                Toast.makeText(getApplicationContext(), "Please enter an 8 digit mobile number!", Toast.LENGTH_LONG).show();
                return;
            } else if (firstDigit < 8) {
                Toast.makeText(getApplicationContext(), "Please enter a valid mobile number!", Toast.LENGTH_LONG).show();
                return;
            }
        }

        mAuth.createUserWithEmailAndPassword(user, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            writeUserToDatabase(task.getResult().getUser(), password, firstName, lastName, mobileNum);
                            finish();
                        } else {
                            Toast.makeText(RegisterDisplay.this, "Sign Up Failed.\n Error message: "+ task.getException().getMessage(),
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void writeUserToDatabase(FirebaseUser user, String password, String firstName, String lastName, String mobileNum){
        String email = user.getEmail().replace("@", "");
        email = email.replace(".", "");
        Date c = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
        String formattedDate = df.format(c).toString();
        mDatabase.child("UserDatabase").child(email).child("isStaff").setValue(0);
        mDatabase.child("UserDatabase").child(email).child("email").setValue(user.getEmail());
        mDatabase.child("UserDatabase").child(email).child("firstName").setValue(firstName);
        mDatabase.child("UserDatabase").child(email).child("lastName").setValue(lastName);
        mDatabase.child("UserDatabase").child(email).child("mobileNo").setValue(mobileNum);
        mDatabase.child("UserDatabase").child(email).child("dateRegistered").setValue(formattedDate);
    }

    private void initializeUI() {
        userTV = findViewById(R.id.username);
        passwordTV = findViewById(R.id.password_login);
        regBtn = findViewById(R.id.register);
        firstNameTV = findViewById(R.id.firstName);
        lastNameTV = findViewById(R.id.lastName);
        mobileNumTV = findViewById(R.id.mobileNum);
    }
}