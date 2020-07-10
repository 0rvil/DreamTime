package com.dreamtime.dreamtimearcade;


import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

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

import java.util.Random;

import javax.annotation.Nullable;

public class rpsLogic extends AppCompatActivity implements RewardedVideoAdListener {
    private RewardedVideoAd mRewardedVideoAd;
    String userID;
    FirebaseFirestore fStore;
    FirebaseAuth fAuth;
    String userChoice;
    String ai;
    TextView gameResult, score;
    ImageView playerImage, cpuImage;
    Button adsplaybtn,Scissors_btn,Rock_btn,Paper_btn;
    int playerpoints= 0;
    int cpupoints= 0;
    int rpsHighScore = 0;
    int roundNum =0;
    int lives = 3;
    int currentHighScore;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.rps);
        gameResult = findViewById(R.id.result);
        playerImage = findViewById(R.id.yourImage);
        cpuImage = findViewById(R.id.cpuImage);
        score = findViewById(R.id.score);
        userChoice = "";
        Scissors_btn = findViewById(R.id.scissors_btn);
        Paper_btn = findViewById(R.id.paper_btn);
        Rock_btn = findViewById(R.id.rock_btn);
        adsplaybtn = findViewById(R.id.adsplay);
        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        userID = fAuth.getCurrentUser().getUid();
        final Context context = this;


        DocumentReference documentReference = fStore.collection("users").document(userID);
        documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                Model mod = documentSnapshot.toObject(Model.class);
                int RPS_High_Score = mod.getRps_High_Score();
                currentHighScore =  RPS_High_Score;

            }
        });

        // Use an activity context to get the rewarded video instance.
        mRewardedVideoAd = MobileAds.getRewardedVideoAdInstance(this);
        mRewardedVideoAd.setRewardedVideoAdListener(this);
        loadRewardedVideoAd();

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
    private void loadRewardedVideoAd() {
        mRewardedVideoAd.loadAd("ca-app-pub-2887669441005455/5631228682",
                new AdRequest.Builder().build());
    }
    private void setHighScore() {
        int PlayerOverall = (playerpoints-cpupoints);
        if(PlayerOverall  > currentHighScore){
            currentHighScore = PlayerOverall;
        }
        DocumentReference documentReference = fStore.collection("users").document(userID);
        documentReference.update("Rps_High_Score",currentHighScore);
    }

        public void rockUserChoice(View view){
            userChoice = "r";
            playerImage.setImageResource(R.drawable.rock);
            playgame();
            Random random = new Random();
            int color =  Color.argb(255,random.nextInt(256),random.nextInt(256),random.nextInt(256));
            view.setBackgroundColor(color);

        }
        public void paperUserChoice(View view){
            userChoice = "p";
            playerImage.setImageResource(R.drawable.paper);
            playgame();
            Random random = new Random();
            int color =  Color.argb(255,random.nextInt(256),random.nextInt(256),random.nextInt(256));
            view.setBackgroundColor(color);
        }
        public void scissorsUserChoice(View view){
            userChoice = "s";
            playerImage.setImageResource(R.drawable.scissors);
            playgame();
            Random random = new Random();
            int color =  Color.argb(255,random.nextInt(256),random.nextInt(256),random.nextInt(256));
            view.setBackgroundColor(color);
        }
        public void playgame(){
        int rnd = (int) (Math.random() * 3);
            if(lives == 0){
                adsplaybtn.setVisibility(View.VISIBLE);
                Scissors_btn.setClickable(false);
                Paper_btn.setClickable(false);
                Rock_btn.setClickable(false);
            }
        if (rnd == 0) {
            ai = "r";
            cpuImage.setImageResource(R.drawable.rock);
        } else if (rnd == 1) {
            ai = "p";
            cpuImage.setImageResource(R.drawable.paper);
        } else  {
            ai = "s";
            cpuImage.setImageResource(R.drawable.scissors);
        }if (userChoice.equals(ai)) {
            score.setText("Player: "+ playerpoints + " - "+ cpupoints +":CPU");
            gameResult.setText(String.format(" It's a Tie"));
            roundNum ++;
        } else if (userChoice.equals("r") && ai.equals("s") || userChoice.equals("p") && ai.equals("r") || userChoice.equals("s") && ai.equals("p")) {
            playerpoints ++;
            lives++;
            setHighScore();
            score.setText("Player: "+ playerpoints + " - "+ cpupoints +":CPU");
            gameResult.setText("You Win");
            roundNum ++;

        }
        else{
            lives--;
            cpupoints ++;
            score.setText("Player: "+ playerpoints + " - "+ cpupoints +":CPU");
            gameResult.setText(String.format("You Lose"));
            if(lives == 0){
                adsplaybtn.setVisibility(View.VISIBLE);
                Scissors_btn.setClickable(false);
                Paper_btn.setClickable(false);
                Rock_btn.setClickable(false);
            }
    } } public void resetMethod(){
            userChoice = "";
            gameResult.setText("Ready?");
            cpupoints = 0;
            playerpoints = 0;
            lives =3;
            score.setText("Player:"+ playerpoints + " - "+ cpupoints +":CPU");
            playerImage.setImageResource(R.drawable.question);
            cpuImage.setImageResource(R.drawable.question);
        adsplaybtn.setVisibility(View.INVISIBLE);
        Scissors_btn.setClickable(true);
        Paper_btn.setClickable(true);
        Rock_btn.setClickable(true);
        }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_new_game) {
            resetMethod();
            adsplaybtn.setVisibility(View.INVISIBLE);
            Scissors_btn.setClickable(true);
            Paper_btn.setClickable(true);
            Rock_btn.setClickable(true);
        }
        return super.onOptionsItemSelected(item);
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
        mRewardedVideoAd = MobileAds.getRewardedVideoAdInstance(this);
        mRewardedVideoAd.setRewardedVideoAdListener(this);
        loadRewardedVideoAd();

    }

    @Override
    public void onRewarded(RewardItem rewardItem) {
        Toast.makeText(this, "Rewarded " + rewardItem.getType() + "  amount: " +
                rewardItem.getAmount(), Toast.LENGTH_SHORT).show();
        adsplaybtn.setVisibility(View.INVISIBLE);
        Scissors_btn.setClickable(true);
        Paper_btn.setClickable(true);
        Rock_btn.setClickable(true);
        lives = 3;

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

    public void playAds(View view) {
        if (mRewardedVideoAd.isLoaded()) {
            mRewardedVideoAd.show();
        }
    }
}