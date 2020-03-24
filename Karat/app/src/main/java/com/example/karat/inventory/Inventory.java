package com.example.karat.inventory;

import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.example.karat.inventory.Listing;
import java.util.ArrayList;

public class Inventory{
    private static Inventory single_instance = null;
    public static ArrayList<Listing> inventoryList = new ArrayList<>();
    private DatabaseReference mDatabase;
    private FirebaseAuth mAuth;

    private Inventory() {}
    
    public void updateList(){
        DatabaseReference mDatabase;
        FirebaseAuth mAuth;
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();
        inventoryList.clear();

        mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Listing currentListing = snapshot.child("Inventory").getValue(Listing.class);
                    inventoryList.add(currentListing);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        //return inventoryList;
    }

    /*public int purchase(int listingId){
        for (Listing l: inventoryList) {
            if (listingId == l.getListingId()){
                if (l.getListingAvailable() == true){
                    int quant = l.getListingQuantity();
                    quant--;
                    l.setListingQuantity(quant);
                    if (l.getListingQuantity() == 0) {
                        l.setListingAvailable(false);
                    }
                    return 1;
                }
            }
        }    
        return 0;
    } */

    public static Inventory getInstance(){
        if(single_instance == null){
            single_instance = new Inventory();
        }

        return single_instance;
    }
}