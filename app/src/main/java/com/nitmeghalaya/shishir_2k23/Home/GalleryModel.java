package com.nitmeghalaya.shishir_2k23.Home;

public class GalleryModel {
    private String imgUrl;
//    private int likes;
    private String id;
    public GalleryModel(){

    }
    public GalleryModel(String imgUrl,String id){

        this.imgUrl = imgUrl;
        this.id=id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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
