package com.jaimeg.technical_test.application.utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import com.jaimeg.technical_test.application.exceptions.ValidationException;

/**
 * Utility class for validating input data such as product IDs, brand IDs, and
 * date formats.
 */
public class ValidationUtil {

    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    /**
     * Validates that the given product ID is greater than 0.
     *
     * @param productId The product ID to validate.
     * @throws ValidationException if the product ID is not greater than 0.
     */
    public static void validateProductId(Integer productId) {
        if (productId <= 0) {
            throw new ValidationException("Product ID must be greater than 0");
        }
    }

    /**
     * Validates that the given brand ID is greater than 0.
     *
     * @param brandId The brand ID to validate.
     * @throws ValidationException if the brand ID is not greater than 0.
     */
    public static void validateBrandId(Long brandId) {
        if (brandId <= 0) {
            throw new ValidationException("Brand ID must be greater than 0");
        }
    }

    /**
     * Validates that the given date string follows the expected format "yyyy-MM-dd
     * HH:mm:ss".
     *
     * @param dateTime The date string to validate.
     * @throws ValidationException if the date format is invalid.
     */
    public static void validateDateFormat(String dateTime) {
        try {
            LocalDateTime.parse(dateTime.trim(), DATE_TIME_FORMATTER);
        } catch (DateTimeParseException e) {
            throw new ValidationException("Invalid date format. Expected format: yyyy-MM-dd HH:mm:ss");
        }
    }
}
