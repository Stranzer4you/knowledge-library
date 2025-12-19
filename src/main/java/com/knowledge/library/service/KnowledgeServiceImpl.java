package com.knowledge.library.service;

import com.knowledge.library.domain.*;
import com.knowledge.library.dto.response.KnowledgeResponse;
import com.knowledge.library.dto.response.LinkKnowledgeResponse;
import com.knowledge.library.dto.response.QuoteKnowledgeResponse;
import com.knowledge.library.dto.response.TextKnowledgeResponse;
import com.knowledge.library.exceptions.BadRequestException;
import com.knowledge.library.repository.KnowledgeRepository;
import com.knowledge.library.util.BaseResponse;
import com.knowledge.library.util.BaseResponseUtility;
import com.knowledge.library.util.ExceptionConstants;
import com.knowledge.library.util.UtilityMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class KnowledgeServiceImpl implements KnowledgeService {

    private final KnowledgeRepository knowledgeRepository;
    private final UtilityMapper mapper;

    public KnowledgeServiceImpl(KnowledgeRepository knowledgeRepository, UtilityMapper mapper) {
        this.knowledgeRepository = knowledgeRepository;
        this.mapper = mapper;
    }

    @Override
    public BaseResponse createText(String title, String description, String content) {
        TextKnowledge knowledge = new TextKnowledge(title, description, content);
        knowledge = knowledgeRepository.save(knowledge);
        TextKnowledgeResponse response = mapper.convertTextDomainToResponse(knowledge);
        return BaseResponseUtility.getBaseResponse(response);
    }

    @Override
    public BaseResponse createLink(String title, String description, String url) {
        LinkKnowledge knowledge = new LinkKnowledge(title, description, url);
        knowledge = knowledgeRepository.save(knowledge);
        LinkKnowledgeResponse response = mapper.convertLinkDomainToResponse(knowledge);
        return BaseResponseUtility.getBaseResponse(response);
    }

    @Override
    public BaseResponse createQuote(String title, String description, String quoteText, String author) {
        QuoteKnowledge knowledge = new QuoteKnowledge(title, description, quoteText, author);
        knowledge = knowledgeRepository.save(knowledge);
        QuoteKnowledgeResponse response  = mapper.convertQuoteDomainToResponse(knowledge);
        return BaseResponseUtility.getBaseResponse(response);
    }

    @Override
    @Transactional(rollbackFor = Exception.class, isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED)
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
        List<Knowledge> all = knowledgeRepository.findAll();
        KnowledgeResponse response = new KnowledgeResponse();
        response.setLinkKnowledgeList(
                all.stream()
                        .filter(LinkKnowledge.class::isInstance)
                        .map(LinkKnowledge.class::cast)
                        .map(mapper::convertLinkDomainToResponse)
                        .toList()
        );

        response.setTextKnowledgeList(
                all.stream()
                        .filter(TextKnowledge.class::isInstance)
                        .map(TextKnowledge.class::cast)
                        .map(mapper::convertTextDomainToResponse)
                        .toList()
        );

        response.setQuoteKnowledgeList(
                all.stream()
                        .filter(QuoteKnowledge.class::isInstance)
                        .map(QuoteKnowledge.class::cast)
                        .map(mapper::convertQuoteDomainToResponse)
                        .toList()
        );

        return BaseResponseUtility.getBaseResponse(response);
    }

    @Override
    public BaseResponse delete(Long id) {
        knowledgeRepository.deleteById(id);
        return BaseResponseUtility.getBaseResponse();
    }

    public Knowledge fetchKnowledgeById(Long id) {
        return knowledgeRepository.findById(id)
                .orElseThrow(() ->
                        new BadRequestException(ExceptionConstants.INVALID_KNOWLEDGE_ID));
    }

}

