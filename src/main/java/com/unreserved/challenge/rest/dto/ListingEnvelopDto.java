package com.unreserved.challenge.rest.dto;

import com.unreserved.challenge.model.ListingBo;
import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
@Builder
public class ListingEnvelopDto {
    private Map<String, Object> meta;
    List<ListingBo> listing;
}
