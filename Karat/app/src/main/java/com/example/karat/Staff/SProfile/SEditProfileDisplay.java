package com.example.karat.Staff.SProfile;

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

import com.example.karat.Customer.CProfile.CProfileDisplay;
import com.example.karat.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import static com.example.karat.R.*;

public class SEditProfileDisplay extends AppCompatActivity {
    private EditText firstNameET, lastNameET, openFET, openTET, mobileNumET, addressET;
    private TextView usernameTV;
    private Button confirmBtn;
    private FirebaseAuth mAuth;
    FirebaseUser user;
    FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    String email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(layout.activity_s_edit_profile_display);

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
                String openHoursOfUser = dataSnapshot.child("UserDatabase").child(email).child("openingHour").getValue(String.class);
                String closeHoursOfUser = dataSnapshot.child("UserDatabase").child(email).child("closingHour").getValue(String.class);
                String mobileNoOfUser = dataSnapshot.child("UserDatabase").child(email).child("mobileNo").getValue(String.class);
                String addressOfUser = dataSnapshot.child("UserDatabase").child(email).child("address").getValue(String.class);
                usernameTV.setText(emailOfUser);
                firstNameET.setText(firstNameOfUser);
                lastNameET.setText(lastNameOfUser);
                openFET.setText(openHoursOfUser);
                openTET.setText(closeHoursOfUser);
                mobileNumET.setText(mobileNoOfUser);
                addressET.setText(addressOfUser);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });

        confirmBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editStoreDetails();
            }
        });

        finish();
    }

    private void editStoreDetails() {
        final String firstName, lastName, openTime, closeTime, mobileNum, address;
        int firstDigit;
        firstName = firstNameET.getText().toString();
        lastName = lastNameET.getText().toString();
        openTime = openFET.getText().toString();
        closeTime = openTET.getText().toString();
        mobileNum = mobileNumET.getText().toString();
        address = addressET.getText().toString();
        firstDigit = Integer.parseInt(String.valueOf(mobileNum.toCharArray()[0]));

        if (TextUtils.isEmpty(firstName)) {
            Toast.makeText(getApplicationContext(), "Please enter first name!", Toast.LENGTH_LONG).show();
            return;
        }
        if (Integer.parseInt(openTime) > 2359) {
            Toast.makeText(getApplicationContext(), "Please enter the opening hours in 24Hour format!", Toast.LENGTH_LONG).show();
            return;
        } else if (openTime.length() != 4) {
            Toast.makeText(getApplicationContext(), "Please enter the opening hours in 24Hour format!", Toast.LENGTH_LONG).show();
            return;
        }
        if (Integer.parseInt(closeTime) > 2359) {
            Toast.makeText(getApplicationContext(), "Please enter the closing hours in 24Hour format!", Toast.LENGTH_LONG).show();
            return;
        } else if (closeTime.length() != 4) {
            Toast.makeText(getApplicationContext(), "Please enter the closing hours in 24Hour format!", Toast.LENGTH_LONG).show();
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

        editStoreToDatabase(firstName, lastName, openTime, closeTime, mobileNum, address);
        Intent SProfileIntent = new Intent(getApplicationContext(), SProfileDisplay.class);
        SProfileIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(SProfileIntent);
        overridePendingTransition(0,0);
    }

    private void editStoreToDatabase( String firstName, String lastName, String openTime, String closeTime, String mobileNum, String address){
        String email = user.getEmail().replace("@", "");
        email = email.replace(".", "");
        databaseReference.child("UserDatabase").child(email).child("firstName").setValue(firstName);
        databaseReference.child("UserDatabase").child(email).child("lastName").setValue(lastName);
        databaseReference.child("UserDatabase").child(email).child("openingHour").setValue(openTime);
        databaseReference.child("UserDatabase").child(email).child("closingHour").setValue(closeTime);
        databaseReference.child("UserDatabase").child(email).child("mobileNo").setValue(mobileNum);
        databaseReference.child("UserDatabase").child(email).child("address").setValue(address);
    }

    private void initializeUI() {
        usernameTV = findViewById(R.id.username);
        firstNameET = findViewById(R.id.firstName);
        lastNameET = findViewById(R.id.lastName);
        openFET = findViewById(id.openHours);
        openTET = findViewById(id.closeHours);
        mobileNumET = findViewById(R.id.mobileNum);
        addressET = findViewById(id.address);
        confirmBtn = findViewById(R.id.confirm);
    }
}
