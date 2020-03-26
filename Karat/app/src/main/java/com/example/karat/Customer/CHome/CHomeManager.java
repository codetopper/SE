package com.example.karat.Customer.CHome;
import androidx.annotation.NonNull;

import com.example.karat.inventory.DataCallback;
import com.example.karat.inventory.Inventory;
import com.example.karat.inventory.Listing;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class CHomeManager {
    private Inventory inventory = new Inventory();
    private ArrayList<Listing> searchList = new ArrayList<>();
	private DatabaseReference mDatabase= FirebaseDatabase.getInstance().getReference();


	public CHomeManager(){}

	public Inventory search(double price, String category, double discount, String location) {
		mDatabase.child("Inventory").addListenerForSingleValueEvent(new ValueEventListener() {
			@Override
			public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
				for(DataSnapshot ds : dataSnapshot.getChildren()){
					Listing listing = ds.getValue(Listing.class);
					assert listing != null;
					String a = "hi";
					//inventoryList.add(listing);
				}
				//myCallback.onCallback(inventoryList);
			}
			@Override
			public void onCancelled(@NonNull DatabaseError databaseError) {
				throw databaseError.toException();
			}
		});
		/*for (Listing l : searchList) {
			if (price != -1.0) {
				if (l.getListingPrice() > price)
					searchList.remove(l);
			}

			if (category != "empty") {
				if (l.getListingCategory() != category)
				searchList.remove(l);
			}

			if (discount != -1.0) {
				if (l.getListingDiscount() < discount)
					searchList.remove(l);
			}

			//need to build search location logic
			if (location != "empty") {
				if (l.getListingLocation() != location)
					searchList.remove(l);
			}
		}*/
		return inventory;
	}
	// cart manager
}
