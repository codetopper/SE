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
import com.example.karat.inventory.Listing;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SHomeManageListingDisplay extends AppCompatActivity {

    private TextView nameTV, addressTV, timeTV;
    private EditText listingNameET, itemPriceET, itemQtyET, itemDiscET, descriptionET, itemCategoryET;
    private Button uploadBtn, deleteBtn, addBtn, cancelBtn;

    private DatabaseReference mDatabase;
    private FirebaseAuth mAuth;
    private Listing currentListing = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_s_home_manage_listing_display);

        mDatabase = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();

        initialiseUI();
        initPage();

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

    private void initPage(){
        final String email = mAuth.getCurrentUser().getEmail().replace("@", "")
                .replace(".", "");

        mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                //header
                String name = dataSnapshot.child("UserDatabase").child(email).child("firstName").getValue(String.class) + " " +
                        dataSnapshot.child("UserDatabase").child(email).child("lastName").getValue(String.class);
                String address = dataSnapshot.child("UserDatabase").child(email).child("address").getValue(String.class);
                String timeStart = dataSnapshot.child("UserDatabase").child(email).child("openingHour").getValue(String.class);
                String timeEnd = dataSnapshot.child("UserDatabase").child(email).child("closingHour").getValue(String.class);
                nameTV.setText(name);
                addressTV.setText(address);
                timeTV.setText(timeStart + "-" + timeEnd);

                //Retrieve Application Context and use to fill
                String listingID;
                if (getIntent().hasExtra("com.example.karat.listingID")) {
                    listingID = getIntent().getExtras().getString("com.example.karat.listingID");
                    currentListing = dataSnapshot.child("Inventory").child(listingID + "").getValue(Listing.class);
                    listingNameET.setText(currentListing.getListingName());
                    itemPriceET.setText(currentListing.getListingPrice() + "");
                    itemQtyET.setText(currentListing.getListingQuantity() + "");
                    itemDiscET.setText(currentListing.getListingDiscount() + "");
                    itemCategoryET.setText(currentListing.getListingCategory());
                    descriptionET.setText(currentListing.getDescription());
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getApplicationContext(), databaseError.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    private void deleteListingFromDatabase(){
        String listingID;
        if (getIntent().hasExtra("com.example.karat.listingID")){
            listingID = getIntent().getExtras().getString("com.example.karat.listingID");
        } else {
            Toast.makeText(getApplicationContext(), "Please select a valid listing to delete", Toast.LENGTH_LONG).show();
            return;
        }

        mDatabase.child("Inventory").child(listingID).setValue(null);

        Toast.makeText(getApplicationContext(), "Listing deleted!", Toast.LENGTH_LONG).show();

        Intent SHomeIntent = new Intent(getApplicationContext(), SHomeDisplay.class);
        SHomeIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(SHomeIntent);
        overridePendingTransition(0,0);
    }

    private void addListingToDatabase(){
        final String listingName, itemPrice, itemQty, itemDiscount, description, supermarket, itemCategory;
        supermarket = nameTV.getText().toString();
        listingName = listingNameET.getText().toString();
        itemPrice = itemPriceET.getText().toString();
        itemQty = itemQtyET.getText().toString();
        itemDiscount = itemDiscET.getText().toString();
        description = descriptionET.getText().toString();
        itemCategory = itemCategoryET.getText().toString();

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

        //Create new listing object with the information
        Listing newProduct = new Listing(Double.parseDouble(itemPrice), Double.parseDouble(itemDiscount), supermarket,
                listingName, itemCategory, description, Integer.parseInt(itemQty));

        int listingID;
        if (currentListing==null) {
            listingID = newProduct.getListingId();
        } else {
            listingID = currentListing.getListingId();
        }

        mDatabase.child("Inventory").child(listingID+"").setValue(newProduct);

        //Patching issues with listingID increments
        if (getIntent().hasExtra("com.example.karat.listingID")) {
            listingID = Integer.parseInt(getIntent().getExtras().getString("com.example.karat.listingID"));
            mDatabase.child("Inventory").child(listingID+"").child("listingId").setValue(listingID);
        }

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
        itemCategoryET = findViewById(R.id.itemCategory);

        uploadBtn = findViewById(R.id.upload);
        deleteBtn = findViewById(R.id.delete);
        addBtn = findViewById(R.id.add);
        cancelBtn = findViewById(R.id.cancel);
    }
}