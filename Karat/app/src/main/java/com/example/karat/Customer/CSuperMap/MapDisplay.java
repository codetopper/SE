package com.example.karat.Customer.CSuperMap;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import com.example.karat.Customer.CHome.CHomeDisplay;
import com.example.karat.Customer.CHome.ShopInfo;
import com.example.karat.Customer.CHome.UpdateApp;
import com.example.karat.Customer.COrder.COrderDisplay;
import com.example.karat.Customer.CProfile.CProfileDisplay;
import com.example.karat.R;
import com.example.karat.Staff.SOrder.SOrderDisplay;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MapDisplay extends FragmentActivity implements OnMapReadyCallback {

    public static Geocoder coder;
    Location currentLocation;
    FusedLocationProviderClient fusedLocationProviderClient;
    private static final int REQUEST_CODE = 101;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        coder = new Geocoder(getApplicationContext());

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        fetchLastLocation();
    }

    private void fetchLastLocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION},REQUEST_CODE);
            return;
        }
        Task<Location> task = fusedLocationProviderClient.getLastLocation();
        task.addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                if (location != null) {
                    currentLocation = location;
                    SupportMapFragment supportMapFragment = (SupportMapFragment)
                            getSupportFragmentManager().findFragmentById(R.id.map);
                    supportMapFragment.getMapAsync(MapDisplay.this);
                }
            }
        });
    }

    @Override
    public void onMapReady(final GoogleMap googleMap) {
        LatLng latLng = new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude());
        googleMap.animateCamera(CameraUpdateFactory.newLatLng(latLng));
        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 13));
        googleMap.setMyLocationEnabled(true);
//        for (int i = 0; i<UpdateApp.nearOnes.size();i++){
//            MarkerOptions markerOptions = new MarkerOptions().position(UpdateApp.nearOnes.get(i).getP()).title(UpdateApp.nearOnes.get(i).getName()).snippet(UpdateApp.nearOnes.get(i).getAddress());
//            googleMap.addMarker(markerOptions);
//        }
        final DatabaseReference users = FirebaseDatabase.getInstance().getReference().child("UserDatabase");
        users.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }

            @Override
            public void onDataChange(DataSnapshot snapshot) {
                for (DataSnapshot ds : snapshot.getChildren()) {
                    try {
                        if (ds.child("isStaff").getValue(Integer.class).equals(1)) {
                            if (ds.child("near").getValue(Integer.class).equals(1)) {
                                LatLng point = new LatLng(ds.child("coord").child("latitude").getValue(Double.class), ds.child("coord").child("longitude").getValue(Double.class));
                                MarkerOptions markerOptions = new MarkerOptions().position(point).title(ds.child("name").getValue(String.class)).snippet(ds.child("licenseNo").getValue(String.class));
                                googleMap.addMarker(markerOptions);
                            }
                        }
                    }catch (Exception ex){}
                }
            }
        });
        googleMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener()
        {
            @Override
            public void onInfoWindowClick(Marker marker) {
                String markertitle = marker.getSnippet();
                Intent i = new Intent(MapDisplay.this, ViewStore.class);
                i.putExtra("id", markertitle);
                startActivity(i);
            }
        });
//        googleMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener(){
//            @Override
//            public boolean onMarkerClick(Marker marker) {
//                String markertitle = marker.getTitle();
//                Intent i = new Intent(MapDisplay.this, ViewStore.class);
//                i.putExtra("name", markertitle);
//                startActivity(i);
//                return false;
//            }
//        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case REQUEST_CODE:
                if (grantResults.length>0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    fetchLastLocation();
                }
                break;
        }
    }

    public void goToHome(View v) {
        Intent i = new Intent(getBaseContext(), CHomeDisplay.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(i);
        overridePendingTransition(0,0);
    }
}