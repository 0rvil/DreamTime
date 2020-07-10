package com.dreamtime.dreamtimearcade;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.reward.RewardItem;
import com.google.android.gms.ads.reward.RewardedVideoAd;
import com.google.android.gms.ads.reward.RewardedVideoAdListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import javax.annotation.Nullable;

public class AccountActivity extends AppCompatActivity  implements RewardedVideoAdListener {
    private RewardedVideoAd mRewardedVideoAd;
    FirebaseFirestore fStore;
    FirebaseAuth fAuth;
    String userID;
    TextView userName,userEmail,userCtHighScore,userRpsHighScore,usertttHighScore;
    public int userRPSHIGHSCORE;
    static int total;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);
        userName = findViewById(R.id.name);
        userEmail = findViewById(R.id.email);
        userCtHighScore = findViewById(R.id.ctHighScore);
        userRpsHighScore = findViewById(R.id.rpsHighScore);
        usertttHighScore = findViewById(R.id.tttHighScore);

        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        userID = fAuth.getCurrentUser().getUid();

        mRewardedVideoAd = MobileAds.getRewardedVideoAdInstance(this);
        mRewardedVideoAd.setRewardedVideoAdListener(this);
        loadRewardedVideoAd();



        final DocumentReference documentReference = fStore.collection("users").document(userID);
        documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                Model mod = documentSnapshot.toObject(Model.class);
                userName.setText("   Name: " + documentSnapshot.getString("Name"));
                userRPSHIGHSCORE = mod.getRps_High_Score();
                int TicTacToe_High_Score = mod.getTicTacToe_High_Score();
                int Coin_Toss_High_Score = mod.getCoin_Toss_High_Score();
                int Rps_High_Score = mod.getRps_High_Score();
                int total = (Rps_High_Score+Coin_Toss_High_Score+TicTacToe_High_Score);
                documentReference.update("High_Score", total);
                userCtHighScore.setText("   Coin Toss High Score:  "+documentSnapshot.get("Coin_Toss_High_Score"));
                userRpsHighScore.setText("   Rock Paper Scissors High Score:  "+ documentSnapshot.get("Rps_High_Score"));
                usertttHighScore.setText("   Tic Tac Toe High Score:  " + documentSnapshot.get("TicTacToe_High_Score"));

            }
        });

        DocumentReference documentReference2 = fStore.collection("users").document(userID);
        documentReference2.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                userEmail.setText("   Email: " + documentSnapshot.getString("Email"));
            }
        });

        final Context context = this;
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {

                switch (item.getItemId()) {
                    case R.id.action_arcade:
                        Intent intentSettings = new Intent(context, MenuActivity.class);
                        startActivity(intentSettings);
                        break;

                    case R.id.action_home:
                        Intent intentHome = new Intent(context, Menu2.class);
                        startActivity(intentHome);
                        break;

                    case R.id.action_account:
                        Intent intentAccount = new Intent(context, AccountActivity.class);
                        startActivity(intentAccount);
                        break;
                }
                return false;
            }
        });
    }
    private void loadRewardedVideoAd() {
        mRewardedVideoAd.loadAd("ca-app-pub-2887669441005455/7280643279",
                new AdRequest.Builder().build());
    }
    public void logout(View view) {
        FirebaseAuth.getInstance().signOut();//logout
        FirebaseFirestore.getInstance().disableNetwork();//Terminate Network with FirebaseFirestore
        startActivity(new Intent(getApplicationContext(), StartActivity.class));
        finish();

    }

    public void emailUs(View view) {
        final Intent emailIntent = new Intent(android.content.Intent.ACTION_SEND);
        emailIntent.setType("plain/text");
        emailIntent.putExtra(android.content.Intent.EXTRA_EMAIL, new String[]{"dreamtimeus@gmail.com"});
        emailIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Subject");
        emailIntent.putExtra(android.content.Intent.EXTRA_TEXT, "Text");
        startActivity(Intent.createChooser(emailIntent, "Send mail..."));
    }

    public void followUs(View view) {
        Intent instagram = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.instagram.com/officialdreamtime/"));
        startActivity(instagram);
    }

    public void privacy(View view) {
        Intent privacy = new Intent(Intent.ACTION_VIEW, Uri.parse("https://elivro.github.io/DreamTimePrivacyPolicy/"));
        startActivity(privacy);
    }

    public void terms(View view) {
        Intent privacy = new Intent(Intent.ACTION_VIEW, Uri.parse("https://elivro.github.io/DreamTimeTermsAndConditions/"));
        startActivity(privacy);
    }

    @Override
    public void onRewardedVideoAdLoaded() {

    }

    @Override
    public void onRewardedVideoAdOpened() {

    }

    @Override
    public void onRewardedVideoStarted() {

    }

    @Override
    public void onRewardedVideoAdClosed() {

    }

    @Override
    public void onRewarded(RewardItem rewardItem) {
        userRPSHIGHSCORE = userRPSHIGHSCORE + 3;
        DocumentReference documentReference3 = fStore.collection("users").document(userID);
        documentReference3.update("Rps_High_Score",userRPSHIGHSCORE);


    }

    @Override
    public void onRewardedVideoAdLeftApplication() {
        onDestroy();
    }

    @Override
    public void onRewardedVideoAdFailedToLoad(int i) {

    }

    @Override
    public void onRewardedVideoCompleted() {

        mRewardedVideoAd = MobileAds.getRewardedVideoAdInstance(this);
        mRewardedVideoAd.setRewardedVideoAdListener(this);
        loadRewardedVideoAd();

    }
    @Override
    public void onResume() {
        mRewardedVideoAd.resume(this);
        super.onResume();
    }

    @Override
    public void onPause() {
        mRewardedVideoAd.pause(this);
        super.onPause();
    }

    @Override
    public void onDestroy() {
        mRewardedVideoAd.destroy(this);
        super.onDestroy();
    }

    public void adsPlay(View view) {
        if (mRewardedVideoAd.isLoaded()) {
            mRewardedVideoAd.show();
        }
    }
}
