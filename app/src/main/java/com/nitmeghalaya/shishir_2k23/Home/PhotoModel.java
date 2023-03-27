package com.nitmeghalaya.shishir_2k23.Home;

public class PhotoModel {
    private String imgUrl;
    private String id;
    public PhotoModel() {
    }

    public PhotoModel(String imgUrl,String id) {

        this.imgUrl = imgUrl;
        this.id=id;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }
}
