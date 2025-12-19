package com.knowledge.library.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;


@Data
public class TextKnowledgeRequest {

    @NotBlank
    private String title;

    @NotBlank
    private String description;

    @NotBlank
    @Size(max = 5000)
    private String content;
}
