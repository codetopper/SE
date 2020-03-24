package com.example.karat.inventory;

import androidx.annotation.NonNull;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Inventory{
    private static Inventory single_instance = null;
    public ArrayList<Listing> inventoryList = new ArrayList<>();
    private DatabaseReference mDatabase;
    private FirebaseAuth mAuth;

    private Inventory() {}

    //check this method
    public void updateList(){
        DatabaseReference mDatabase;
        FirebaseAuth mAuth;
        mDatabase = FirebaseDatabase.getInstance().getReference();
        DatabaseReference inventoryRef = mDatabase.child("Inventory");
        mAuth = FirebaseAuth.getInstance();

        ValueEventListener eventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot ds : dataSnapshot.getChildren()){
                    Listing listing = (Listing) ds.getValue();
                    inventoryList.add(listing);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {}
        };
        inventoryRef.addListenerForSingleValueEvent(eventListener);
    }


    public static Inventory getInstance(){
        if(single_instance == null){
            single_instance = new Inventory();
        }

        return single_instance;
    }
}