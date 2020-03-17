package com.example.karat.order;

import java.util.ArrayList;

import static java.lang.reflect.Array.newInstance;
import static java.lang.reflect.Array.set;

public class CustomerOrders {
    protected static int orderQuantity = 0;
    static ArrayList<Receipt> orders = new ArrayList<>();

    public static void purchase(int itemId, String name, int custId) {
        orders.add(new Receipt(itemId, name, custId));
        orderQuantity++;
    }
}
