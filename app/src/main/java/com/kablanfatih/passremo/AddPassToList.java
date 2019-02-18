package com.kablanfatih.passremo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AddPassToList extends AppCompatActivity {

    Button savePassword, backList;
    EditText adress, username, password;
    private FirebaseAuth mAuth;
    FirebaseDatabase database;
    DatabaseReference myRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_pass_to_list);

        init();
    }

    public void init() {

        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference();
        savePassword = (Button) findViewById(R.id.savepassword);
        backList = (Button) findViewById(R.id.backlist);
        adress = (EditText) findViewById(R.id.adress);
        username = (EditText) findViewById(R.id.username);
        password = (EditText) findViewById(R.id.password);

        savePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                writeInfoToDatabase();
            }
        });

        backList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddPassToList.this, UserActivity.class);
                startActivity(intent);
            }
        });
    }

    public void writeInfoToDatabase() {
        String recordId = myRef.push().getKey();
        FirebaseUser user = mAuth.getCurrentUser();
        assert user != null;
        String userEmail = user.getEmail();
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("Records").child(user.getUid());
        ListPassword listPassword = new ListPassword();
        listPassword.setRecordId(recordId);
        listPassword.setEmail(userEmail);
        listPassword.setTitle(adress.getText().toString());
        listPassword.setName(username.getText().toString());
        listPassword.setPassword(password.getText().toString());

        if (!listPassword.getTitle().isEmpty() && !listPassword.getName().isEmpty() && !listPassword.getPassword().isEmpty()) {

            myRef.child(recordId).setValue(listPassword);

            Toast.makeText(getApplicationContext(), "Parolanız Listenize Eklenmiştir", Toast.LENGTH_LONG).show();
            adress.setText("");
            username.setText("");
            password.setText("");

        } else {
            Toast.makeText(getApplicationContext(), "Lütfen Boşlukları Doldurunuz", Toast.LENGTH_LONG).show();
        }
    }
}
