package com.kablanfatih.passremo;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SignIn extends AppCompatActivity {

    FirebaseAuth mAuth;
    EditText gmail, password;
    Button signIn;
    Button back;
    TextView forgetPassword;


    SharedPreferences loginPreferences;
    SharedPreferences.Editor loginPrefsEditor;
    CheckBox rememberMe;
    boolean remember;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        define();

        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String gmailText = gmail.getText().toString();
                gmail.setText("");
                String passwordText = password.getText().toString();
                password.setText("");

                if (!gmailText.equals("") && !passwordText.equals("")) {
                    signIn(gmailText, passwordText);
                }
            }
        });

        forgetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignIn.this, ForgetPassword.class);
                startActivity(intent);
                finish();
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(SignIn.this,MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private void signIn(final String gmail, final String password) {
        mAuth.signInWithEmailAndPassword(gmail, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if (task.isSuccessful()) {

                    if (rememberMe.isChecked()) {
                        loginPrefsEditor.putBoolean("remember", true);
                        loginPrefsEditor.putString("username", String.valueOf(gmail));
                        loginPrefsEditor.putString("password", String.valueOf(password));
                        loginPrefsEditor.commit();
                    } else {
                        loginPrefsEditor.clear();
                        loginPrefsEditor.commit();
                    }
                    Intent intent = new Intent(SignIn.this, UserActivity.class);
                    startActivity(intent);
                    finish();

                } else {
                    Toast.makeText(getApplicationContext(), "Kullanıcı E-posta veya Parola Hatalı", Toast.LENGTH_LONG).show();

                }

            }
        });

    }

    void define() {
        gmail = (EditText) findViewById(R.id.userEmail);
        password = (EditText) findViewById(R.id.userPass);
        signIn = (Button) findViewById(R.id.signin);
        back = (Button) findViewById(R.id.back);
        mAuth = FirebaseAuth.getInstance();
        forgetPassword = (TextView) findViewById(R.id.forgetPassword);
        rememberMe = (CheckBox) findViewById(R.id.rememberMe);

        loginPreferences = getSharedPreferences("loginPrefs", MODE_PRIVATE);
        loginPrefsEditor = loginPreferences.edit();
        remember = loginPreferences.getBoolean("remember", false);
        if (remember == true) {
            gmail.setText(loginPreferences.getString("username", ""));
            password.setText(loginPreferences.getString("password", ""));
            rememberMe.setChecked(true);
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        return false;

    }
}
