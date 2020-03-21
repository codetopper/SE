package com.example.karat.home;

import java.util.ArrayList;
import java.util.List;

public class CHomeManager {

	public CHomeManager(){}
	Inventory inventory = Inventory.getInstance();
	ArrayList<Listing> searchList = inventory.getList();

	public int search(double price, String category, double discount, String location){
		searchList = inventory.getList();
		for (Listing l: searchList) {
			if(price != null){
				searchList.removeIf(l -> (l.getListingPrice() <= price));
			}

			if(category != null){
				searchList.removeIf(l -> (l.getListingCategory() != category));
			}

			if(discount != null){
				searchList.removeIf(l -> (l.getListingDiscount() >= discount));
			}

			if(discount != null){
				searchList.removeIf(l -> (l.getListingLocation() != location));
			}
		}
	// cart manager
}
