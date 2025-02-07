package com.jaimeg.technical_test.infraestructure.controller;

import com.jaimeg.priceservice.model.PriceResponse;
import com.jaimeg.technical_test.application.service.priceService.PriceService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class PriceControllerTest {

    private MockMvc mockMvc;

    @Mock
    private PriceService priceService;

    private static final Integer PRODUCT_ID = 35455;
    private static final Long BRAND_ID = 1L;
    private static final String DATE = "2025-02-07 10:00:00";
    private static final Double PRICE = 100.0;
    private static final String INVALID_PRODUCT_ID = "invalidString";

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        PriceController priceController = new PriceController(priceService);
        mockMvc = MockMvcBuilders.standaloneSetup(priceController).build();
    }

    @Test
    void testGetApplicablePrice_Success() throws Exception {
        PriceResponse priceResponse = new PriceResponse();
        priceResponse.setPrice(PRICE);

        when(priceService.getApplicablePrices(PRODUCT_ID, BRAND_ID, DATE)).thenReturn(Optional.of(priceResponse));
        
        mockMvc.perform(get("/api/prices")
                .param("productId", PRODUCT_ID.toString())
                .param("brandId", BRAND_ID.toString())
                .param("date", DATE)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.price").value(priceResponse.getPrice()));
    }

    @Test
    void testGetApplicablePrice_TypeMismatch() throws Exception {
        mockMvc.perform(get("/api/prices")
                .param("productId", INVALID_PRODUCT_ID)
                .param("brandId", BRAND_ID.toString())
                .param("date", DATE)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testGetApplicablePriceAt10amOnJune14() throws Exception {
        String date = "2020-06-14 10:00:00";
        PriceResponse priceResponse = new PriceResponse();
        priceResponse.setPrice(35.50);

        when(priceService.getApplicablePrices(PRODUCT_ID, BRAND_ID, date)).thenReturn(Optional.of(priceResponse));

        mockMvc.perform(get("/api/prices")
                .param("productId", PRODUCT_ID.toString())
                .param("brandId", BRAND_ID.toString())
                .param("date", date)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.price").value(priceResponse.getPrice()));
    }

    @Test
    void testGetApplicablePriceAt4pmOnJune14() throws Exception {
        String date = "2020-06-14 16:00:00";
        PriceResponse priceResponse = new PriceResponse();
        priceResponse.setPrice(25.45);

        when(priceService.getApplicablePrices(PRODUCT_ID, BRAND_ID, date)).thenReturn(Optional.of(priceResponse));

        mockMvc.perform(get("/api/prices")
                .param("productId", PRODUCT_ID.toString())
                .param("brandId", BRAND_ID.toString())
                .param("date", date)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.price").value(priceResponse.getPrice()));
    }

    @Test
    void testGetApplicablePriceAt9pmOnJune14() throws Exception {
        String date = "2020-06-14 21:00:00";
        PriceResponse priceResponse = new PriceResponse();
        priceResponse.setPrice(35.50);

        when(priceService.getApplicablePrices(PRODUCT_ID, BRAND_ID, date)).thenReturn(Optional.of(priceResponse));

        mockMvc.perform(get("/api/prices")
                .param("productId", PRODUCT_ID.toString())
                .param("brandId", BRAND_ID.toString())
                .param("date", date)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.price").value(priceResponse.getPrice()));
    }

    @Test
    void testGetApplicablePriceAt10amOnJune15() throws Exception {
        String date = "2020-06-15 10:00:00";
        PriceResponse priceResponse = new PriceResponse();
        priceResponse.setPrice(30.50);

        when(priceService.getApplicablePrices(PRODUCT_ID, BRAND_ID, date)).thenReturn(Optional.of(priceResponse));

        mockMvc.perform(get("/api/prices")
                .param("productId", PRODUCT_ID.toString())
                .param("brandId", BRAND_ID.toString())
                .param("date", date)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.price").value(priceResponse.getPrice()));
    }

    @Test
    void testGetApplicablePriceAt9pmOnJune16() throws Exception {
        String date = "2020-06-16 21:00:00";
        PriceResponse priceResponse = new PriceResponse();
        priceResponse.setPrice(38.95);

        when(priceService.getApplicablePrices(PRODUCT_ID, BRAND_ID, date)).thenReturn(Optional.of(priceResponse));

        mockMvc.perform(get("/api/prices")
                .param("productId", PRODUCT_ID.toString())
                .param("brandId", BRAND_ID.toString())
                .param("date", date)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.price").value(priceResponse.getPrice()));
    }
}
