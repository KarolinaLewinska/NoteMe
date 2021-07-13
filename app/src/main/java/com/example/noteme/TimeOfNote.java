package com.example.noteme;
import android.content.Context;

public class TimeOfNote {
    private static final int SECOND_MILLIS = 1000;
    private static final int MINUTE_MILLIS = 60 * SECOND_MILLIS;
    private static final int HOUR_MILLIS = 60 * MINUTE_MILLIS;
    private static final int DAY_MILLIS = 24 * HOUR_MILLIS;

    public static String getTimeModification(long time, Context ctx) {
        if (time < 1000000000000L)
            time *= 1000;

        long now = System.currentTimeMillis();

        if (time > now || time <= 0)
            return null;

        final long diff = now - time;

        if (diff < MINUTE_MILLIS)
            return "przed chwilą";
        else if (diff < 2 * MINUTE_MILLIS)
            return "minutę temu";
        else if (diff < 50 * MINUTE_MILLIS)
            return (diff / MINUTE_MILLIS + " minut(y) temu");
        else if (diff < 90 * MINUTE_MILLIS)
            return "godzinę temu";
        else if (diff < 24 * HOUR_MILLIS)
            return (diff / HOUR_MILLIS + " godzin(y) temu");
        else if (diff < 48 * HOUR_MILLIS)
            return "wczoraj";
        else
            return diff / DAY_MILLIS + " dni temu";
    }
}
