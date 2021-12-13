package com.unreserved.challenge.domain;

import java.math.BigDecimal;

public class ReservedPriceByCity {
    private String city;
    private BigDecimal reservedPrice;

    public ReservedPriceByCity(String city, BigDecimal reservedPrice) {
        this.city = city;
        this.reservedPrice = reservedPrice;
    }

    public String getCity() {
        return city;
    }

    public BigDecimal getReservedPrice() {
        return reservedPrice;
    }
}
