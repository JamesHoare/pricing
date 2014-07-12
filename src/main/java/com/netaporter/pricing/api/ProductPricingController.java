package com.netaporter.pricing.api;


import com.netaporter.pricing.domain.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;


/**
 * Created by jameshoare on 13/04/2014.
 */
@RestController
public class ProductPricingController {


    private final ProductPricingRepository pricingRepository;

    @Autowired
    public ProductPricingController(final ProductPricingRepository pricingRepository) {
        this.pricingRepository = pricingRepository;
    }


    @RequestMapping(value = "/products", method = RequestMethod.GET)
    public Optional<List<Product>> getProducts() {
            return pricingRepository.getProducts();
    }


}
