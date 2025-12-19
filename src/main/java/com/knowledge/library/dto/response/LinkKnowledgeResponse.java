package com.knowledge.library.dto.response;

import lombok.Data;

@Data
public class LinkKnowledgeResponse {
    private Long id;
    private String title;
    private String description;
    private String url;
}
