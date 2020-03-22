package com.example.karat.Customer.Cart;

import java.util.concurrent.atomic.AtomicInteger;

public class Cart {
    private int quantity;
    private double price;
    private String name;
    private int mImageResource;



    public Cart(double price, String name, int quantity,int mImageResource){
        this.price = price;
        this.name = name;
        this.quantity = quantity;
        this.mImageResource = mImageResource;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getmImageResource() {
        return mImageResource;
    }

    public void setmImageResource(int mImageResource) {
        this.mImageResource = mImageResource;
    }
}


