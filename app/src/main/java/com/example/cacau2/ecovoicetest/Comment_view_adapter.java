package com.example.cacau2.ecovoicetest;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.database.DataSetObserver;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;


public class Comment_view_adapter extends ArrayAdapter<Comment_data> {
    private List<Comment_data> mData = new ArrayList<Comment_data>();
    Button b;
    PopupMenu popup;
    private ListView comment_list;

    public Comment_view_adapter(Context context, int textViewResourceId) {
        super(context, textViewResourceId);
    }

    public Comment_view_adapter(Context context, int resource, List<Comment_data> items) {
        super(context, 0, items);
        mData = new ArrayList<Comment_data>(items);
    }
    public void addItem(final Comment_data item) {
        mData.add(item);
        notifyDataSetChanged();
    }
    @Override
    public View getView(final int position, final View convertView, final ViewGroup parent) {

        View v = convertView;

        if (v == null) {
            LayoutInflater vi;
            vi = LayoutInflater.from(getContext());
            v = vi.inflate(R.layout.row_comment, null);

        }



        comment_list = parent.findViewById(R.id.list_comments);
        Comment_data p = getItem(position);
            if (p != null) {
                TextView tt1 = (TextView) v.findViewById(R.id.default_recent);
                TextView tt2 = (TextView) v.findViewById(R.id.default_event);
                TextView tt3 = (TextView) v.findViewById(R.id.comment_text);
                ImageView image = (ImageView) v.findViewById(R.id.default_img);
                Button b = v.findViewById(R.id.btn_menu);
                if (tt1 != null) {
                    tt1.setText(p.get_user_name());
                }

                if (tt2 != null) {
                    tt2.setText(p.get_time());
                }

                if (tt3 != null) {
                    tt3.setText(p.get_comment_text());
                }
                if(image != null){
                    image.setBackgroundResource(R.mipmap.ic_launcher_round);
                }
                final View finalV = v;
                b.setOnClickListener(new View.OnClickListener(){
                    public void onClick(View view) {
                        final AlertDialog alertDialog = new AlertDialog.Builder(parent.getContext()).create();



                        popup = new PopupMenu(getContext(), view);
                        MenuInflater inflater = popup.getMenuInflater();
                        inflater.inflate(R.menu.game_menu, popup.getMenu());
                        popup.show();
                        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                            public boolean onMenuItemClick(MenuItem item) {
                                switch (item.getItemId()) {
                                    case R.id.op_del:
                                        alertDialog.setTitle("Warning");
                                        alertDialog.setMessage("Proceed Excluding comment?");
                                        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "OK",
                                                new DialogInterface.OnClickListener() {
                                                    public void onClick(DialogInterface dialog, int which) {
                                                        dialog.dismiss();
                                                        comment_list.invalidateViews();
                                                        delete(position);
                                                    }
                                                });
                                        alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "Cancel",
                                                new DialogInterface.OnClickListener() {
                                                    public void onClick(DialogInterface dialog, int which) {
                                                        dialog.dismiss();
                                                    }
                                                });
                                        alertDialog.show();


                                        return true;
                                    case R.id.op_edt:
                                        //EditText t = finalV.findViewById(R.id.post_text);
                                        //t.setText(getItem(position).comment_text);

                                        return true;
                                    default:
                                        return false;
                                }
                            }
                        });
                    }

                });




            }

        return v;
    }

    public void delete(int position){
        mData.remove(position);
        notifyDataSetChanged();
    }
    @Override
    public void insert(Comment_data data,int pos){
        mData.add(pos, data);

    }
    public void update(Comment_data data,int pos,String text){

        Comment_data d = this.mData.get(pos);
        d.comment_text = text;
        this.mData.set(pos,d);
        notifyDataSetChanged();
    }
    public void insertComment(Comment_data commentData){
        mData.add(commentData);
        notifyDataSetChanged();
    }
    @Override
    public boolean areAllItemsEnabled() {
        return false;
    }

    @Override
    public boolean isEnabled(int position) {
        return false;
    }

    @Override
    public void registerDataSetObserver(DataSetObserver observer) {

    }

    @Override
    public void unregisterDataSetObserver(DataSetObserver observer) {

    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public Comment_data getItem(int position) {
        return mData.get(position);
    }

    @Override
    public int getViewTypeCount() {
        return mData.size();
    }

    @Override
    public boolean isEmpty() {
        return mData.isEmpty();
    }



}


