package com.example.karat.Customer.COrder;

import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

class COrderManager extends AppCompatActivity {

    public static ArrayList<Order> orders = new ArrayList<>();
    private DatabaseReference mDatabase= FirebaseDatabase.getInstance().getReference();

    public COrderManager(){
        final FirebaseUser currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser() ;
        String email = currentFirebaseUser.getEmail().replace("@", "");
        final String finalEmail = email.replace(".", "");
        mDatabase.child("COrders").child(finalEmail).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                orders.clear();
//                DataSnapshot orderlist = new dataSnapshot.getChildren();
                int count = 1;
                for (DataSnapshot ord: dataSnapshot.getChildren()){
                    for (DataSnapshot details : ord.getChildren()) {
                        if (!details.hasChild("Quantity")) {continue;}
                        orders.add(new Order(" " + count, details.child("Name").getValue(String.class),
                                details.child("Quantity").getValue(Integer.class), details.child("Location").getValue(String.class),
                                details.child("licenseNo").getValue(String.class), details.child("Price").getValue(Double.class)));
                    }
                    Log.d("lol", count+"");
                    count+=1;
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }
}
