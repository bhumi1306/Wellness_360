package com.example.wellness360;

public class Reminder {
    private String message;
    private int hour;
    private int minute;
    private int requestCode;

    public Reminder(String message, int hour, int minute, int requestCode) {
        this.message = message;
        this.hour = hour;
        this.minute = minute;
        this.requestCode = requestCode;
    }

    public String getMessage() {
        return message;
    }

    public int getHour() {
        return hour;
    }

    public int getMinute() {
        return minute;
    }

    public int getRequestCode() {
        return requestCode;
    }
}
