package com.example.karat.Customer.CHome;
import com.example.karat.inventory.Inventory;
import com.example.karat.inventory.Listing;

import java.util.ArrayList;
import java.util.List;

public class CHomeManager {
    Inventory inventory = Inventory.getInstance();
    public ArrayList<Listing> searchList = new ArrayList<>();

	public CHomeManager(){}

	public ArrayList<Listing> search(double price, String category, double discount, String location) {
		inventory.updateList();
		searchList = (ArrayList<Listing>) inventory.inventoryList.clone();
		/*for (Listing l : searchList) {
			if (price != -1.0) {
				if (l.getListingPrice() <= price)
					searchList.remove(l);
			}

			if (category != "empty") {
				if (l.getListingCategory() != category)
				searchList.remove(l);
			}

			if (discount != -1.0) {
				if (l.getListingDiscount() >= discount)
					searchList.remove(l);
			}

			//need to build search location logic
			if (location != "empty") {
				if (l.getListingLocation() != location)
					searchList.remove(l);
			}
		}*/
		return searchList;
	}
	// cart manager
}
