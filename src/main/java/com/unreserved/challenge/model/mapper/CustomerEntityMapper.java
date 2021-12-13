package com.unreserved.challenge.model.mapper;

import com.unreserved.challenge.domain.Customer;
import com.unreserved.challenge.model.CustomerBo;
import com.unreserved.challenge.rest.dto.CustomerDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CustomerEntityMapper {
    CustomerBo map(Customer source);

    Customer map(CustomerBo source);
}
