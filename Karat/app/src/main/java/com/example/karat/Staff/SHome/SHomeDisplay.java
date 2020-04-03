package com.example.karat.Staff.SHome;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.karat.Customer.CHome.CHomeDisplay;
import com.example.karat.Customer.CHome.StaggeredRecyclerViewAdapter;
import com.example.karat.Customer.COrder.COrderDisplay;
import com.example.karat.Customer.CProfile.CProfileDisplay;
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

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import java.util.ArrayList;
import java.util.List;

public class SHomeDisplay extends AppCompatActivity {

    private TextView nameTV, addressTV, timeTV, licenseTV;
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;
    private Button addListingBtn;
    private static final int Num_Columns = 1;
    private RecyclerView recyclerView;
    private staffListingAdapter mAdapter;
    private ArrayList<Listing> postList = new ArrayList<>();
    private ArrayList<Listing> filterList = new ArrayList<>();

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_s_home_display);

        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();

        initialiseUI();
        setHeader();

        mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                postList.clear();
                filterList.clear();
                //TBC
                int id = 1;
                //
                for(DataSnapshot ds : dataSnapshot.child("Inventory").getChildren()){
                    Listing listing = ds.getValue(Listing.class);
                    assert listing != null;
                    listing.setImage_url(ds.child("imageUrl").getValue(String.class));
                    listing.setListingId(id);
                    listing.setLicense(ds.child("licenseNo").getValue(String.class));
                    postList.add(listing);
                    id ++;
                }

                String license = licenseTV.getText().toString();

                for (Listing i : postList){
                    if (i.getLicense().equals(license)){
                        filterList.add(i);
                    }
                }
                mAdapter.reset(filterList);
                mAdapter.notifyDataSetChanged();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getApplicationContext(), databaseError.getMessage(), Toast.LENGTH_LONG).show();
            }
        });

        addListingBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent SListingIntent = new Intent(getApplicationContext(), SHomeManageListingDisplay.class);
                startActivity(SListingIntent);
                overridePendingTransition(0,0);
            }
        });

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navi);

        bottomNavigationView.setSelectedItemId(R.id.Home);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.Home:
                        Intent SHomeIntent = new Intent(getApplicationContext(), SHomeDisplay.class);
                        SHomeIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(SHomeIntent);
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.Orders:
                        Intent SOrderIntent = new Intent(getApplicationContext(), SOrderDisplay.class);
                        SOrderIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(SOrderIntent);
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.Profile:
                        Intent SProfileIntent = new Intent(getApplicationContext(), SProfileDisplay.class);
                        SProfileIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(SProfileIntent);
                        overridePendingTransition(R.anim.nav_default_enter_anim, R.anim.nav_default_enter_anim);
                        return true;
                }
                return false;
            }
        });
    }


    private void setHeader(){
        final String email = mAuth.getCurrentUser().getEmail().replace("@", "")
                .replace(".", "");

        mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String name = dataSnapshot.child("UserDatabase").child(email).child("name").getValue(String.class);
                String address = dataSnapshot.child("UserDatabase").child(email).child("address").getValue(String.class);
                String timeStart = dataSnapshot.child("UserDatabase").child(email).child("openingHour").getValue(String.class);
                String timeEnd = dataSnapshot.child("UserDatabase").child(email).child("closingHour").getValue(String.class);
                String license = dataSnapshot.child("UserDatabase").child(email).child("licenseNo").getValue(String.class);
                nameTV.setText(name);
                addressTV.setText(address);
                timeTV.setText(timeStart+"-"+timeEnd);
                licenseTV.setText(license);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getApplicationContext(), databaseError.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    private void initialiseUI(){
        addListingBtn = findViewById(R.id.addListing);
        nameTV = findViewById(R.id.nameTV);
        licenseTV = findViewById(R.id.licenseTV);
        addressTV = findViewById(R.id.addressTV);
        timeTV = findViewById(R.id.timeTV);
        recyclerView = findViewById(R.id.recyclerViewEdit);
        StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(Num_Columns, LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(staggeredGridLayoutManager);
        mAdapter = new staffListingAdapter(SHomeDisplay.this, new ArrayList<Listing>());
        recyclerView.setAdapter(mAdapter);
    }
}

