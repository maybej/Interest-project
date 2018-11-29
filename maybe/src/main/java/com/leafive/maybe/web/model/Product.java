package com.leafive.maybe.web.model;

import java.math.BigDecimal;

public class Product {
    /** */
    private Long id;

    /** 商品名*/
    private String name;

    /** 价格*/
    private BigDecimal price;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }
}