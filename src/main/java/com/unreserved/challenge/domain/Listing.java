package com.unreserved.challenge.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.Set;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "listing")
public class Listing {

    @Id
    @Column(name = "id", nullable = false, columnDefinition = "CHAR(36)")
    private String id;

    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    private ListingStatus status;

    @Column(name = "reserve_price")
    private BigDecimal reservePrice;

    @Column(name = "bed_number")
    private Integer bedNumber;

    // Bath number can be e.g. 2.5, see https://www.quora.com/What-does-2-5-bathrooms-mean?share=1
    @Column(name = "bath_number")
    private BigDecimal bathNumber;

    @Column(name = "property_sqft")
    private Integer propertySqft;

    @Column(name = "condo_fees")
    private BigDecimal condoFees;

    @Column(name = "lot_sqft")
    private Integer lotSqft;

    @Column(name = "region_code")
    private String regionCode;

    @Column(name = "listing_type", nullable = false)
    @Enumerated(EnumType.STRING)
    private ListingType type;

    @Column(name = "city")
    private String city;

    @Column(name = "country")
    private String country;

    @Column(name = "address1")
    private String address1;

    @Column(name = "building_id")
    private String buildingId;

    @ManyToMany(mappedBy = "listingsInterested", fetch = FetchType.LAZY)
    private Set<Customer> customers;
}
