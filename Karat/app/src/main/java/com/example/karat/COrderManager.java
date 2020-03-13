package com.example.karat;

class COrderManager {
    CustomerOrders orders = new CustomerOrders();
    Receipt[] COrders;

    COrderManager(int cust) {
        COrders = orders.getCOrders(cust);
    }
}
