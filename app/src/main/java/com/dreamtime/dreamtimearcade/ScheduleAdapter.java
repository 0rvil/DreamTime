package com.dreamtime.dreamtimearcade;

import android.app.Activity;
import android.content.Intent;
import android.provider.AlarmClock;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;


public class ScheduleAdapter extends ArrayAdapter {

    private final String[] daysOfTheWeek;
    private final Activity context;

    public ScheduleAdapter(Activity context, String[] daysOfTheWeekParam) {

        super(context, R.layout.home_list_row, daysOfTheWeekParam);
        this.context = context;
        this.daysOfTheWeek = daysOfTheWeekParam;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView = inflater.inflate(R.layout.schedule_items_layout, null, true);

        TextView dayOfWeek = rowView.findViewById(R.id.dayOfTheWeek);
        dayOfWeek.setText(daysOfTheWeek[position]);
        final int day = position;

        final EditText wakeUpHour = rowView.findViewById(R.id.wakeUpHour);
        final EditText wakeUpMinute = rowView.findViewById(R.id.wakeUpMinute);
        final Switch Switch = rowView.findViewById(R.id.amOrPm);

        Button wakeUpButton = rowView.findViewById(R.id.wakeUpButton);
        wakeUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String hour = wakeUpHour.getText().toString();
                String minute = wakeUpMinute.getText().toString();
                boolean Pm;
                Pm = Switch.isChecked();

                final String showText = "Wake Up";
                createAlarmsForTheWeek(hour,minute,Pm, day);
            }
        });

        final EditText napTimeHour = rowView.findViewById(R.id.napTimeHour);
        final EditText napTimeMinute = rowView.findViewById(R.id.napTimeMinute);
        final Switch Switch1  = rowView.findViewById(R.id.NapAmOrPm);

        Button napTimeButton = rowView.findViewById(R.id.napTimeButton);
        napTimeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String hour = napTimeHour.getText().toString();
                String minute = napTimeMinute.getText().toString();
                boolean Pm;
                Pm = Switch1.isChecked();
                final String showText = "Nap Time";
                createAlarmsForTheWeek(hour,minute,Pm, day);
            }
        });

        final EditText sleepTimeHour = rowView.findViewById(R.id.sleepTimeHour);
        final EditText sleepTimeMinute = rowView.findViewById(R.id.sleepTimeMinute);
        final Switch Switch2 = rowView.findViewById(R.id.SleepAmOrPm);
        Button sleepTimeButton = rowView.findViewById(R.id.sleepTimeButton);
        sleepTimeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String hour = sleepTimeHour.getText().toString();
                String minute = sleepTimeMinute.getText().toString();
                boolean Pm;
                Pm = Switch2.isChecked();
                final String showText = "Sleep Time";
                createAlarmsForTheWeek(hour,minute,Pm, day);
            }
        });

        return rowView;

    }


    void createAlarmsForTheWeek(String hourText, String minuteText, boolean PM, int position){

        String hour = hourText;
        String minute= minuteText;
        boolean pmam = PM;
        Intent i = new Intent(AlarmClock.ACTION_SET_ALARM);


        if (pmam == true) {


                i.putExtra(AlarmClock.EXTRA_HOUR, Integer.parseInt(hour));
                i.putExtra(AlarmClock.EXTRA_MINUTES,Integer.parseInt(minute));

                switch (position) {
                    case 0:
                        ArrayList<Integer> list = new ArrayList<>();
                        list.add(Calendar.MONDAY);
                        i.putExtra(AlarmClock.EXTRA_DAYS, list);
                        i.putExtra(AlarmClock.EXTRA_MESSAGE, "Monday");

                        break;
                }
                switch (position) {
                    case 1:
                        ArrayList<Integer> list = new ArrayList<>();
                        list.add(Calendar.TUESDAY);
                        i.putExtra(AlarmClock.EXTRA_DAYS, list);
                        i.putExtra(AlarmClock.EXTRA_MESSAGE, "Tuesday");

                        break;
                }
                switch (position) {
                    case 2:
                        ArrayList<Integer> list = new ArrayList<>();
                        list.add(Calendar.WEDNESDAY);
                        i.putExtra(AlarmClock.EXTRA_DAYS, list);
                        i.putExtra(AlarmClock.EXTRA_MESSAGE, " Wednesday");

                        break;
                }
                switch (position) {
                    case 3:
                        ArrayList<Integer> list = new ArrayList<>();
                        list.add(Calendar.THURSDAY);
                        i.putExtra(AlarmClock.EXTRA_DAYS, list);
                        i.putExtra(AlarmClock.EXTRA_MESSAGE, " Thursday");

                        break;
                }
                switch (position) {
                    case 4:
                        ArrayList<Integer> list = new ArrayList<>();
                        list.add(Calendar.FRIDAY);
                        i.putExtra(AlarmClock.EXTRA_DAYS, list);
                        i.putExtra(AlarmClock.EXTRA_MESSAGE, " Friday");

                        break;
                }
                switch (position) {
                    case 5:
                        ArrayList<Integer> list = new ArrayList<>();
                        list.add(Calendar.SATURDAY);
                        i.putExtra(AlarmClock.EXTRA_DAYS, list);
                        i.putExtra(AlarmClock.EXTRA_MESSAGE, " Saturday");
                        break;

                }
                switch (position) {
                    case 6:
                        ArrayList<Integer> list = new ArrayList<>();
                        list.add(Calendar.SUNDAY);
                        i.putExtra(AlarmClock.EXTRA_DAYS, list);
                        i.putExtra(AlarmClock.EXTRA_MESSAGE, " Sunday");

                        break;

                }


                context.startActivity(i);
            }
            else {
            if (pmam == false ) {
                i.putExtra(AlarmClock.EXTRA_HOUR, Integer.parseInt(hour) + 12);
                i.putExtra(AlarmClock.EXTRA_MINUTES,Integer.parseInt(minute));
                ArrayList<Integer> list = new ArrayList<>();

                switch (position) {
                    case 0:
                        ArrayList<Integer> list2 = new ArrayList<>();
                        list.add(Calendar.MONDAY);
                        i.putExtra(AlarmClock.EXTRA_DAYS, list);
                        i.putExtra(AlarmClock.EXTRA_MESSAGE, " Monday");

                        break;
                }
                switch (position) {
                    case 1:
                        ArrayList<Integer> list2 = new ArrayList<>();
                        list.add(Calendar.TUESDAY);
                        i.putExtra(AlarmClock.EXTRA_DAYS, list);
                        i.putExtra(AlarmClock.EXTRA_MESSAGE, " Tuesday");

                        break;
                }
                switch (position) {
                    case 2:
                        ArrayList<Integer> list2 = new ArrayList<>();
                        list.add(Calendar.WEDNESDAY);
                        i.putExtra(AlarmClock.EXTRA_DAYS, list);
                        i.putExtra(AlarmClock.EXTRA_MESSAGE, " Wednesday");

                        break;
                }
                switch (position) {
                    case 3:
                        ArrayList<Integer> list2 = new ArrayList<>();
                        list.add(Calendar.THURSDAY);
                        i.putExtra(AlarmClock.EXTRA_DAYS, list);
                        i.putExtra(AlarmClock.EXTRA_MESSAGE, "Thursday");

                        break;
                }
                switch (position) {
                    case 4:
                        ArrayList<Integer> list2 = new ArrayList<>();
                        list.add(Calendar.FRIDAY);
                        i.putExtra(AlarmClock.EXTRA_DAYS, list);
                        i.putExtra(AlarmClock.EXTRA_MESSAGE, " Friday");

                        break;
                }
                switch (position) {
                    case 5:
                        ArrayList<Integer> list2 = new ArrayList<>();
                        list.add(Calendar.SATURDAY);
                        i.putExtra(AlarmClock.EXTRA_DAYS, list);
                        i.putExtra(AlarmClock.EXTRA_MESSAGE, " Saturday");
                        break;
                }
                switch (position) {
                    case 6:
                        ArrayList<Integer> list2 = new ArrayList<>();
                        list.add(Calendar.SUNDAY);
                        i.putExtra(AlarmClock.EXTRA_DAYS, list);
                        i.putExtra(AlarmClock.EXTRA_MESSAGE, " Sunday");
                        break;
                }
                context.startActivity(i);
            }
        }
    }
}
