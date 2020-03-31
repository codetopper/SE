package com.example.karat.Login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.example.karat.Customer.CHome.UpdateApp;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import static com.example.karat.Login.GetData.*;

public class UpdateDatabase extends AppCompatActivity {
    private static ArrayList data = new ArrayList();
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
        mAuth = FirebaseAuth.getInstance();

        for(int i = 0; i< code.size(); i++){
            String[] dataItem = new String[] {names.get(i).toString(), code.get(i).toString(), id.get(i).toString(), address.get(i).toString()};
            data.add(dataItem);
        }
        updateDatabase();
        Intent intent = new Intent(getApplicationContext(), UpdateApp.class);
        startActivity(intent);
    }

    private void updateDatabase() {
        DatabaseReference users = databaseReference.child("UserDatabase");
        users.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }

            boolean broke;
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                for(int i = 0; i<id.size(); i++) {
                    for (DataSnapshot ds : snapshot.getChildren()) {
                        if (ds.child("isStaff").getValue(Integer.class).equals(1)) {
                            if (ds.child("licenseNo").getValue().toString().equals(id.get(i).toString())) {
                                ds.child("postalCode").getRef().setValue(code.get(i).toString());
                                ds.child("name").getRef().setValue(names.get(i).toString());
                                ds.child("address").getRef().setValue(address.get(i).toString());
                                broke = true;
                                break;
                            }
                        }
                    }
                    if(broke){
                        broke = false;
                        continue;
                    }
                    add(i);
                }
            }
        });
    }

    private void add(int i){
        final String email = id.get(i) + "@gmail.com";
        final String password = "password";
        final int finalI = i;
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            writeUserToDatabase(task.getResult().getUser(), code.get(finalI).toString(), names.get(finalI).toString(), id.get(finalI).toString(), address.get(finalI).toString());
                            finish();
                        }
                    }
                });
    }

    private void writeUserToDatabase(FirebaseUser email, String code, String names, String id, String address){
        String user = email.getEmail().replace("@", "");
        user = user.replace(".", "");
        Date c = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
        String formattedDate = df.format(c).toString();
        databaseReference.child("UserDatabase").child(user).child("postalCode").setValue(code);
        databaseReference.child("UserDatabase").child(user).child("name").setValue(names);
        databaseReference.child("UserDatabase").child(user).child("licenseNo").setValue(id);
        databaseReference.child("UserDatabase").child(user).child("address").setValue(address);
        databaseReference.child("UserDatabase").child(user).child("isStaff").setValue(1);
        databaseReference.child("UserDatabase").child(user).child("dateRegistered").setValue(formattedDate);
    }
}
