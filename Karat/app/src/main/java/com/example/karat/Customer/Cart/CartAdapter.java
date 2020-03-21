package com.example.karat.Customer.Cart;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.karat.R;

import org.w3c.dom.Text;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.ExampleViewHolder> {
    /* Instance Variable */

    private ArrayList<Cart> cartArrayList;


    /* ExampleViewHolders */

    public static class ExampleViewHolder extends RecyclerView.ViewHolder{
        public ImageView mImageView;
        public TextView mTextView1;
        public TextView mTextView2;
        public TextView mTextView3;


        public ExampleViewHolder(@NonNull View itemView) {
            super(itemView);
            mImageView =itemView.findViewById(R.id.imageView);
            mTextView1 =itemView.findViewById(R.id.priceView);
            mTextView2 =itemView.findViewById(R.id.itemView);
            mTextView3 =itemView.findViewById(R.id.quantity);

        }

    }


    /* Constructors */

    public CartAdapter(ArrayList<Cart> cartArrayList){
        this.cartArrayList = cartArrayList;
    }




    @NonNull
    @Override
    public ExampleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_item,parent,false);
        ExampleViewHolder evh = new ExampleViewHolder(v);
        return evh;
    }

    @Override
    public void onBindViewHolder(@NonNull ExampleViewHolder holder, int position) {
        Cart currentItem = cartArrayList.get(position);

        //holder.mImageView.setImageResource(currentItem.getmImageResource());
        holder.mTextView1.setText(String.valueOf(currentItem.getPrice()));
        holder.mTextView2.setText(String.valueOf(currentItem.getName()));
        holder.mTextView3.setText(String.valueOf(currentItem.getQuantity()));


    }

    @Override
    public int getItemCount() {
        return cartArrayList.size();
    }

}
