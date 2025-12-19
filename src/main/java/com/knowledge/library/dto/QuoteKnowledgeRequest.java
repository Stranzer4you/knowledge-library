package com.knowledge.library.dto;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class QuoteKnowledgeRequest {
    @NotBlank
    private String title;

    @NotBlank
    private String description;

    @NotBlank
    @Size(max = 1000)
    private String quoteText;

    @NotBlank
    private String author;
}
