package com.example.karat.Customer.CHome;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.WindowManager;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.example.karat.Login.LoginDisplay;
import com.example.karat.Login.ProgressBarAnimation;
import com.example.karat.R;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class UpdateApp extends AppCompatActivity{

    LatLng p;
    static Geocoder coder;
    FusedLocationProviderClient fusedLocationClient;
    Location currentLocation = new Location("dummyprovider");
    private static final int REQUEST_CODE = 101;
//    public static ArrayList<ShopInfo> nearOnes = new ArrayList<>();

    ProgressBar progressBar;
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.loading_screen);

        coder = new Geocoder(getApplicationContext());
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        progressBar = findViewById(R.id.progressBar);
        textView = findViewById(R.id.progressPercent);
        progressBar.setMax(100);
        progressBar.setScaleY(3f);
        progressBar.getProgressDrawable().setColorFilter(
                Color.WHITE, android.graphics.PorterDuff.Mode.SRC_IN);
        fetchLastLocation();
//        startThread();
        getNearby();
//        Intent login = new Intent(getApplicationContext(), Animation.class);
//        startActivity(login);
//        final Handler handler = new Handler();
//        handler.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                // Do something after 5s = 5000ms
//                Intent login = new Intent(getApplicationContext(), LoginDisplay.class);
//                startActivity(login);
//            }
//        }, 1500);
//        Intent login = new Intent(getApplicationContext(), Animation.class);
//        startActivity(login);
        progressAnimation();
    }

    private void fetchLastLocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION},REQUEST_CODE);
            return;
        }
        Task<Location> task = fusedLocationClient.getLastLocation();
        task.addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                if (location != null) {
                    currentLocation = location;
                    p = new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude());
                }
            }
        });
    }

//    public void startThread(){
//        thread thread = new thread();
//        thread.start();
//    }
//
//    class thread extends Thread {
//        @Override
//        public void run() {
//            final DatabaseReference users = FirebaseDatabase.getInstance().getReference().child("UserDatabase");
//            users.addListenerForSingleValueEvent(new ValueEventListener() {
//                @Override
//                public void onCancelled(@NonNull DatabaseError databaseError) {
//                }
//
//                @Override
//                public void onDataChange(DataSnapshot snapshot) {
//                    LatLng p = new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude());
//                    for (DataSnapshot ds : snapshot.getChildren()) {
//                        if (ds.child("isStaff").getValue(Integer.class).equals(1)) {
//                            float[] distance = new float[1];
//                            LatLng adr = getLocationFromAddress(ds.child("postalCode").getValue().toString(), coder);
//                            if (adr!=null) {
//                                Location.distanceBetween(p.latitude, p.longitude, adr.latitude, adr.longitude, distance);
//                                ds.child("distance").getRef().setValue(distance[0]);
//                                if (distance[0] < 3000.00) {
//                                    nearOnes.add(new ShopInfo(adr, ds.child("name").getValue().toString(), ds.child("address").getValue().toString()));
//                                }
//                            }
//                        }
//                    }
//                    Toast.makeText(getApplicationContext(),"There are " + nearOnes.size() + " shops near you!", Toast.LENGTH_LONG).show();
//                    finish();
//                }
//            });
//            progressAnimation();
//        }
//    }


    void getNearby(){
        final DatabaseReference users = FirebaseDatabase.getInstance().getReference().child("UserDatabase");
        users.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }

            @Override
            public void onDataChange(DataSnapshot snapshot) {
                LatLng p1 = p;
                for (DataSnapshot ds : snapshot.getChildren()) {
                    if (ds.child("isStaff").getValue(Integer.class).equals(1)) {
                        float[] distance = new float[1];
                        LatLng adr = getLocationFromAddress(ds.child("postalCode").getValue().toString(), coder);
                        if (adr!=null) {
                            Location.distanceBetween(p1.latitude, p1.longitude, adr.latitude, adr.longitude, distance);
                            if (Math.abs(ds.child("distance").getValue(Float.class) - distance[0]) < 50 ){
                                break;
                            }
                            ds.child("coord").getRef().setValue(adr);
                            ds.child("near").getRef().setValue(0);
                            ds.child("distance").getRef().setValue(distance[0]);
                            if (distance[0] < 3000.00) {
//                                nearOnes.add(new ShopInfo(adr, ds.child("name").getValue().toString(), ds.child("address").getValue().toString()));
                                ds.child("near").getRef().setValue(1);
                            }
                        }
                    }
                }
                Toast.makeText(getApplicationContext(),"Updated!", Toast.LENGTH_LONG).show();
            }
        });
    }

    public static LatLng getLocationFromAddress(String strAddress, Geocoder coder){
        List<Address> address;
        LatLng p1 = null;
        String strAddress1 = "Singapore" + strAddress;
        try
        {
            address = coder.getFromLocationName(strAddress1, 5);
            if(address==null)
            {
                return null;
            }
            Address location = address.get(0);
            location.getLatitude();
            location.getLongitude();

            p1 = new LatLng(location.getLatitude(), location.getLongitude());
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return p1;
    }

    public void progressAnimation() {
        ProgressBarAnimation anim = new ProgressBarAnimation(this, progressBar, textView, 0f, 100f);
        anim.setDuration(2000);
        progressBar.setAnimation(anim);
    }
}
