package com.example.karat.Customer.CSuperMap;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.example.karat.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class StoreDetailsDisplay extends AppCompatActivity {
    private FirebaseAuth mAuth;
    FirebaseUser user;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    private TextView SNameTV, openinghrsTV, addTV, telNoTV;
    String email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store_details_display);

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

        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String SName = dataSnapshot.child("UserDatabase").child(email).child("name").getValue(String.class);
                String address = dataSnapshot.child("UserDatabase").child(email).child("address").getValue(String.class);
                String openh = dataSnapshot.child("UserDatabase").child(email).child("openingHour").getValue(String.class) + " - " +
                        dataSnapshot.child("UserDatabase").child(email).child("closingHour").getValue(String.class);
                String mobileno = dataSnapshot.child("UserDatabase").child(email).child("mobileNo").getValue(String.class);
                SNameTV.setText(SName);
                telNoTV.setText(mobileno);
                addTV.setText(address);
                openinghrsTV.setText(openh);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }

        });

    }
    private void initialiseUI() {
        SNameTV = findViewById(R.id.SNameTV);
        telNoTV = findViewById(R.id.mobileTV);
        addTV = findViewById(R.id.addressTV);
        openinghrsTV = findViewById(R.id.openhrsTV);
    }
}
