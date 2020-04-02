package com.example.karat.Customer.COrder;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
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
    List<Order> orderList;

    public OrderAdapter(List<Order> orderList) {
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

        Order order = orderList.get(position);
        holder.titleTextView.setText("Order" + order.getOrderNum());
        holder.desc.setText(order.getCDesc());


        boolean isExpanded = orderList.get(position).isExpanded();
        holder.expandableLayout.setVisibility(isExpanded ? View.VISIBLE : View.GONE);
        holder.up.setVisibility(isExpanded ? View.VISIBLE : View.GONE);
        holder.down.setVisibility(!isExpanded ? View.VISIBLE : View.GONE);

    }

    @Override
    public int getItemCount() {
        return orderList.size();
    }

    class OrderVH extends RecyclerView.ViewHolder {

        private static final String TAG = "OrderVH";

        CardView container;
        ConstraintLayout expandableLayout;
        TextView titleTextView, desc;
        ImageView up, down;

        public OrderVH(@NonNull final View itemView) {
            super(itemView);

            titleTextView = itemView.findViewById(R.id.order_number);
            desc = itemView.findViewById(R.id.cdesc);
            expandableLayout = itemView.findViewById(R.id.expandableLayout);
            container = itemView.findViewById(R.id.order_card);
            up = itemView.findViewById(R.id.arrowup);
            down = itemView.findViewById(R.id.arrowdown);

            container.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Order order = orderList.get(getAdapterPosition());
                    order.setExpanded(!order.isExpanded());
                    notifyItemChanged(getAdapterPosition());

                }
            });
        }
    }
}
