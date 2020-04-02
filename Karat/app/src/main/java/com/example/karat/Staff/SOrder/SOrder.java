package com.example.karat.Staff.SOrder;

public class SOrder {
    String saleNum;
    String sDesc = "";
    boolean isExpanded;

    public SOrder(String saleNum, String sDesc) {
        setSaleNum(saleNum);
        setSDesc(sDesc);
        setExpanded(false);
    }

    public String getSaleNum() {
        return saleNum;
    }

    public void setSaleNum(String saleNum) {
        this.saleNum = saleNum;
    }

    public String getSDesc() {
        return sDesc;
    }

    public void setSDesc(String sDesc) {
        this.sDesc = sDesc;
    }

    public boolean isExpanded() {
        return isExpanded;
    }

    public void setExpanded(boolean expanded) {
        isExpanded = expanded;
    }
}
