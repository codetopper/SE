package com.example.karat.Customer.COrder;

import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class Order {

    String orderNum;
    String cDesc = "";
    boolean isExpanded;

    public Order(String orderNum, String cDesc) {
        setOrderNum(orderNum);
        setCDesc(cDesc);
        setExpanded(false);
    }

    public String getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(String orderNum) {
        this.orderNum = orderNum;
    }

    public String getCDesc() {
        return cDesc;
    }

    public void setCDesc(String cDesc) {
        this.cDesc = cDesc;
    }

    public boolean isExpanded() {
        return isExpanded;
    }

    public void setExpanded(boolean expanded) {
        isExpanded = expanded;
    }


}
