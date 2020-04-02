package com.example.karat.Customer.COrder;

import java.util.ArrayList;

import static java.lang.reflect.Array.newInstance;

public class CustomerOrders {
    protected static int orderQuantity = 0;
    public static ArrayList<Receipt> orders = new ArrayList<>();

    public static void purchase(int itemId, String name, int custId, int quantity, double price, String date, int supId, String supName) {
        orders.add(new Receipt(itemId, name, custId, quantity, price, date, supId, supName));
        orderQuantity++;
    }
}
