package com.example.karat.Customer.Cart;
import com.example.karat.Customer.COrder.Receipt;

import java.util.ArrayList;

public class CartTotal {

    /* Attributes */

    protected static int cartAmount = 0;
    private static ArrayList<Cart> cartlist = new ArrayList<>();
    private static ArrayList<String> cartlistname = new ArrayList<>();
    /* Getters */

    public static int getCartAmount() {
        return cartAmount;
    }
    public static ArrayList<Cart> getCartlist() {
        return cartlist;
    }
    public static ArrayList<String> getNamelist(){
        return cartlistname;
    }


    /* Constructors */

    CartTotal(){
    }

    /* Methods */


    public void addtoCart(double price, String name, int quantity,int mImageResource) {

        if (checkInCart(name) == true){
            for (Cart c: cartlist) {
                if (c.getName() == name) {
                    c.setQuantity(c.getQuantity() + 1);
                    return;
                }
            }
        }

        else {
            cartlist.add(new Cart(price, name, quantity, mImageResource));
            cartAmount++;
            cartlistname.add(name);
        }
    }

    public void removefromCart(String name){
        int position = 0;
        for (Cart c: cartlist) {
            if (c.getName() == name) {
                if (c.getQuantity() == 1) {
                    cartlist.remove(position);
                    return;
                }
                else {
                    c.quantity -= 1;
                    return;
                }
            }
            position++;
        }
    }

    public static void emptyCart() {
        cartlist.clear();
        cartlistname.clear();
        return;
    }


    public boolean checkInCart(String name){
        return(cartlistname.contains(name));
    }

}


