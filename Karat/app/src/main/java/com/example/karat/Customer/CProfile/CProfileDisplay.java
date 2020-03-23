package com.example.karat.Customer.CProfile;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.karat.Customer.Cart.CartDisplay;
import com.example.karat.Login.LoginDisplay;
import com.example.karat.Login.ResetPwDisplay;
import com.example.karat.R;
import com.example.karat.Customer.CHome.CHomeDisplay;
import com.example.karat.Customer.COrder.COrderDisplay;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class CProfileDisplay extends AppCompatActivity {

    private Button logOffBtn, changePwBtn, editProfileBtn;
    private FirebaseAuth mAuth;
    FirebaseUser user;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    private TextView nameTV, dateJTV, mobileTV;
    String email;

    //private static final String USER = "UserDatabase";
    //private String email;
    //private String userid;
    //private final String TAG = this.getClass().getName().toUpperCase();

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //View view = inflater.inflate(R.layout.activity_c_profile_display, container, true);
        setContentView(R.layout.activity_c_profile_display);

        initialiseUI();

        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
        assert user != null;
        email = user.getEmail();
        assert email != null;
        email = email.replace("@", "");
        email = email.replace(".", "");
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();

        nameTV = findViewById(R.id.nameTV);
        dateJTV = findViewById(R.id.dateJTV);
        mobileTV = findViewById(R.id.mobileTV);

        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    String name = dataSnapshot.child("UserDatabase").child(email).child("firstName").getValue(String.class) + " " +
                            dataSnapshot.child("UserDatabase").child(email).child("lastName").getValue(String.class);
                    String datejoined = dataSnapshot.child("UserDatabase").child(email).child("dateRegistered").getValue(String.class);
                    String mobileno = dataSnapshot.child("UserDatabase").child(email).child("mobileNo").getValue(String.class);
                    nameTV.setText(name);
                    dateJTV.setText(datejoined);
                    mobileTV.setText(mobileno);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }

        });



        //Profile Name and Date Registered
        //Intent intent= getIntent();
        //email = intent.getStringExtra("email");

        //Log.v("User UID", userRef.getKey().toString());

        /*userRef.addValueEventListener(new ValueEventListener() {
            String fname, datereg;
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot keyId: dataSnapshot.getChildren()){
                    if(Objects.equals(keyId.child("email").getValue(), email)){
                        fname = keyId.child("firstName").getValue(String.class);
                        datereg = keyId.child("dateRegistered").getValue(String.class);
                        break;
                    }
                }
                nameTextView.setText(fname);
                dateJTextView.setText(datereg);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", databaseError.toException());
            }
        });
*/
        //Log off Button
        logOffBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAuth.signOut();
                finish();
            }
        });

        //ChangePw Button
        changePwBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent changePwIntent = new Intent(getApplicationContext(), ResetPwDisplay.class);
                startActivity(changePwIntent);
                overridePendingTransition(0,0);
            }
        });

        //EditProfile Button
        editProfileBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent editProfileIntent = new Intent(getApplicationContext(), CEditProfileDisplay.class);
                startActivity(editProfileIntent);
                overridePendingTransition(0,0);
            }
        });

        //Navigation code
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navi);
        bottomNavigationView.setSelectedItemId(R.id.Profile);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.Home:
                        startActivity(new Intent(getApplicationContext(), CHomeDisplay.class));
                        overridePendingTransition(0,0);
                        finish();
                        return true;
                    case R.id.Orders:
                        startActivity(new Intent(getApplicationContext(), COrderDisplay.class));
                        overridePendingTransition(0,0);
                        finish();
                        return true;
                    case R.id.Profile:
                        startActivity(new Intent(getApplicationContext(), CProfileDisplay.class));
                        overridePendingTransition(0,0);
                        finish();
                        return true;
                }
                return false;
            }
        });

    }
    private void initialiseUI(){
        logOffBtn = findViewById(R.id.logOff);
        changePwBtn = findViewById(R.id.changePw);
        editProfileBtn = findViewById(R.id.editProfile);
    }

    public void goToCart(View v) {
        Intent i = new Intent(getBaseContext(), CartDisplay.class);
        startActivity(i);
        overridePendingTransition(0,0);
    }

}
