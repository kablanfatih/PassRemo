package com.kablanfatih.passremo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class UpdateRecord extends AppCompatActivity {

    private FirebaseAuth mAuth;
    FirebaseUser user;
    FirebaseDatabase database;
    DatabaseReference myRef;
    Button buttonChangePassword;
    EditText editTextNewPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_record);
        init();
    }

    private void init() {
        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference();
        buttonChangePassword = (Button) findViewById(R.id.password_confirm);
        editTextNewPassword = (EditText) findViewById(R.id.new_password);

        buttonChangePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle extras = getIntent().getExtras();
                String recordId = extras.getString("recordId");
                Log.i("tag",recordId);
                changePasswordToDatabase(recordId);

            }
        });
    }

    public void changePasswordToDatabase(String recordId) {

        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference().child("Records").child(user.getUid());

        ListPassword listPassword = new ListPassword();
        listPassword.setEmail(user.getEmail());
        listPassword.setTitle("titledeneme");
        listPassword.setName("denmeup");
        listPassword.setPassword("passdeneme");
        listPassword.setRecordId(recordId);
        myRef.child(recordId).setValue(listPassword);

    }
}
