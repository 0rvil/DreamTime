package com.dreamtime.dreamtimearcade;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.ads.MobileAds;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import hotchemi.android.rate.AppRate;
import hotchemi.android.rate.OnClickButtonListener;

import javax.annotation.Nullable;

public class MenuActivity extends AppCompatActivity {

    TextView userName;
    FirebaseFirestore fStore;
    FirebaseAuth fAuth;
    String userID;

    public Toast mToastToShow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        userName = findViewById(R.id.userName);
        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        userID = fAuth.getCurrentUser().getUid();

        MobileAds.initialize(this, "ca-app-pub-2887669441005455~5802619071");

        DocumentReference documentReference = fStore.collection("users").document(userID);
        documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                userName.setText("   Welcome back, " + documentSnapshot.getString("Name"));

            }
        });
        AppRate.with(this)
                .setInstallDays(1)
                .setLaunchTimes(3)
                .setRemindInterval(2)
                .setShowLaterButton(true)
                .setOnClickButtonListener(new OnClickButtonListener() { // callback listener.
                    @Override
                    public void onClickButton(int which) {
                        Log.d(login.class.getName(), Integer.toString(which));
                    }
                })
                .monitor();
        AppRate.showRateDialogIfMeetsConditions(this);
        AppRate.with(this).clearAgreeShowDialog();
               final Context context = this;

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {

                switch (item.getItemId()) {
                    case R.id.action_arcade:
                        Intent intentSettings = new Intent(context, MenuActivity.class);
                        startActivity(intentSettings);
                        finish();
                        break;

                    case R.id.action_home:
                        Intent intentHome = new Intent(context, Menu2.class);
                        startActivity(intentHome);
                        finish();
                        break;

                    case R.id.action_account:
                        Intent intentAccount = new Intent(context, AccountActivity.class);
                        startActivity(intentAccount);
                        finish();
                        break;
                }
                return false;

            }
        });

    }
    public void rps(View view){

        Intent rps = new Intent(this,rpsLogic.class);
        startActivity(rps);
        Toast.makeText(this,"Play Rock Paper Scissors. Rock beats scissors. Scissors beats Paper. Paper beats Rock. You have 3 lives. Each time you win you gain a life. Each time you lose you lose a life. If you run out of lives you can watch a video for 3 lives and continue the game, or you can restart a new game. Aim for the highest score.",Toast.LENGTH_LONG).show();
        finish();
    }
    public void ttt(View view){
        Intent ttt = new Intent(this,MainActivity.class);
        startActivity(ttt);
        Toast.makeText(this,"Line up 3 X's in a row horizontally, vertically or diagonally. You have 3 lives. Each time you win you gain a life. Each time you lose you lose a life. If you run out of lives you can watch a video for 3 lives and continue the game, or you can restart a new game. Aim for the highest score.",Toast.LENGTH_LONG).show();
        finish();
    }
    public void ct(View view){
        Intent ct = new Intent(this,coinTossActivity.class);
        startActivity(ct);
         Toast.makeText(this,"Choose what you think the coin will land on. Either heads or tails.  ",Toast.LENGTH_LONG).show();

        finish();
    }


    public void lb(View view) {


        Intent lb = new Intent(this,LeaderboardActivity.class);
        startActivity(lb);
        Toast.makeText(this,"View others High Scores and your own.",Toast.LENGTH_LONG).show();
        finish();
    }
}
