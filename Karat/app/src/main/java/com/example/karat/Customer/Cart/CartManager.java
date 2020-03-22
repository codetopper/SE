package com.example.karat.Customer.Cart;

import com.example.karat.Customer.COrder.CustomerOrders;
import com.example.karat.Customer.COrder.Receipt;
import com.example.karat.Customer.Cart.Cart;
import com.example.karat.Customer.Cart.CartTotal;

import java.util.ArrayList;
import java.util.List;

public class CartManager {
    CartTotal total = new CartTotal();
    List <Cart>  cartList;

    public CartManager() {
        cartList = new ArrayList<>();
        for (Cart c: total.getCartlist()){
            cartList.add(c);
        }
    }

    public double subtotal() {
        double sum = 0;
        for (Cart c: total.getCartlist()){
            sum += c.getPrice();
        }
        return sum;
    }

    public double gst() {
        return (subtotal() * 0.07);
    }
}


