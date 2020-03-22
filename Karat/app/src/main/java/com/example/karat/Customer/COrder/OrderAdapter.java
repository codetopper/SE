package com.example.karat.Customer.COrder;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
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
        holder.titleTextView.setText("Order" + order.receiptNum);
        holder.itemIdTextView.setText("Item Name: " + order.getItemName());
        holder.date.setText("Date Ordered: " + order.getDate());
        holder.quantity.setText("Quantity: " + order.getQuantity());
        holder.price.setText("Total Price: " + order.getPrice());
        holder.place.setText("@ " + order.getSupName());


        boolean isExpanded = orderList.get(position).isExpanded();
        holder.expandableLayout.setVisibility(isExpanded ? View.VISIBLE : View.GONE);

    }

    @Override
    public int getItemCount() {
        return orderList.size();
    }

    class OrderVH extends RecyclerView.ViewHolder {

        private static final String TAG = "OrderVH";

        CardView container;
        ConstraintLayout expandableLayout;
        TextView titleTextView, itemIdTextView, date, quantity, price, place;

        public OrderVH(@NonNull final View itemView) {
            super(itemView);

            titleTextView = itemView.findViewById(R.id.order_number);
            date = itemView.findViewById(R.id.order_date);
            quantity = itemView.findViewById(R.id.order_quantity);
            price = itemView.findViewById(R.id.order_price);
            place = itemView.findViewById(R.id.order_place);
            itemIdTextView = itemView.findViewById(R.id.item_name);
            expandableLayout = itemView.findViewById(R.id.expandableLayout);
            container = itemView.findViewById(R.id.order_card);

            container.setOnClickListener(new View.OnClickListener() {
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
