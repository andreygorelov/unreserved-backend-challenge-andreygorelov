package com.unreserved.challenge.repository.specification;

import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class SpecificationsBuilder<T> {

    private final List<SearchField> fields;

    private final boolean distinct;

    public SpecificationsBuilder(List<SearchField> fields) {
        this(fields, false);
    }

    public SpecificationsBuilder(List<SearchField> fields, boolean distinct) {
        this.fields = fields;
        this.distinct = distinct;
    }

    public Specification<T> build() {
        if (fields.isEmpty()) {
            return null;
        }

        List<Specification<T>> specs = new ArrayList<>();
        for (SearchField field : fields) {
            if (distinct) {
                specs.add(new DistinctSearchSpecification<>(field));
            } else {
                specs.add(new SearchSpecification<>(field));
            }
        }

        if (specs.isEmpty()) {
            return null;
        }

        Specification<T> result = specs.get(0);
        for (int i = 1; i < specs.size(); i++) {
            result = Specification.where(result).and(specs.get(i));
        }

        return result;
    }
}
