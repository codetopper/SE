
package com.example.karat.Customer.Cart;

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

import java.util.ArrayList;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.ExampleViewHolder> {
    /* Instance Variable */

    private ArrayList<Cart> cartArrayList;
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

    public CartAdapter(ArrayList<Cart> cartArrayList){
        this.cartArrayList = cartArrayList;
    }




    @NonNull
    @Override
    public ExampleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_item,parent,false);
        ExampleViewHolder evh = new ExampleViewHolder(v,mListener);
        return evh;
    }

    @Override
    public void onBindViewHolder(@NonNull ExampleViewHolder holder, int position) {
        final Cart currentItem = cartArrayList.get(position);

        //holder.mImageView.setImageResource(currentItem.getmImageResource());
        holder.mTextView1.setText("$" + String.valueOf(currentItem.getPrice()));
        holder.mTextView2.setText(String.valueOf(currentItem.getName()));
        holder.mTextView3.setText(String.valueOf(currentItem.getQuantity()));

        holder.add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CartManager.total.addtoCart(currentItem.getPrice(),currentItem.getName(),1,1);
                CartDisplay.getSubtotal().setText("SUBTOTAL: $" + String.valueOf(df2.format(CartManager.subtotal() * 0.93)));
                CartDisplay.getGST().setText("GST: $" + String.valueOf(df2.format(CartManager.gst())));
                CartDisplay.getTotal().setText("TOTAL PAYABLE: $" + String.valueOf(df2.format(CartManager.total())));

                notifyDataSetChanged();


            }
        });
        holder.minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CartManager.total.removefromCart(currentItem.getName());
                CartDisplay.getSubtotal().setText("SUBTOTAL: $" + String.valueOf(df2.format(CartManager.subtotal() * 0.93)));
                CartDisplay.getGST().setText("GST: $" + String.valueOf(df2.format(CartManager.gst())));
                CartDisplay.getTotal().setText("TOTAL PAYABLE: $" + String.valueOf(df2.format(CartManager.total())));

                notifyDataSetChanged();



            }
        });
    }

    @Override
    public int getItemCount() {
        return cartArrayList.size();
    }

    public void change(){
        CartDisplay.getSubtotal().setText("SUBTOTAL: $" + String.valueOf(df2.format(CartManager.subtotal() * 0.93)));
        CartDisplay.getGST().setText("GST: $" + String.valueOf(df2.format(CartManager.gst())));
        CartDisplay.getTotal().setText("TOTAL PAYABLE: $" + String.valueOf(df2.format(CartManager.total())));;

        notifyDataSetChanged();
    }

}