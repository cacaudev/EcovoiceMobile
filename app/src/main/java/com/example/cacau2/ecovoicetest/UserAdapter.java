package com.example.cacau2.ecovoicetest;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Cacau2 on 21/06/2018.
 */

public class UserAdapter extends ArrayAdapter<User> {

    private Context context;
    private ArrayList<User> lista;

    public UserAdapter(Context context, ArrayList<User> lista) {
        super(context,0,lista);
        this.context = context;
        this.lista = lista;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        final User itemPosicao = getItem(position);

        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.layout_row_user_view, parent, false);
        }

        TextView name_text = convertView.findViewById(R.id.name_user_view_item);
        TextView email_text = convertView.findViewById(R.id.email_view_item);

        name_text.setText(itemPosicao.getFull_name());
        email_text.setText(itemPosicao.getEmail());

        return convertView;
    }
}
