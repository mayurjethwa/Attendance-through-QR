package com.example.scandie;

import java.util.SplittableRandom;

public class UserHelperClass {

    String name;
    String email;
    String regno;
    String rollno;
    String password;
    String conf_pass;
    String branch;
    String year;

    public UserHelperClass() {
    }

    public UserHelperClass(String name, String email, String regno, String rollno, String password, String conf_pass, String branch, String year) {
        this.name = name;
        this.email = email;
        this.regno = regno;
        this.rollno = rollno;
        this.password = password;
        this.conf_pass = conf_pass;
        this.branch = branch;
        this.year = year;

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

    public String getRegno() {
        return regno;
    }

    public void setRegno(String regno) {
        this.regno = regno;
    }

    public String getRoll() {
        return rollno;
    }

    public void setRoll(String rollno) {
        this.rollno = rollno;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConf_pass() {
        return conf_pass;
    }

    public void setConf_pass(String conf_pass) {
        this.conf_pass = conf_pass;
    }

    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }
}
