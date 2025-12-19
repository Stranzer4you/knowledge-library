package com.knowledge.library.dto.request;

import com.knowledge.library.util.ExceptionConstants;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;


@Data
public class TextKnowledgeRequest {

    @NotBlank(message = ExceptionConstants.MANDATORY_TITLE)
    private String title;

    @NotBlank(message = ExceptionConstants.MANDATORY_DESCRIPTION)
    private String description;

    @NotBlank(message = ExceptionConstants.MANDATORY_CONTENT)
    private String content;
}
