package com.unreserved.challenge.model.mapper;

import com.unreserved.challenge.domain.Listing;
import com.unreserved.challenge.model.ListingBo;
import com.unreserved.challenge.rest.dto.ListingDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ListingEntityMapper {
    ListingBo map(Listing source);

    Listing map(ListingBo source);
}
