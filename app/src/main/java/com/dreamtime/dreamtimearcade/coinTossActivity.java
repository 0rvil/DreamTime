package com.dreamtime.dreamtimearcade;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.util.Random;

public class coinTossActivity extends AppCompatActivity {
    private ImageView coin;
    public static final Random RANDOM = new Random();
    public String userChoice;
    public int userScore;
    public int currentHighScore;
    public TextView result, userResult;
    String userID;
    FirebaseFirestore fStore;
    FirebaseAuth fAuth;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coin_toss);

        coin = findViewById(R.id.imageView);
        result = findViewById(R.id.cointossresult);
        userResult = findViewById(R.id.userResult);
        userScore = 0;
        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        userID = fAuth.getCurrentUser().getUid();


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
        DocumentReference documentReference = fStore.collection("users").document(userID);
        documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                Model mod = documentSnapshot.toObject(Model.class);
                int highScore = mod.getCoin_Toss_High_Score();
                currentHighScore = highScore;
                System.out.println(currentHighScore);
            }
        });

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_new_game) {
            newGame();
        }
        return super.onOptionsItemSelected(item);
    }

    private void newGame() {
        userScore = 0;
        userResult.setText("Score:"+userScore);
        result.setText("Ready?");
        coin.setImageResource(R.drawable.question);

    }

    public void flipcointails(android.view.View view) {
        Random random = new Random();
        int color =  Color.argb(255,random.nextInt(256),random.nextInt(256),random.nextInt(256));
        view.setBackgroundColor(color);
        userChoice = "t";
        Animation fadeout = new AlphaAnimation(1, 0);
        fadeout.setInterpolator(new AccelerateInterpolator());
        fadeout.setDuration(1000);
        fadeout.setFillAfter(true);
        fadeout.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                if(RANDOM.nextFloat()> 0.5f){
                    coin.setImageResource(R.drawable.coinhead);
                    result.setText("Heads win");
                    setHighScores();
                } else {coin.setImageResource(R.drawable.cointail);
                    result.setText("Tails win");
                    userScore ++;
                    userResult.setText("Score:"+ userScore);
                    setHighScores();
                }
                Animation fadein = new AlphaAnimation(0, 1);
                fadein.setInterpolator(new AccelerateInterpolator());
                fadein.setDuration(2000);
                fadein.setFillAfter(true);
                coin.startAnimation(fadein);
                setHighScores();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        coin.startAnimation(fadeout);
    }


    public void flipcoinheads(android.view.View view) {
        Random random = new Random();
        int color =  Color.argb(255,random.nextInt(256),random.nextInt(256),random.nextInt(256));
        view.setBackgroundColor(color);
        userChoice = "h";

        Animation fadeout = new AlphaAnimation(1, 0);
        fadeout.setInterpolator(new AccelerateInterpolator());
        fadeout.setDuration(1000);
        fadeout.setFillAfter(true);
        fadeout.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                if(RANDOM.nextFloat()> 0.5f){
                    coin.setImageResource(R.drawable.coinhead);
                    result.setText("Heads win");
                    userScore ++;
                    userResult.setText("Score:"+ userScore);
                    setHighScores();
                } else {coin.setImageResource(R.drawable.cointail);
                    result.setText("Tails win");}
                Animation fadein = new AlphaAnimation(0, 1);
                fadein.setInterpolator(new AccelerateInterpolator());
                fadein.setDuration(2000);
                fadein.setFillAfter(true);
                setHighScores();
                coin.startAnimation(fadein);

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        coin.startAnimation(fadeout);
    }
    public void setHighScores() {
        if(userScore > currentHighScore){
            currentHighScore = userScore;
        }DocumentReference documentReference = fStore.collection("users").document(userID);
        documentReference.update("Coin_Toss_High_Score",currentHighScore);

    }
}