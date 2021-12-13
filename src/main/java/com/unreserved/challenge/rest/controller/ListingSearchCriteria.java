package com.unreserved.challenge.rest.controller;

import lombok.Data;

import javax.validation.constraints.Min;
import java.beans.ConstructorProperties;
import java.math.BigInteger;
import java.util.List;

@Data
public class ListingSearchCriteria {

    @Min(0)
    private final Integer pageNumber;
    @Min(1)
    private final Integer pageSize;

    private final String listingType;
    private final List<String> cities;

    private final BigInteger gte;
    private final BigInteger lte;

    @ConstructorProperties({"pageNumber", "pageSize", "type", "city", "gte", "lte"})
    public ListingSearchCriteria(Integer pageNumber, Integer pageSize, String type, List<String> cities, BigInteger gte, BigInteger lte) {
        this.pageNumber = pageNumber != null ? pageNumber : 0;
        this.pageSize = pageSize != null ? pageSize : 100;
        this.listingType = type;
        this.cities = cities;
        this.gte = gte;
        this.lte = lte;
    }
}
