package com.example.shishir_2k23.Home;

public class SliderDataModel {
    // string for our image url.
    private String imgUrl;

    // empty constructor which is
    // required when using Firebase.
    public SliderDataModel() {
    }

    // Constructor
    public SliderDataModel(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    // Getter method.
    public String getImgUrl() {
        return imgUrl;
    }

    // Setter method.
    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }
}
