package com.example.karat.order;

import java.util.ArrayList;

import static java.lang.reflect.Array.newInstance;
import static java.lang.reflect.Array.set;

class Inventory{
    private static Inventory single_instance = null;
    public static ArrayList<Receipt> meatInventory = new ArrayList<>();
    public static ArrayList<Receipt> vegInventory = new ArrayList<>();
    public static ArrayList<Receipt> fruitsInventory = new ArrayList<>();
    public static ArrayList<Receipt> seafoodInventory = new ArrayList<>();
    public static ArrayList<Receipt> cannedInventory = new ArrayList<>();

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