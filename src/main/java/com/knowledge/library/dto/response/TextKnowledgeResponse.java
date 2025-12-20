package com.knowledge.library.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class TextKnowledgeResponse {
    private Long id;
    private String title;
    private String description;
    private String content;

    public TextKnowledgeResponse(String title, String description, String content) {
        this.title = title;
        this.description = description;
        this.content = content;
    }
}
