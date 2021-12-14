package com.unreserved.challenge;

import com.unreserved.challenge.domain.ListingStatus;
import com.unreserved.challenge.domain.ListingType;
import com.unreserved.challenge.rest.dto.ListingDto;
import org.junit.Test;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * NOTE this test might fail if data is added/changed via sql scripts
 */
public class ListingsTest extends AbstractTest {

    @Test
    public void addListingsSuccess() throws Exception {
        // Get existing listings if there were prepopulated
        MvcResult mvcResult = listingsGetRequest(null, status().isOk());
        Map<String, List<ListingDto>> listingMap = getListingListFromResponse(mvcResult);
        int initialLytistingSizeAll = listingMap.get("listings").size();

        // Get listing condo size
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("type", "condo");
        mvcResult = listingsGetRequest(params, status().isOk());
        listingMap = getListingListFromResponse(mvcResult);
        int initialListingSizeCondos = listingMap.get("listings").size();

        params.add("type", "hoouse");
        mvcResult = listingsGetRequest(params, status().isOk());
        listingMap = getListingListFromResponse(mvcResult);
        int initialListingSizeHouses = listingMap.get("listings").size();


        // Add 3 extra listing entries
        ListingDto listingDto = ListingDto.builder()
                .status(ListingStatus.DRAFT)
                .reservePrice(BigDecimal.valueOf(10000))
                .bathNumber(BigDecimal.valueOf(2)).bedNumber(2)
                .propertySqft(800)
                .regionCode("ON")
                .buildingId("375e16e5-d5c2-11eb-ad41-0abe8932f98d")
                .condoFees(BigDecimal.valueOf(100))
                .country("Canada")
                .city("Toronto")
                .address1("100 Williby ave")
                .type(ListingType.CONDO).build();
        // Verify
        mvcResult = listingPostRequest(listingDto, status().isCreated());
        ListingDto responseDto = getListingDTOFromResponse(mvcResult);
        assertEquals(ListingType.CONDO, responseDto.getType());
        assertEquals("375e16e5-d5c2-11eb-ad41-0abe8932f98d", responseDto.getBuildingId());
        assertEquals(BigDecimal.valueOf(100), responseDto.getCondoFees());

        listingDto = ListingDto.builder()
                .status(ListingStatus.DRAFT)
                .reservePrice(BigDecimal.valueOf(10000))
                .bathNumber(BigDecimal.valueOf(2.5)).bedNumber(2)
                .propertySqft(800)
                .regionCode("ON")
                .buildingId("000008f5-3374-4313-92fd-6a11d0068f2b")
                .condoFees(BigDecimal.valueOf(200))
                .country("Canada")
                .city("Toronto")
                .address1("100 Williby ave")
                .type(ListingType.CONDO).build();
        // Verify
        mvcResult = listingPostRequest(listingDto, status().isCreated());
        responseDto = getListingDTOFromResponse(mvcResult);
        assertEquals(ListingType.CONDO, responseDto.getType());
        assertEquals(BigDecimal.valueOf(10000), responseDto.getReservePrice());
        assertEquals(Integer.valueOf(800), responseDto.getPropertySqft());

        listingDto = ListingDto.builder()
                .status(ListingStatus.DRAFT)
                .reservePrice(BigDecimal.valueOf(500000))
                .bathNumber(BigDecimal.valueOf(4)).bedNumber(3)
                .propertySqft(2800)
                .regionCode("ON")
                .country("Canada")
                .city("Toronto")
                .lotSqft(200)
                .address1("90 Princes Ave")
                .type(ListingType.HOUSE).build();
        // Verify
        mvcResult = listingPostRequest(listingDto, status().isCreated());
        responseDto = getListingDTOFromResponse(mvcResult);
        assertEquals(ListingType.HOUSE, responseDto.getType());
        assertEquals(BigDecimal.valueOf(500000), responseDto.getReservePrice());
        assertEquals(Integer.valueOf(2800), responseDto.getPropertySqft());
        assertEquals(Integer.valueOf(200), responseDto.getLotSqft());

        // Check pagination
        params = new LinkedMultiValueMap<>();
        params.add("pageNumber", "0");
        params.add("pageSize", "10");

        mvcResult = listingsGetRequest(params, status().isOk());
        listingMap = getListingListFromResponse(mvcResult);
        // initialSize from sql script and 3 here
        assertEquals(initialLytistingSizeAll + 3, listingMap.get("listings").size());

        // Filter by condo type
        params.add("type", "condo");
        mvcResult = listingsGetRequest(params, status().isOk());
        listingMap = getListingListFromResponse(mvcResult);
        // Initial listing size condos 3 from sql and 2 here
        assertEquals(initialListingSizeCondos + 2, listingMap.get("listings").size());

        // Filter by house type
        params.replace("type", List.of("HOUSE"));
        mvcResult = listingsGetRequest(params, status().isOk());
        listingMap = getListingListFromResponse(mvcResult);
        // Initial listing size houses from sql and 1 here
        assertEquals(initialListingSizeHouses + 1, listingMap.get("listings").size());
    }

    @Test
    public void checkListingPostConstrains() throws Exception {
        // Condo fees are missing for condo
        ListingDto listingDto = ListingDto.builder()
                .status(ListingStatus.DRAFT)
                .reservePrice(BigDecimal.valueOf(10000))
                .bathNumber(BigDecimal.valueOf(2)).bedNumber(2)
                .propertySqft(800)
                .regionCode("ON")
                .buildingId("375e16e5-d5c2-11eb-ad41-0abe8932f98d")
                //.condoFees(BigDecimal.valueOf(100)) // no condo fees includes in payload
                .country("Canada")
                .city("Toronto")
                .address1("101 Williby ave")
                .type(ListingType.CONDO).build();
        // Verify
        MvcResult mvcResult = listingPostRequest(listingDto, status().isBadRequest());
        assertMessageResponse("Monthly condo fees are required!", mvcResult);

        // Building id is missing for condo
        listingDto = ListingDto.builder()
                .status(ListingStatus.DRAFT)
                .reservePrice(BigDecimal.valueOf(10000))
                .bathNumber(BigDecimal.valueOf(2.5)).bedNumber(2)
                .propertySqft(800)
                .regionCode("ON")
                // .buildingId("000008f5-3374-4313-92fd-6a11d0068f2b") no reference to building
                .condoFees(BigDecimal.valueOf(200))
                .country("Canada")
                .city("Toronto")
                .address1("102 Williby ave")
                .type(ListingType.CONDO).build();
        // Verify
        mvcResult = listingPostRequest(listingDto, status().isBadRequest());
        assertMessageResponse("Building reference id is required!", mvcResult);

        // lot square footage for a house
        listingDto = ListingDto.builder()
                .status(ListingStatus.DRAFT)
                .reservePrice(BigDecimal.valueOf(500000))
                .bathNumber(BigDecimal.valueOf(4)).bedNumber(3)
                .propertySqft(2800)
                .regionCode("ON")
                .country("Canada")
                .city("Toronto")
                // .lotSqft(200) lot square footage not included
                .address1("90 Princes Ave")
                .type(ListingType.HOUSE).build();
        // Verify
        mvcResult = listingPostRequest(listingDto, status().isBadRequest());
        assertMessageResponse("Lot square footage is required!", mvcResult);
    }
}
