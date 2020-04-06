package com.example.karat.Customer.CSuperMap;

import android.content.Context;
import android.view.View;

import androidx.annotation.NonNull;

import com.example.karat.Customer.CHome.StaggeredRecyclerViewAdapter;

public class StoreRecyclerView extends StaggeredRecyclerViewAdapter {
    public StoreRecyclerView(Context context) {
        super(context);
    }

    @Override
    public void onBindViewHolder(@NonNull Viewholder holder, int position) {
        super.onBindViewHolder(holder, position);
        holder.viewStore.setVisibility(View.GONE);
    }
}
