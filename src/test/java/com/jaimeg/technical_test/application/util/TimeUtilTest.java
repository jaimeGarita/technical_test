package com.jaimeg.technical_test.application.util;

import org.junit.jupiter.api.Test;

import com.jaimeg.technical_test.application.utils.TimeUtil;

import java.sql.Timestamp;

import static org.junit.jupiter.api.Assertions.*;

class TimeUtilTest {

    private final TimeUtil timeUtil = new TimeUtil();

    @Test
    void testConvertToTimestamp_ValidDate() {
        String validDate = "2020-06-14 10:00:00";
        Timestamp result = timeUtil.convertToTimestamp(validDate);
        assertNotNull(result, "The Timestamp should not be null");
        Timestamp expectedTimestamp = Timestamp.valueOf("2020-06-14 10:00:00.0");
        assertEquals(expectedTimestamp, result, "The Timestamps should be equal");
    }

    @Test
    void testConvertToTimestamp_InvalidDate() {
        String invalidDate = "2013-1-1";
        Timestamp result = timeUtil.convertToTimestamp(invalidDate);
        assertNull(result, "The Timestamp should be null for an invalid date");
    }

    @Test
    void testConvertToTimestamp_EmptyString() {
        String emptyDate = "";
        Timestamp result = timeUtil.convertToTimestamp(emptyDate);
        assertNull(result, "The Timestamp should be null for an empty string");
    }
}