package com.example.cacau2.ecovoicetest;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class Activity_validate_and_change_password extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_validate_and_change_password);
        getSupportActionBar().hide();
    }
    public void change_password(View view){
        if(validate()) {
            Intent intent = new Intent(getBaseContext(), Activity_register_new_password.class);
            startActivity(intent);
            finish();
        }else{
            Toast.makeText(getBaseContext(), R.string.invalid_key, Toast.LENGTH_SHORT).show();
        }
    }
    public boolean validate(){
        EditText key = findViewById(R.id.validation_key);

        if(key.getText().toString().equals("igor")){
            return true;
        }
        return  false;
    }
}
