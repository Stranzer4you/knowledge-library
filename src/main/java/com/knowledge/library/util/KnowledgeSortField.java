package com.knowledge.library.util;

import com.knowledge.library.exceptions.BadRequestException;
import lombok.Data;
import lombok.Getter;

import java.util.Arrays;


@Getter
public enum KnowledgeSortField {
    CREATED_AT("createdAt"),
    TITLE("title");

    private final String field;

    KnowledgeSortField(String field) {
        this.field = field;
    }

    public static KnowledgeSortField from(String value) {
        return Arrays.stream(values())
                .filter(v -> v.name().equalsIgnoreCase(value))
                .findFirst()
                .orElseThrow(() ->
                        new BadRequestException(ExceptionConstants.INVALID_SORT_FIELD));
    }
}
