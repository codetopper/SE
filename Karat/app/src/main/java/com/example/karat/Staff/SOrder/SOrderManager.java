package com.example.karat.Staff.SOrder;

import com.example.karat.Customer.COrder.CustomerOrders;
import com.example.karat.Customer.COrder.Receipt;

import java.util.ArrayList;
import java.util.List;

public class SOrderManager {
    CustomerOrders orders = new CustomerOrders();
    List<Receipt> sOrders;

    SOrderManager(int staff) {
        sOrders = new ArrayList<>();
        for (Receipt r: orders.orders) {
            if (r.getCustId() == staff) {
                sOrders.add(r);
            }
        }
    }
}
