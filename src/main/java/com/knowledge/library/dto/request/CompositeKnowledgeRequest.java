package com.knowledge.library.dto.request;

import com.knowledge.library.util.ExceptionConstants;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

import java.util.Set;

@Data
public class CompositeKnowledgeRequest {
    @NotBlank(message = ExceptionConstants.MANDATORY_TITLE)
    private String title;

    @NotBlank(message = ExceptionConstants.MANDATORY_DESCRIPTION)
    private String description;

    @NotEmpty(message = ExceptionConstants.MANDATORY_KNOWLEDGE_IDS)
    private Set<Long> knowledgeIds;
}
