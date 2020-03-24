
package com.example.karat.Customer.Cart;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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
    private OnItemClickListener mListener;

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
        public Button plusButton;
        public Button minusButton;

        public ExampleViewHolder(@NonNull View itemView, final OnItemClickListener listener) {
            super(itemView);
            mImageView =itemView.findViewById(R.id.imageView);
            mTextView1 =itemView.findViewById(R.id.priceView);
            mTextView2 =itemView.findViewById(R.id.itemView);
            mTextView3 =itemView.findViewById(R.id.quantity);
            plusButton = itemView.findViewById(R.id.plus);
            minusButton = itemView.findViewById(R.id.minus);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null){
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION){
                            listener.onItemClick(position);
                        }
                    }
                }
            });

            plusButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null){
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION){
                            listener.onAddClick(position);
                        }
                    }

                }
            });
            minusButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null){
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION){
                            listener.onDeleteClick(position);
                        }
                    }

                }
            });



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