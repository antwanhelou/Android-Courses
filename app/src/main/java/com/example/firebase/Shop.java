package com.example.firebase;

public class Shop {
    private String name;
    private  String desc;
    private int price;
    public Shop(){

    }
    public Shop(String name,String desc,int price){
        this.name=name;
        this.desc=desc;
        this.price=price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}
