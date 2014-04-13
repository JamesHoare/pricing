package com.netaporter.pricing.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Optional;

/**
 * Product Domain
 */
@Entity
public class Product implements ProductPricing {


    public Integer getProductId() {
        return productId;
    }

    @Override
    public String toString() {
        return String.format("Proudct[id=%d, description=%s, designer=%s, category=%s]", productId, description, designer, category);
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer productId;

    public String getCategory() {
        return category;
    }

    public String getDescription() {
        return description;
    }

    public Product(final String category, final String description, final String designer) {
        this.category = category;
        this.description = description;
        this.designer = designer;

    }

    Product() {
    }

    @NotEmpty(message = "category is required")
    private String category;

    public String getDesigner() {
        return designer;
    }

    @NotEmpty(message = "designer is required")
    private String designer;

    @NotEmpty(message = "decription is required")
    private String description;

    @JsonFormat(shape = JsonFormat.Shape.OBJECT)
    enum PRODUCT_TYPE {
        SHIPPING, PACKAGING, VOUCHER, SUBSCRIPTION, WEARBALE
    }

    @JsonFormat(shape = JsonFormat.Shape.OBJECT)
    public enum PRODUCT_AVAILABILITY {
        INSTOCK {
            @Override
            public Optional<Boolean> isBuyable(PRODUCT_AVAILABILITY product_availability) {
                return Optional.of(Boolean.TRUE);
            }
        },
        OUT_OF_STOCK {
            @Override
            public Optional<Boolean> isBuyable(PRODUCT_AVAILABILITY product_availability) {
                return Optional.of(Boolean.FALSE);
            }
        },
        RESERVED {
            @Override
            public Optional<Boolean> isBuyable(PRODUCT_AVAILABILITY product_availability) {
                return Optional.of(Boolean.FALSE);
            }
        };

        public abstract Optional<Boolean> isBuyable(PRODUCT_AVAILABILITY product_availability);


    }


}
