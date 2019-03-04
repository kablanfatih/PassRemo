package com.kablanfatih.passremo;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

    private FirebaseAuth mAuth;
    EditText email, password;
    Button signIn;
    TextView register;
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
    }

    private void signIn(final String email, final String password) {
        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if (task.isSuccessful()) {

                    if (rememberMe.isChecked()) {
                        loginPrefsEditor.putBoolean("remember", true);
                        loginPrefsEditor.putString("username", String.valueOf(email));
                        loginPrefsEditor.putString("password", String.valueOf(password));
                    }
                    loginPrefsEditor.clear();
                    loginPrefsEditor.commit();

                    Intent intent = new Intent(SignIn.this, UserActivity.class);
                    startActivity(intent);
                    finish();

                } else {
                    Toast.makeText(getApplicationContext(), "Kullanıcı E-posta veya Parola Hatalı", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

   @SuppressLint("CommitPrefEdits")
   private void define() {
        email = (EditText) findViewById(R.id.userEmail);
        password = (EditText) findViewById(R.id.userPass);
        signIn = (Button) findViewById(R.id.signin);
        register = (TextView) findViewById(R.id.register);
        back = (Button) findViewById(R.id.back);
        mAuth = FirebaseAuth.getInstance();
        forgetPassword = (TextView) findViewById(R.id.forgetPassword);
        rememberMe = (CheckBox) findViewById(R.id.rememberMe);

        loginPreferences = getSharedPreferences("loginPrefs", MODE_PRIVATE);
        loginPrefsEditor = loginPreferences.edit();
        remember = loginPreferences.getBoolean("remember", false);
        if (remember) {
            email.setText(loginPreferences.getString("username", ""));
            password.setText(loginPreferences.getString("password", ""));
            rememberMe.setChecked(true);
        }

       signIn.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               String emailText = email.getText().toString();
               email.setText("");
               String passwordText = password.getText().toString();
               password.setText("");

               if (!emailText.equals("") && !passwordText.equals("")) {
                   signIn(emailText, passwordText);
               }
           }
       });

       register.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               Intent intent = new Intent(SignIn.this, SignUp.class);
               intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
               startActivity(intent);
               finish();
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
               finish();
           }
       });
    }
}
