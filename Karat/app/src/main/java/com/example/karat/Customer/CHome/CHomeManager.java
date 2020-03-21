package com.example.karat.Customer.CHome;
import com.example.karat.inventory.Inventory;
import com.example.karat.inventory.Listing;

import java.util.ArrayList;
import java.util.List;

public class CHomeManager {

	public CHomeManager(){}
	Inventory inventory = Inventory.getInstance();
	ArrayList<Listing> searchList = inventory.getList();

	public ArrayList search(double price, String category, double discount, String location) {
		searchList = inventory.getList();
		for (Listing l : searchList) {
			if (price != -1) {
				if (l.getListingPrice() <= price)
					searchList.remove(l);
			}

			if (category != "empty") {
				if (l.getListingCategory() != category)
				searchList.remove(l);
			}

			if (discount != -1) {
				if (l.getListingDiscount() >= discount)
					searchList.remove(l);
			}

			if (location != "empty") {
				if (l.getListingLocation() != location)
					searchList.remove(l);
			}
		}
		return searchList;
	}
	// cart manager
}
