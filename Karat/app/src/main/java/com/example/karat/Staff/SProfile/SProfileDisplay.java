package com.example.karat.Staff.SProfile;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.karat.Customer.CHome.CHomeDisplay;
import com.example.karat.Customer.COrder.COrderDisplay;
import com.example.karat.Customer.CProfile.CEditProfileDisplay;
import com.example.karat.Customer.CProfile.CProfileDisplay;
import com.example.karat.Login.LoginDisplay;
import com.example.karat.Login.ResetPwDisplay;
import com.example.karat.R;
import com.example.karat.Staff.SHome.SHomeDisplay;
import com.example.karat.Staff.SOrder.SOrderDisplay;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SProfileDisplay extends AppCompatActivity {

    private Button logOffBtn, changePwBtn, editProfileBtn;
    private FirebaseAuth mAuth;
    FirebaseUser user;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    private TextView SNameTV, addressTV, openTV, closeTV, mobileTV;
    String email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_s_profile_display);

        initialiseUI();

        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
        assert user != null;
        email = user.getEmail();
        assert email != null;
        email = email.replace("@", "");
        email = email.replace(".", "");
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();

        SNameTV = findViewById(R.id.SNameTV);
        addressTV = findViewById(R.id.addressTV);
        openTV = findViewById(R.id.openTV);
        closeTV = findViewById(R.id.closeTV);

        databaseReference.addListenerForSingleValueEvent(new ValueEventListener(){
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String SName = dataSnapshot.child("UserDatabase").child(email).child("firstName").getValue(String.class) + " " +
                        dataSnapshot.child("UserDatabase").child(email).child("lastName").getValue(String.class);
                String address = dataSnapshot.child("UserDatabase").child(email).child("address").getValue(String.class);
                String openh = dataSnapshot.child("UserDatabase").child(email).child("openingHour").getValue(String.class);
                String closeh = dataSnapshot.child("UserDatabase").child(email).child("closingHour").getValue(String.class);
                String mobileno = dataSnapshot.child("UserDatabase").child(email).child("mobileNo").getValue(String.class);
                SNameTV.setText(SName);
                addressTV.setText(address);
                openTV.setText(openh);
                closeTV.setText(closeh);
                mobileTV.setText(mobileno);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
            //Log off Button
        logOffBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAuth.signOut();
                Intent logOffIntent = new Intent(getApplicationContext(), LoginDisplay.class);
                startActivity(logOffIntent);
                overridePendingTransition(0,0);
            }
        });

        //ChangePw Button
        changePwBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent changePwIntent = new Intent(getApplicationContext(), ResetPwDisplay.class);
                startActivity(changePwIntent);
                overridePendingTransition(0,0);
            }
        });

        //EditProfile Button
        editProfileBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent editProfileIntent = new Intent(getApplicationContext(), SEditProfileDisplay.class);
                startActivity(editProfileIntent);
                overridePendingTransition(0,0);
            }
        });

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navi);

        bottomNavigationView.setSelectedItemId(R.id.Profile);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.Home:
                        startActivity(new Intent(getApplicationContext(), SHomeDisplay.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.Orders:
                        startActivity(new Intent(getApplicationContext(), SOrderDisplay.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.Profile:
                        startActivity(new Intent(getApplicationContext(), SProfileDisplay.class));
                        overridePendingTransition(0,0);
                        return true;
                }
                return false;
            }
        });
    }
    private void initialiseUI(){
        logOffBtn = findViewById(R.id.logOff);
        changePwBtn = findViewById(R.id.changePw);
        editProfileBtn = findViewById(R.id.editProfile);
        mobileTV = findViewById(R.id.mobileTV);
    }
}
