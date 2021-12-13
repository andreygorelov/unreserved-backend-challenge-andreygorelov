package com.unreserved.challenge.repository;

import com.unreserved.challenge.domain.Listing;
import com.unreserved.challenge.domain.ReservedPriceByCity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.persistence.Tuple;
import java.math.BigDecimal;
import java.util.List;

@Repository
public interface ListingRepository extends JpaRepository<Listing, String> {

    Page<Listing> findAll(Specification<Listing> spec, Pageable pageable);

    // Average reserve price by listings city.
    // Should be ordered by the number DESC
    // and show only cities where the number is higher than 1,000,000(threshold value may be changed in the future).
    @Query("SELECT l.city AS city, AVG (l.reservePrice) AS average_price from Listing l where l.reservePrice > :threshold group by city order by average_price DESC")
    List<Tuple> getAveragePriceByCity(@Param("threshold") BigDecimal threshold);

    // Count of interested customers aggregated by listing building name(condos only), sorted by the count DESC.
    @Query("SELECT b.name AS building_name, COUNT(lc.customerId) AS count_customer FROM ListingCustomer lc, Building b " +
            "JOIN Listing l ON lc.listingId = l.id and b.id = l.buildingId GROUP BY b.name ORDER BY count_customer DESC")
    List<Tuple> getCustomerCountByBuilding();

}
