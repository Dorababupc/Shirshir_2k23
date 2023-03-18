package com.example.shishir_2k23.Home;

public class GalleryModel {
    private String imgUrl;

    public GalleryModel(){

    }
    public GalleryModel(String imgUrl){
        this.imgUrl = imgUrl;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }
}
