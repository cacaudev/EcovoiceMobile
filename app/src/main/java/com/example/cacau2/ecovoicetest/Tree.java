package com.example.cacau2.ecovoicetest;

/**
 * Created by Cacau2 on 23/06/2018.

 #  id                 :integer          not null, primary key
 #  kind_id            :integer
 #  latitude           :float
 #  longitude          :float
 #  address            :string
 #  user_id            :integer
 #  company_id         :integer
 #  created_at         :datetime         not null
 #  updated_at         :datetime         not null
 #  nursery_company_id :integer
 #  keep_seeds         :boolean          default("false")
 #  pictures_count     :integer          default("0")
 #  old_pictures_count :integer          default("0")
 */

public class Tree {

    private int id;
    private int kind_id;
    private float latitude;
    private float longitude;
    private String address;
    private int user_id;
    private int company_id;
    private int nursery_company_id;
    private boolean keep_seeds;
    private int pictures_count;
    private int old_pictures_count;

    public Tree() {}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getKind_id() {
        return kind_id;
    }

    public void setKind_id(int kind_id) {
        this.kind_id = kind_id;
    }

    public float getLatitude() {
        return latitude;
    }

    public void setLatitude(float latitude) {
        this.latitude = latitude;
    }

    public float getLongitude() {
        return longitude;
    }

    public void setLongitude(float longitude) {
        this.longitude = longitude;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public int getCompany_id() {
        return company_id;
    }

    public void setCompany_id(int company_id) {
        this.company_id = company_id;
    }

    public int getNursery_company_id() {
        return nursery_company_id;
    }

    public void setNursery_company_id(int nursery_company_id) {
        this.nursery_company_id = nursery_company_id;
    }

    public boolean isKeep_seeds() {
        return keep_seeds;
    }

    public void setKeep_seeds(boolean keep_seeds) {
        this.keep_seeds = keep_seeds;
    }

    public int getPictures_count() {
        return pictures_count;
    }

    public void setPictures_count(int pictures_count) {
        this.pictures_count = pictures_count;
    }

    public int getOld_pictures_count() {
        return old_pictures_count;
    }

    public void setOld_pictures_count(int old_pictures_count) {
        this.old_pictures_count = old_pictures_count;
    }
}
