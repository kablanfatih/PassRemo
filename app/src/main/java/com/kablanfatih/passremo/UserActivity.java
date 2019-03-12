package com.kablanfatih.passremo;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
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

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class UserActivity extends AppCompatActivity {

    FirebaseAuth mAuth;
    FirebaseUser user;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference myRef;
    RecyclerView recyclerView;
    FloatingActionButton addButton;
    ArrayList<ListPassword> listPassword;
    ListAdapter listAdapter;
    EncryptionService encryptionService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        createRecyclerView();
        define();
        createPassword();
        getDataFromFirebase();

        if (user == null) {

            Intent intent = new Intent(UserActivity.this, SignIn.class);
            startActivity(intent);
            finish();
        }
    }

    void define() {

        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
        firebaseDatabase = FirebaseDatabase.getInstance();
        myRef = firebaseDatabase.getReference();
        addButton = (FloatingActionButton) findViewById(R.id.addButton);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        encryptionService = new EncryptionService();

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
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
    }

    private void createPassword() {

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(UserActivity.this, AddPassToList.class);
                startActivity(intent);
            }
        });
    }

    public void getDataFromFirebase() {

        myRef = firebaseDatabase.getReference();
        myRef.child("Records").child(user.getUid()).addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                listPassword = new ArrayList<ListPassword>();
                String email = user.getEmail();

                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    ListPassword getRecord = ds.getValue(ListPassword.class);

                    assert getRecord != null;
                    String password = getRecord.getPassword();

                    String decrypted = null;
                    try {
                        decrypted = encryptionService.decrypt(password, email);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    getRecord.setPassword(decrypted);
                    listPassword.add(getRecord);
                }
                listAdapter = new ListAdapter(UserActivity.this, listPassword);
                recyclerView.setAdapter(listAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }

    public void deleteRecord(String recordId) {

        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
        firebaseDatabase = FirebaseDatabase.getInstance();
        myRef = firebaseDatabase.getReference().child("Records").child(user.getUid()).child(recordId);
        myRef.removeValue();
    }
}