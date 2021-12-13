package com.unreserved.challenge.repository.specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

public class DistinctSearchSpecification<P> extends SearchSpecification<P> {

    public DistinctSearchSpecification(SearchField field) {
        super(field);
    }

    @Override
    public Predicate toPredicate(Root<P> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
        query.distinct(true);
        return super.toPredicate(root, query, builder);
    }
}
