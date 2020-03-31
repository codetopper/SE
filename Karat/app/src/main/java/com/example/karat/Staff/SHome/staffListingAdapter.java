package com.example.karat.Staff.SHome;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.karat.R;
import com.example.karat.inventory.Listing;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class staffListingAdapter extends RecyclerView.Adapter<staffListingAdapter.Viewholder>{

    private static final String TAG = "staffListingAdapter";
    private ArrayList<Listing> Listing;
    private Context mContext;

    public staffListingAdapter(Context context){
        mContext = context;
    }

    public staffListingAdapter(Context context, ArrayList<Listing> Listing){
        this.Listing = Listing;
        mContext = context;
    }

    public void reset(ArrayList<Listing> Listing){
        this.Listing = Listing;
    }

    @Override
    public Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.stafflist_display, parent, false);
        return new Viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Viewholder holder, final int position) {
        RequestOptions requestOptions = new RequestOptions();
        requestOptions.placeholder(R.drawable.ic_launcher_background);
        Glide.with(mContext).load(Listing.get(position).getImage_url()).apply(requestOptions).into(holder.image);

        holder.name.setText("Name:\t"+Listing.get(position).getListingName());
        holder.itemQty.setText("Qty:\t"+Listing.get(position).getListingQuantity());
        holder.category.setText("Category:\t"+Listing.get(position).getListingCategory());
        holder.discount.setText("Discount %:\t"+Listing.get(position).getListingDiscount());
        holder.description.setText("Description:\n"+Listing.get(position).getDescription());
        holder.price.setText("Price $:\t"+Listing.get(position).getListingPrice());

        holder.image.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Log.d(TAG, "onClick: clicked on: " + Listing.get(position));
                Toast.makeText(mContext, "Buy me!", Toast.LENGTH_SHORT).show(); }
        });

        holder.editBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(mContext, SHomeManageListingDisplay.class);
                i.putExtra("listingID", Listing.get(position).getListingId()+"");
                mContext.startActivity(i); }
        });
    }

    @Override
    public int getItemCount() {
        return Listing.size();
    }

    public class Viewholder extends RecyclerView.ViewHolder /*extends RecyclerView.ViewHolder */{
        ImageView image;
        TextView name, itemQty, description, discount, category, price;
        Button editBtn;

        public Viewholder(View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.imageview_widget);
            name = itemView.findViewById(R.id.name_widget);
            itemQty = itemView.findViewById(R.id.itemQty);
            editBtn = itemView.findViewById(R.id.editBtn);
            description = itemView.findViewById(R.id.description);
            discount = itemView.findViewById(R.id.discount);
            category = itemView.findViewById(R.id.category);
            price = itemView.findViewById(R.id.price);
        }
    }
}
