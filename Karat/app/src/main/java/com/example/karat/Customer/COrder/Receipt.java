package com.example.karat.Customer.COrder;

import java.util.concurrent.atomic.AtomicInteger;

public class Receipt {
    private int itemId;
    private String itemName;
    private static final AtomicInteger count = new AtomicInteger(0);
    final int receiptNum;
    private int quantity;
    private double price;
    private String date;
    private int custId;
    private boolean expanded;
    private int supId;
    private String supName;

    public Receipt(int itemId, String itemName, int custId, int quantity, double price, String date, int supId, String supName) {
        this.itemId = itemId;
        receiptNum = count.incrementAndGet();
        this.itemName = itemName;
        this.custId = custId;
        this.expanded = false;
        this.quantity = quantity;
        this.price = price;
        this.date = date;
        this.supId = supId;
        this.supName = supName;
    }

    public int getItemId() {
        return itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public int getCustId() {
        return custId;
    }

    public void setCustId(int custId) {
        this.custId = custId;
    }

    public boolean isExpanded() {
        return expanded;
    }

    public void setExpanded(boolean expanded) {
        this.expanded = expanded;
    }


    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getSupId() {
        return supId;
    }

    public void setSupId(int supId) {
        this.supId = supId;
    }


    public String getSupName() {
        return supName;
    }

    public void setSupName(String supName) {
        this.supName = supName;
    }

}
