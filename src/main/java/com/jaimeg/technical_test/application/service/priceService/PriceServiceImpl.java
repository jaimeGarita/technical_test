package com.jaimeg.technical_test.application.service.priceService;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import com.jaimeg.priceservice.model.PriceResponse;
import com.jaimeg.technical_test.application.mapper.PriceMapper;
import com.jaimeg.technical_test.application.utils.TimeUtil;
import com.jaimeg.technical_test.domain.model.Price;
import com.jaimeg.technical_test.domain.repository.PriceRepository;

@Service
public class PriceServiceImpl implements PriceService {

    private final PriceRepository priceRepository;
    private final PriceMapper priceMapper;
    private final TimeUtil timeUtil;

    public PriceServiceImpl(PriceRepository priceRepository, PriceMapper priceMapper, TimeUtil timeUtil) {
        this.priceRepository = priceRepository;
        this.priceMapper = priceMapper;
        this.timeUtil = timeUtil;
    }

    @Override
    public Optional<PriceResponse> getApplicablePrices(Integer productId, Long brandId, String date) {

        Timestamp applicationDate = this.timeUtil.convertToTimestamp(date);
    
        List<Price> prices = this.priceRepository.findApplicablePrices(productId, brandId, applicationDate);
    
        if (!prices.isEmpty()) {
            Price highestPriorityPrice = prices.get(0);
            PriceResponse priceResponse = priceMapper.priceToPriceResponse(highestPriorityPrice);
            return Optional.of(priceResponse);
        }

        return Optional.empty();
    }

}
