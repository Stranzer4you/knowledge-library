package com.knowledge.library.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.Getter;

@Entity
@Getter
public class QuoteKnowledge extends Knowledge {

    @Column(length = 1000)
    private String quoteText;

    private String author;

    protected QuoteKnowledge() {}

    public QuoteKnowledge(String title, String description, String quoteText, String author) {
        super(title, description);
        this.quoteText = quoteText;
        this.author = author;
    }
}

