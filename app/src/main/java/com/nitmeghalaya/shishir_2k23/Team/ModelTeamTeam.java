package com.nitmeghalaya.shishir_2k23.Team;

public class ModelTeamTeam {
    String email,purl,name,position;
    Long contact;
    public ModelTeamTeam(){

    }

    public ModelTeamTeam(Long contact,String email,String purl,String name,String position) {
        this.contact = contact;
        this.email=email;

        this.purl=purl;
        this.name=name;
        this.position=position;
    }
    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public Long getContact() {
        return contact;
    }

    public String getPosition() {
        return position;
    }

    public String getPurl() {
        return purl;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setContact(Long contact) {
        this.contact = contact;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public void setPurl(String purl) {
        this.purl = purl;
    }
}
