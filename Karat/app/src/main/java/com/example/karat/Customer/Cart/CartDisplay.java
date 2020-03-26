package com.example.karat.Customer.Cart;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.app.AlertDialog;
import android.content.DialogInterface;
import android.view.View;
import android.widget.Button;
import android.os.Build;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.karat.Customer.CHome.CHomeDisplay;
import com.example.karat.Customer.COrder.COrderDisplay;
import com.example.karat.Customer.CProfile.CProfileDisplay;
import com.example.karat.Login.LoginDisplay;
import com.example.karat.R;
import com.example.karat.Staff.SHome.SHomeDisplay;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class CartDisplay extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private CartAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private static TextView subtotal;
    private static TextView GST;
    private static TextView total;
    private Button EmptyCart;

    CartManager cartManager;

    public static TextView getSubtotal() {
        return subtotal;
    }

    public static TextView getGST() {
        return GST;
    }

    public static TextView getTotal() {
        return total;
    }
    AlertDialog dialog;
    AlertDialog.Builder builder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart_display);

        /* Initialise Recycler View */


        cartManager= new CartManager();
        createExampleList();
        buildRecyclerView();
        EmptyCart = findViewById(R.id.emptyCart);
        EmptyCart.setOnClickListener(new View.OnClickListener() {
                                         @Override
                                         public void onClick(View v) {
                                             EmptyCartdialog();
                                         }
                                     }
        );
        /* Initialise Values *
        /* Initialise Bottom Navigation Menu */
        NavigationMenu();
        calculatePrices();


    }
    /* Methods */

    public void EmptyCartdialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(CartDisplay.this);
        builder.setMessage("Are you sure you want to empty the shopping cart?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        try {
                            CartManager.total.emptyCart();
                            mAdapter.setData(cartManager.total.getCartlist());
                            mAdapter.notifyDataSetChanged();
                            //recreate();
                        }catch(Exception e) {
                            Toast.makeText(getApplicationContext(),e.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }
                }).setNegativeButton("No", null);
        AlertDialog alert = builder.create();
        alert.show();

    }


    public void createExampleList(){
        cartManager.total.addtoCart(3.5,"Chicken",3,R.drawable.ic_person);
        cartManager.total.addtoCart(4.5,"Duck",3,R.drawable.ic_history);
        cartManager.total.addtoCart( 5.5,"Rice",3,R.drawable.ic_person);
    }

    public void buildRecyclerView(){
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView2);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mAdapter = new CartAdapter(cartManager.total.getCartlist());

        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);
    }

    public void NavigationMenu() {
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navi);

        bottomNavigationView.findViewById(R.id.Home).setSelected(false);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.Home:
                        Intent CHomeIntent = new Intent(getApplicationContext(), CHomeDisplay.class);
                        CHomeIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(CHomeIntent);
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.Orders:
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


        public void calculatePrices(){
            subtotal = findViewById(R.id.subTotal);
            GST =  findViewById(R.id.GST);
            total = findViewById(R.id.total);
            subtotal.setText("SUBTOTAL: $" + String.valueOf(cartManager.subtotal()));
            GST.setText("GST: $" + String.valueOf(Math.round(cartManager.gst()* 100)/100));
            total.setText("TOTAL PAYABLE: $" + String.valueOf(Math.round(100 * (cartManager.gst() + cartManager.subtotal()))/100));
        }

    }


