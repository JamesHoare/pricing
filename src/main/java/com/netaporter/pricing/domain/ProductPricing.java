package com.netaporter.pricing.domain;

import com.fasterxml.jackson.annotation.JsonAutoDetect;

/**
 * Created by jameshoare on 13/04/2014.
 */
@JsonAutoDetect
public interface ProductPricing {


    default Pricing getProductPricing() {
        return null;
    }

}
