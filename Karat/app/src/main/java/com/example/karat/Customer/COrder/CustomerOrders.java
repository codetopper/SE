package com.example.karat.Customer.COrder;

import java.util.ArrayList;

import static java.lang.reflect.Array.newInstance;

public class CustomerOrders {
    protected static int orderQuantity = 0;
    public static ArrayList<Receipt> orders = new ArrayList<>();

    public static void purchase(int itemId, String name, int custId) {
        orders.add(new Receipt(itemId, name, custId));
        orderQuantity++;
    }
}
