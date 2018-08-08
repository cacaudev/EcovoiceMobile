package com.example.cacau2.ecovoicetest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.HorizontalScrollView;
import android.widget.ScrollView;
import android.widget.Toast;

public class Activity_feedback extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getSupportActionBar().hide();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);

        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN  |WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE );
        ScrollView scrollView = this.getWindow().getDecorView().findViewById(R.id.scroll_feedback);



    }
    public void close_feedback(View v){
        finish();
    }


}
