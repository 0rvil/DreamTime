package com.dreamtime.dreamtimearcade;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;

public class StartActivity extends AppCompatActivity {

    FirebaseAuth fAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        fAuth = FirebaseAuth.getInstance();

        if(fAuth.getCurrentUser() != null){
            startActivity(new Intent(getApplicationContext(),MenuActivity.class));
            finish();
        }
    }
    public void login(View view){
        Intent logIntent = new Intent(StartActivity.this, login.class);
        startActivity(logIntent);
    }

    public void register(View view){
        Intent regIntent = new Intent(StartActivity.this, register.class);
        startActivity(regIntent);
    }

}
