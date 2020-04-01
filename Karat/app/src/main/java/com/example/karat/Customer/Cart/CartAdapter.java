
package com.example.karat.Customer.Cart;

import android.content.Context;
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
    private ArrayList<Listing> cartArrayList;
    private OnItemClickListener mListener;
    private static DecimalFormat df2 = new DecimalFormat("#.##");
    DecimalFormat df = new DecimalFormat("#.00"); // Set your desired format here.

    public interface OnItemClickListener {
        void onItemClick(int position);
        void onAddClick(int position);
        void onDeleteClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        mListener = listener;
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
    };

    public void setData(ArrayList<Listing> cartArrayList){
        this.cartArrayList.clear();
        for (Listing listing: cartArrayList){
            cartArrayList.add(listing);
        }
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
        final Listing currentItem = cartArrayList.get(position);

        //holder.mImageView.setImageResource(currentItem.getmImageResource());
        holder.mTextView1.setText("$" + String.valueOf(currentItem.getListingPrice()));
        holder.mTextView2.setText(String.valueOf(currentItem.getListingName()));
        holder.mTextView3.setText(String.valueOf(currentItem.getListingQuantity()));

        holder.add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int sum = 0;
                int curQty = (int)cartArrayList.get(position).getListingQuantity();
                cartArrayList.get(position).setListingQuantity(curQty+1);
                for (Listing l : cartArrayList){
                    sum += l.getListingPrice() * l.getListingQuantity();
                }
                CartDisplay.getSubtotal().setText("SUBTOTAL: $" + String.valueOf(df2.format(sum * 0.93)));
                CartDisplay.getGST().setText("GST: $" + String.valueOf(df2.format(sum * 0.07)));
                CartDisplay.getTotal().setText("TOTAL PAYABLE: $" + String.valueOf(df2.format(sum)));

                notifyDataSetChanged();


            }
        });
        holder.minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int sum = 0;
                int curQty = (int)cartArrayList.get(position).getListingQuantity();
                cartArrayList.get(position).setListingQuantity(curQty-1);
                if (curQty<=0){
                    cartArrayList.remove(position);
                }
                for (Listing l : cartArrayList){
                    sum += l.getListingPrice() * l.getListingQuantity();
                }
                CartDisplay.getSubtotal().setText("SUBTOTAL: $" + String.valueOf(df2.format(sum * 0.93)));
                CartDisplay.getGST().setText("GST: $" + String.valueOf(df2.format(sum * 0.07)));
                CartDisplay.getTotal().setText("TOTAL PAYABLE: $" + String.valueOf(df2.format(sum)));

                notifyDataSetChanged();
            }
        });
    }

    @Override
    public int getItemCount() {
        return cartArrayList.size();
    }
}