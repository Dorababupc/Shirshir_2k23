package com.nitmeghalaya.shishir_2k23.Shop;

public class BuyModelClass {
    public String name;
    public String rollNumber;
    public String sizeOfCloth;
    public String email;

    public BuyModelClass() {
    }

    public BuyModelClass(String name, String rollNumber, String sizeOfCloth, String email) {
        this.name = name;
        this.rollNumber = rollNumber;
        this.sizeOfCloth = sizeOfCloth;
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRollNumber() {
        return rollNumber;
    }

    public void setRollNumber(String rollNumber) {
        this.rollNumber = rollNumber;
    }

    public String getSizeOfCloth() {
        return sizeOfCloth;
    }

    public void setSizeOfCloth(String sizeOfCloth) {
        this.sizeOfCloth = sizeOfCloth;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
