package com.netaporter.pricing.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.validation.annotation.Validated;

import java.util.Optional;

/**
 * Created by jameshoare on 13/04/2014.
 */
@Validated
public interface ProductPricingRepository extends JpaRepository<Product,Integer> {

    Optional<Product> findOne(Long id);


    default Optional<Product> findProduct(Long id) {
        return findOne(id);
    }


}
