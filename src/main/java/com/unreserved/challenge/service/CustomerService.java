package com.unreserved.challenge.service;

import com.unreserved.challenge.domain.Customer;
import com.unreserved.challenge.model.CustomerBo;
import com.unreserved.challenge.model.mapper.CustomerEntityMapper;
import com.unreserved.challenge.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CustomerService {
    private final CustomerRepository customerRepository;
    private final CustomerEntityMapper customerEntityMapper;

    public CustomerBo saveCustomer(CustomerBo customerBo) {
        customerBo.setCreatedAt(LocalDateTime.now());
        customerBo.setId(UUID.randomUUID().toString());
        return customerEntityMapper.map(customerRepository.save(customerEntityMapper.map(customerBo)));
    }

    @Transactional
    public List<CustomerBo> getCustomers(Sort sort) {
        List<Customer> customers = customerRepository.findAll(sort);
        return customers.stream().map(customerEntityMapper::map).collect(Collectors.toList());
    }
}
