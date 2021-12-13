package com.unreserved.challenge.repository.specification;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Singular;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SearchPath {
    @Singular
    private List<String> fields;
    public static SearchPath path(String field) {
        return SearchPath.builder().field(field).build();
    }
    public String buildPath() {
        return String.join(".", fields);
    }
}