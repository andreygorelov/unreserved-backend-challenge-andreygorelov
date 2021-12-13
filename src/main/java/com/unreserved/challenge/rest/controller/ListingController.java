package com.unreserved.challenge.rest.controller;

import com.unreserved.challenge.domain.ListingType;
import com.unreserved.challenge.repository.specification.SearchField;
import com.unreserved.challenge.repository.specification.SearchPath;
import com.unreserved.challenge.rest.dto.ListingDto;
import com.unreserved.challenge.rest.mapper.ListingDtoMapper;
import com.unreserved.challenge.service.ListingService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/listings")
@Validated
@RequiredArgsConstructor
public class ListingController extends BaseController{

    private final ListingService listingService;
    private final ListingDtoMapper listingDtoMapper;

    @GetMapping("/{id}")
    public ResponseEntity<ListingDto> getOne(@Valid @PathVariable("id") String listingId) {
        return ResponseEntity.status(HttpStatus.OK).body(listingDtoMapper.map(listingService.getById(listingId)));
    }

    @PostMapping("")
    public ResponseEntity<ListingDto> saveListing(@RequestBody ListingDto listingDto) {
        validateListing(listingDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(listingDtoMapper.map(listingService.saveListing(listingDtoMapper.map(listingDto))));
    }

    @GetMapping("")
    public ResponseEntity<Map<String, List<ListingDto>>> getListings(@Valid ListingSearchCriteria listingSearchCriteria) {

        List<SearchField> fields = buildSearchFields(listingSearchCriteria);
        Pageable pageable = PageRequest.of(listingSearchCriteria.getPageNumber(), listingSearchCriteria.getPageSize());
        List<ListingDto> listingDtos =
                (listingService.getListings(fields, pageable).stream().map(listingDtoMapper::map).collect(Collectors.toList()));
        return ResponseEntity.status(HttpStatus.OK).body(Map.of("listings", listingDtos));
    }

    private List<SearchField> buildSearchFields(ListingSearchCriteria listingSearchCriteria) {
        List<SearchField> fields = new ArrayList<>();
        if(listingSearchCriteria.getListingType() != null){
            fields.add(SearchField.eq(SearchPath.path("type"), ListingType.fromValue(listingSearchCriteria.getListingType())));
        }
        if(!CollectionUtils.isEmpty(listingSearchCriteria.getCities())){
            fields.add(SearchField.in(SearchPath.path("orgId"), listingSearchCriteria.getCities()));
        }
        if(listingSearchCriteria.getGte() != null){
            fields.add(SearchField.gte(SearchPath.path("reservedPrice"), listingSearchCriteria.getGte()));
        }
        if(listingSearchCriteria.getLte() != null){
            fields.add(SearchField.lte(SearchPath.path("reservedPrice"), listingSearchCriteria.getLte()));
        }
        return fields;
    }
}
