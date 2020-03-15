package com.example.karat.order;

import java.util.ArrayList;
import java.util.List;

class COrderManager {
    CustomerOrders orders = new CustomerOrders();
    List<Receipt> cOrders;
    private int orderSum = 0;

    COrderManager(int cust) {
        cOrders = new ArrayList<>();
        for (Receipt r: orders.orders) {
            if (r.getCustId() == cust) {
                cOrders.add(r);
                incOrderSum();
            }
        }
    }

    public int getOrderSum() {
        return orderSum;
    }

    public void incOrderSum() {
        this.orderSum++;
    }
}
