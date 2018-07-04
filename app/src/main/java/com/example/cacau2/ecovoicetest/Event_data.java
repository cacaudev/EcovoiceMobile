package com.example.cacau2.ecovoicetest;


import java.io.Serializable;
import java.sql.Time;
import java.util.List;

/**
 * Created by igoro on 15/06/2018.
 */

class Event_data implements Serializable {
    public String title;
    public String description;
    public int imageId;
    public int type;
    public String name;
    public Time time;
    List<Comment_data> comment_list ;

    Event_data(String title, String description, int imageId, int type, String name, List<Comment_data> list) {

        this.title = title;
        this.description = description;
        this.imageId = imageId;
        this.type = type;
        this.name = name;
        this.comment_list = list;
    }

}