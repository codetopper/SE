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
import androidx.cardview.widget.CardView;
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

        holder.name.setText(Listing.get(position).getListingName());
        holder.itemQty.setText(Listing.get(position).getListingQuantity() +"");
        holder.category.setText(Listing.get(position).getListingCategory());
        holder.discount.setText(Listing.get(position).getListingDiscount()+" %");
        holder.descTxt.setText(Listing.get(position).getDescription());
        holder.price.setText("$ "+Listing.get(position).getListingPrice());

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

        boolean isExpanded = Listing.get(position).isExpanded();
        holder.desc.setVisibility(isExpanded ? View.VISIBLE : View.GONE);
        holder.descTxt.setVisibility(isExpanded ? View.VISIBLE : View.GONE);
        holder.arrowd.setVisibility(!isExpanded ? View.VISIBLE : View.GONE);
        holder.arrowu.setVisibility(isExpanded ? View.VISIBLE : View.GONE);
    }

    @Override
    public int getItemCount() {
        return Listing.size();
    }

    public class Viewholder extends RecyclerView.ViewHolder /*extends RecyclerView.ViewHolder */{
        ImageView image, arrowu, arrowd;
        CardView container;
        TextView name, itemQty, desc, descTxt, discount, category, price;
        Button editBtn;

        public Viewholder(View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.imageview_widget);
            editBtn = itemView.findViewById(R.id.editBtn);
            container = itemView.findViewById(R.id.shomecard);
            name = itemView.findViewById(R.id.name_read);
            price = itemView.findViewById(R.id.price_widget);
            itemQty = itemView.findViewById(R.id.qty_widget);
            discount = itemView.findViewById(R.id.disc_widget);
            category = itemView.findViewById(R.id.category_widget);
            descTxt = itemView.findViewById(R.id.description_text);
            desc = itemView.findViewById(R.id.description);
            arrowd = itemView.findViewById(R.id.arrowdown);
            arrowu = itemView.findViewById(R.id.arrowup);

            container.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Listing list = Listing.get(getAdapterPosition());
                    list.setExpanded(!list.isExpanded());
                    notifyItemChanged(getAdapterPosition());
                }
            });
        }
    }
}
