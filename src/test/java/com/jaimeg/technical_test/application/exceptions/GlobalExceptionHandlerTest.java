package com.jaimeg.technical_test.application.exceptions;

import com.jaimeg.technical_test.infraestructure.controller.PriceController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class GlobalExceptionHandlerTest {

    private MockMvc mockMvc;

    @Mock
    private PriceController priceController;

    private String apiEndpoint = "/api/prices";
    private String productIdParam = "productId";
    private String brandIdParam = "brandId";
    private String dateParam = "date";
    private String invalidProductId = "invalidString";
    private String validProductId = "-1";
    private String brandId = "1";
    private String date = "2025-02-07 10:00:00";

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(priceController)
                .setControllerAdvice(new GlobalExceptionHandler())
                .build();
    }

    @Test
    void testHandleMethodArgumentTypeMismatchException() throws Exception {
        mockMvc.perform(get(apiEndpoint)
                .param(productIdParam, invalidProductId)
                .param(brandIdParam, brandId)
                .param(dateParam, date)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error").value("Bad Request"))
                .andExpect(jsonPath("$.message").value(
                        "Invalid value for parameter 'productId'. Expected type 'Integer', but received value 'invalidString'."));
    }

    @Test
    void testHandleValidationException() throws Exception {
        when(priceController.getApplicablePrice(Integer.parseInt(validProductId), Long.parseLong(brandId), date))
                .thenThrow(new ValidationException("Product ID must be greater than 0"));

        mockMvc.perform(get(apiEndpoint)
                .param(productIdParam, validProductId)
                .param(brandIdParam, brandId)
                .param(dateParam, date)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error").value("Validation Error"))
                .andExpect(jsonPath("$.message").value("Product ID must be greater than 0"));
    }
}
