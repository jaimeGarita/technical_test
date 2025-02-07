package com.jaimeg.technical_test.application.mapper;

import com.jaimeg.priceservice.model.PriceResponse;
import com.jaimeg.technical_test.application.exceptions.PriceNotFoundException;
import com.jaimeg.technical_test.domain.model.Price;

import java.util.Comparator;
import java.util.List;

import org.springframework.stereotype.Component;

@Component
public class PriceMapperImpl implements PriceMapper {

    @Override
    public PriceResponse priceToPriceResponse(Price price) {
        if (price == null) {
            return null;
        }

        PriceResponse priceResponse = new PriceResponse();

        priceResponse.setId(price.getId());
        priceResponse.setProductId(price.getProductId());
        priceResponse.setPriceList(price.getPriceList());
        priceResponse.setPrice(price.getPrice());
        priceResponse.setCurr(price.getCurr());

        if (price.getStartDate() != null) {
            priceResponse.setStartDate(price.getStartDate().toLocalDateTime().toLocalDate());
        }
        if (price.getEndDate() != null) {
            priceResponse.setEndDate(price.getEndDate().toLocalDateTime().toLocalDate());
        }

        if (price.getBrand() != null && price.getBrand().getId() != null) {
            priceResponse.setBrandId(price.getBrand().getId());
        }

        return priceResponse;
    }

    public PriceResponse getHighestPriceResponse(List<Price> prices) {
        if (prices == null) {
            throw new PriceNotFoundException("Price list cannot be null");
        }
        if (prices.isEmpty()) {
            return null;
        }

        Price highestPrice = prices.stream()
                .max(Comparator.comparing(Price::getPrice))
                .orElseThrow(() -> new PriceNotFoundException("No price found for the given parameters"));

        return priceToPriceResponse(highestPrice);
    }

}
