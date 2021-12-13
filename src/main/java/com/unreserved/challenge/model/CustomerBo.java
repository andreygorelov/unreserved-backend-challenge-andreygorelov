package com.unreserved.challenge.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CustomerBo {
    private String id;
    private LocalDateTime createdAt;
    private String email;
    private List<ListingBo> listingsInterested;
}
