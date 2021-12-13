package com.unreserved.challenge.repository.specification;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class SearchField<T> {

    public enum Operation {
        EQUALS,
        NOT_EQUALS,
        LIKE,
        IN_LIST,
        GREATER_OR_EQUAL,
        LESS_OR_EQUAL,
    }

    private SearchPath path;
    private SearchField.Operation operation;

    private T value;
    private List<T> valueList;

    private SearchField(SearchPath path, SearchField.Operation operation, T value) {
        this.path = path;
        this.operation = operation;
        this.value = value;
    }

    private SearchField(SearchPath path, SearchField.Operation operation, List<T> valueList) {
        this.path = path;
        this.operation = operation;
        this.valueList = valueList;
    }

    // Can be extended
    public static <T> SearchField<T> eq(SearchPath path, T value) {
        return new SearchField<>(path, SearchField.Operation.EQUALS, value);
    }

    public static <T> SearchField<T> in(SearchPath path, List<T> valueList) {
        return new SearchField<>(path, SearchField.Operation.IN_LIST, valueList);
    }

    public static <T> SearchField<T> gte(SearchPath path, T value) {
        return new SearchField<>(path, SearchField.Operation.GREATER_OR_EQUAL, value);
    }

    public static <T> SearchField<T> lte(SearchPath path, T value) {
        return new SearchField<>(path, SearchField.Operation.LESS_OR_EQUAL, value);
    }
}