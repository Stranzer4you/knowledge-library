package com.knowledge.library.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@Entity
public class CompositeKnowledge extends Knowledge {

    @ManyToMany
    @JoinTable(
            name = "composite_knowledge_items",
            joinColumns = @JoinColumn(name = "composite_id"),
            inverseJoinColumns = @JoinColumn(name = "child_knowledge_id")
    )
    private Set<Knowledge> items = new HashSet<>();

    protected CompositeKnowledge() {}

    public CompositeKnowledge(String title, String description) {
        super(title, description);
    }

    public void addItem(Knowledge knowledge) {
        this.items.add(knowledge);
    }

    public Set<Knowledge> getItems() {
        return Collections.unmodifiableSet(items);
    }
}
