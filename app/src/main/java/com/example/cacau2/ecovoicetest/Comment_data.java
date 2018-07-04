package com.example.cacau2.ecovoicetest;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Comment_data implements Serializable {

    public String user_name;
    public String comment_text;
   // public int imageId;
    public String time;
    public int id;

    Comment_data(String user_name, String comment_text, /*int imageId,*/ String time, int id) {

        this.user_name = user_name;
        this.comment_text = comment_text;
       // this.imageId = imageId;
        this.time = time;
        this.id = id;

    }
    String get_user_name(){
        return  this.user_name;
    }
    String get_comment_text(){
        return this.comment_text;
    }
    String get_time(){
        Date currentTime = Calendar.getInstance().getTime();
        SimpleDateFormat sdf = new SimpleDateFormat("dd 'of' MM yyyy 'at' HH:mm");
        String currentDateandTime = sdf.format(currentTime);

        return currentDateandTime;
    }
    int get_id(){
        return this.id;
    }
}

