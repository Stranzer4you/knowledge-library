package com.knowledge.library.util;

import com.knowledge.library.exceptions.BadRequestException;

import java.util.Arrays;

public enum KnowledgeType {
    LinkKnowledge,
    TextKnowledge,
    QuoteKnowledge;

    public static  KnowledgeType from(String value) {
        return Arrays.stream(values())
                .filter(v -> v.name().equalsIgnoreCase(value))
                .findFirst()
                .orElseThrow(() ->
                        new BadRequestException(ExceptionConstants.INVALID_KNOWLEDGE_TYPE));
    }

    }