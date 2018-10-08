package com.kablanfatih.passremo;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.CountDownTimer;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.security.Timestamp;
import java.util.TimerTask;

import static java.lang.Thread.sleep;

public class UserActivity extends AppCompatActivity {

    FirebaseAuth mAuth;
    FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        define();

        if (user == null) {

            Intent intent = new Intent(UserActivity.this, SignIn.class);
            startActivity(intent);
            finish();
        }
    }

    void define() {

        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
    }

    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.setting, menu);
        return super.onCreateOptionsMenu(menu);

    }

    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.pass_change:
                Intent passChange = new Intent(UserActivity.this, PassChange.class);
                startActivity(passChange);
                finish();
                return true;

            case R.id.sign_out:
                new AlertDialog.Builder(this)
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setMessage("Çıkmak İstediğinize Emin misiniz?")
                        .setPositiveButton("Evet", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                mAuth.signOut();
                                Intent signOut = new Intent(UserActivity.this, MainActivity.class);
                                startActivity(signOut);
                                finish();
                            }
                        })
                        .setNegativeButton("Hayır", null)
                        .show();

                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public boolean onKeyDown(final int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK) {

            new AlertDialog.Builder(this)
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .setMessage("Çıkmak İstediğinize Emin misiniz?")
                    .setPositiveButton("Evet", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent = new Intent(UserActivity.this, SignIn.class);
                            startActivity(intent);
                            finish();
                        }
                    })
                    .setNegativeButton("Hayır", null)
                    .show();
        }
        return super.onKeyDown(keyCode, event);

    }
/*
    @Override
    protected void onStop() {

        new CountDownTimer(5000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {

            }

            @Override
            public void onFinish() {

                finish();
            }
        }.start();

        super.onStop();
    }

   /* @Override
    protected void onUserLeaveHint() {

        finish();
    }*/

}

