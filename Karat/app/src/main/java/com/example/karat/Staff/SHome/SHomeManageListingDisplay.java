package com.example.karat.Staff.SHome;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;


import android.content.Context;
import android.content.DialogInterface;
import android.app.Activity;
import android.app.ProgressDialog;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import com.example.karat.R;
import com.example.karat.Staff.SOrder.SOrderDisplay;
import com.example.karat.Staff.SProfile.SProfileDisplay;
import com.example.karat.inventory.Listing;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URI;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class SHomeManageListingDisplay extends AppCompatActivity{

    private static final int GET_FROM_GALLERY = 3;
    private ImageView imageView;
    private TextView nameTV, addressTV, timeTV;
    private EditText listingNameET, itemPriceET, itemQtyET, itemDiscET, descriptionET, categoryET;
    private Button uploadBtn, deleteBtn, addBtn, cancelBtn;

    private FirebaseStorage mStorage;
    private DatabaseReference mDatabase;
    private FirebaseAuth mAuth;
    private Listing currentListing = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_s_home_manage_listing_display);

        mDatabase = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();
        mStorage = FirebaseStorage.getInstance();

        initialiseUI();
        initPage();

        //Cancel
        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //confirmbox("Discard changes?", 3);
            }
        });

        uploadBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context context = SHomeManageListingDisplay.this;
                selectImage(context);
            }
        });

        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //confirmbox("Are you sure you update listing?", 1);
                addListingToDatabase();
            }
        });

        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //confirmbox("Are you sure you delete listing", 2);
                deleteListingFromDatabase();
            }
        });
    }

    private void selectImage(Context context) {
        final CharSequence[] options = {"Choose from Gallery","Cancel" };

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Choose your picture");

        builder.setItems(options, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (options[item].equals("Choose from Gallery")) {
                    startActivityForResult(new Intent(Intent.ACTION_PICK,
                            android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI), GET_FROM_GALLERY);
                } else if (options[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //Detects request codes
        if(requestCode==GET_FROM_GALLERY && resultCode == Activity.RESULT_OK) {
            Uri selectedImage = data.getData();
            Bitmap bitmap = null;
            try {
                bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), selectedImage);
                imageView.setImageBitmap(bitmap);
            } catch (FileNotFoundException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }

    private void initPage(){
        final String email = mAuth.getCurrentUser().getEmail().replace("@", "")
                .replace(".", "");

        mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                //header
                String name = dataSnapshot.child("UserDatabase").child(email).child("firstName").getValue(String.class) + " " +
                        dataSnapshot.child("UserDatabase").child(email).child("lastName").getValue(String.class);
                String address = dataSnapshot.child("UserDatabase").child(email).child("address").getValue(String.class);
                String timeStart = dataSnapshot.child("UserDatabase").child(email).child("openingHour").getValue(String.class);
                String timeEnd = dataSnapshot.child("UserDatabase").child(email).child("closingHour").getValue(String.class);
                nameTV.setText(name);
                addressTV.setText(address);
                timeTV.setText(timeStart + "-" + timeEnd);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getApplicationContext(), databaseError.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    private void deleteListingFromDatabase(){
        String listingID;
        if (getIntent().hasExtra("com.example.karat.listingID")){
            listingID = getIntent().getExtras().getString("com.example.karat.listingID");
        } else {
            Toast.makeText(getApplicationContext(), "Please select a valid listing to delete", Toast.LENGTH_LONG).show();
            return;
        }

        mDatabase.child("Inventory").child(listingID).setValue(null);

        Toast.makeText(getApplicationContext(), "Listing deleted!", Toast.LENGTH_LONG).show();

        Intent SHomeIntent = new Intent(getApplicationContext(), SHomeDisplay.class);
        SHomeIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(SHomeIntent);
        overridePendingTransition(0,0);
    }

    private void addListingToDatabase(){
        String listingName, itemPrice, itemQty, itemDiscount, description, supermarket, itemCategory;
        supermarket = nameTV.getText().toString();
        listingName = listingNameET.getText().toString();
        itemPrice = itemPriceET.getText().toString();
        itemQty = itemQtyET.getText().toString();
        itemDiscount = itemDiscET.getText().toString();
        description = descriptionET.getText().toString();
        itemCategory = categoryET.getText().toString();

        if (TextUtils.isEmpty(listingNameET.getText().toString())) {
            Toast.makeText(getApplicationContext(), "Please enter the product name...", Toast.LENGTH_LONG).show();
            return;
        }
        if (TextUtils.isEmpty(itemPriceET.getText().toString())) {
            Toast.makeText(getApplicationContext(), "Please enter the price...", Toast.LENGTH_LONG).show();
            return;
        }
        if (TextUtils.isEmpty(itemQtyET.getText().toString())) {
            Toast.makeText(getApplicationContext(), "Please enter the quantity...", Toast.LENGTH_LONG).show();
            return;
        }
        if (TextUtils.isEmpty(itemDiscET.getText().toString())) {
            Toast.makeText(getApplicationContext(), "Please enter the discount(%)...", Toast.LENGTH_LONG).show();
            return;
        }
        if (TextUtils.isEmpty(descriptionET.getText().toString())) {
            Toast.makeText(getApplicationContext(), "Please enter the description...", Toast.LENGTH_LONG).show();
            return;
        }
        if (TextUtils.isEmpty(categoryET.getText().toString())) {
            Toast.makeText(getApplicationContext(), "Please enter the category...", Toast.LENGTH_LONG).show();
            return;
        }

        //Create new listing object with the information
        Listing newProduct = new Listing(Double.parseDouble(itemPrice), Double.parseDouble(itemDiscount), supermarket,
                listingName, itemCategory, description, Integer.parseInt(itemQty));

        int listingID;
        if (currentListing==null) {
            listingID = newProduct.getListingId();
        } else {
            listingID = currentListing.getListingId();
        }

        mDatabase.child("Inventory").child(listingID+"").setValue(newProduct);
        //Patching issues with listingID increments
        if (getIntent().hasExtra("com.example.karat.listingID")) {
            listingID = Integer.parseInt(getIntent().getExtras().getString("com.example.karat.listingID"));
            mDatabase.child("Inventory").child(listingID+"").child("listingId").setValue(listingID);
        }

        //Upload image to firebase
        StorageReference StoreRef = mStorage.getReference();
        StorageReference uploadImgPath = StoreRef.child("InventoryImages").child(listingID+".jpg");
        imageView.setDrawingCacheEnabled(true);
        imageView.buildDrawingCache();
        Bitmap bitmap = ((BitmapDrawable) imageView.getDrawable()).getBitmap();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] data = baos.toByteArray();

        UploadTask uploadTask = uploadImgPath.putBytes(data);
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                Toast.makeText(getApplicationContext(), exception.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Toast.makeText(getApplicationContext(), "Listing updated!", Toast.LENGTH_SHORT).show();
            }
        });

        String image_url = String.valueOf(mStorage.getReference().child("InventoryImages").child(listingID+".jpg")
                .getDownloadUrl());

        mDatabase.child("Inventory").child(listingID+"").child("imageUrl").setValue(image_url);

        //Return to home activity
        Intent SHomeIntent = new Intent(getApplicationContext(), SHomeDisplay.class);
        SHomeIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(SHomeIntent);
        overridePendingTransition(0,0);
    }

    private void initialiseUI(){
        nameTV = findViewById(R.id.nameTV);
        addressTV = findViewById(R.id.addressTV);
        timeTV = findViewById(R.id.timeTV);

        listingNameET = findViewById(R.id.listingName);
        itemPriceET = findViewById(R.id.itemPrice);
        itemQtyET = findViewById(R.id.itemQty);
        itemDiscET = findViewById(R.id.itemDisc);
        descriptionET = findViewById(R.id.description);

        uploadBtn = findViewById(R.id.upload);
        deleteBtn = findViewById(R.id.delete);
        addBtn = findViewById(R.id.add);
        cancelBtn = findViewById(R.id.cancel);

        imageView = findViewById(R.id.uploadImg);

        categoryET = findViewById(R.id.category);
    }


    private void confirmbox(String message, final int choice){
        AlertDialog.Builder builder = new AlertDialog.Builder(SHomeManageListingDisplay.this);
        builder.setMessage(message)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (choice == 1)
                            addListingToDatabase();
                        else if (choice == 2)
                            deleteListingFromDatabase();
                        else if (choice == 3){
                            Intent SHomeIntent = new Intent(getApplicationContext(), SHomeDisplay.class);
                            SHomeIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(SHomeIntent);
                            overridePendingTransition(0,0);
                        }
                    }
                }).setNegativeButton("No", null);
        AlertDialog alert = builder.create();
        alert.show();
    }
}