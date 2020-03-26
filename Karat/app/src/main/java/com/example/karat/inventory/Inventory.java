package com.example.karat.inventory;

import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.karat.Customer.CHome.CHomeDisplay;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Inventory{
    private final ArrayList<Listing> inventoryList = new ArrayList<>();
    private DatabaseReference mDatabase= FirebaseDatabase.getInstance().getReference();

    public Inventory() {
    }
    public void update(final DataCallback myCallback){
        mDatabase.child("Inventory").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot ds : dataSnapshot.getChildren()){
                    Listing listing = ds.getValue(Listing.class);
                    assert listing != null;
                    String a = "hi";
                    inventoryList.add(listing);
                }
                myCallback.onCallback(inventoryList);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                throw databaseError.toException();
            }
        });
    }
    public ArrayList<Listing> getInventory(){
        return (ArrayList<Listing>)inventoryList.clone();
    }
}