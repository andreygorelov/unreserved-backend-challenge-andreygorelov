package com.unreserved.challenge.rest.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.unreserved.challenge.model.ListingBo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(value = JsonInclude.Include.NON_NULL)
public class CustomerDto {
    private String id;
    @NotBlank
    private String email;
    private LocalDateTime createdAt;
    private List<ListingBo> listingsInterested;
}
