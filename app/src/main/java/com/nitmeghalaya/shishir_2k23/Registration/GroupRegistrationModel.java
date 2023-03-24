package com.nitmeghalaya.shishir_2k23.Registration;

import java.util.List;

public class GroupRegistrationModel {
    private String Teamname;
    private String leader_name;
    private String email;
    private String phoneNumber;
    private String rollNumber;
    private String collegeName;
    private String year;
    private String department;
    private String program;
    private List<String> teamMembers;

    public GroupRegistrationModel(String teamname, String leader_name, String email, String phoneNumber, String rollNumber, String collegeName, String year, String department, String program, List<String> teamMembers) {
        Teamname = teamname;
        this.leader_name = leader_name;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.rollNumber = rollNumber;
        this.collegeName = collegeName;
        this.year = year;
        this.department = department;
        this.program = program;
        this.teamMembers = teamMembers;
    }

    public String getTeamname() {
        return Teamname;
    }

    public void setTeamname(String teamname) {
        Teamname = teamname;
    }

    public String getLeader_name() {
        return leader_name;
    }

    public void setLeader_name(String leader_name) {
        this.leader_name = leader_name;
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

    public String getCollegeName() {
        return collegeName;
    }

    public void setCollegeName(String collegeName) {
        this.collegeName = collegeName;
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

    public List<String> getTeamMembers() {
        return teamMembers;
    }

    public void setTeamMembers(List<String> teamMembers) {
        this.teamMembers = teamMembers;
    }
}
