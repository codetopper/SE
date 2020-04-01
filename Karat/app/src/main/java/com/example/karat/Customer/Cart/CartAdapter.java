
package com.example.karat.Customer.Cart;

import android.content.Context;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import java.text.DecimalFormat;


import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.karat.R;
import com.example.karat.inventory.Listing;

import java.util.ArrayList;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.ExampleViewHolder> {
    /* Instance Variable */
    private Context mContext;
    private OnItemClickListener mListener;
    private static ArrayList<Double> mPrice;
    private static ArrayList<Integer> mQty;
    private static ArrayList<String> mName;
    private static DecimalFormat df2 = new DecimalFormat("#.##");
    private ArrayList<Listing> cartArrayList;
    DecimalFormat df = new DecimalFormat("#.00"); // Set your desired format here.

    public interface OnItemClickListener {
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
    };

    public void setData(ArrayList<Listing> cartArrayList){
        for (Listing listing: cartArrayList){
            mName.add(listing.getListingName());
            mQty.add(Integer.parseInt(listing.getListingQuantity()+""));
            mPrice.add(Double.parseDouble(listing.getListingPrice()+""));
        }
    }

    public static void clearData(){
        mPrice.clear();
        mName.clear();
        mQty.clear();
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
                int curQty =  mQty.get(position);
                mQty.set(position, curQty+1);
                CartDisplay.getSubtotal().setText("SUBTOTAL: $" + String.valueOf(df2.format(calculateSubtotal())));
                CartDisplay.getGST().setText("GST: $" + String.valueOf(df2.format(calculateGST())));
                CartDisplay.getTotal().setText("TOTAL PAYABLE: $" + String.valueOf(df2.format(calculateTotal())));

                notifyDataSetChanged();
            }
        });
        holder.minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int curQty =  mQty.get(position);
                mQty.set(position, curQty-1);
                if (curQty == 1){
                    mQty.remove(position);
                    mPrice.remove(position);
                    mName.remove(position);
                }
                CartDisplay.getSubtotal().setText("SUBTOTAL: $" + String.valueOf(df2.format(calculateSubtotal())));
                CartDisplay.getGST().setText("GST: $" + String.valueOf(df2.format(calculateGST())));
                CartDisplay.getTotal().setText("TOTAL PAYABLE: $" + String.valueOf(df2.format(calculateTotal())));
                notifyDataSetChanged();



            }
        });
    }



    @Override
    public int getItemCount() {
        return mQty.size();
    }

    public void change(){
        CartDisplay.getSubtotal().setText("SUBTOTAL: $" + String.valueOf(df2.format(CartManager.subtotal() * 0.93)));
        CartDisplay.getGST().setText("GST: $" + String.valueOf(df2.format(CartManager.gst())));
        CartDisplay.getTotal().setText("TOTAL PAYABLE: $" + String.valueOf(df2.format(CartManager.total())));;

        notifyDataSetChanged();
    }

}
