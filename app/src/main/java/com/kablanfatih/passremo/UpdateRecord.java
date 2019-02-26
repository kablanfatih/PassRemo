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

public class UpdateRecord extends AppCompatActivity {

    FirebaseUser user;
    FirebaseDatabase database;
    DatabaseReference myRef;
    Button buttonChangePassword;
    Button buttonBack;
    EditText editTextNewPassword;
    EncryptionService encryptionService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_record);
        init();
    }

    private void init() {
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference();
        buttonChangePassword = (Button) findViewById(R.id.password_confirm);
        buttonBack = (Button) findViewById(R.id.back);
        editTextNewPassword = (EditText) findViewById(R.id.new_password);
        encryptionService = new EncryptionService();

        buttonChangePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle extras = getIntent().getExtras();
                String recordId = Objects.requireNonNull(extras).getString("recordId");
                changePasswordToDatabase(recordId);
            }
        });

        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    public void changePasswordToDatabase(String recordId) {

        myRef = database.getReference().child("Records").child(user.getUid()).child(recordId).child("password");

        String email = user.getEmail();
        String newPassword = editTextNewPassword.getText().toString();

        String encryptedPassword = null;
        try {
            encryptedPassword = encryptionService.encrypt(newPassword, email);
        } catch (Exception e) {
            e.printStackTrace();
        }
        myRef.setValue(encryptedPassword);

        Toast.makeText(getApplicationContext(), "Parolanız Değiştirilmiştir", Toast.LENGTH_SHORT).show();
        editTextNewPassword.setText("");

    }
}
