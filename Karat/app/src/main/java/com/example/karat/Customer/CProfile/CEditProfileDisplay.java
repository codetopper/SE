package com.example.karat.Customer.CProfile;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.karat.Login.LoginDisplay;
import com.example.karat.Login.RegisterDisplay;
import com.example.karat.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class CEditProfileDisplay extends AppCompatActivity {
    private EditText firstNameET, lastNameET, mobileNumET;
    private TextView username;
    private Button confirmBtn;
    private FirebaseAuth mAuth;
    FirebaseUser user;
    FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    String email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_c_edit_profile_display);

        initializeUI();

        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
        assert user != null;
        email = user.getEmail();
        assert email != null;
        email = email.replace("@", "");
        email = email.replace(".", "");
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();

        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String emailOfUser = dataSnapshot.child("UserDatabase").child(email).child("email").getValue(String.class);
                String firstNameOfUser = dataSnapshot.child("UserDatabase").child(email).child("firstName").getValue(String.class);
                String lastNameOfUser = dataSnapshot.child("UserDatabase").child(email).child("lastName").getValue(String.class);
                String mobileNoOfUser = dataSnapshot.child("UserDatabase").child(email).child("mobileNo").getValue(String.class);
                username.setText(emailOfUser);
                firstNameET.setText(firstNameOfUser);
                lastNameET.setText(lastNameOfUser);
                mobileNumET.setText(mobileNoOfUser);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });

        confirmBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editUserDetails();
            }
        });
    }

    private void editUserDetails() {
        final String firstName, lastName, mobileNum;
        int firstDigit;
        firstName = firstNameET.getText().toString();
        lastName = lastNameET.getText().toString();
        mobileNum = mobileNumET.getText().toString();
        firstDigit = Integer.parseInt(String.valueOf(mobileNum.toCharArray()[0]));

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
        } else if (mobileNum.length()!=8) {
            Toast.makeText(getApplicationContext(), "Please enter an 8 digit mobile number!", Toast.LENGTH_LONG).show();
            return;
        } else if (firstDigit<8) {
            Toast.makeText(getApplicationContext(), "Please enter a valid mobile number!", Toast.LENGTH_LONG).show();
            return;
        }

        editUserToDatabase(firstName, lastName, mobileNum);
        Intent CProfileIntent = new Intent(getApplicationContext(), CProfileDisplay.class);
        CProfileIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(CProfileIntent);
    }

    private void editUserToDatabase( String firstName, String lastName, String mobileNum){
        String email = user.getEmail().replace("@", "");
        email = email.replace(".", "");
        databaseReference.child("UserDatabase").child(email).child("firstName").setValue(firstName);
        databaseReference.child("UserDatabase").child(email).child("lastName").setValue(lastName);
        databaseReference.child("UserDatabase").child(email).child("mobileNo").setValue(mobileNum);
    }

    private void initializeUI() {
        firstNameET = findViewById(R.id.firstName);
        lastNameET = findViewById(R.id.lastName);
        mobileNumET = findViewById(R.id.mobileNum);
        username = findViewById(R.id.username);
        confirmBtn = findViewById(R.id.confirm);
    }
}
