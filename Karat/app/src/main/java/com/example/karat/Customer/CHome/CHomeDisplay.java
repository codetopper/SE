package com.example.karat.Customer.CHome;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.karat.Customer.CSuperMap.MapDisplay;
import com.example.karat.Customer.Cart.CartDisplay;
import com.example.karat.Login.GetData;
import com.example.karat.R;
import com.example.karat.Customer.COrder.COrderDisplay;
import com.example.karat.Customer.CProfile.CProfileDisplay;
import com.example.karat.inventory.DataCallback;
import com.example.karat.inventory.Inventory;
import com.example.karat.inventory.Listing;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;

import static com.example.karat.Customer.COrder.CustomerOrders.purchase;

public class CHomeDisplay extends AppCompatActivity implements AdapterView.OnItemSelectedListener{
    private static final int Num_Columns = 1;
    private ArrayList<Listing> searchList = new ArrayList<>();
    private ArrayList<Listing> searched = new ArrayList<>();
    private DatabaseReference mDatabase= FirebaseDatabase.getInstance().getReference();
    private RecyclerView recyclerView;
    private StaggeredRecyclerViewAdapter staggeredRecyclerViewAdapter;
    private Spinner categorySpinner, priceSpinner, discountSpinner, locationSpinner;
    private Button search;

    private String catparam;
    private String locparam;
    private double pxparam;
    private double discparam;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_c_home_display);
        initUI();

        //Do search
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Extracting data from asynchronous database
                searchList.clear();
                searched.clear();
                mDatabase.child("Inventory").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for(DataSnapshot ds : dataSnapshot.getChildren()){
                            Listing listing = ds.getValue(Listing.class);
                            listing.setImage_url(ds.child("imageUrl").getValue(String.class));
                            assert listing != null;
                            searchList.add(listing);
                        }
                        //Data extracted

                        /////////////////////////////////////////
                        //Getting input
                        String cathold = categorySpinner.getSelectedItem().toString();
                        if(cathold.equals("Category")) {
                            catparam = "empty";
                        }
                        else
                            catparam = cathold;

                        String pxhold = priceSpinner.getSelectedItem().toString();
                        if (pxhold.equals("Prices(max$)")){
                            pxparam = -1.0;
                        }
                        else
                            pxparam = Double.parseDouble(pxhold);

                        String dischold = discountSpinner.getSelectedItem().toString();
                        if (dischold.equals("Discounts(min%)")){
                            discparam = -1.0;
                        }
                        else
                            discparam = Double.parseDouble(dischold);


                        String lochold = locationSpinner.getSelectedItem().toString();
                        if (lochold.equals("Location")){
                            locparam = "empty";
                        }
                        else
                            locparam = lochold;
                        //Filtering results
                        try {
                            for (Listing l : searchList) {
                                boolean add = true;
                                double price = l.getListingPrice();
                                if (pxparam != -1.0) {
                                    if (price > pxparam)
                                        add = false;
                                }

                                String category = l.getListingCategory();
                                if (!catparam.equals("empty")) {
                                    if (!category.equals(catparam))
                                        add = false;
                                }

                                double discount = l.getListingDiscount();
                                if (discparam > -1.0) {
                                    if (discount < discparam)
                                        add = false;
                                }

                                if (add){
                                    searched.add(l);
                                }
                            }
                        }catch (Exception e) {
                            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                        }
                            //need to build search location logic
                            //if (location != "empty") {
                            //    if (l.getListingLocation() != lochold)
                            //        searchList.remove(l);
                            //}

                        //setting recycler view
                        staggeredRecyclerViewAdapter.reset(searched);
                        staggeredRecyclerViewAdapter.notifyDataSetChanged();
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        throw databaseError.toException();
                    }
                });
                    //add api for location

            }
        });
        //Editing quantity


        //Navigation bar
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navi);
        bottomNavigationView.setSelectedItemId(R.id.Home);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.Home:
                        Intent CHomeIntent = new Intent(getApplicationContext(), CHomeDisplay.class);
