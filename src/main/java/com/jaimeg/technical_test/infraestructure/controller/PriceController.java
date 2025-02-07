package com.jaimeg.technical_test.infraestructure.controller;

import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.jaimeg.priceservice.model.PriceResponse;
import com.jaimeg.technical_test.application.exceptions.ValidationException;
import com.jaimeg.technical_test.application.service.priceService.PriceService;
import com.jaimeg.technical_test.application.utils.ValidationUtil;

@RestController
@RequestMapping("/api")
@CrossOrigin
public class PriceController {

    private final PriceService priceService;

    public PriceController(PriceService priceService) {
        this.priceService = priceService;
    }

    @GetMapping("/prices")
    public ResponseEntity<PriceResponse> getApplicablePrice(
            @RequestParam Integer productId,
            @RequestParam Long brandId,
            @RequestParam String date) {

        try {
            ValidationUtil.validateProductId(productId);
            ValidationUtil.validateBrandId(brandId);
            ValidationUtil.validateDateFormat(date);

            Optional<PriceResponse> priceResponse = this.priceService.getApplicablePrices(productId, brandId, date);

            return priceResponse
                    .map(response -> new ResponseEntity<>(response, HttpStatus.OK))
                    .orElse(new ResponseEntity<>(HttpStatus.NO_CONTENT));

        } catch (ValidationException ex) {
            throw ex;
        }
    }

}
