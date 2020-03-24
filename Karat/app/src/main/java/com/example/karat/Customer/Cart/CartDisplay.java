package com.example.karat.Customer.Cart;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.karat.Customer.CHome.CHomeDisplay;
import com.example.karat.Customer.COrder.COrderDisplay;
import com.example.karat.Customer.CProfile.CProfileDisplay;
import com.example.karat.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class CartDisplay extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private CartAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private TextView subtotal;
    private TextView GST;

    CartManager cartManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart_display);

        /* Initialise Recycler View */


        cartManager= new CartManager();
        createExampleList();
        buildRecyclerView();

        /* Initialise Values */


        subtotal = (TextView) findViewById(R.id.subTotal);
        GST = (TextView) findViewById(R.id.GST);
        subtotal.setText(String.valueOf(cartManager.subtotal()));
        GST.setText(String.valueOf(cartManager.gst()));

        /* Initialise Bottom Navigation Menu */

        NavigationMenu();


    }
    /* Methods */
    public void createExampleList(){
        cartManager.total.addtoCart(3.5,"Chicken",3,R.drawable.ic_person);
        cartManager.total.addtoCart(4.5,"Duck",3,R.drawable.ic_history);
        cartManager.total.addtoCart( 5.5,"Rice",3,R.drawable.ic_person);
    }

    public void buildRecyclerView(){
        mRecyclerView = (RecyclerView) findViewById(R.id.RecyclerView2);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mAdapter = new CartAdapter(cartManager.total.getCartlist());

        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);

        mAdapter.setOnItemClickListener(new CartAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                cartManager.total.getCartlist().get(position);
            }

            @Override
            public void onAddClick(int position) {

            }

            @Override
            public void onDeleteClick(int position) {

            }
        });
    }

    public void NavigationMenu(){
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navi);

        bottomNavigationView.findViewById(R.id.Home).setSelected(false);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.Home:
                        startActivity(new Intent(getApplicationContext(), CHomeDisplay.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.Orders:
                        startActivity(new Intent(getApplicationContext(), COrderDisplay.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.Profile:
                        startActivity(new Intent(getApplicationContext(), CProfileDisplay.class));
                        overridePendingTransition(0,0);
                        return true;
                }
                return false;
            }
        });

    }
}