package com.example.wellness360;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class Notificationdisplay extends AppCompatActivity {
    private LinearLayout remindersLayout;
    private List<Reminder> remindersList;
    private String reminderMessage = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notificationdisplay);

        remindersLayout = findViewById(R.id.remindersLayout);
        remindersList = new ArrayList<>();

        Button setReminderButton = findViewById(R.id.setreminder);
        setReminderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showReminderDialog();
            }
        });
    }
    private void showReminderDialog() {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.layoutreminder, null);
        dialogBuilder.setView(dialogView);

        Button setTimeButton = dialogView.findViewById(R.id.setTimeButton);
        setTimeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText reminderEditText = dialogView.findViewById(R.id.reminderEditText);
                reminderMessage = reminderEditText.getText().toString();
                showTimePickerDialog();
            }
        });

        dialogBuilder.setPositiveButton("Set Reminder", null);
        dialogBuilder.setNegativeButton("Cancel", null);

        final AlertDialog alertDialog = dialogBuilder.create();
        alertDialog.setOnShowListener(dialog -> {
            Button positiveButton = alertDialog.getButton(AlertDialog.BUTTON_POSITIVE);
            positiveButton.setOnClickListener(view -> {
                if (!reminderMessage.isEmpty()) {
                    updateReminderDetails();
                    alertDialog.dismiss();
                } else {
                    Toast.makeText(Notificationdisplay.this, "Please enter a reminder message", Toast.LENGTH_SHORT).show();
                    updateReminderDetails();
                    alertDialog.dismiss();
                }
            });
        });

        alertDialog.show();
    }

    private void showTimePickerDialog() {
        Calendar currentTime = Calendar.getInstance();
        int hourOfDay = currentTime.get(Calendar.HOUR_OF_DAY);
        int minute = currentTime.get(Calendar.MINUTE);
        TimePickerDialog timePickerDialog = new TimePickerDialog(this,
                new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int selectedHour, int selectedMinute) {
                        createReminder(reminderMessage, selectedHour, selectedMinute);
                        updateReminderDetails();
                    }
                }, hourOfDay, minute, true);

        timePickerDialog.show();
    }

    private void createReminder(String reminderMessage,int hourOfDay, int minute) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
        calendar.set(Calendar.MINUTE, minute);
        calendar.set(Calendar.SECOND, 0);

        int requestCode = (int) System.currentTimeMillis();
        Intent intent = new Intent(this, NotificationBroadcastReceiver.class);
        intent.putExtra("message", reminderMessage);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, requestCode, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);

        Reminder reminder = new Reminder(reminderMessage, hourOfDay, minute, requestCode);
        remindersList.add(reminder);
    }

    private void updateReminderDetails() {
        remindersLayout.removeAllViews();
        for (Reminder reminder : remindersList) {
            String formattedTime = String.format("%02d:%02d", reminder.getHour(), reminder.getMinute());
            String reminderDetails = "Reminder: " + formattedTime;
            TextView textView = new TextView(this);
            textView.setText(reminderDetails);
            textView.setTextColor(getResources().getColor(R.color.black));
            textView.setTextSize(20);
            remindersLayout.addView(textView);
        }
    }

    private void deleteReminder(int requestCode) {
        Reminder foundReminder = null;
        for (Reminder reminder : remindersList) {
            if (reminder.getRequestCode() == requestCode) {
                foundReminder = reminder;
                break;
            }
        }

        if (foundReminder != null) {
            remindersList.remove(foundReminder);
            // Additional logic to cancel the pending intent if needed
        }
        updateReminderDetails();
    }


}