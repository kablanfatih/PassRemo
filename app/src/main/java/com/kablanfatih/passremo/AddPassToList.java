package com.kablanfatih.passremo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class AddPassToList extends AppCompatActivity {

    Button savePassword, backList;
    EditText adress, username, password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_pass_to_list);

        init();

        savePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("BİLGİ",adress.getText().toString());
                Log.i("BİLGİ", username.getText().toString());
                Log.i("BİLGİ", password.getText().toString());
            }
        });

        backList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddPassToList.this,UserActivity.class);
                startActivity(intent);
             }
        });
    }

    public void init() {

        savePassword = (Button) findViewById(R.id.savepassword);
        backList = (Button) findViewById(R.id.backlist);
        adress = (EditText) findViewById(R.id.adress);
        username = (EditText) findViewById(R.id.username);
        password = (EditText) findViewById(R.id.password);
    }
}
