package com.example.karat.inventory;

import java.util.ArrayList;

class Inventory{
    private static Inventory single_instance = null;
    public static ArrayList<Listing> Inventory = new ArrayList<>();

    private Inventory() {}
    
    public void addListing(double price, double discount, String name, String category) {
        Inventory.add(new Listing(price, discount, name, category));
    }

    public int purchase(int listingId){
        for (Listing l: Inventory) {
            if (listingId == l.getListingId()){
                if (l.available == true){
                    l.quantity--;
                    if (l.quantity == 0) {
                        l.setListingAvailable(false)
                    }
                    return 1;
                }
            }
        }    
        return 0;
    }

    public static Inventory getInstance(){
        if(single_instance == null){
            single_instance = new Inventory();
        }

        return single_instance;
    }
}