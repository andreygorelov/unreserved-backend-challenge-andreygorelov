package com.unreserved.challenge.configuration;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.math.BigDecimal;

@Data
@Configuration
@ConfigurationProperties(prefix = "unreserved.props")
public class UnreservedProperties {
    private BigDecimal reservedPriceThreshold;
}
