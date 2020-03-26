package com.example.karat.Login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import static com.example.karat.Login.GetData.*;

public class UpdateDatabase extends AppCompatActivity {
    static ArrayList data = new ArrayList();
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
        for(int i = 0; i< code.size(); i++){
            String[] dataItem = new String[] {names.get(i).toString(), code.get(i).toString(), id.get(i).toString(), address.get(i).toString()};
            data.add(dataItem);
        }
        updateDatabase();
        Intent i = new Intent(getApplicationContext(), LoginDisplay.class);
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(i);
    }

    private void updateDatabase() {
        DatabaseReference users = databaseReference.child("UserDatabase");
        users.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }

            @Override
            public void onDataChange(DataSnapshot snapshot) {
                for(int i = 0; i<id.size(); i++) {
                    for (DataSnapshot ds : snapshot.getChildren()) {
                        if (ds.child("licenceNo").equals(id.get(i))) {
                            break;
                        }
                        databaseReference.child("UserDatabase").child(id+"@gmail.com").child("postalCode").setValue(code.get(i).toString());
                        databaseReference.child("UserDatabase").child(id+"@gmail.com").child("name").setValue(names.get(i).toString());
                        databaseReference.child("UserDatabase").child(id+"@gmail.com").child("licenseNo").setValue(id.get(i).toString());
                        databaseReference.child("UserDatabase").child(id+"@gmail.com").child("address").setValue(address.get(i).toString());
                        databaseReference.child("UserDatabase").child(id+"@gmail.com").child("isStaff").setValue(1);
                    }
                }
            }
        });
    }
}
