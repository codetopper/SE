package com.example.karat.Customer.CHome;

import com.google.android.gms.maps.model.LatLng;

public class ShopInfo {

    private LatLng p;
    private String name;
    private String address;

    public ShopInfo(LatLng p, String name, String address) {
        setP(p);
        setAddress(address);
        setName(name);
    }

    public LatLng getP() {
        return p;
    }

    public void setP(LatLng p) {
        this.p = p;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
