package com.unreserved.challenge.service;

import com.unreserved.challenge.configuration.UnreservedProperties;
import com.unreserved.challenge.domain.ReservedPriceByCity;
import com.unreserved.challenge.repository.ListingRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.persistence.Tuple;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class StatsService {

    private final ListingRepository listingRepository;
    private final UnreservedProperties properties;

    /**
     * Print stats every minute at info
     */
    @Scheduled(fixedRate = 60 * 1000)
    public void getStats() {
        List<Tuple> pricesByCity = listingRepository.getAveragePriceByCity(properties.getReservedPriceThreshold());
        log.info("Average listing reserve price by city:");
        pricesByCity.forEach(tuple -> log.info(tuple.get("city") + " : " + tuple.get("average_price")));


       List<Tuple> customerCountByBuilding = listingRepository.getCustomerCountByBuilding();
       log.info("Number of interested customers by listing building name:");
       customerCountByBuilding.forEach(tuple -> log.info(tuple.get("building_name") + " : " + tuple.get("count_customer")));
    }
}
