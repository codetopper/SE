package com.example.karat.Customer.Cart;

import com.example.karat.Customer.COrder.CustomerOrders;
import com.example.karat.Customer.COrder.Receipt;
import com.example.karat.Customer.Cart.Cart;
import com.example.karat.Customer.Cart.CartTotal;

import java.util.ArrayList;
import java.util.List;

public class CartManager {
    static CartTotal total = new CartTotal();
    static List <Cart>  cartList;

    public CartManager() {
        cartList = new ArrayList<>();
        for (Cart c: total.getCartlist()){
            cartList.add(c);
        }
    }

    public static double total() {
        double sum = 0;
        for (Cart c: total.getCartlist()){
            sum += c.getPrice() * c.getQuantity();
        }
        return sum;
    }

    public static double gst() {
        return (total() * 0.07);
    }
    public static double subtotal() {return total() * 0.93;}



}


