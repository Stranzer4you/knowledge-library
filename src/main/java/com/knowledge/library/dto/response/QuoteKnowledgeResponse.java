package com.knowledge.library.dto.response;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class QuoteKnowledgeResponse {
    private Long id;
    private String title;
    private String description;
    private String quoteText;
    private String author;

    public QuoteKnowledgeResponse(String title, String description, String quoteText, String author) {
        this.title = title;
        this.description = description;
        this.quoteText =  quoteText;
        this.author = author;
    }
}
