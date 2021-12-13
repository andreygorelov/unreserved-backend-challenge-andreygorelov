package com.unreserved.challenge.repository;

import com.unreserved.challenge.domain.Customer;
import com.unreserved.challenge.domain.Listing;
import com.unreserved.challenge.domain.ReservedPriceByCity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, String> {
}
