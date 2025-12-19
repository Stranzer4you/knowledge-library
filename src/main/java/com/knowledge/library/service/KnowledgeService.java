package com.knowledge.library.service;


import com.knowledge.library.domain.Knowledge;
import com.knowledge.library.util.BaseResponse;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

public interface KnowledgeService {
    BaseResponse createText(String title, String description, String content);

    BaseResponse createLink(String title, String description, String url);

    BaseResponse createQuote(String title, String description, String quoteText, String author);

    BaseResponse createComposite(String title, String description, Set<Long> childKnowledgeIds);

    BaseResponse getAll();

    BaseResponse getById(Long id);

    BaseResponse delete(Long id);

}
