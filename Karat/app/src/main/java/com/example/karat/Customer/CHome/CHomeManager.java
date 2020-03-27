package com.example.karat.Customer.CHome;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;

import androidx.annotation.NonNull;

import com.example.karat.Customer.CSuperMap.MapDisplay;
import com.example.karat.Login.GetData;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;

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

// 	static ArrayList[] nearOnes = new ArrayList[GetData.code.size()];

// 	static void showNearby(final LatLng p){
// 		final DatabaseReference users = FirebaseDatabase.getInstance().getReference().child("UserDatabase");
// 		users.addListenerForSingleValueEvent(new ValueEventListener() {
// 			@Override
// 			public void onCancelled(@NonNull DatabaseError databaseError) {
// 			}

// 			@Override
// 			public void onDataChange(DataSnapshot snapshot) {
// 				int i = 0;
// 				for (DataSnapshot ds : snapshot.getChildren()) {
// 					if (ds.child("isStaff").getValue(Integer.class).equals(1)) {
// 						float[] distance = new float[1];
// 						LatLng adr = getLocationFromAddress(ds.child("postalCode").getValue().toString(), MapDisplay.coder);
// 						Location.distanceBetween(p.latitude, p.longitude, adr.latitude, adr.longitude, distance);
// 						ds.child("distance").getRef().setValue(distance[0]);
// 						if (distance[0] < 5000){
// 							nearOnes[i].set(0, adr);
// 							nearOnes[i].set(1, ds.child("name").getValue().toString());
// 							nearOnes[i].set(2, ds.child("address").getValue().toString());
// 						}
// 					}
// 				}
// 			}
// 		});
// 	}

// 	public static LatLng getLocationFromAddress(String strAddress, Geocoder coder){
// 		List<Address> address;
// 		LatLng p1 = null;
// 		String strAddress1 = "Singapore" + strAddress;
// 		try
// 		{
// 			address = coder.getFromLocationName(strAddress1, 5);
// 			if(address==null)
// 			{
// 				return null;
// 			}
// 			Address location = address.get(0);
// 			location.getLatitude();
// 			location.getLongitude();

// 			p1 = new LatLng(location.getLatitude(), location.getLongitude());
// 		}
// 		catch (Exception e)
// 		{
// 			e.printStackTrace();
// 		}
// 		return p1;
// 	}
// }
