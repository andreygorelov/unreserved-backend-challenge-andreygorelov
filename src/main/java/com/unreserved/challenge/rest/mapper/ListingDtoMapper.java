package com.unreserved.challenge.rest.mapper;

import com.unreserved.challenge.model.ListingBo;
import com.unreserved.challenge.rest.dto.ListingDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ListingDtoMapper {
    ListingDto map(ListingBo source);

    ListingBo map(ListingDto source);
}
