package com.knowledge.library.dto.request;
import com.knowledge.library.util.ExceptionConstants;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class LinkKnowledgeRequest {

    @NotBlank(message = ExceptionConstants.MANDATORY_TITLE)
    private String title;

    @NotBlank(message = ExceptionConstants.MANDATORY_DESCRIPTION)
    private String description;

    @NotBlank(message = ExceptionConstants.MANDATORY_URL)
    @Pattern(regexp = "https?://.*",message = ExceptionConstants.INVALID_URL_FORMAT)
    private String url;
}


