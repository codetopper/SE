package com.example.add;

public class manager {
    int num1;
    int num2;

    public void setNum1(int num1) {
        this.num1 = num1;
    }

    public void setNum2(int num2) {
        this.num2 = num2;
    }

    public int addition(int n1, int n2){
        setNum1(n1);
        setNum2(n2);
        return num1 + num2;
    }
}
