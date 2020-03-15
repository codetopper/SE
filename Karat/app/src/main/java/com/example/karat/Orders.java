package com.example.karat;

public class Orders {
    private static int orderQuantity = 0;

    public static int getOrderQuantity() {
        return orderQuantity;
    }

    public static void incOrderQuantity() {
        Orders.orderQuantity++;
    }
}
