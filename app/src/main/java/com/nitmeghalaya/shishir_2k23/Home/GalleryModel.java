package com.nitmeghalaya.shishir_2k23.Home;

public class GalleryModel {
    private String imgUrl;
//    private int likes;
    public GalleryModel(){

    }
    public GalleryModel(String imgUrl){
        this.imgUrl = imgUrl;
    }

    public String getImgUrl() {
        return imgUrl;
    }

//    public int getLikes() {
//        return likes;
//    }
//
//    public void setLikes(int likes) {
//        this.likes = likes;
//    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }
}
