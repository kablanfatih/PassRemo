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
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SignUp extends AppCompatActivity {
    FirebaseAuth mAuth;
    FirebaseUser user;
    EditText gmail, password;
    Button signUp;
    Button back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_up);

        define();

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userName = gmail.getText().toString();
                String pass = password.getText().toString();

                if (!userName.isEmpty() && !pass.isEmpty()) {
                    signUp(userName, pass);
                } else
                    Toast.makeText(SignUp.this, "Lütfen Boşlukları Doldurunuz", Toast.LENGTH_SHORT).show();
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(SignUp.this, SignIn.class);
                startActivity(intent);
                finish();

            }
        });
    }

    private void signUp(String uName, String pass) {


        mAuth.createUserWithEmailAndPassword(uName, pass).addOnCompleteListener(SignUp.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if (task.isSuccessful()) {
                    Toast.makeText(SignUp.this, "Kayıt Başarılı", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(SignUp.this, SignIn.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                    startActivity(intent);


                } else {
                    Toast.makeText(SignUp.this, "HATA OLUŞTU", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    void define() {

        gmail = (EditText) findViewById(R.id.gmail);
        password = (EditText) findViewById(R.id.password);
        signUp = (Button) findViewById(R.id.signUp);
        back = (Button) findViewById(R.id.back);
        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        return false;
    }

}
