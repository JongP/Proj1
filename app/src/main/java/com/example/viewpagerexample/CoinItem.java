package com.example.viewpagerexample;

public class CoinItem {
    private String name;
    private int many;

    public CoinItem(String name, int many) {
        this.name = name;
        this.many = many;
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

}
