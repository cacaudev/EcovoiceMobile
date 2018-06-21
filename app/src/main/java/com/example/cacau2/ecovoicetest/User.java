package com.example.cacau2.ecovoicetest;

import java.sql.Date;

/**
 * Created by Cacau2 on 06/05/2018.
 */

public class User {

    private int id;
    private String email;
    private String full_name;
    private Date birthday;
    private String website;
    private String avatar;
    private int trees_count;
    private int kinds_count;
    private String locale;

    // Constructor
    public User() {
        this.id = -1;
        this.email = "";
        this.full_name = null;
        this.birthday = null;
        this.website = null;
        this.avatar = null;
        this.trees_count = 0;
        this.kinds_count = 0;
        this.locale = null;
    }

    public String getFull_name() {
        return full_name;
    }

    public void setFull_name(String full_name) {
        this.full_name = full_name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public int getTrees_count() {
        return trees_count;
    }

    public void setTrees_count(int trees_count) {
        this.trees_count = trees_count;
    }

    public int getKinds_count() {
        return kinds_count;
    }

    public void setKinds_count(int kinds_count) {
        this.kinds_count = kinds_count;
    }

    public String getLocale() {
        return locale;
    }

    public void setLocale(String locale) {
        this.locale = locale;
    }
}
