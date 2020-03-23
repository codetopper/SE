package com.example.karat.Customer.CHome;

import com.example.karat.Customer.CHome.CHomeManager;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.karat.Customer.COrder.OrderAdapter;
import com.example.karat.Customer.CSuperMap.MapDisplay;
import com.example.karat.Customer.Cart.CartDisplay;
import com.example.karat.R;
import com.example.karat.Customer.COrder.COrderDisplay;
import com.example.karat.Customer.CProfile.CProfileDisplay;
import com.example.karat.inventory.Listing;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.w3c.dom.Text;

import java.util.ArrayList;

import static com.example.karat.Customer.COrder.CustomerOrders.purchase;

public class CHomeDisplay extends AppCompatActivity implements AdapterView.OnItemSelectedListener{
    RecyclerView recyclerView;
    ArrayList searchList;
    String catparam;
    double pxparam;
    double discparam;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_c_home_display);

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

        Button search = (Button) findViewById(R.id.ExecuteSearch);

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String cathold = categorySpinner.getSelectedItem().toString();
                if(cathold.equals( "--")) {
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
                CHomeManager manager = new CHomeManager();
                searchList = manager.search(pxparam, catparam, discparam);
            }
        });

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navi);

        bottomNavigationView.setSelectedItemId(R.id.Home);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.Home:
                        startActivity(new Intent(getApplicationContext(), CHomeDisplay.class));
                        overridePendingTransition(0,0);
                        finish();
                        return true;
                    case R.id.Orders:
                        initData();
                        startActivity(new Intent(getApplicationContext(), COrderDisplay.class));
                        overridePendingTransition(0,0);
                        finish();
                        return true;
                    case R.id.Profile:
                        startActivity(new Intent(getApplicationContext(), CProfileDisplay.class));
                        overridePendingTransition(0,0);
                        finish();
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
        Intent i = new Intent(getBaseContext(), CartDisplay.class);
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
// zy version most likely wrong cause i haven't get cyrus' update
//         Spinner spin = (Spinner)parent;
//         Spinner spin2 = (Spinner)parent;
//         Spinner spin3 = (Spinner)parent;
//         if(spin.getId() == R.id.spinnerCategory) {
//             String selectCategory = parent.getItemAtPosition(position).toString();
//             //Toast.makeText(parent.getContext(), selectCategory, Toast.LENGTH_SHORT).show();
//         }

//         if(spin2.getId() == R.id.spinnerPrice) {
//             Integer selectPrice = (Integer) parent.getItemAtPosition(position);
//             //Toast.makeText(parent.getContext(), selectPrice, Toast.LENGTH_SHORT).show();
//         }

//         if(spin.getId() == R.id.spinnerDiscounts) {
//             Integer selectDiscount = (Integer) parent.getItemAtPosition(position);
//             //Toast.makeText(parent.getContext(), selectDiscount, Toast.LENGTH_SHORT).show();

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
        }
    }

    public void onNothingSelected(AdapterView<?> parent) {

    }
}
