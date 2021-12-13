package com.unreserved.challenge.rest.controller;

import com.unreserved.challenge.rest.dto.CustomerDto;
import com.unreserved.challenge.rest.mapper.CustomerDtoMapper;
import com.unreserved.challenge.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/customers")
@RequiredArgsConstructor
public class CustomerController extends BaseController{

    private final CustomerService customerService;
    private final CustomerDtoMapper customerDtoMapper;

    @PostMapping("")
    public ResponseEntity<CustomerDto> saveCustomer(@RequestBody CustomerDto customerDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(customerDtoMapper.map(customerService.saveCustomer(customerDtoMapper.map(customerDto))));
    }

    @GetMapping("")
    public ResponseEntity<Map<String, List<CustomerDto>>> getCustomers() {
        Sort sort = Sort.by(new Sort.Order(Sort.Direction.DESC, "createdAt"));
        List<CustomerDto> customerDtos =
                (customerService.getCustomers(sort).stream().map(customerDtoMapper::map).collect(Collectors.toList()));
        return ResponseEntity.status(HttpStatus.OK).body(Map.of("customers", customerDtos));
    }
}
