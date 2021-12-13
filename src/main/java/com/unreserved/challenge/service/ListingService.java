package com.unreserved.challenge.service;

import com.unreserved.challenge.domain.Listing;
import com.unreserved.challenge.exception.ListingNotFoundException;
import com.unreserved.challenge.model.ListingBo;
import com.unreserved.challenge.model.mapper.ListingEntityMapper;
import com.unreserved.challenge.repository.ListingRepository;
import com.unreserved.challenge.repository.specification.SearchField;
import com.unreserved.challenge.repository.specification.SpecificationsBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ListingService {

    private final ListingRepository listingRepository;
    private final ListingEntityMapper listingEntityMapper;

    public ListingBo getById(String id) {
        Optional<Listing> listing = listingRepository.findById(id);
        if (listing.isEmpty()) {
            throw new ListingNotFoundException("Listing for this property not found!", id);
        }
        return listingEntityMapper.map(listing.get());
    }

    public ListingBo saveListing(ListingBo listingBo) {
        listingBo.setId(UUID.randomUUID().toString());
        Listing listings = listingRepository.save(listingEntityMapper.map(listingBo));
        return listingEntityMapper.map(listings);
    }

    public List<ListingBo> getListings(List<SearchField> fields, Pageable pageable) {
        SpecificationsBuilder<Listing> builder = new SpecificationsBuilder<>(fields);
        Specification<Listing> spec = builder.build();
        Page<Listing> listingPage = listingRepository.findAll(spec, pageable);
        return listingPage.getContent().stream().map(listingEntityMapper::map).collect(Collectors.toList());
    }
}
