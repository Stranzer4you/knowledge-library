package com.knowledge.library.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

import java.util.Set;

@Data
public class CompositeKnowledgeRequest {
    @NotBlank
    private String title;

    @NotBlank
    private String description;

    @NotEmpty
    private Set<Long> knowledgeIds;
}
