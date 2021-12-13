package com.unreserved.challenge.repository.specification;

import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

public class SearchSpecification<P> implements Specification<P> {

    private SearchField field;

    public SearchSpecification(SearchField field) {
        this.field = field;
    }

    public SearchField<?> getSearchField() {
        return this.field;
    }

    @Override
    public Predicate toPredicate(Root<P> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
        if (field.getValue() == null && field.getOperation() == SearchField.Operation.NOT_EQUALS) {
            return builder.isNotNull(buildPath(root, field.getPath()));
        } else if (field.getValue() == null && field.getOperation() == SearchField.Operation.EQUALS) {
            return builder.isNull(buildPath(root, field.getPath()));
        } else if (field.getOperation() == SearchField.Operation.LIKE) {
            // like searches are case-insensitive
            return builder.like(builder.lower(buildPath(root, field.getPath())),
                    builder.lower(builder.literal("%" + field.getValue() + "%")));
        } else if (field.getOperation() == SearchField.Operation.NOT_EQUALS) {
            return builder.notEqual(buildPath(root, field.getPath()), field.getValue());
        } else if (field.getOperation() == SearchField.Operation.IN_LIST) {
            List<Predicate> predicates = new ArrayList<>();
            for (Object value : field.getValueList()) {
                predicates.add(builder.equal(buildPath(root, field.getPath()), value));
            }
            return builder.or(predicates.toArray(new Predicate[0]));
        }
        return builder.equal(buildPath(root, field.getPath()), field.getValue());
    }

    private Expression buildPath(Root<P> root, SearchPath path) {
        // currently only supports single or double paths
        // but probably could be extended to more?

        if (path.getFields().size() == 1) {
            return root.get(path.getFields().get(0));
        } else if (path.getFields().size() == 2) {
            Join<P, ?> join = root.join(path.getFields().get(0));
            return join.get(path.getFields().get(1));
        } else if (path.getFields().size() > 2) {
            Join<P, ?> join = root.join(path.getFields().get(0));
            List<String> subFields = path.getFields().subList(1, path.getFields().size() - 1);
            for (String subField : subFields) {
                join = join.join(subField);
            }
            return join.get(path.getFields().get(path.getFields().size() - 1));
        }
        return null;
    }
}