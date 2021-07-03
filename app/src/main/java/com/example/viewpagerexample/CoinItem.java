package com.example.viewpagerexample;

public class CoinItem {
    private String name;
    private int many;
    private double price;
    private double ratio;


    public CoinItem(String name, int many, double price, double ratio) {
        this.name = name;
        this.many = many;
        this.price = price;
        this.ratio = ratio;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getMany() {
        return many;
    }

    public void setMany(int many) {
        this.many = many;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getRatio() {
        return ratio;
    }

    public void setRatio(double ratio) {
        this.ratio = ratio;
    }
}
