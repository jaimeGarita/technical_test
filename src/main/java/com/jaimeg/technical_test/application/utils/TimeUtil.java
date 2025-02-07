package com.jaimeg.technical_test.application.utils;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.stereotype.Component;

/**
 * Utility class for converting date-time strings into {@link Timestamp}.
 */
@Component
public class TimeUtil {

    /**
     * Converts a date-time string in the format "yyyy-MM-dd HH:mm:ss" into a {@link Timestamp}.
     *
     * @param dateTime The date-time string to be converted.
     * @return A {@link Timestamp} representing the given date-time string, or {@code null} if the parsing fails.
     */
    public Timestamp convertToTimestamp(String dateTime) {
        try {
            DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date date = formatter.parse(dateTime);

            return new Timestamp(date.getTime());
        } catch (ParseException e) {
            return null;
        }

    }

}