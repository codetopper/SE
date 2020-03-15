package com.example.karat;

public class CustomerOrders extends Orders {

    private static Receipt[] custOrders = new Receipt[0];

    void purchase(int item) {
        custOrders[getOrderQuantity()].itemId = item;
        incOrderQuantity();
    }

    public Receipt[] getCOrders(int cust) {
        int i = 0;
        Receipt[] COrders = new Receipt[0];
        for (Receipt r: custOrders) {
            if (r.custId == cust) {
                COrders[i++] = r;
            }
        }
        return COrders;
    }
}
