package com.kablanfatih.passremo;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class UserActivity extends AppCompatActivity {

    FirebaseAuth mAuth;
    FirebaseUser user;
    RecyclerView recyclerView;
    FloatingActionButton addButton;
    LinearLayout passwordInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        createRecyclerView();
        define();
        createPassword();

        if (user == null) {

            Intent intent = new Intent(UserActivity.this, SignIn.class);
            startActivity(intent);
            finish();
        }
    }

    void define() {

        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
        addButton = (FloatingActionButton) findViewById(R.id.addButton);
        passwordInfo = (LinearLayout) findViewById(R.id.password_info);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerview);
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
                                Intent signOut = new Intent(UserActivity.this, SignIn.class);
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

    private void createRecyclerView() {

        recyclerView = (RecyclerView) findViewById(R.id.recyclerview);

        ListAdapter listAdapter = new ListAdapter(this, ListPassword.getData());
        recyclerView.setAdapter(listAdapter);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);

    }

    private void createPassword() {

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(UserActivity.this,AddPassToList.class);
                startActivity(intent);
            }
        });
    }
}

