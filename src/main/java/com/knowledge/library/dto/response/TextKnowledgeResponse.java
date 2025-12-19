package com.knowledge.library.dto.response;

import lombok.Data;

@Data
public class TextKnowledgeResponse {
    private Long id;
    private String title;
    private String description;
    private String content;
}
