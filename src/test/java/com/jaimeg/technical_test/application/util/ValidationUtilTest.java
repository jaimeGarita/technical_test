package com.jaimeg.technical_test.application.util;

import com.jaimeg.technical_test.application.exceptions.ValidationException;
import com.jaimeg.technical_test.application.utils.ValidationUtil;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ValidationUtilTest {

    private static final Integer VALID_PRODUCT_ID = 1;
    private static final Integer INVALID_PRODUCT_ID = -1;
    private static final Long VALID_BRAND_ID = 1L;
    private static final Long INVALID_BRAND_ID = -1L;
    private static final String VALID_DATE = "2020-06-14 10:00:00";
    private static final String INVALID_DATE = "2020-06-14 10:00";
    private static final String EMPTY_DATE = "";

    @Test
    void testValidateProductId_ValidProductId() {
        assertDoesNotThrow(() -> ValidationUtil.validateProductId(VALID_PRODUCT_ID));
    }

    @Test
    void testValidateProductId_InvalidProductId() {
        ValidationException exception = assertThrows(ValidationException.class, () -> {
            ValidationUtil.validateProductId(INVALID_PRODUCT_ID);
        });

        assertEquals("Product ID must be greater than 0", exception.getMessage());
    }

    @Test
    void testValidateBrandId_ValidBrandId() {
        assertDoesNotThrow(() -> ValidationUtil.validateBrandId(VALID_BRAND_ID));
    }

    @Test
    void testValidateBrandId_InvalidBrandId() {
        ValidationException exception = assertThrows(ValidationException.class, () -> {
            ValidationUtil.validateBrandId(INVALID_BRAND_ID);
        });

        assertEquals("Brand ID must be greater than 0", exception.getMessage());
    }

    @Test
    void testValidateDateFormat_ValidDate() {
        assertDoesNotThrow(() -> ValidationUtil.validateDateFormat(VALID_DATE));
    }

    @Test
    void testValidateDateFormat_InvalidDate() {
        ValidationException exception = assertThrows(ValidationException.class, () -> {
            ValidationUtil.validateDateFormat(INVALID_DATE);
        });

        assertEquals("Invalid date format. Expected format: yyyy-MM-dd HH:mm:ss", exception.getMessage());
    }

    @Test
    void testValidateDateFormat_EmptyDate() {
        ValidationException exception = assertThrows(ValidationException.class, () -> {
            ValidationUtil.validateDateFormat(EMPTY_DATE);
        });

        assertEquals("Invalid date format. Expected format: yyyy-MM-dd HH:mm:ss", exception.getMessage());
    }
}
