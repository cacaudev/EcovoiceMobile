package com.example.cacau2.ecovoicetest;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.LayerDrawable;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.PopupWindow;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Event_view_adapter extends RecyclerView.Adapter<Event_view_holder> implements Serializable {
    private Context mContext;
    private Activity mActivity;

    private ConstraintLayout mRelativeLayout;
    private Button mButton;

    private PopupWindow mPopupWindow;
    List<Event_data> list = Collections.emptyList();
    List<Comment_data> comments = Collections.emptyList();
    Context context;
    ViewGroup viewg;
    private int pos = -1;
    Comment_view_adapter adapter;

    public Event_view_adapter(List<Event_data> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @Override
    public Event_view_holder onCreateViewHolder(final ViewGroup parent, int viewType) {
        //Inflate the layout, initialize the View Holder

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_layout, parent, false);
        viewg = parent;

        final Event_view_holder holder = new Event_view_holder(v);


        // Get the application context
        mContext = this.context;

        // Get the activity

        // Get the widgets reference from XML layout
        mButton = holder.btn;



        return holder;

    }

    @Override
    public void onBindViewHolder(final Event_view_holder holder, final int position) {
        String color;
        pos = position;
        int color_parsed;

        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(),Activity_comments.class);
                Bundle args = new Bundle();
                args.putSerializable("ARRAYLIST",(Serializable)list.get(position).comment_list);
                //args.putSerializable("ADAPTER",(Serializable)holder.adapter);
                intent.putExtra("BUNDLE",args);
                view.getContext().startActivity(intent);


            }
        });


        holder.title.setText(list.get(position).title);
        holder.description.setText(list.get(position).description);
        holder.btn.setTag(position);

        switch (list.get(position).type){
            case 1: //planting
                color = "#4caf50";
                break;
            case 2: // risk
                color = "#f44336";
                break;
            case 3: // death
                color = "#dddee0";
                break;
            case 4: // comment
                color = "#3f51b5";
                break;
            case 5: // disease
                color = "#475069";
                break;
            case 6: // picture
                color = "#7e57c2";
            case 7: // curiosity
                color = "#f44336";
            case 8: // metric
                color = "#ffc107";

            default:
                color = "#dddee0";
                break;

        }
        color_parsed = Color.parseColor(color);
        holder.bar.setBackgroundColor(color_parsed);

        Drawable background = holder.ball.getBackground();


        LayerDrawable bgDrawable = (LayerDrawable)background;
        GradientDrawable bigs = (GradientDrawable)bgDrawable.findDrawableByLayerId (R.id.bigc);
        GradientDrawable smalls = (GradientDrawable)bgDrawable.findDrawableByLayerId (R.id.smallc);
        bigs.setColorFilter(color_parsed, PorterDuff.Mode.SRC_ATOP);
        smalls.setColorFilter(color_parsed, PorterDuff.Mode.SRC_ATOP);



    }

    @Override
    public int getItemCount() {
        //returns the number of elements the RecyclerView will display
        return list.size();
    }
    public int getPos(){
        return this.pos;
    }
    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    // Insert a new item to the RecyclerView on a predefined position
    public void insert(int position, Event_data eventdata) {
        list.add(position, eventdata);
        notifyItemInserted(position);
    }
    public void insertComment(int position, ArrayList<Comment_data> commentData){
        list.get((Integer)position ).comment_list.addAll(commentData);
        notifyItemChanged(position);
    }

    // Remove a RecyclerView item containing a specified Event_data object
    public void remove(Event_data eventdata) {
        int position = list.indexOf(eventdata);
        list.remove(position);
        notifyItemRemoved(position);
    }
    public Event_data getItem(int position) {
        return list.get(position);
    }

}