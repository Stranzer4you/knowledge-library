package com.knowledge.library.dto;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class LinkKnowledgeRequest {

    @NotBlank
    private String title;

    @NotBlank
    private String description;

    @NotBlank
    @Pattern(regexp = "https?://.*",message = "URL must be valid (http/https)")
    private String url;
}


