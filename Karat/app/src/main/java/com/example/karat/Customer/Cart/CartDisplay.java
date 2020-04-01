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
import com.example.karat.inventory.Listing;

import com.example.karat.Customer.CHome.CHomeDisplay;
import com.example.karat.Customer.COrder.COrderDisplay;
import com.example.karat.Customer.CProfile.CProfileDisplay;
import com.example.karat.R;
import com.example.karat.Staff.SHome.SHomeDisplay;
import com.example.karat.inventory.Listing;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class CartDisplay extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private CartAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private static TextView subtotal;
    private static TextView GST;
    private static TextView total;
    private FirebaseAuth mAuth;
    private Button EmptyCart;
    private ArrayList<Listing> cartList;
    private static DecimalFormat df2 = new DecimalFormat("#.##");
    DecimalFormat df = new DecimalFormat("#.00"); // Set your desired format here.

    private DatabaseReference mDatabase;

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
        mDatabase = FirebaseDatabase.getInstance().getReference();



        cartManager= new CartManager();
        createExampleList();
        buildRecyclerView();
        EmptyCart = findViewById(R.id.emptyCart);
        EmptyCart.setOnClickListener(new View.OnClickListener() {
                                         @Override
                                         public void onClick(View v) { EmptyCartdialog();
                                         }
                                     });
        /* Initialise Values *
        /* Initialise Bottom Navigation Menu */
        NavigationMenu();
        calculatePrices();


    }
    /* Methods */

    public void EmptyCartdialog(){
        builder = new AlertDialog.Builder(CartDisplay.this);
        builder.setTitle("Are you sure you want to empty the shopping cart?");
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                CartManager.total.emptyCart();
                mAdapter.notifyDataSetChanged();
                calculatePrices();

            }
        });
        builder.setNegativeButton("Cancel", null);
        dialog = builder.create();
        dialog.show();
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
        mAdapter = new CartAdapter();

        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);
        mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                int listingId;
                String email = mAuth.getCurrentUser().getEmail().replace("@", "")
                        .replace(".", "");
                DataSnapshot getListingIds = dataSnapshot.child("UserCart").child(email);
                for (DataSnapshot ds : getListingIds.getChildren()){
                    listingId = (int) ds.child("listingId").getValue();
                    Listing listing = dataSnapshot.child("Inventory").child(listingId+"").getValue(Listing.class);
                    listing.setListingQuantity((int) ds.child("cartQty").getValue());
                    cartList.add(listing);
                }
                mAdapter.setData(cartList);
                mAdapter.notifyDataSetChanged();
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
//                        CHomeIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                        CHomeIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(CHomeIntent);
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.Orders:
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


        public void calculatePrices(){
            subtotal = findViewById(R.id.subTotal);
            GST =  findViewById(R.id.GST);
            total = findViewById(R.id.total);
            CartDisplay.getSubtotal().setText("SUBTOTAL: $" + String.valueOf(df2.format(CartManager.subtotal())));
            CartDisplay.getGST().setText("GST: $" + String.valueOf(df2.format(CartManager.gst())));
            CartDisplay.getTotal().setText("TOTAL PAYABLE: $" + String.valueOf(df2.format(CartManager.total())));
        }

    }


