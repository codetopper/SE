package com.example.karat.Customer.Cart;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
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
import com.example.karat.inventory.Listing;
import com.example.karat.Customer.CHome.CHomeDisplay;
import com.example.karat.Customer.COrder.COrderDisplay;
import com.example.karat.Customer.CProfile.CProfileDisplay;
import com.example.karat.R;
import com.example.karat.Staff.SHome.SHomeDisplay;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.text.DecimalFormat;

public class CartDisplay extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private CartAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private static TextView subtotal;
    private static TextView GST;
    private static TextView total;
    private Button EmptyCart;
    private static DecimalFormat df2 = new DecimalFormat("#.##");
    DecimalFormat df = new DecimalFormat("#.00"); // Set your desired format here.
    private DatabaseReference mDatabase;

    CartManager cartManager;
    AlertDialog dialog;
    AlertDialog.Builder builder;


    /* Methods for getting TextView */

    public static TextView getSubtotal() {
        return subtotal;
    }

    public static TextView getGST() {
        return GST;
    }

    public static TextView getTotal() {
        return total;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart_display);

        /* Initialise Recycler View */

        //cartManager= new CartManager();
        init();
        buildRecyclerView();
        EmptyCart.setOnClickListener(
                new View.OnClickListener() {
             @Override
        public void onClick(View v) {
                if (mAdapter.getmName().isEmpty()){
                    EmptyCart.setEnabled(false);
                    EmptyCart.setBackgroundColor(Color.DKGRAY);
                }
                else {
                    EmptyCartdialog();

                }
        }
    });

        /* Initialise Values *
        /* Initialise Bottom Navigation Menu */

        NavigationMenu();
    }
    /* Methods */

    public void EmptyCartdialog(){
        builder = new AlertDialog.Builder(CartDisplay.this);
        builder.setTitle("Are you sure you want to empty the shopping cart?");
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                CartAdapter.clearData();
                resetPrices();
            }
        });
        builder.setNegativeButton("Cancel", null);
        dialog = builder.create();
        dialog.show();
    }

    public void init(){
        subtotal = findViewById(R.id.subTotal);
        GST =  findViewById(R.id.GST);
        total = findViewById(R.id.total);
        EmptyCart = findViewById(R.id.emptyCart);

        mRecyclerView = findViewById(R.id.recyclerView2);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mAdapter = new CartAdapter(cartManager.total.getCartlist());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);

        mDatabase = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();
    }

    public void buildRecyclerView(){
        mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ArrayList<Listing> cartList = new ArrayList<Listing>();
                int listingId;
                String email = mAuth.getCurrentUser().getEmail().replace("@", "")
                        .replace(".", "");
                DataSnapshot getListingIds = dataSnapshot.child("UserCart").child(email);
               for (DataSnapshot ds : getListingIds.getChildren()){
                   listingId =  Integer.parseInt(ds.child("listingId").getValue()+"");
                   Listing listing = dataSnapshot.child("Inventory").child(listingId+"").getValue(Listing.class);
                   listing.setListingQuantity(Integer.parseInt(ds.child("cartQty").getValue()+""));
                   cartList.add(listing);
                }

               mAdapter.setData(cartList);
               mAdapter.notifyDataSetChanged();
               resetPrices();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

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
    public void resetPrices() {
        mAdapter.notifyDataSetChanged();
        CartDisplay.getSubtotal().setText("SUBTOTAL: $" + String.valueOf(df2.format(CartAdapter.calculateSubtotal())));
        CartDisplay.getGST().setText("GST: $" + String.valueOf(df2.format(CartAdapter.calculateGST())));
        CartDisplay.getTotal().setText("TOTAL PAYABLE: $" + String.valueOf(df2.format(CartAdapter.calculateTotal())));
    }
    }


