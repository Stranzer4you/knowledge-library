package com.knowledge.library.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.Getter;

@Entity
@Getter
public class TextKnowledge extends Knowledge {

    @Column(length = 5000)
    private String content;

    protected TextKnowledge() {}

    public TextKnowledge(String title, String description, String content) {
        super(title, description);
        this.content = content;
    }
}

