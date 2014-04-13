package com.netaporter.pricing.domain;

import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * Product Domain
 */
@Entity
public class Product {


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

    public Product() {}

    @NotEmpty(message = "category is required")
    private String category;

    public String getDesigner() {
        return designer;
    }

    @NotEmpty(message = "designer is required")
    private String designer;

    @NotEmpty(message = "decription is required")
    private String description;


}
