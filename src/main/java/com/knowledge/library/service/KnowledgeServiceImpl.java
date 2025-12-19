package com.knowledge.library.service;

import com.knowledge.library.domain.*;
import com.knowledge.library.dto.KnowledgeResponse;
import com.knowledge.library.exceptions.BadRequestException;
import com.knowledge.library.repository.KnowledgeRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

@Service
@Transactional
public class KnowledgeServiceImpl implements KnowledgeService {

    private final KnowledgeRepository knowledgeRepository;

    public KnowledgeServiceImpl(KnowledgeRepository knowledgeRepository) {
        this.knowledgeRepository = knowledgeRepository;
    }

    @Override
    public Knowledge createText(String title, String description, String content) {
        TextKnowledge knowledge = new TextKnowledge(title, description, content);
        return knowledgeRepository.save(knowledge);
    }

    @Override
    public Knowledge createLink(String title, String description, String url) {
        LinkKnowledge knowledge = new LinkKnowledge(title, description, url);
        return knowledgeRepository.save(knowledge);
    }

    @Override
    public Knowledge createQuote(String title, String description, String quoteText, String author) {
        QuoteKnowledge knowledge =
                new QuoteKnowledge(title, description, quoteText, author);
        return knowledgeRepository.save(knowledge);
    }

    @Override
    public Knowledge createComposite(
            String title,
            String description,
            Set<Long> childKnowledgeIds) {

        CompositeKnowledge composite =
                new CompositeKnowledge(title, description);

        childKnowledgeIds.forEach(id -> {
            Knowledge child = getById(id);
            composite.addItem(child);
        });

        return knowledgeRepository.save(composite);
    }

    @Override
    public List<Knowledge> getAll() {
        return knowledgeRepository.findAll();
    }

    @Override
    public Knowledge getById(Long id) {
        return knowledgeRepository.findById(id)
                .orElseThrow(() ->
                        new BadRequestException("Knowledge not found with id: " + id));
    }

    @Override
    public void delete(Long id) {
        knowledgeRepository.deleteById(id);
    }
}

