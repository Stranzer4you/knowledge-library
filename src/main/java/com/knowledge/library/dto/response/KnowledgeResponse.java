package com.knowledge.library.dto.response;

import lombok.Data;

import java.util.List;

@Data
public class KnowledgeResponse {
    private Long id;
    private String type;
    private String title;
    private String description;

    private List<Long> itemIds;

}
