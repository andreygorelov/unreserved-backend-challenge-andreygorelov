package com.unreserved.challenge;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
@EntityScan(basePackages = "com.unreserved.challenge")
@EnableJpaRepositories(basePackages="com.unreserved.challenge")
public class UnreservedApplication {

    public static void main(String[] args) {
        new SpringApplicationBuilder(UnreservedApplication.class).run(args);
    }
}
