package com.unreserved.challenge;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.unreserved.challenge.rest.controller.ListingController;
import com.unreserved.challenge.rest.dto.CustomerDto;
import com.unreserved.challenge.rest.dto.ListingDto;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.mock.http.MockHttpOutputMessage;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.util.MultiValueMapAdapter;
import org.springframework.web.context.WebApplicationContext;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@RunWith(SpringRunner.class)
@SpringBootTest
public abstract class AbstractTest {
    protected static final MediaType contentType = new MediaType(MediaType.APPLICATION_JSON.getType(),
            MediaType.APPLICATION_JSON.getSubtype(), StandardCharsets.UTF_8);

    protected MockMvc mockMvc;
    protected HttpMessageConverter<Object> mappingJackson2HttpMessageConverter;

    @Autowired
    protected WebApplicationContext webApplicationContext;

    @Autowired
    protected ObjectMapper objectMapper;

    @Autowired
    protected void setConverters(HttpMessageConverter<Object>[] converters) {
        mappingJackson2HttpMessageConverter = Arrays.stream(converters).filter(
                hmc -> hmc instanceof MappingJackson2HttpMessageConverter).findAny().get();
        assertNotNull("the JSON message converter must not be null", mappingJackson2HttpMessageConverter);
    }

    @Before
    public void setupMockMvc() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    protected MvcResult listingPostRequest(ListingDto listingDto, ResultMatcher resultMatcher) throws Exception {
        return mockMvc.perform(post("/listings")
                        .content(json(listingDto))
                        .contentType(contentType))
                .andDo(print())
                .andExpect(resultMatcher)
                .andReturn();
    }

    protected MvcResult customerPostRequest(CustomerDto customerDto, ResultMatcher resultMatcher) throws Exception {
        return mockMvc.perform(post("/customers")
                        .content(json(customerDto))
                        .contentType(contentType))
                .andDo(print())
                .andExpect(resultMatcher)
                .andReturn();
    }

    protected MvcResult listingsGetRequest(MultiValueMap<String, String> params, ResultMatcher resultMatcher) throws Exception {
        MultiValueMap<String, String> params2 = params != null? params: new LinkedMultiValueMap<>();
        return mockMvc.perform(get("/listings").params(params2))
                .andDo(print())
                .andExpect(resultMatcher)
                .andReturn();
    }

    protected MvcResult customersGetRequest(ResultMatcher resultMatcher) throws Exception {
        return mockMvc.perform(get("/customers"))
                .andDo(print())
                .andExpect(resultMatcher)
                .andReturn();
    }

    protected ListingDto getListingDTOFromResponse(MvcResult mvcResult) throws UnsupportedEncodingException, JsonProcessingException {
        return objectMapper.readValue(mvcResult.getResponse().getContentAsString(), ListingDto.class);
    }

    protected CustomerDto getCustomerDTOFromResponse(MvcResult mvcResult) throws UnsupportedEncodingException, JsonProcessingException {
        return objectMapper.readValue(mvcResult.getResponse().getContentAsString(), CustomerDto.class);
    }

    protected Map<String, List<ListingDto>> getListingListFromResponse(MvcResult mvcResult) throws UnsupportedEncodingException, JsonProcessingException {
        return objectMapper.readValue(mvcResult.getResponse().getContentAsString(), new TypeReference<>() {
        });
    }

    protected Map<String, List<CustomerDto>> getCustomerListFromResponse(MvcResult mvcResult) throws UnsupportedEncodingException, JsonProcessingException {
        return objectMapper.readValue(mvcResult.getResponse().getContentAsString(), new TypeReference<>() {
        });
    }

    protected void assertMessageResponse(String expectedMessage, MvcResult mvcResult) throws UnsupportedEncodingException {
        String messageResponse = mvcResult.getResponse().getContentAsString();
        assertEquals(expectedMessage, messageResponse);
    }

    // Helper method to convert dto to json string
    private String json(Object o) throws IOException {
        MockHttpOutputMessage mockHttpOutputMessage = new MockHttpOutputMessage();
        mappingJackson2HttpMessageConverter.write(
                o, MediaType.APPLICATION_JSON, mockHttpOutputMessage);
        return mockHttpOutputMessage.getBodyAsString();
    }

}
