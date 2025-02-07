package com.jaimeg.technical_test.application.service.priceService;

import java.util.Optional;

import com.jaimeg.priceservice.model.PriceResponse;

public interface PriceService {

    
    Optional<PriceResponse> getApplicablePrices(Integer productId, Long brandId, String date);
}
