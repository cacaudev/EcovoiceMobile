package com.example.cacau2.ecovoicetest;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by igoro on 15/06/2018.
 */

public class Comment_view_holder extends RecyclerView.ViewHolder {

    TextView user;
    TextView comment;
    ImageView profile_pic;
    View bar;

    Comment_view_holder(View itemView) {
        super(itemView);
        user = (TextView) itemView.findViewById(R.id.default_recent);
        comment = (TextView) itemView.findViewById(R.id.comment_text);
        profile_pic = (ImageView) itemView.findViewById(R.id.default_img);
        bar = (View) itemView.findViewById(R.id.comment_bar);
    }
}