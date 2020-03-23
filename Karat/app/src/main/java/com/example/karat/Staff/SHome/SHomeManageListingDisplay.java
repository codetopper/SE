package com.example.karat.Staff.SHome;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.karat.R;
import com.example.karat.Staff.SOrder.SOrderDisplay;
import com.example.karat.Staff.SProfile.SProfileDisplay;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SHomeManageListingDisplay extends AppCompatActivity {

    private TextView nameTV, addressTV, timeTV;
    private EditText listingNameET, itemPriceET, itemQtyET, itemDiscET, descriptionET;
    private Button uploadBtn, deleteBtn, addBtn, cancelBtn;
    private DatabaseReference mDatabase;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_s_home_manage_listing_display);

        mDatabase = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();

        initialiseUI();
        setHeader();

        //Retrieve Application Context and use to fill
        autoFill("new");

        //Cancel
        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent SHomeIntent = new Intent(getApplicationContext(), SHomeDisplay.class);
                SHomeIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(SHomeIntent);
                overridePendingTransition(0,0);
            }
        });

        uploadBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Dunno how upload image
            }
        });

        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addListingToDatabase();
            }
        });

        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteListingFromDatabase();
            }
        });
    }

    private void setHeader(){
        final String email = mAuth.getCurrentUser().getEmail().replace("@", "")
                .replace(".", "");

        mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String name = dataSnapshot.child("UserDatabase").child(email).child("firstName").getValue(String.class) +
                        dataSnapshot.child("UserDatabase").child(email).child("lastName").getValue(String.class);
                String address = dataSnapshot.child("UserDatabase").child(email).child("address").getValue(String.class);
                String timeStart = dataSnapshot.child("UserDatabase").child(email).child("openingHour").getValue(String.class);
                String timeEnd = dataSnapshot.child("UserDatabase").child(email).child("closingHour").getValue(String.class);
                nameTV.setText(name);
                addressTV.setText(address);
                timeTV.setText(timeStart+"-"+timeEnd);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getApplicationContext(), databaseError.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    private void autoFill(final String listingName){
        final String supermarket = nameTV.getText().toString();

        if (!listingName.equals("new")) {
            mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    String fillListingName, fillItemPrice, fillItemQty, fillItemDiscET, fillDescription;
                    for (DataSnapshot postSnapshot : dataSnapshot.child("Inventory").child(supermarket).getChildren()) {
                        // TODO: handle the post

                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    Toast.makeText(getApplicationContext(), databaseError.getMessage(), Toast.LENGTH_LONG).show();
                }
            });
        }
    }

    private void deleteListingFromDatabase(){
        final String listingName, supermarket;
        supermarket = nameTV.getText().toString();
        listingName = listingNameET.getText().toString();

        if (TextUtils.isEmpty(listingNameET.getText().toString())) {
            Toast.makeText(getApplicationContext(), "Please enter the product name...", Toast.LENGTH_LONG).show();
            return;
        }

        mDatabase.child("Inventory").child(supermarket).child(listingName).setValue(null);

        Toast.makeText(getApplicationContext(), "Listing deleted!", Toast.LENGTH_LONG).show();

        Intent SHomeIntent = new Intent(getApplicationContext(), SHomeDisplay.class);
        SHomeIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(SHomeIntent);
        overridePendingTransition(0,0);
    }

    private void addListingToDatabase(){
        final String listingName, itemPrice, itemQty, itemDiscount, description, supermarket;
        supermarket = nameTV.getText().toString();
        listingName = listingNameET.getText().toString();
        itemPrice = itemPriceET.getText().toString();
        itemQty = itemQtyET.getText().toString();
        itemDiscount = itemDiscET.getText().toString();
        description = descriptionET.getText().toString();

        if (TextUtils.isEmpty(listingNameET.getText().toString())) {
            Toast.makeText(getApplicationContext(), "Please enter the product name...", Toast.LENGTH_LONG).show();
            return;
        }
        if (TextUtils.isEmpty(itemPriceET.getText().toString())) {
            Toast.makeText(getApplicationContext(), "Please enter the price...", Toast.LENGTH_LONG).show();
            return;
        }
        if (TextUtils.isEmpty(itemQtyET.getText().toString())) {
            Toast.makeText(getApplicationContext(), "Please enter the quantity...", Toast.LENGTH_LONG).show();
            return;
        }
        if (TextUtils.isEmpty(itemDiscET.getText().toString())) {
            Toast.makeText(getApplicationContext(), "Please enter the discount(%)...", Toast.LENGTH_LONG).show();
            return;
        }
        if (TextUtils.isEmpty(descriptionET.getText().toString())) {
            Toast.makeText(getApplicationContext(), "Please enter the description...", Toast.LENGTH_LONG).show();
            return;
        }

        mDatabase.child("Inventory").child(supermarket).child(listingName).child("itemPrice").setValue(itemPrice);
        mDatabase.child("Inventory").child(supermarket).child(listingName).child("itemQuantity").setValue(itemQty);
        mDatabase.child("Inventory").child(supermarket).child(listingName).child("itemDiscount").setValue(itemDiscount);
        mDatabase.child("Inventory").child(supermarket).child(listingName).child("description").setValue(description);

        Toast.makeText(getApplicationContext(), "Listing updated!", Toast.LENGTH_LONG).show();

        Intent SHomeIntent = new Intent(getApplicationContext(), SHomeDisplay.class);
        SHomeIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(SHomeIntent);
        overridePendingTransition(0,0);
    }

    private void initialiseUI(){
        nameTV = findViewById(R.id.nameTV);
        addressTV = findViewById(R.id.addressTV);
        timeTV = findViewById(R.id.timeTV);

        listingNameET = findViewById(R.id.listingName);
        itemPriceET = findViewById(R.id.itemPrice);
        itemQtyET = findViewById(R.id.itemQty);
        itemDiscET = findViewById(R.id.itemDisc);
        descriptionET = findViewById(R.id.description);

        uploadBtn = findViewById(R.id.upload);
        deleteBtn = findViewById(R.id.delete);
        addBtn = findViewById(R.id.add);
        cancelBtn = findViewById(R.id.cancel);
    }
}