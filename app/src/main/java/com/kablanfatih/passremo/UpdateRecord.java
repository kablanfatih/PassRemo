package com.kablanfatih.passremo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.tinmegali.security.mcipher.MEncryptor;
import com.tinmegali.security.mcipher.MEncryptorBuilder;
import com.tinmegali.security.mcipher.exceptions.MEncryptorException;

import java.util.Objects;

public class UpdateRecord extends AppCompatActivity {

    private FirebaseAuth mAuth;
    FirebaseUser user;
    FirebaseDatabase database;
    DatabaseReference myRef;
    Button buttonChangePassword;
    EditText editTextNewPassword;
    MEncryptor mEncryptor;

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
                String recordId = Objects.requireNonNull(extras).getString("recordId");
                changePasswordToDatabase(recordId);
            }
        });
    }

    public void changePasswordToDatabase(String recordId){

        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference().child("Records").child(user.getUid()).child(recordId).child("password");

        String ALIAS = "0https4://08digital-9transltr82firebaseio.0com/";
        String newPassword = editTextNewPassword.getText().toString();
        try {
        mEncryptor = new MEncryptorBuilder(ALIAS).build();
        String encryptedPassword = mEncryptor.encryptString(newPassword,this);
            myRef.setValue(encryptedPassword);
        } catch (MEncryptorException e) {
            e.printStackTrace();
        }
    }
}
