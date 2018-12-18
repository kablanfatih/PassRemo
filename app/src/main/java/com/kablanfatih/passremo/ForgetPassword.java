package com.kablanfatih.passremo;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ForgetPassword extends AppCompatActivity {

    FirebaseAuth mAuth;
    Button sendMail;
    Button back;
    EditText email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);
        define();


        sendMail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String mail = email.getText().toString().trim();

                if (TextUtils.isEmpty(mail)) {

                    Toast.makeText(getApplicationContext(), "Lütfen Mail Adresinizi Giriniz", Toast.LENGTH_LONG).show();
                    return;
                }

                mAuth.sendPasswordResetEmail(mail).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

                        if (task.isSuccessful()) {

                            Toast.makeText(getApplicationContext(), "Parola Sıfırlama Maili Gönderildi", Toast.LENGTH_LONG).show();

                            Intent intent = new Intent(ForgetPassword.this, SignIn.class);
                            startActivity(intent);
                            finish();
                        } else {
                            Toast.makeText(getApplicationContext(), "Hata: Mail Gönderilemedi Tekrar Deneyiniz", Toast.LENGTH_LONG).show();
                        }

                    }
                });
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ForgetPassword.this,SignIn.class);
                startActivity(intent);
                finish();
            }
        });
    }

    void define() {

        mAuth = FirebaseAuth.getInstance();
        sendMail = (Button) findViewById(R.id.sendMail);
        back = (Button) findViewById(R.id.back);
        email = (EditText) findViewById(R.id.email);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        return false;
    }
}
