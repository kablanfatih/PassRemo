package com.kablanfatih.passremo;

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

import java.util.Objects;

public class AddPassToList extends AppCompatActivity {

    Button savePassword, backList;
    EditText adress, username, password;
    FirebaseDatabase database;
    DatabaseReference myRef;
    EncryptionService encryptionService;
    FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_pass_to_list);

        init();
    }

    public void init() {

        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference();
        user = mAuth.getCurrentUser();
        savePassword = (Button) findViewById(R.id.savepassword);
        backList = (Button) findViewById(R.id.backlist);
        adress = (EditText) findViewById(R.id.adress);
        username = (EditText) findViewById(R.id.username);
        password = (EditText) findViewById(R.id.password);
        encryptionService = new EncryptionService();

        savePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    writeInfoToDatabase();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        backList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    public void writeInfoToDatabase() throws Exception {

        String getPassword = password.getText().toString();
        String userEmail = Objects.requireNonNull(user).getEmail();
        String encryptedPassword = encryptionService.encrypt(getPassword,userEmail);

        String recordId = myRef.push().getKey();
        myRef = database.getReference("Records").child(user.getUid());
        ListPassword listPassword = new ListPassword();
        listPassword.setRecordId(recordId);
        listPassword.setEmail(userEmail);
        listPassword.setTitle(adress.getText().toString());
        listPassword.setName(username.getText().toString());
        listPassword.setPassword(encryptedPassword);

        if (!listPassword.getTitle().isEmpty() && !listPassword.getName().isEmpty() && !password.getText().toString().isEmpty()) {

            myRef.child(Objects.requireNonNull(recordId)).setValue(listPassword);

            Toast.makeText(getApplicationContext(), "Parolanız Listenize Eklenmiştir", Toast.LENGTH_LONG).show();
            adress.setText("");
            username.setText("");
            password.setText("");

        } else {
            Toast.makeText(getApplicationContext(), "Lütfen Boşlukları Doldurunuz", Toast.LENGTH_LONG).show();
        }
    }
}