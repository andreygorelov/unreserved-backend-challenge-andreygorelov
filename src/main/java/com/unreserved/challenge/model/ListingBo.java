package com.unreserved.challenge.model;

import com.unreserved.challenge.domain.ListingStatus;
import com.unreserved.challenge.domain.ListingType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ListingBo {
    private String id;
    private ListingStatus status;
    private BigDecimal reservePrice;
    private Integer bedNumber;
    private BigDecimal bathNumber;
    private Integer propertySqft;
    private BigDecimal condoFees;
    private String buildingId;
    private Integer lotSqft;
    private String regionCode;
    private ListingType type;
    private String city;
    private String country;
    private String address1;
}