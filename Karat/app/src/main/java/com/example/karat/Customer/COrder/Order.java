package com.example.karat.Customer.COrder;

import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class Order {

    String orderNum;
    String listingName;
    String mart;
    String licenseNo;
    double price;
    int qty;
    boolean isExpanded;

    public Order(String orderNum, String listingName, int qty, String mart, String licenseNo, double price) {
        setOrderNum(orderNum);
        setlistingName(listingName);
        setQty(qty);
        setMart(mart);
        setLicenseNo(licenseNo);
        setExpanded(false);
        setPrice(price);
    }

    public String getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(String orderNum) {
        this.orderNum = orderNum;
    }

    public String getListingName() {
        return listingName;
    }

    public void setlistingName(String listingName) {
        this.listingName = listingName;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    public String getMart() {
        return mart;
    }

    public void setMart(String mart) {
        this.mart = mart;
    }

    public String getLicenseNo() {
        return licenseNo;
    }

    public void setLicenseNo(String licenseNo) {
        this.licenseNo = licenseNo;
    }


    public boolean isExpanded() {
        return isExpanded;
    }

    public void setExpanded(boolean expanded) {
        isExpanded = expanded;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

}
