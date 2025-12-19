package com.knowledge.library.dto.response;

import lombok.Data;

@Data
public class QuoteKnowledgeResponse {
    private Long id;
    private String title;
    private String description;
    private String quoteText;
    private String author;
}
