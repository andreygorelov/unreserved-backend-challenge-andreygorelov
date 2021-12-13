package com.unreserved.challenge.rest.controller;

import com.unreserved.challenge.domain.ListingType;
import com.unreserved.challenge.exception.ListingNotFoundException;
import com.unreserved.challenge.exception.ValidationException;
import com.unreserved.challenge.rest.dto.ListingDto;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.math.BigDecimal;
import java.util.regex.Pattern;

public abstract class BaseController {

    private final static String REGION_CODE_PATTERN = "[A-Z]{2}";
    @ExceptionHandler({Exception.class })
    @ResponseBody
    public ResponseEntity<String> handleExecutionException(Exception ex) {
        if (ex instanceof ValidationException) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
        } else if (ex instanceof ListingNotFoundException) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
        }
        // This can be improved
        else if(ex instanceof DataIntegrityViolationException){
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    public void validateListing(ListingDto listingDto) {
        // HOUSE / CONDO extra params validation
        if (listingDto.getType().equals(ListingType.CONDO)) {
            if (listingDto.getCondoFees() == null) {
                throw new ValidationException("Monthly condo fees are required!");
            }
            if (listingDto.getBuildingId() == null) {
                throw new ValidationException("Building reference id is required!");
            }
        }
        // HOUSE
        else {
            if (listingDto.getLotSqft() == null) {
                throw new ValidationException("Lot square footage is required!");
            }
        }
        // ReservePrice
        if (listingDto.getReservePrice() != null && listingDto.getReservePrice().compareTo(BigDecimal.ZERO) < 0) {
            throw new ValidationException("Reserve Price must be positive!");
        }
        // RegionCode
        if (listingDto.getRegionCode() != null) {
            if (!Pattern.compile(REGION_CODE_PATTERN).matcher(listingDto.getRegionCode()).matches()
                    || listingDto.getRegionCode().length() != 2) {
                throw new ValidationException("Invalid Region Code. Must be two upper case letters!");
            }
        }
        // BedNumber
        if (listingDto.getBedNumber() != null && listingDto.getBedNumber() < 1) {
            throw new ValidationException("Number of bed rooms invalid!");
        }
        // BathRooms
        if (listingDto.getBathNumber() != null && listingDto.getBathNumber().compareTo(BigDecimal.ZERO) == -1) {
            throw new ValidationException("Number of bath rooms invalid!");
        }
        // Property
        if (listingDto.getPropertySqft() != null && listingDto.getPropertySqft() < 1) {
            throw new ValidationException("Property square footage is invalid!");
        }

    }

}