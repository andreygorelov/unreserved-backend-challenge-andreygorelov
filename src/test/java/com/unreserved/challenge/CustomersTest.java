package com.unreserved.challenge;

import com.unreserved.challenge.rest.dto.CustomerDto;
import com.unreserved.challenge.rest.dto.ListingDto;
import com.unreserved.challenge.rest.dto.ListingEnvelopDto;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.util.CollectionUtils;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * NOTE this test might fail if data is added/changed via sql scripts
 */
public class CustomersTest extends AbstractTest {

    @Test
    public void addCustomersSuccess() throws Exception {
        MvcResult mvcResult = customersGetRequest(status().isOk());
        Map<String, List<CustomerDto>> customers = getCustomerListFromResponse(mvcResult);
        int initialCustomerSize = customers.get("customers").size();

        // Customer without listings
        CustomerDto customerDto = CustomerDto.builder().email("testuser4@domain.net").build();
        // Verify
        mvcResult = customerPostRequest(customerDto, status().isCreated());
        CustomerDto responseDto = getCustomerDTOFromResponse(mvcResult);
        assertEquals("testuser4@domain.net", responseDto.getEmail());

        // Add a user who is interested in condos only
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("type", "condo");
        mvcResult = listingsGetRequest(params, status().isOk());
        ListingEnvelopDto listings = getListingListFromResponse(mvcResult);

        customerDto = CustomerDto.builder().email("testuser5@domain.net").listingsInterested(listings.getListing()).build();
        // Verify
        mvcResult = customerPostRequest(customerDto, status().isCreated());
        responseDto = getCustomerDTOFromResponse(mvcResult);
        assertEquals("testuser5@domain.net", responseDto.getEmail());
        assertEquals(listings.getListing().size(), responseDto.getListingsInterested().size());

        // There should be 7 customers with 3, 2, and 1 listings
        mvcResult = customersGetRequest(status().isOk());
        customers = getCustomerListFromResponse(mvcResult);
        assertEquals(initialCustomerSize + 2, customers.get("customers").size());

        customers.get("customers").forEach(customer -> {
            if ("testuser5@domain.net".equals(customer.getEmail())) {
                assertEquals(customer.getListingsInterested().size(), 3);
            } else if ("testuser1@domain.net".equals(customer.getEmail())) {
                assertEquals(customer.getListingsInterested().size(), 2);
            } else if ("testuser2@domain.net".equals(customer.getEmail())) {
                assertEquals(customer.getListingsInterested().size(), 1);
            }
        });
    }

    @Test
    public void checkCustomerPostConstrains() throws Exception {
        // Add existing user / non unique email
        CustomerDto customerDto = CustomerDto.builder().email("testuser1@domain.net").build();
        customerPostRequest(customerDto, status().isBadRequest());
    }
}
