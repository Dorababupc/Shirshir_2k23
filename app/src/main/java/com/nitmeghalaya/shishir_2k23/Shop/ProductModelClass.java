package com.nitmeghalaya.shishir_2k23.Shop;

import java.util.List;

public class ProductModelClass {
    private String name;
    private String price;
    private List<String> imageUrls;

    public ProductModelClass(String name, String price, List<String> imageUrls) {
        this.name = name;
        this.price = price;
        this.imageUrls = imageUrls;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public List<String> getImageUrls() {
        return imageUrls;
    }

    public void setImageUrls(List<String> imageUrls) {
        this.imageUrls = imageUrls;
    }
}
