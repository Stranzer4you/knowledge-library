package com.knowledge.library.dto.request;


import com.knowledge.library.util.ExceptionConstants;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class QuoteKnowledgeRequest {
    @NotBlank(message = ExceptionConstants.MANDATORY_TITLE)
    private String title;

    @NotBlank(message = ExceptionConstants.MANDATORY_DESCRIPTION)
    private String description;

    @NotBlank(message = ExceptionConstants.MANDATORY_QUOTE_TEXT)
    private String quoteText;

    @NotBlank(message = ExceptionConstants.MANDATORY_AUTHOR)
    private String author;
}
