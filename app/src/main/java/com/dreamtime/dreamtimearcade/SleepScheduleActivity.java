package com.dreamtime.dreamtimearcade;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ListView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class SleepScheduleActivity extends AppCompatActivity {

    String[] dayOfTheWeek = { "Monday","Tuesday","Wednesday","Thursday","Friday","Saturday","Sunday", };
    ListView listView;
    final Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sleep_schedule);

        ScheduleAdapter adapter = new ScheduleAdapter(this, dayOfTheWeek);
        listView = findViewById(R.id.schedule);
        listView.setAdapter(adapter);
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
}
