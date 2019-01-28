package com.kablanfatih.passremo;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class PassChange extends AppCompatActivity {

    private FirebaseAuth mAuth;
    FirebaseUser user;
    Button confirmPass;
    Button back;
    EditText newPass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pass_change);

        define();

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PassChange.this, UserActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    void define() {

        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
        confirmPass = (Button) findViewById(R.id.pass_confirm);
        back = (Button) findViewById(R.id.back);
        newPass = (EditText) findViewById(R.id.pass_new);
        confirmPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                changePassword(newPass.getText().toString());
            }
        });
    }


    void changePassword(String newPass) {

        user.updatePassword(newPass).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {

                if (task.isSuccessful()) {

                    Intent intent = new Intent(PassChange.this, SignIn.class);
                    startActivity(intent);
                    finish();
                    Toast.makeText(getApplicationContext(), "Şifreniz Değiştirilmiştir", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplicationContext(), "Lütfen En Az 6 Haneli Bir Şifre Girin", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        return false;
    }
}
