package com.example.karat.Customer.COrder;

public class Receipt {
    private int itemId;
    private String itemName;
    private int custId;
    private boolean expanded;

    public Receipt(int itemId, String itemName, int custId) {
        this.itemId = itemId;
        this.itemName = itemName;
        this.custId = custId;
        this.expanded = false;
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
}
