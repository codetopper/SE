package com.example.karat.inventory;

import java.util.concurrent.atomic.AtomicInteger;

public class Listing{
    private static final AtomicInteger count = new AtomicInteger(0);
    private final int listingId;
    private int quantity;
    private double price;
    private double discount;
    private String name;
    private String category;
    private String location;
    private boolean available;

    public Listing(double price, double discount, String location, String name, String category){
            listingId = count.incrementAndGet();
            this.price = price;
            this.discount = discount;
            this.name = name;
            this.category = category;
            this.location = location;
            this.available = true;
        }

    public int getListingId() {return listingId;}

    public int getListingQuantity() {return quantity;}

    public void setListingQuantity(int quantity) {this.quantity = quantity;}

    public double getListingPrice() {return price;}

    public void setListingPrice(double price) {this.price = price;}

    public double getListingDiscount() {return discount;}

    public void setListingDiscount(double discount) {this.discount = discount;}

    public String getListingName() {return name;}

    public void setListingName(String name) {this.name = name;}

    public String getListingCategory() {return category;}

    public void setListingCategory(String category) {this.category = category;}

    public String getListingLocation() {return location;}

    public void setListingLocation(String location) {this.location = location;}

    public boolean getListingAvailable() {return available;}

    public void setListingAvailable(boolean available) {this.available = available;}
}