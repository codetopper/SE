package com.example.karat.order;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.karat.R;

import java.util.List;

import static java.lang.String.valueOf;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.OrderVH> {

    private static final String TAG = "OrderAdapter";
    List<Receipt> orderList;

    public OrderAdapter(List<Receipt> orderList) {
        this.orderList = orderList;
    }

    @NonNull
    @Override
    public OrderVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.order_row, parent, false);
        return new OrderVH(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderVH holder, int position) {

        Receipt order = orderList.get(position);
        holder.titleTextView.setText(order.getItemName());
        holder.itemIdTextView.setText("Item ID: "+order.getItemId());
        holder.custIdTextView.setText("Customer ID: "+order.getCustId());

        boolean isExpanded = orderList.get(position).isExpanded();
        holder.expandableLayout.setVisibility(isExpanded ? View.VISIBLE : View.GONE);

    }

    @Override
    public int getItemCount() {
        return orderList.size();
    }

    class OrderVH extends RecyclerView.ViewHolder {

        private static final String TAG = "OrderVH";

        ConstraintLayout expandableLayout;
        TextView titleTextView, itemIdTextView, custIdTextView;

        public OrderVH(@NonNull final View itemView) {
            super(itemView);

            titleTextView = itemView.findViewById(R.id.titleText);
            itemIdTextView = itemView.findViewById(R.id.itemIdText);
            custIdTextView = itemView.findViewById(R.id.custIdText);
            expandableLayout = itemView.findViewById(R.id.expandableLayout);


            titleTextView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Receipt order = orderList.get(getAdapterPosition());
                    order.setExpanded(!order.isExpanded());
                    notifyItemChanged(getAdapterPosition());

                }
            });
        }
    }
}
