package com.nitmeghalaya.shishir_2k23.Sponsors;

public class ModelSponsor {
    String website,logo,title;
    public ModelSponsor(){

    }
    public ModelSponsor(String website,String logo,String name,String title){
        this.website=website;
        this.logo=logo;
        this.title=title;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
