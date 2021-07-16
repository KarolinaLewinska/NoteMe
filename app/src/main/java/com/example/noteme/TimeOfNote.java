package com.example.noteme;
import android.content.Context;

public class TimeOfNote {
    private static final int SECOND_MILLIS = 1000;
    private static final int MINUTE_MILLIS = 60 * SECOND_MILLIS;
    private static final int HOUR_MILLIS = 60 * MINUTE_MILLIS;
    private static final int DAY_MILLIS = 24 * HOUR_MILLIS;

    public static String getTimeModification(long time, Context ctx) {
        long now = System.currentTimeMillis();
        final long TIME_DIFFERENCE = now - time;

        if (time < 1000000000000L) {
            time *= 1000;
        }

        if (time > now || time <= 0) {
            return null;
        }

        if (TIME_DIFFERENCE < MINUTE_MILLIS) {
            return "przed chwilą";
        }
        else if (TIME_DIFFERENCE < 2 * MINUTE_MILLIS) {
            return "minutę temu";
        }
        else if (TIME_DIFFERENCE < 50 * MINUTE_MILLIS) {
            return (TIME_DIFFERENCE / MINUTE_MILLIS + " minut(y) temu");
        }
        else if (TIME_DIFFERENCE < 90 * MINUTE_MILLIS) {
            return "godzinę temu";
        }
        else if (TIME_DIFFERENCE < 24 * HOUR_MILLIS) {
            return (TIME_DIFFERENCE / HOUR_MILLIS + " godzin(y) temu");
        }
        else if (TIME_DIFFERENCE < 48 * HOUR_MILLIS) {
            return "wczoraj";
        }
        else {
            return TIME_DIFFERENCE / DAY_MILLIS + " dni temu";
        }
    }
}
