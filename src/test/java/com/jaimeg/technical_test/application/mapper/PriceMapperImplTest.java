package com.jaimeg.technical_test.application.mapper;

import com.jaimeg.priceservice.model.PriceResponse;
import com.jaimeg.technical_test.domain.model.Brand;
import com.jaimeg.technical_test.domain.model.Price;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Timestamp;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class PriceMapperImplTest {

    private PriceMapperImpl priceMapper;

    private static final Long BRAND_ID = 1L;
    private static final String BRAND_NAME = "BrandA";
    private static final String START_DATE = "2025-02-07 10:00:00";
    private static final String END_DATE = "2025-02-08 10:00:00";
    private static final int PRIORITY = 1;
    private static final int PRODUCT_ID = 101;
    private static final double PRICE_VALUE = 100.0;
    private static final String CURRENCY = "USD";

    @BeforeEach
    void setUp() {
        priceMapper = new PriceMapperImpl();
    }

    private Price createPrice(Long brandId, String brandName, String startDate, String endDate,
            int priority, int productId, double priceValue, String currency) {
        return new Price(UUID.randomUUID(), new Brand(brandId, brandName),
                Timestamp.valueOf(startDate), Timestamp.valueOf(endDate),
                priority, productId, 1, priceValue, currency);
    }

    @Test
    void testPriceToPriceResponse_NullPrice() {
        assertNull(priceMapper.priceToPriceResponse(null));
    }

    @Test
    void testPriceToPriceResponse_ValidPrice() {
        Price price = createPrice(BRAND_ID, BRAND_NAME, START_DATE, END_DATE, PRIORITY, PRODUCT_ID, PRICE_VALUE,
                CURRENCY);
        PriceResponse response = priceMapper.priceToPriceResponse(price);

        assertNotNull(response);
        assertEquals(price.getId(), response.getId());
        assertEquals(price.getProductId(), response.getProductId());
        assertEquals(price.getPrice(), response.getPrice());
        assertEquals(price.getCurr(), response.getCurr());
        assertEquals(price.getStartDate().toLocalDateTime().toLocalDate(), response.getStartDate());
        assertEquals(price.getEndDate().toLocalDateTime().toLocalDate(), response.getEndDate());
        assertEquals(price.getBrand().getId(), response.getBrandId());
    }

    @Test
    void testGetHighestPriceResponse_EmptyList() {
        assertNull(priceMapper.getHighestPriceResponse(Collections.emptyList()));
    }

    @Test
    void testGetHighestPriceResponse_ValidList() {
        Price price1 = createPrice(BRAND_ID, BRAND_NAME, START_DATE, END_DATE, 1, PRODUCT_ID, PRICE_VALUE, CURRENCY);
        Price price2 = createPrice(2L, "BrandB", START_DATE, END_DATE, 2, 102, 150.0, "USD");
        List<Price> prices = List.of(price1, price2);

        PriceResponse response = priceMapper.getHighestPriceResponse(prices);

        assertNotNull(response);
        assertEquals(price2.getId(), response.getId());
        assertEquals(price2.getPrice(), response.getPrice());
    }

    @Test
    void testGetHighestPriceResponse_SinglePrice() {
        Price price = createPrice(BRAND_ID, BRAND_NAME, START_DATE, END_DATE, 1, PRODUCT_ID, PRICE_VALUE, CURRENCY);
        List<Price> prices = List.of(price);
        PriceResponse response = priceMapper.getHighestPriceResponse(prices);

        assertNotNull(response);
        assertEquals(price.getId(), response.getId());
    }

    @Test
    void testGetHighestPriceResponse_Exception() {
        assertThrows(RuntimeException.class, () -> priceMapper.getHighestPriceResponse(null));
    }
}
