package com.knowledge.library.dto.request;

import com.knowledge.library.util.ExceptionConstants;
import com.knowledge.library.util.KnowledgeSortField;
import com.knowledge.library.util.KnowledgeType;
import jakarta.validation.constraints.Min;
import lombok.Data;

@Data
public class KnowledgePageRequest {
    @Min(value = 1, message = ExceptionConstants.PAGE_NUMBER_MUST_BE_GREATER_THAN_EQUAL_ONE)
    private Integer pageNo = 1;

    private String sortBy = KnowledgeSortField.CREATED_AT.name();
    private String sortOrder = "DESC";
    private String type;
}
