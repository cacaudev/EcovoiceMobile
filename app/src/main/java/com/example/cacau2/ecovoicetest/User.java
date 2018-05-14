package com.example.cacau2.ecovoicetest;

import java.net.InetAddress;
import java.sql.Date;
import java.sql.Timestamp;

/**
 * Created by Cacau2 on 06/05/2018.
 */

public class User {

    private int id;
    private String email;
    private String encrypted_password;
    private String reset_password_token;
    private Timestamp reset_password_sent_at;
    private Timestamp remember_create_at;
    private int sign_in_count;
    private Timestamp current_sign_in_at;
    private Timestamp last_sign_in_at;
    private InetAddress current_sign_in_ip;
    private InetAddress last_sign_in_ip;
    private String first_name;
    private String last_name;
    private Date birthday;
    private String gender;
    private String website;
    private String avatar;
    private Timestamp created_at;
    private Timestamp updated_at;
    private String address;
    private Double latitude;
    private Double longitude;
    private int trees_count;
    private int kinds_count;
    private int feedbacks_count;
    private String locale;

    // Constructor
    public User() {
        this.id = -1;
        this.email = "";
        this.encrypted_password = "";
        this.reset_password_token = null;
        this.reset_password_sent_at = null;
        this.remember_create_at = null;
        this.sign_in_count = 0;
        this.current_sign_in_at = null;
        this.last_sign_in_at = null;
        this.current_sign_in_ip = null;
        this.last_sign_in_ip = null;
        this.first_name = null;
        this.last_name = null;
        this.birthday = null;
        this.gender = null;
        this.website = null;
        this.avatar = null;
        this.created_at = null;
        this.updated_at = null;
        this.address = null;
        this.latitude = null;
        this.longitude = null;
        this.trees_count = 0;
        this.kinds_count = 0;
        this.feedbacks_count = 0;
        this.locale = null;
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

    public String getEncrypted_password() {
        return encrypted_password;
    }

    public void setEncrypted_password(String encrypted_password) {
        this.encrypted_password = encrypted_password;
    }

    public String getReset_password_token() {
        return reset_password_token;
    }

    public void setReset_password_token(String reset_password_token) {
        this.reset_password_token = reset_password_token;
    }

    public Timestamp getReset_password_sent_at() {
        return reset_password_sent_at;
    }

    public void setReset_password_sent_at(Timestamp reset_password_sent_at) {
        this.reset_password_sent_at = reset_password_sent_at;
    }

    public Timestamp getRemember_create_at() {
        return remember_create_at;
    }

    public void setRemember_create_at(Timestamp remember_create_at) {
        this.remember_create_at = remember_create_at;
    }

    public int getSign_in_count() {
        return sign_in_count;
    }

    public void setSign_in_count(int sign_in_count) {
        this.sign_in_count = sign_in_count;
    }

    public Timestamp getCurrent_sign_in_at() {
        return current_sign_in_at;
    }

    public void setCurrent_sign_in_at(Timestamp current_sign_in_at) {
        this.current_sign_in_at = current_sign_in_at;
    }

    public Timestamp getLast_sign_in_at() {
        return last_sign_in_at;
    }

    public void setLast_sign_in_at(Timestamp last_sign_in_at) {
        this.last_sign_in_at = last_sign_in_at;
    }

    public InetAddress getCurrent_sign_in_ip() {
        return current_sign_in_ip;
    }

    public void setCurrent_sign_in_ip(InetAddress current_sign_in_ip) {
        this.current_sign_in_ip = current_sign_in_ip;
    }

    public InetAddress getLast_sign_in_ip() {
        return last_sign_in_ip;
    }

    public void setLast_sign_in_ip(InetAddress last_sign_in_ip) {
        this.last_sign_in_ip = last_sign_in_ip;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
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

    public Timestamp getCreated_at() {
        return created_at;
    }

    public void setCreated_at(Timestamp created_at) {
        this.created_at = created_at;
    }

    public Timestamp getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(Timestamp updated_at) {
        this.updated_at = updated_at;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
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

    public int getFeedbacks_count() {
        return feedbacks_count;
    }

    public void setFeedbacks_count(int feedbacks_count) {
        this.feedbacks_count = feedbacks_count;
    }

    public String getLocale() {
        return locale;
    }

    public void setLocale(String locale) {
        this.locale = locale;
    }
}
