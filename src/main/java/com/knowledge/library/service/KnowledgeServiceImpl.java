package com.knowledge.library.service;

import com.knowledge.library.domain.*;
import com.knowledge.library.exceptions.BadRequestException;
import com.knowledge.library.repository.KnowledgeRepository;
import com.knowledge.library.util.BaseResponse;
import com.knowledge.library.util.BaseResponseUtility;
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
    public BaseResponse createText(String title, String description, String content) {
        TextKnowledge knowledge = new TextKnowledge(title, description, content);
        knowledge = knowledgeRepository.save(knowledge);
        return BaseResponseUtility.getBaseResponse(knowledge);
    }

    @Override
    public BaseResponse createLink(String title, String description, String url) {
        LinkKnowledge knowledge = new LinkKnowledge(title, description, url);
        knowledge = knowledgeRepository.save(knowledge);
        return BaseResponseUtility.getBaseResponse(knowledge);
    }

    @Override
    public BaseResponse createQuote(String title, String description, String quoteText, String author) {
        QuoteKnowledge knowledge = new QuoteKnowledge(title, description, quoteText, author);
        knowledge = knowledgeRepository.save(knowledge);
        return BaseResponseUtility.getBaseResponse(knowledge);
    }

    @Override
    public BaseResponse createComposite(String title, String description, Set<Long> childKnowledgeIds) {
        CompositeKnowledge composite = new CompositeKnowledge(title, description);
        CompositeKnowledge finalComposite = composite;
        childKnowledgeIds.forEach(id -> {
            Knowledge child = fetchKnowledgeById(id);
            finalComposite.addItem(child);
        });

        composite = knowledgeRepository.save(composite);
        return BaseResponseUtility.getBaseResponse(composite);
    }

    @Override
    public BaseResponse getById(Long id) {
        Knowledge knowledge = fetchKnowledgeById(id);
        return BaseResponseUtility.getBaseResponse(knowledge);
    }

    @Override
    public BaseResponse getAll() {
        return BaseResponseUtility.getBaseResponse(knowledgeRepository.findAll());
    }

    @Override
    public BaseResponse delete(Long id) {
        knowledgeRepository.deleteById(id);
        return BaseResponseUtility.getBaseResponse();
    }

    public Knowledge fetchKnowledgeById(Long id) {
        return knowledgeRepository.findById(id)
                .orElseThrow(() ->
                        new BadRequestException("Knowledge not found with id: " + id));
    }

}

