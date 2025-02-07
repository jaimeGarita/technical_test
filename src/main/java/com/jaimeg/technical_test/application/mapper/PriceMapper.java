package com.jaimeg.technical_test.application.mapper;

import com.jaimeg.priceservice.model.PriceResponse;
import com.jaimeg.technical_test.domain.model.Price;

public interface PriceMapper {
    PriceResponse priceToPriceResponse(Price prices);
}
