package com.knowledge.library.service;


import com.knowledge.library.domain.Knowledge;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

public interface KnowledgeService {
    Knowledge createText(String title, String description, String content);

    Knowledge createLink(String title, String description, String url);

    Knowledge createQuote(String title, String description, String quoteText, String author);

    Knowledge createComposite(String title, String description, Set<Long> childKnowledgeIds);

    List<Knowledge> getAll();

    Knowledge getById(Long id);

    void delete(Long id);

}
