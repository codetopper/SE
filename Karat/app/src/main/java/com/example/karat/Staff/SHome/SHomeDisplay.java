package com.example.karat.Staff.SHome;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
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

import static com.example.karat.Customer.COrder.CustomerOrders.purchase;

public class SHomeDisplay extends AppCompatActivity {

    private TextView nameTV, addressTV, timeTV;
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;
    private Button addListingBtn;
    private static final int Num_Columns = 1;
    private RecyclerView recyclerView;
    private EditListingAdapter editListingAdapter;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_s_home_display);

        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();

        initialiseUI();
        setHeader();

        ////////changes somewhere here///////////////
        @Override
        public void onNoteClick(int position) {
            Intent intent = new Intent(this, NoteActivity.class);
            intent.putExtra("selected_note", mNotes.get(position));
            startActivity(intent);
        }

        @Override
        public void onClick(View view) {
            Intent intent = new Intent(this, NoteActivity.class);
            startActivity(intent);
        }


        //Generate recycler view with buttons?
        //Where each button has a extra intent of productID
        editListing1Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Test if listing exists
                mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.child("Inventory").child(1 + "").getValue(Listing.class)==null) {
                            Toast.makeText(getApplicationContext(), "Listing does not exist!", Toast.LENGTH_LONG).show();
                        } else {
                            Intent SListingIntent = new Intent(getApplicationContext(), SHomeManageListingDisplay.class);
                            SListingIntent.putExtra("com.example.karat.listingID", "1");
                            startActivity(SListingIntent);
                            overridePendingTransition(0,0);
                        }
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        Toast.makeText(getApplicationContext(), databaseError.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });
            }
        });

        ////////changes somewhere here///////////////

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
                String name = dataSnapshot.child("UserDatabase").child(email).child("firstName").getValue(String.class) + " " +
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

    private void initialiseUI(){
        addListingBtn = findViewById(R.id.addListing);
        nameTV = findViewById(R.id.nameTV);
        addressTV = findViewById(R.id.addressTV);
        timeTV = findViewById(R.id.timeTV);
        recyclerView = findViewById(R.id.recyclerViewEdit);
        StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(Num_Columns, LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(staggeredGridLayoutManager);
        Context context = getApplicationContext();
        editListingAdapter = new EditListingAdapter(context);
        recyclerView.setAdapter(editListingAdapter);
    }

    /*
    private void initData() {
        purchase(1, "Apple", 1, 1, 0.5, "1/3/2020", 1, "Giant");
        purchase(2, "Orange", 1, 2, 1.0, "1/3/2020", 1, "Giant");
        purchase(3, "Pear", 1, 1, 0.8, "3/3/2020", 1, "Giant");
        purchase(4, "Pineapple", 1, 2, 7.8, "4/3/2020", 1, "Giant");
        purchase(5, "Durian", 2, 4, 50, "10/3/2020", 1, "Giant");
    }
    */
}

