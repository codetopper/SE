package com.example.karat.inventory;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

@IgnoreExtraProperties
public class Listing{
    private static final AtomicInteger count = new AtomicInteger(0);
    private final int listingId;

    private int quantity;
    private double price;
    private double discount;
    private String name;
    private String category;
    private String location;

    private String description;
    private boolean available;
    public Map<String, Boolean> stars = new HashMap<>();

    public Listing(){
        listingId = count.get();
    }

    public Listing(double price, double discount, String location, String name, String category, String description, int quantity){
            listingId = count.incrementAndGet();
            this.price = price;
            this.discount = discount;
            this.name = name;
            this.category = category;
            this.location = location;
            this.quantity = quantity;
            this.description = description;
            this.available = true;
        }

    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("price", price);
        result.put("discount", discount);
        result.put("name", name);
        result.put("category", category);
        result.put("location", location);
        result.put("quantity", quantity);
        result.put("description", description);
        result.put("available", true);

        return result;
    }

    public String getDescription() {return description;}

    public void setDescription(String description) {this.description = description;}

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