package com.knowledge.library.service;

import com.knowledge.library.domain.*;
import com.knowledge.library.dto.request.KnowledgePageRequest;
import com.knowledge.library.dto.response.KnowledgeResponse;
import com.knowledge.library.dto.response.LinkKnowledgeResponse;
import com.knowledge.library.dto.response.QuoteKnowledgeResponse;
import com.knowledge.library.dto.response.TextKnowledgeResponse;
import com.knowledge.library.exceptions.BadRequestException;
import com.knowledge.library.exceptions.ConflictException;
import com.knowledge.library.exceptions.ResourceNotFoundException;
import com.knowledge.library.repository.KnowledgeRepository;
import com.knowledge.library.util.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class KnowledgeServiceImpl implements KnowledgeService {

    @Value("${knowledge.pagination.page-size}")
    private int pageSize;


    private final KnowledgeRepository knowledgeRepository;
    private final UtilityMapper mapper;

    public KnowledgeServiceImpl(KnowledgeRepository knowledgeRepository, UtilityMapper mapper) {
        this.knowledgeRepository = knowledgeRepository;
        this.mapper = mapper;
    }

    @Override
    public BaseResponse createText(String title, String description, String content) {
        checkForExistingKnowledge(KnowledgeType.TextKnowledge.name(),title);
        TextKnowledge knowledge = new TextKnowledge(title, description, content);
        knowledge = knowledgeRepository.save(knowledge);
        TextKnowledgeResponse response = mapper.convertTextDomainToResponse(knowledge);
        return BaseResponseUtility.getBaseResponse(response);
    }

    @Override
    public BaseResponse createLink(String title, String description, String url) {
        checkForExistingKnowledge(KnowledgeType.LinkKnowledge.name(),title);
        LinkKnowledge knowledge = new LinkKnowledge(title, description, url);
        knowledge = knowledgeRepository.save(knowledge);
        LinkKnowledgeResponse response = mapper.convertLinkDomainToResponse(knowledge);
        return BaseResponseUtility.getBaseResponse(response);
    }

    @Override
    public BaseResponse createQuote(String title, String description, String quoteText, String author) {
        checkForExistingKnowledge(KnowledgeType.QuoteKnowledge.name(),title);
        QuoteKnowledge knowledge = new QuoteKnowledge(title, description, quoteText, author);
        knowledge = knowledgeRepository.save(knowledge);
        QuoteKnowledgeResponse response = mapper.convertQuoteDomainToResponse(knowledge);
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
    public BaseResponse getAll(KnowledgePageRequest request) {

        long totalRecords = knowledgeRepository.count();
        Long totalPages = (long) Math.ceil((double) totalRecords / pageSize);

        KnowledgeSortField sortField = KnowledgeSortField.from(request.getSortBy());

        Pageable pageable = PageRequest.of(request.getPageNo() - 1, pageSize, Sort.by(resolveSortDirection(request.getSortOrder()), sortField.getField()));
        Page<Knowledge> page;

        if (request.getType() != null) {
            KnowledgeType knowledgeType = KnowledgeType.from(request.getType());
            page = knowledgeRepository.findByKnowledgeType(knowledgeType.name(), pageable);
        } else {
            page = knowledgeRepository.findAll(pageable);
        }

        KnowledgeResponse response = new KnowledgeResponse();
        response.setPageNumber(request.getPageNo());
        response.setTotalPages(totalPages);

        List<Knowledge> knowledgeList = page.getContent();
        Map<KnowledgeType, List<Knowledge>> groupedByType =
                knowledgeList.stream()
                        .collect(Collectors.groupingBy(
                                k -> KnowledgeType.from(k.getKnowledgeType())
                        ));


        response.setLinkKnowledgeList(
                groupedByType
                        .getOrDefault(KnowledgeType.LinkKnowledge, List.of())
                        .stream()
                        .map(k -> mapper.convertLinkDomainToResponse((LinkKnowledge) k))
                        .toList()
        );

        response.setTextKnowledgeList(
                groupedByType
                        .getOrDefault(KnowledgeType.TextKnowledge, List.of())
                        .stream()
                        .map(k -> mapper.convertTextDomainToResponse((TextKnowledge) k))
                        .toList()
        );

        response.setQuoteKnowledgeList(
                groupedByType
                        .getOrDefault(KnowledgeType.QuoteKnowledge, List.of())
                        .stream()
                        .map(k -> mapper.convertQuoteDomainToResponse((QuoteKnowledge) k))
                        .toList()
        );

        return BaseResponseUtility.getBaseResponse(response);
    }

    @Override
    public BaseResponse delete(Long id) {
        fetchKnowledgeById(id);
        knowledgeRepository.deleteById(id);
        return BaseResponseUtility.getBaseResponse();
    }

    public Knowledge fetchKnowledgeById(Long id) {
        return knowledgeRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(ExceptionConstants.INVALID_KNOWLEDGE_ID));
    }

    private Sort.Direction resolveSortDirection(String sortOrder) {
        try {
            return Sort.Direction.valueOf(sortOrder.toUpperCase());
        } catch (IllegalArgumentException ex) {
            throw new BadRequestException(
                    ExceptionConstants.INVALID_SORT_ORDER
            );
        }
    }

    public void checkForExistingKnowledge(String type,String title){
        Knowledge existingKnowledge = knowledgeRepository.findByTitleAndKnowledgeType(title,type);
        if(!ObjectUtils.isEmpty(existingKnowledge)){
            throw new ConflictException(ExceptionConstants.KNOWLEDGE_ALREADY_EXISTS);
        }
    }
}

