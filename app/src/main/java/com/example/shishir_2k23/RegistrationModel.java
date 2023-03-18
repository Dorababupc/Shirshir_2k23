package com.example.shishir_2k23;

import java.util.List;

public class RegistrationModel {
    private String name;
    private String email;
    private String phoneNumber;
    private String rollNumber;
    private String collegeName;
    private String year;
    private String department;
    private String program;



    public RegistrationModel(String name, String email, String phoneNumber, String rollNumber,String collegeName, String year, String department, String program) {
        this.name = name;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.rollNumber = rollNumber;
        this.collegeName = collegeName;
        this.year = year;
        this.department = department;
        this.program = program;

    }

    public String getCollegeName() {
        return collegeName;
    }

    public void setCollegeName(String collegeName) {
        this.collegeName = collegeName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getRollNumber() {
        return rollNumber;
    }

    public void setRollNumber(String rollNumber) {
        this.rollNumber = rollNumber;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getProgram() {
        return program;
    }

    public void setProgram(String program) {
        this.program = program;
    }


}

