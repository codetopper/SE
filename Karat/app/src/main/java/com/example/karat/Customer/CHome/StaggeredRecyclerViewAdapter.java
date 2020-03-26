package com.example.karat.Customer.CHome;

import android.content.Context;
import android.media.Image;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.karat.R;
import com.example.karat.inventory.Listing;
import com.google.firebase.storage.FirebaseStorage;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class StaggeredRecyclerViewAdapter extends RecyclerView.Adapter<StaggeredRecyclerViewAdapter.Viewholder>{

    private static final String TAG = "StaggeredRecyclerViewAd";
    private ArrayList<Listing> Listing;
    private ArrayList<String> mNames = new ArrayList<>();
    private ArrayList<String> mImageUrls = new ArrayList<>();
    private Context mContext;

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
        }
        mContext = context;

        /*mNames = names;
        mImageUrls = imageUrls;
        mContext = context;*/
    }

    public void reset(ArrayList<Listing> Listing){
        mNames.clear();
        mImageUrls.clear();
        for(Listing listing: Listing) {
            mNames.add(listing.getListingName());
            mImageUrls.add(listing.getImage_url());
        }
    }

    @Override
    public Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.search_display, parent, false);
        return new Viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Viewholder holder, final int position) {

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
    }

    @Override
    public int getItemCount() {
        return mImageUrls.size();
    }

    public class Viewholder extends RecyclerView.ViewHolder /*extends RecyclerView.ViewHolder */{
        ImageView image;
        TextView name;

        public Viewholder(View itemView) {
            super(itemView);
            this.image = itemView.findViewById(R.id.imageview_widget);
            this.name = itemView.findViewById(R.id.name_widget);
        }
    }
}
