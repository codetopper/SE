package com.example.karat.Customer.COrder;

import java.util.ArrayList;
import java.util.List;

class COrderManager {
    CustomerOrders orders = new CustomerOrders();
    List<Receipt> cOrders;

    COrderManager(int cust) {
        cOrders = new ArrayList<>();
        for (Receipt r: orders.orders) {
            if (r.getCustId() == cust) {
                cOrders.add(r);
            }
        }
    }
}
