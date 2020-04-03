package com.example.karat.Staff.SOrder;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.karat.Customer.COrder.Order;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class SOrderManager extends AppCompatActivity {

    public static ArrayList<SOrder> orders = new ArrayList<>();
    private DatabaseReference mDatabase= FirebaseDatabase.getInstance().getReference();

    public SOrderManager(){
        final FirebaseUser currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser() ;
        String email = currentFirebaseUser.getEmail().replace("@", "");
        final String finalEmail = email.replace(".", "");

        mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String licenseNum = dataSnapshot.child("UserDatabase").child(finalEmail).child("licenseNo").getValue(String.class);
                DataSnapshot sorder = dataSnapshot.child("SOrders").child(licenseNum);
                orders.clear();
//                DataSnapshot orderlist = new dataSnapshot.getChildren();
                int count = 1;
//                Log.d("lol", "lol");
                for (DataSnapshot ord: sorder.getChildren()){
                    String info = "";
//                    Log.d("lol", "lol");
                    for (DataSnapshot details : ord.getChildren()) {
                        info = info + "Name: "+details.child("Name").getValue(String.class)+"\nQty: "+
                                details.child("Quantity").getValue(Integer.class)+"\nItem Price: "+
                                details.child("Price").getValue(Double.class)+"\n"+"\n";
                    }
                    orders.add(new SOrder(" " + count, info));
//                    Log.d("lol", count+"");
                    count+=1;
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }

}
