package com.example.karat.Staff.SHome;

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

public class EditListingAdapter extends RecyclerView.Adapter<EditListingAdapter.Viewholder>{

    private static final String TAG = "EditListingAdapter";
    private ArrayList<Listing> Listing;
    private ArrayList<String> clicktoedit = new ArrayList<>();
    private Context mContext;

    public EditListingAdapter(Context context){
        mContext = context;
    }

    public void reset(ArrayList<Listing> Listing){
        clicktoedit.clear();
        for(Listing listing: Listing) {
            clicktoedit.add(listing.getListingName());
        }
    }

    @Override
    public Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.edit_listing_option, parent, false);
        return new Viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Viewholder holder, final int position) {

        RequestOptions requestOptions = new RequestOptions();
        requestOptions.placeholder(R.drawable.ic_launcher_background);

        holder.name.setText(clicktoedit.get(position));
    }

    @Override
    public int getItemCount() {
        return clicktoedit.size();
    }

    public class Viewholder extends RecyclerView.ViewHolder /*extends RecyclerView.ViewHolder */{
        TextView name;

        public Viewholder(View itemView) {
            super(itemView);
            this.name = itemView.findViewById(R.id.listing4edit);
        }
    }
}
