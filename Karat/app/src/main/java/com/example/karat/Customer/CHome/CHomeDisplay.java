package com.example.karat.Customer.CHome;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.example.karat.Customer.CSuperMap.MapDisplay;
import com.example.karat.Customer.Cart.CartDisplay;
import com.example.karat.R;
import com.example.karat.Customer.COrder.COrderDisplay;
import com.example.karat.Customer.CProfile.CProfileDisplay;
import com.example.karat.inventory.Listing;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;

import static com.example.karat.Customer.COrder.CustomerOrders.purchase;

public class CHomeDisplay extends AppCompatActivity implements AdapterView.OnItemSelectedListener{
    RecyclerView recyclerView;
    private static final int Num_Columns = 2;
    ArrayList<Listing> searchList = new ArrayList<>();
    ArrayList<String> mNames = new ArrayList<String>();
    ArrayList<String> mImageUrls = new ArrayList<String>();


    String catparam;
    String locparam;
    double pxparam;
    double discparam;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_c_home_display);

        initImageBitmaps();

        final Spinner categorySpinner = findViewById(R.id.spinnerCategory);
        final ArrayAdapter<CharSequence> categoryAdapter = ArrayAdapter.createFromResource(this, R.array.Categories, android.R.layout.simple_spinner_item);
        categoryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        categorySpinner.setAdapter(categoryAdapter);
        categorySpinner.setOnItemSelectedListener(this);

        final Spinner priceSpinner = findViewById(R.id.spinnerPrice);
        ArrayAdapter<CharSequence> priceAdapter = ArrayAdapter.createFromResource(this, R.array.Prices, android.R.layout.simple_spinner_item);
        priceAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        priceSpinner.setAdapter(priceAdapter);
        priceSpinner.setOnItemSelectedListener(this);

        final Spinner discountSpinner = findViewById(R.id.spinnerDiscounts);
        final ArrayAdapter<CharSequence> discountAdapter = ArrayAdapter.createFromResource(this, R.array.Discounts, android.R.layout.simple_spinner_item);
        discountAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        discountSpinner.setAdapter(discountAdapter);
        discountSpinner.setOnItemSelectedListener(this);

        final Spinner locationSpinner = findViewById(R.id.spinnerLocation);
        final ArrayAdapter<CharSequence> locationAdapter = ArrayAdapter.createFromResource(this, R.array.Location, android.R.layout.simple_spinner_item);
        locationAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        locationSpinner.setAdapter(locationAdapter);
        locationSpinner.setOnItemSelectedListener(this);

        Button search = (Button) findViewById(R.id.ExecuteSearch);

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String cathold = categorySpinner.getSelectedItem().toString();
                if(cathold.equals("--")) {
                    catparam = "empty";
                }
                else
                    catparam = cathold;

                String pxhold = priceSpinner.getSelectedItem().toString();
                if (pxhold.equals("--")){
                    pxparam = -1;
                }
                else
                    pxparam = Double.parseDouble(pxhold);

                String dischold = discountSpinner.getSelectedItem().toString();
                if (dischold.equals("--")){
                    discparam = -1;
                }
                else
                    discparam = Double.parseDouble(dischold);

                String lochold = locationSpinner.getSelectedItem().toString();
                if (lochold.equals("All")){
                    locparam = "empty";
                }
                else
                    locparam = lochold;
                    //add api for location
                CHomeManager manager = new CHomeManager();
                searchList = manager.search(pxparam, catparam, discparam, locparam);

                initRecyclerView();

            }
        });

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navi);

        bottomNavigationView.setSelectedItemId(R.id.Home);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.Home:
                        Intent CHomeIntent = new Intent(getApplicationContext(), CHomeDisplay.class);
                        CHomeIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(CHomeIntent);
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.Orders:
                        initData();
                        Intent COrderIntent = new Intent(getApplicationContext(), COrderDisplay.class);
                        COrderIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(COrderIntent);
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.Profile:
                        Intent CProfileIntent = new Intent(getApplicationContext(), CProfileDisplay.class);
                        CProfileIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
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

    private void initImageBitmaps(){

        mImageUrls.add("https://c1.staticflickr.com/5/4636/25316407448_de5fbf183d_o.jpg");
        mNames.add("Havasu Falls");

        mImageUrls.add("https://i.redd.it/tpsnoz5bzo501.jpg");
        mNames.add("Trondheim");

        mImageUrls.add("https://i.redd.it/qn7f9oqu7o501.jpg");
        mNames.add("Portugal");

        mImageUrls.add("https://i.redd.it/j6myfqglup501.jpg");
        mNames.add("Rocky Mountain National Park");


        mImageUrls.add("https://i.redd.it/0h2gm1ix6p501.jpg");
        mNames.add("Mahahual");

        mImageUrls.add("https://i.redd.it/k98uzl68eh501.jpg");
        mNames.add("Frozen Lake");


        mImageUrls.add("https://i.redd.it/glin0nwndo501.jpg");
        mNames.add("White Sands Desert");

        mImageUrls.add("https://i.redd.it/obx4zydshg601.jpg");
        mNames.add("Austrailia");

        mImageUrls.add("https://i.imgur.com/ZcLLrkY.jpg");
        mNames.add("Washington");

        initRecyclerView();

    }

    public void goToCart(View v) {
        Intent i = new Intent(getApplicationContext(), CartDisplay.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
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

    private void initRecyclerView(){
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        //StaggeredRecyclerViewAdapter staggeredRecyclerViewAdapter = new StaggeredRecyclerViewAdapter(this, mNames, mImageUrls);
        StaggeredRecyclerViewAdapter staggeredRecyclerViewAdapter = new StaggeredRecyclerViewAdapter(this, searchList);
        StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(Num_Columns, LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(staggeredGridLayoutManager);
        recyclerView.setAdapter(staggeredRecyclerViewAdapter);
    }
}
