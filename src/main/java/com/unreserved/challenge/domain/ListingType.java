package com.unreserved.challenge.domain;

public enum ListingType {
    HOUSE, CONDO;

    public static ListingType fromValue(String value) {
        return value.equalsIgnoreCase(HOUSE.name()) ? HOUSE : CONDO;
    }
}