//                        CHomeIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                        CHomeIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(CHomeIntent);
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.Orders:
                        initData();
                        Intent COrderIntent = new Intent(getApplicationContext(), COrderDisplay.class);
//                        COrderIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                        COrderIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(COrderIntent);
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.Profile:
                        Intent CProfileIntent = new Intent(getApplicationContext(), CProfileDisplay.class);
//                        CProfileIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                        CProfileIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(CProfileIntent);
                        overridePendingTransition(0,0);
                        return true;
                }
                return false;
            }
        });


    }

    private void initData() {
        purchase(1, "Apple", 1, 1, 0.5, "1/3/2020", 1, "Giant");
        purchase(2, "Orange", 1, 2, 1.0, "1/3/2020", 1, "Giant");
        purchase(3, "Pear", 1, 1, 0.8, "3/3/2020", 1, "Giant");
        purchase(4, "Pineapple", 1, 2, 7.8, "4/3/2020", 1, "Giant");
        purchase(5, "Durian", 2, 4, 50, "10/3/2020", 1, "Giant");
    }

    public void goToCart(View v) {
        Intent i = new Intent(getApplicationContext(), CartDisplay.class);
//        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(i);
        overridePendingTransition(0,0);
    }

    public void goToMap(View v) {
        Intent i = new Intent(getBaseContext(), MapDisplay.class);
        startActivity(i);
        overridePendingTransition(0,0);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        switch(parent.getId()) {
            case R.id.spinnerCategory:
                String cat = String.valueOf(parent.getItemAtPosition(position));
                //Toast.makeText(this, cat, Toast.LENGTH_SHORT).show();
            case R.id.spinnerPrice:
                String px = String.valueOf(parent.getItemAtPosition(position));
                //Toast.makeText(this, px, Toast.LENGTH_SHORT).show();
            case R.id.spinnerDiscounts:
                String disc = String.valueOf(parent.getItemAtPosition(position));
                //Toast.makeText(this, disc, Toast.LENGTH_SHORT).show();
            case R.id.spinnerLocation:
                String loc = String.valueOf(parent.getItemAtPosition(position));
                //Toast.makeText(this, disc, Toast.LENGTH_SHORT).show();
        }
    }

    public void onNothingSelected(AdapterView<?> parent) {

    }

    private void initUI(){
        recyclerView = findViewById(R.id.recyclerView);
        StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(Num_Columns, LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(staggeredGridLayoutManager);
        locationSpinner = findViewById(R.id.spinnerLocation);
        discountSpinner = findViewById(R.id.spinnerDiscounts);
        priceSpinner = findViewById(R.id.spinnerPrice);
        categorySpinner = findViewById(R.id.spinnerCategory);
        Context context = getApplicationContext();
        staggeredRecyclerViewAdapter = new StaggeredRecyclerViewAdapter(context);
        recyclerView.setAdapter(staggeredRecyclerViewAdapter);
        search = findViewById(R.id.ExecuteSearch);

        //Setting spinners
        ArrayAdapter<CharSequence> categoryAdapter = ArrayAdapter.createFromResource(this, R.array.Categories, android.R.layout.simple_spinner_item);
        categoryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        categorySpinner.setAdapter(categoryAdapter);

        ArrayAdapter<CharSequence> priceAdapter = ArrayAdapter.createFromResource(this, R.array.Prices, android.R.layout.simple_spinner_item);
        priceAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        priceSpinner.setAdapter(priceAdapter);

        ArrayAdapter<CharSequence> discountAdapter = ArrayAdapter.createFromResource(this, R.array.Discounts, android.R.layout.simple_spinner_item);
        discountAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        discountSpinner.setAdapter(discountAdapter);

        ArrayAdapter<CharSequence> locationAdapter = ArrayAdapter.createFromResource(this, R.array.Location, android.R.layout.simple_spinner_item);
        locationAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        locationSpinner.setAdapter(locationAdapter);
    }
}
