
package com.example.karat.Customer.Cart;

import android.content.Context;
import android.content.Intent;
import android.provider.ContactsContract;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.text.DecimalFormat;


import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.karat.Customer.CHome.CHomeDisplay;
import com.example.karat.R;
import com.example.karat.Staff.SHome.SHomeManageListingDisplay;
import com.example.karat.inventory.Listing;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.ExampleViewHolder> {
    /* Instance Variable */
    private static Context mContext;
    //private ArrayList<Cart> cartArrayList;
    private OnItemClickListener mListener;
    private static ArrayList<Double> mPrice;
    private static ArrayList<Integer> mQty;
    private static ArrayList<String> mName;
    private static ArrayList<Integer> mListingID;
    private static ArrayList<String> mLocation;
    private static ArrayList<String> mLicense;
    private static DecimalFormat df2 = new DecimalFormat("#.##");
    private ArrayList<Listing> cartArrayList;
    DecimalFormat df = new DecimalFormat("#.00"); // Set your desired format here.

    public interface OnItemClickListener {
    }

    public static ArrayList<Integer> getmListingID() {
        return mListingID;
    }

    public static void setmPrice(ArrayList<Double> mPrice) {
        CartAdapter.mPrice = mPrice;
    }

    public static void setmQty(ArrayList<Integer> mQty) {
        CartAdapter.mQty = mQty;
    }

    public void setmName(ArrayList<String> mName) {
        this.mName = mName;
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        mListener = listener;
    }

    public ArrayList<Double> getmPrice() {
        return mPrice;
    }

    public ArrayList<Integer> getmQty() {
        return mQty;
    }

    public ArrayList<String> getmName() {
        return mName;
    }
    /* ExampleViewHolders */

    public static class ExampleViewHolder extends RecyclerView.ViewHolder{
        public ImageView mImageView;
        public TextView mTextView1;
        public TextView mTextView2;
        public TextView mTextView3;
        public Button add;
        public Button minus;
        public ExampleViewHolder(@NonNull View itemView, final OnItemClickListener listener) {
            super(itemView);
            mImageView =itemView.findViewById(R.id.imageView);
            mTextView1 =itemView.findViewById(R.id.priceView);
            mTextView2 =itemView.findViewById(R.id.itemView);
            mTextView3 =itemView.findViewById(R.id.quantity);
            add = itemView.findViewById(R.id.plus);
            minus = itemView.findViewById(R.id.minus);
        }

    }


    /* Constructors */

    public CartAdapter(Context context){
        mContext = context;
        mPrice = new ArrayList<Double>();
        mName = new ArrayList<String>();
        mQty= new ArrayList<Integer>();
        mListingID = new ArrayList<Integer>();
        mLicense = new ArrayList<String>();
        mLocation = new ArrayList<String>();
    };

    public void setData(ArrayList<Listing> cartArrayList){
        for (Listing listing: cartArrayList){
            mName.add(listing.getListingName());
            mQty.add(Integer.parseInt(listing.getListingQuantity()+""));
            mPrice.add(Double.parseDouble(listing.getListingPrice()+""));
            mListingID.add(Integer.parseInt(listing.getListingId()+""));
            mLicense.add(listing.getLicense()+"");
            mLocation.add(listing.getListingLocation());
        }
    }

    public static void clearData(){
        mPrice.clear();
        mName.clear();
        mQty.clear();
        mListingID.clear();
        mLicense.clear();
    }
    public static double calculateTotal(){
        double total = 0;
        for (int i = 0; i < mQty.size(); i++) {
            total += mQty.get(i) * mPrice.get(i);
        }
        return total;
    }

    public static double calculateGST(){
        return calculateTotal() * 0.07;
    }
    public static double calculateSubtotal(){
        return calculateTotal() * 0.93;
    }


    @NonNull
    @Override
    public ExampleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_item,parent,false);
        ExampleViewHolder evh = new ExampleViewHolder(v,mListener);
        return evh;
    }

    @Override
    public void onBindViewHolder(@NonNull ExampleViewHolder holder, final int position) {

        //holder.mImageView.setImageResource(currentItem.getmImageResource());
        holder.mTextView1.setText("$" + String.valueOf(mPrice.get(position)));
        holder.mTextView2.setText(mName.get(position));
        holder.mTextView3.setText(mQty.get(position)+"");

        holder.add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final int curQty =  mQty.get(position);
                final int ListingId = mListingID.get(position);

                // Real Database

                CartDisplay.getmDatabase();
                CartDisplay.getmAuth();
                CartDisplay.getmDatabase().addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        String email = CartDisplay.getmAuth().getCurrentUser().getEmail().replace("@", "")
                                .replace(".", "");
                      int quantity = Integer.parseInt(dataSnapshot.child("Inventory").child(ListingId + "").child("listingQuantity").getValue()+ "");
                    if (curQty == quantity){
                        Toast.makeText(mContext,"Exceeded Maximum Quantity", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    else {
                        CartDisplay.getmDatabase().child("UserCart").child(email).child(ListingId + "").child("cartQty").setValue(curQty + 1);
                        mQty.set(position, curQty+1);
                        CartDisplay.getSubtotal().setText("SUBTOTAL: $" + String.valueOf(df2.format(calculateSubtotal())));
                        CartDisplay.getGST().setText("GST: $" + String.valueOf(df2.format(calculateGST())));
                        CartDisplay.getTotal().setText("TOTAL PAYABLE: $" + String.valueOf(df2.format(calculateTotal())));
                        notifyDataSetChanged();
                    }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        });
        holder.minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int curQty =  mQty.get(position);
                mQty.set(position, curQty-1);
                int ListingID = mListingID.get(position);
                if (curQty == 1){
                    mQty.remove(position);
                    mPrice.remove(position);
                    mName.remove(position);
                    mListingID.remove(position);
                }
                CartDisplay.getSubtotal().setText("SUBTOTAL: $" + String.valueOf(df2.format(calculateSubtotal())));
                CartDisplay.getGST().setText("GST: $" + String.valueOf(df2.format(calculateGST())));
                CartDisplay.getTotal().setText("TOTAL PAYABLE: $" + String.valueOf(df2.format(calculateTotal())));
                notifyDataSetChanged();

                CartDisplay.getmDatabase();
                CartDisplay.getmAuth();
                final String email = CartDisplay.getmAuth().getCurrentUser().getEmail().replace("@", "")
                        .replace(".", "");
                //Toast.makeText(mContext, Listing.get(position).getListingName(), Toast.LENGTH_LONG).show();
                CartDisplay.getmDatabase().child("UserCart").child(email).child(ListingID+ "").child("cartQty").setValue(curQty-1);

                if (curQty == 1){
                    CartDisplay.getmDatabase().child("UserCart").child(email).child(ListingID + "").removeValue();
                }



            }
        });
    }



    @Override
    public int getItemCount() {
        return mQty.size();
    }

    public static void writePurchase(){
        final DatabaseReference mDatabase = CartDisplay.getmDatabase();
        mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String email = CartDisplay.getmAuth().getCurrentUser().getEmail().replace("@", "")
                        .replace(".", "");
                int TId = 1;
                if (dataSnapshot.child("COrders").hasChild(email)) {
                    TId = Integer.parseInt(dataSnapshot.child("COrders").child(email).getChildrenCount() + "") + 1;
                }
                for (int i=0; i<mName.size();i++) {
                    mDatabase.child("COrders").child(email).child("Order"+TId).child("Item"+i).child("Name").setValue(mName.get(i));
                    int id = mListingID.get(i);
                    int quantity = mQty.get(i);
                    int currStock = 0;
                    try {
                        currStock = Integer.parseInt(dataSnapshot.child("Inventory").child(id + "").child("listingQuantity").getValue() + "");
                    } catch (Exception e){
                        Toast.makeText(mContext,"Listing does not exist!", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    if (quantity>currStock){
                        Toast.makeText(mContext,"Purchase Failed :C", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    if(quantity == currStock){
                        mDatabase.child("Inventory").child(id + "").setValue(null);
                    }
                    else {
                        mDatabase.child("Inventory").child(id + "").child("listingQuantity").setValue(currStock-quantity);
                    }
                    mDatabase.child("COrders").child(email).child("Order"+TId).child("Item"+i).child("Quantity").setValue(mQty.get(i));
                    mDatabase.child("COrders").child(email).child("Order"+TId).child("Item"+i).child("License").setValue(mLicense.get(i));
                    mDatabase.child("COrders").child(email).child("Order"+TId).child("Item"+i).child("Price").setValue(mPrice.get(i));
                    mDatabase.child("COrders").child(email).child("Order"+TId).child("Item"+i).child("Location").setValue(mLocation.get(i));
                }
                mDatabase.child("COrders").child(email).child("Order"+TId).child("totalPrice").setValue(calculateTotal());
                mDatabase.child("UserCart").child(email).setValue(null);
                clearData();
                CartDisplay.resetPrices();
                Toast.makeText(mContext,"Purchase Successful!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

}
