package com.example.cacau2.ecovoicetest;

import android.app.Activity;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

/**
 * Created by igoro on 15/06/2018.
 */

public class Event_view_holder extends RecyclerView.ViewHolder {

    CardView cv;
    TextView title;
    TextView description;
    ImageView imageView;
    View bar;
    ImageView ball;
    RecyclerView recyclerView;
    Button btn;
    ConstraintLayout mRelativeLayout;
    Activity act;
    ListView listview;


    Event_view_holder(View itemView) {
        super(itemView);
        cv = (CardView) itemView.findViewById(R.id.cardView);
        title = (TextView) itemView.findViewById(R.id.title);
        description = (TextView) itemView.findViewById(R.id.description);
        bar = (View) itemView.findViewById(R.id.comment_bar);
        ball = (ImageView) itemView.findViewById(R.id.ball_view);
        recyclerView = (RecyclerView)itemView.findViewById(R.id.recyclerview);
        btn = (Button) itemView.findViewById(R.id.comments);
        mRelativeLayout = (ConstraintLayout) itemView.findViewById(R.id.clayout);
        act = (Activity) itemView.getApplicationWindowToken();
        listview = (ListView) itemView.findViewById(R.id.list_comments);
    }
}