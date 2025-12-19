package com.knowledge.library.domain;

import jakarta.persistence.Entity;
import lombok.Getter;

@Entity
@Getter
public class LinkKnowledge extends Knowledge {

    private String url;

    protected LinkKnowledge() {}

    public LinkKnowledge(String title, String description, String url) {
        super(title, description);
        this.url = url;
    }


}

