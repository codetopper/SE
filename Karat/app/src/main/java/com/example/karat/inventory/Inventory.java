package com.example.karat.inventory;

import java.util.ArrayList;

class Inventory{
    private static Inventory single_instance = null;
    public static ArrayList<Listing> meatInventory = new ArrayList<>();
    public static ArrayList<Listing> vegInventory = new ArrayList<>();
    public static ArrayList<Listing> fruitsInventory = new ArrayList<>();
    public static ArrayList<Listing> seafoodInventory = new ArrayList<>();
    public static ArrayList<Listing> cannedInventory = new ArrayList<>();

    private Inventory() {}
    
    public void addMeat(double price, double discount, String name) {
        meatInventory.add(new Listing(price, discount, name, "meats"));
    }

    public void addVeg(double price, double discount, String name) {
        vegInventory.add(new Listing(price, discount, name, "vegetables"));
    }

    public void addFruits(double price, double discount, String name) {
        fruitsInventory.add(new Listing(price, discount, name, "fruits"));
    }

    public void addSeafood(double price, double discount, String name) {
        seafoodInventory.add(new Listing(price, discount, name, "seafood"));
    }

    public void addCanned(double price, double discount, String name) {
        cannedInventory.add(new Listing(price, discount, name, "canned"));
    }

    public static Inventory getInstance(){
        if(single_instance == null){
            single_instance = new Inventory();
        }

        return single_instance;
    }
}