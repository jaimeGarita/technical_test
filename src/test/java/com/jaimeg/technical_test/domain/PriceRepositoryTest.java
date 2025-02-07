package com.jaimeg.technical_test.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.sql.Timestamp;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;

import com.jaimeg.technical_test.domain.model.Brand;
import com.jaimeg.technical_test.domain.model.Price;
import com.jaimeg.technical_test.domain.repository.PriceRepository;

import jakarta.transaction.Transactional;

@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Transactional
class PriceRepositoryTest {

    @Autowired
    private PriceRepository priceRepository;

    private static final Integer PRODUCT_ID = 35455;
    private static final Long BRAND_ID = 1L;
    private static final String BRAND_NAME = "ZARA";
    private static final Timestamp APPLICATION_DATE = Timestamp.valueOf("2025-02-07 10:00:00");
    
    private static final String START_DATE_1 = "2025-01-01 00:00:00";
    private static final String END_DATE_1 = "2025-12-31 23:59:59";
    private static final String START_DATE_2 = "2025-01-01 00:00:00";
    private static final String END_DATE_2 = "2025-06-30 23:59:59";

    private static final int PRIORITY_1 = 0;
    private static final int PRIORITY_2 = 1;

    private Price createPrice(Integer productId, Long brandId, String brandName, String startDate, String endDate,
            int priority) {
        Price price = new Price();
        price.setProductId(productId);
        price.setBrand(new Brand(brandId, brandName));
        price.setStartDate(Timestamp.valueOf(startDate));
        price.setEndDate(Timestamp.valueOf(endDate));
        price.setPriority(priority);
        return price;
    }

    @Test
    void testFindApplicablePrices() {
        Price price1 = createPrice(PRODUCT_ID, BRAND_ID, BRAND_NAME, START_DATE_1, END_DATE_1, PRIORITY_1);
        Price price2 = createPrice(PRODUCT_ID, BRAND_ID, BRAND_NAME, START_DATE_2, END_DATE_2, PRIORITY_2);

        priceRepository.save(price1);
        priceRepository.save(price2);

        List<Price> applicablePrices = priceRepository.findApplicablePrices(PRODUCT_ID, BRAND_ID, APPLICATION_DATE);
        
        assertNotNull(applicablePrices);
        assertEquals(2, applicablePrices.size());
        assertEquals(price2.getPriority(), applicablePrices.get(0).getPriority());
    }
}
