package com.knowledge.library.dto.response;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class LinkKnowledgeResponse {
    private Long id;
    private String title;
    private String description;
    private String url;

    public LinkKnowledgeResponse(String title, String description, String url) {
        this.title = title;
        this.description = description;
        this.url = url;
    }
}
