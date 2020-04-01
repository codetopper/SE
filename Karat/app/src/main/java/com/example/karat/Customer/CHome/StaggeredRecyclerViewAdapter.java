package com.example.karat.Customer.CHome;

import android.content.Context;
import android.media.Image;
import android.util.Log;
import android.view.ContextMenu;
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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;

import org.w3c.dom.Text;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class StaggeredRecyclerViewAdapter extends RecyclerView.Adapter<StaggeredRecyclerViewAdapter.Viewholder>{

    private static final String TAG = "StaggeredRecyclerViewAd";
    private ArrayList<Listing> Listing;
    private ArrayList<String> mNames = new ArrayList<>();
    private ArrayList<String> mImageUrls = new ArrayList<>();
    private Context mContext;
    private static ArrayList<Integer> mListingId = new ArrayList<>();
    private static ArrayList<Integer> mQty = new ArrayList<>();
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference mDatabase;
    private FirebaseAuth mAuth;
    private Button addtoCart;

    public StaggeredRecyclerViewAdapter(Context context){
        mContext = context;
    }

    public StaggeredRecyclerViewAdapter(Context context,
                                        ArrayList<Listing> Listing
                                        //ArrayList<String> names, ArrayList<String> imageUrls
    ){

        for(Listing listing: Listing) {
            mNames.add(listing.getListingName());
            mImageUrls.add(listing.getImage_url());
            mListingId.add(listing.getListingId());
            mQty.add(1);
        }
        mContext = context;

        /*mNames = names;
        mImageUrls = imageUrls;
        mContext = context;*/
    }

    public void reset(ArrayList<Listing> listing){
        Listing = (ArrayList<com.example.karat.inventory.Listing>) listing.clone();
        mNames.clear();
        mImageUrls.clear();
        for(Listing list: Listing) {
            mNames.add(list.getListingName());
            mImageUrls.add(list.getImage_url());
        }
    }

    @Override
    public Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.search_display, parent, false);
        return new Viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Viewholder holder, final int position) {

        firebaseDatabase = FirebaseDatabase.getInstance();
        mDatabase = firebaseDatabase.getReference();

        RequestOptions requestOptions = new RequestOptions();
        requestOptions.placeholder(R.drawable.ic_launcher_background);

        Glide.with(mContext).load(mImageUrls.get(position)).apply(requestOptions).into(holder.image);

        holder.name.setText(mNames.get(position));

        holder.image.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Log.d(TAG, "onClick: clicked on: " + mNames.get(position));
                Toast.makeText(mContext, mNames.get(position), Toast.LENGTH_SHORT).show();
            }
        });
        holder.addtoCart.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {

                mDatabase.child("UserDatabase").child("cust1@gmail.com").child("mobileNo").setValue(mListingId.get(position));
                String email = mAuth.getCurrentUser().getEmail().replace("@", "").replace(".", "");
                int id = mListingId.get(position);
                mDatabase.child("UserCart").child(email).child(id+"").child("listingId").setValue(id);
                mDatabase.child("UserCart").child(email).child(id+"").child("cartQty")
                        .setValue(Integer.parseInt(holder.homequantity.getText().toString()));

            }
        });
        boolean isExpanded = Listing.get(position).isExpanded();
        holder.desc.setVisibility(isExpanded ? View.VISIBLE : View.GONE);
    }

    @Override
    public int getItemCount() {
        return mImageUrls.size();
    }

    public class Viewholder extends RecyclerView.ViewHolder /*extends RecyclerView.ViewHolder */{
        ImageView image;
        TextView name;
        Button addtoCart;
        TextView desc;
        CardView container;

        public Viewholder(View itemView) {
            super(itemView);
            this.image = itemView.findViewById(R.id.imageview_widget);
            this.name = itemView.findViewById(R.id.name_widget);
            this.addtoCart = itemView.findViewById(R.id.addtoCart);
            desc = itemView.findViewById(R.id.textView19);
            container = itemView.findViewById(R.id.chomecard);

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
