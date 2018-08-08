package com.example.cacau2.ecovoicetest;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.io.Serializable;
import java.util.ArrayList;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

public class Activity_comments extends AppCompatActivity implements Serializable {

    private ListView comment_list;
    private ArrayList<Comment_data> comment_list_data;
    private Comment_view_adapter adaptador;
    private Button menu;
    @Nullable
    View emptyView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comments);
        Intent intent = getIntent();
        Bundle args = intent.getBundleExtra("BUNDLE");
        comment_list_data = (ArrayList<Comment_data>) args.getSerializable("ARRAYLIST");

        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        RecyclerView r = findViewById(R.id.recyclerview);


        adaptador = new Comment_view_adapter(getApplicationContext(), 0, comment_list_data);

        comment_list = this.findViewById(R.id.list_comments);
        comment_list.setAdapter(adaptador);
      // comment_list.setEmptyView(findViewById(R.id.emptyElement));


        Button post_comment = findViewById(R.id.post_comment);
        post_comment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = "igor";
                EditText post_text = findViewById(R.id.post_text);
                String comment = post_text.getText().toString();
                String time = "10";
                Comment_data comment_data = new Comment_data(name, comment, time, 1);
                comment_list_data.add(comment_data);

                adaptador.insertComment(comment_data);
                adaptador.notifyDataSetChanged();

                comment_list.invalidateViews();

                post_text.setText("");

                Intent intent1 = new Intent(view.getContext(), Activity_comments.class);
                Bundle args = new Bundle();
                args.putSerializable("ARRAYLIST", (Serializable) comment_list_data);

                intent1.putExtra("BUNDLE", args);
            }
        });


    }



}
