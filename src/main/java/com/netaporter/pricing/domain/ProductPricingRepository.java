package com.netaporter.pricing.domain;

import org.springframework.data.repository.Repository;
import org.springframework.validation.annotation.Validated;

import java.util.List;
import java.util.Optional;

/**
 * Created by jameshoare on 13/04/2014.
 */
@Validated
public interface ProductPricingRepository extends Repository<Product,Integer> {

    Optional<List<Product>> getProducts();




}
