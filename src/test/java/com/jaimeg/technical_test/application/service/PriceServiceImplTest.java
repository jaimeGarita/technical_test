package com.jaimeg.technical_test.application.service;

import com.jaimeg.priceservice.model.PriceResponse;
import com.jaimeg.technical_test.application.mapper.PriceMapper;
import com.jaimeg.technical_test.application.service.priceService.PriceServiceImpl;
import com.jaimeg.technical_test.application.utils.TimeUtil;
import com.jaimeg.technical_test.domain.model.Brand;
import com.jaimeg.technical_test.domain.model.Price;
import com.jaimeg.technical_test.domain.repository.PriceRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PriceServiceImplTest {

    @Mock
    private PriceRepository priceRepository;

    @Mock
    private PriceMapper priceMapper;

    @Mock
    private TimeUtil timeUtil;

    private PriceServiceImpl priceService;

    private static final Integer PRODUCT_ID = 1;
    private static final Long BRAND_ID = 2L;
    private static final String DATE = "2025-02-07 10:00:00";
    private static final double PRICE_1 = 100.0;
    private static final double PRICE_2 = 120.0;
    private static final int PRIORITY_1 = 1;
    private static final int PRIORITY_2 = 0;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        priceService = new PriceServiceImpl(priceRepository, priceMapper, timeUtil);
    }

    private Price createPrice(double priceValue, int priority) {
        Price price = new Price();
        price.setPrice(priceValue);
        price.setPriority(priority);
        price.setProductId(PRODUCT_ID);
        price.setBrand(new Brand(BRAND_ID, "BrandA"));
        return price;
    }

    private Timestamp createTimestampFromString(String date) {
        return Timestamp.valueOf(LocalDateTime.parse(date.replace(" ", "T")));
    }

    @Test
    void testGetApplicablePrices_PricesFound() {
        Timestamp applicationDate = createTimestampFromString(DATE);
        Price price = createPrice(PRICE_1, PRIORITY_1);
        List<Price> prices = List.of(price);
        PriceResponse priceResponse = new PriceResponse();
        priceResponse.setPrice(price.getPrice());

        when(timeUtil.convertToTimestamp(DATE)).thenReturn(applicationDate);
        when(priceRepository.findApplicablePrices(PRODUCT_ID, BRAND_ID, applicationDate)).thenReturn(prices);
        when(priceMapper.priceToPriceResponse(price)).thenReturn(priceResponse);

        Optional<PriceResponse> result = priceService.getApplicablePrices(PRODUCT_ID, BRAND_ID, DATE);

        assertTrue(result.isPresent());
        assertEquals(priceResponse.getPrice(), result.get().getPrice());
    }

    @Test
    void testGetApplicablePrices_NoPricesFound() {
        Timestamp applicationDate = createTimestampFromString(DATE);
        List<Price> prices = List.of();
        when(timeUtil.convertToTimestamp(DATE)).thenReturn(applicationDate);
        when(priceRepository.findApplicablePrices(PRODUCT_ID, BRAND_ID, applicationDate)).thenReturn(prices);

        Optional<PriceResponse> result = priceService.getApplicablePrices(PRODUCT_ID, BRAND_ID, DATE);

        assertFalse(result.isPresent());
    }

    @Test
    void testGetApplicablePrices_WithHighestPriorityPrice() {

        Timestamp applicationDate = createTimestampFromString(DATE);
        Price price1 = createPrice(PRICE_1, PRIORITY_1);
        Price price2 = createPrice(PRICE_2, PRIORITY_2);
        List<Price> prices = List.of(price1, price2);

        PriceResponse priceResponse = new PriceResponse();
        priceResponse.setPrice(price1.getPrice());

        when(timeUtil.convertToTimestamp(DATE)).thenReturn(applicationDate);
        when(priceRepository.findApplicablePrices(PRODUCT_ID, BRAND_ID, applicationDate)).thenReturn(prices);
        when(priceMapper.priceToPriceResponse(price1)).thenReturn(priceResponse);

        Optional<PriceResponse> result = priceService.getApplicablePrices(PRODUCT_ID, BRAND_ID, DATE);

        assertTrue(result.isPresent());
        assertEquals(priceResponse.getPrice(), result.get().getPrice());
    }
}
