package com.unreserved.challenge.rest.mapper;

import com.unreserved.challenge.model.CustomerBo;
import com.unreserved.challenge.rest.dto.CustomerDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CustomerDtoMapper {
    CustomerDto map(CustomerBo source);

    CustomerBo map(CustomerDto source);
}
