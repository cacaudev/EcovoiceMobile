package com.example.cacau2.ecovoicetest;

import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class Activity_detail extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        Typeface iconFont = FontManager.getTypeface(getApplicationContext(), FontManager.FONTAWESOME);
        Typeface bootsicon = FontManager.getTypeface(getApplicationContext(),FontManager.BOOTSTRAP);

        FontManager.markAsIconContainer(findViewById(R.id.photo_icon_text), iconFont);
        FontManager.markAsIconContainer(findViewById(R.id.disease_icon_text), iconFont);
        FontManager.markAsIconContainer(findViewById(R.id.planting_icon_text), bootsicon);
        FontManager.markAsIconContainer(findViewById(R.id.risk_icon_text), iconFont);
        FontManager.markAsIconContainer(findViewById(R.id.curiosity_icon_text), iconFont);
        FontManager.markAsIconContainer(findViewById(R.id.death_icon_text), iconFont);
        FontManager.markAsIconContainer(findViewById(R.id.measurement_icon_text), iconFont);
        FontManager.markAsIconContainer(findViewById(R.id.comment_icon_text), iconFont);

    }
}
