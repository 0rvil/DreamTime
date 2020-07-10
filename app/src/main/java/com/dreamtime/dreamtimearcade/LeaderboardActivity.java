package com.dreamtime.dreamtimearcade;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Collections;


public class LeaderboardActivity extends AppCompatActivity {
     ArrayList<String> usersInfo = new ArrayList<>();
     ArrayList<Integer> userHS = new ArrayList<>();

    FirebaseFirestore fStore = FirebaseFirestore.getInstance();
    FirebaseAuth fAuth= FirebaseAuth.getInstance();

    ListView lv;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leaderboard);

        lv = findViewById(R.id.leaderboardlist);


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
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.leaderboardmenu, menu);
        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_new_game) {
            displayMethod();
        }
        return super.onOptionsItemSelected(item);
    }

    private void displayMethod() {
        CollectionReference cr = fStore.collection("users");
        cr.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots){
                    Model mod = documentSnapshot.toObject(Model.class);
                    String Name = mod.getName();
                    int total = mod.getHigh_Score();
                    Integer.toString(total);
                    usersInfo.add("Name:  "+Name +"      Score:  " + total);
                    userHS.add(total);
                }
                Collections.sort(userHS, Collections.reverseOrder());
                System.out.println(usersInfo);


            }
        });
        ArrayAdapter<String> adapter = new ArrayAdapter(this,android.R.layout.simple_list_item_1,usersInfo);
        lv.setAdapter(adapter);
        System.out.println(usersInfo);

    }


}