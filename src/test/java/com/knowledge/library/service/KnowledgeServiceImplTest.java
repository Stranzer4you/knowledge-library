package com.knowledge.library.service;

import com.knowledge.library.domain.*;
import com.knowledge.library.dto.request.KnowledgePageRequest;
import com.knowledge.library.dto.response.LinkKnowledgeResponse;
import com.knowledge.library.dto.response.QuoteKnowledgeResponse;
import com.knowledge.library.dto.response.TextKnowledgeResponse;
import com.knowledge.library.exceptions.ConflictException;
import com.knowledge.library.exceptions.ResourceNotFoundException;
import com.knowledge.library.repository.KnowledgeRepository;
import com.knowledge.library.util.BaseResponse;
import com.knowledge.library.util.ExceptionConstants;
import com.knowledge.library.util.KnowledgeType;
import com.knowledge.library.util.UtilityMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class KnowledgeServiceImplTest {

    @Mock
    private KnowledgeRepository knowledgeRepository;

    @InjectMocks
    private KnowledgeServiceImpl knowledgeService;

    @Mock
    private UtilityMapper mapper;


    @Test
    void shouldCreateTextSuccessfully() {
        String title = "Java Basics";
        String description = "Intro to Java";
        String content = "Java is OOP";

        TextKnowledge savedEntity = new TextKnowledge(title, description, content);

        TextKnowledgeResponse responseDto = new TextKnowledgeResponse(title, description, content);

        when(knowledgeRepository.save(any(TextKnowledge.class))).thenReturn(savedEntity);

        when(mapper.convertTextDomainToResponse(savedEntity)).thenReturn(responseDto);

        BaseResponse response = knowledgeService.createText(title, description, content);

        assertNotNull(response);
        assertEquals(responseDto, response.getData());

        verify(knowledgeRepository).save(any(TextKnowledge.class));
        verify(mapper).convertTextDomainToResponse(savedEntity);
    }

    @Test
    void shouldThrowConflictExceptionWhenTextKnowledgeAlreadyExists() {
        Knowledge existingKnowledge = new TextKnowledge("Java Basics", "desc", "content");
        when(knowledgeRepository.findByTitleAndKnowledgeType("Java Basics", KnowledgeType.TextKnowledge.name())).thenReturn(existingKnowledge);
        ConflictException ex = assertThrows(ConflictException.class, () -> knowledgeService.checkForExistingKnowledge(KnowledgeType.TextKnowledge.name(), "Java Basics"));
        assertEquals(ExceptionConstants.KNOWLEDGE_ALREADY_EXISTS, ex.getMessage());
    }


    @Test
    void shouldCreateLinkSuccessfully() {

        String title = "Spring Docs";
        String description = "Official Spring Documentation";
        String url = "https://spring.io/docs";

        LinkKnowledge savedEntity = new LinkKnowledge(title, description, url);

        LinkKnowledgeResponse responseDto = new LinkKnowledgeResponse(title, description, url);

        when(knowledgeRepository.findByTitleAndKnowledgeType(title, KnowledgeType.LinkKnowledge.name())).thenReturn(null);

        when(knowledgeRepository.save(any(LinkKnowledge.class))).thenReturn(savedEntity);

        when(mapper.convertLinkDomainToResponse(savedEntity)).thenReturn(responseDto);

        BaseResponse response = knowledgeService.createLink(title, description, url);

        assertNotNull(response);
        assertEquals(responseDto, response.getData());

        verify(knowledgeRepository).save(any(LinkKnowledge.class));
        verify(mapper).convertLinkDomainToResponse(savedEntity);
    }

    @Test
    void shouldThrowConflictExceptionWhenLinkKnowledgeAlreadyExists() {
        Knowledge existingKnowledge = new LinkKnowledge("Spring Docs", "desc", "url");
        when(knowledgeRepository.findByTitleAndKnowledgeType("Spring Docs", KnowledgeType.LinkKnowledge.name())).thenReturn(existingKnowledge);
        ConflictException ex = assertThrows(ConflictException.class, () -> knowledgeService.createLink("Spring Docs", "Official Spring Documentation", "https://spring.io/docs"));
        assertEquals(ExceptionConstants.KNOWLEDGE_ALREADY_EXISTS, ex.getMessage());
        verify(knowledgeRepository, never()).save(any());
    }

    @Test
    void shouldCreateQuoteSuccessfully() {

        String title = "Inspiration";
        String description = "Motivational quote";
        String quoteText = "Stay hungry, stay foolish";
        String author = "Steve Jobs";

        QuoteKnowledge savedEntity = new QuoteKnowledge(title, description, quoteText, author);
        QuoteKnowledgeResponse responseDto = new QuoteKnowledgeResponse(title, description, quoteText, author);

        when(knowledgeRepository.findByTitleAndKnowledgeType(title, KnowledgeType.QuoteKnowledge.name())).thenReturn(null);
        when(knowledgeRepository.save(any(QuoteKnowledge.class))).thenReturn(savedEntity);
        when(mapper.convertQuoteDomainToResponse(savedEntity)).thenReturn(responseDto);

        BaseResponse response = knowledgeService.createQuote(title, description, quoteText, author);

        assertNotNull(response);
        assertEquals(responseDto, response.getData());

        verify(knowledgeRepository).save(any(QuoteKnowledge.class));
        verify(mapper).convertQuoteDomainToResponse(savedEntity);
    }

    @Test
    void shouldThrowConflictExceptionWhenQuoteKnowledgeAlreadyExists() {
        Knowledge existingKnowledge = new QuoteKnowledge("Inspiration", "desc", "quote", "author");
        when(knowledgeRepository.findByTitleAndKnowledgeType("Inspiration", KnowledgeType.QuoteKnowledge.name())).thenReturn(existingKnowledge);
        ConflictException ex = assertThrows(ConflictException.class, () -> knowledgeService.createQuote("Inspiration", "Motivational quote", "Stay hungry, stay foolish", "Steve Jobs"));
        assertEquals(ExceptionConstants.KNOWLEDGE_ALREADY_EXISTS, ex.getMessage());
        verify(knowledgeRepository, never()).save(any());
    }


    @Test
    void shouldReturnKnowledgeWhenIdExists() {
        Long id = 1L;
        Knowledge knowledge = new TextKnowledge("Java", "desc", "content");
        when(knowledgeRepository.findById(id)).thenReturn(Optional.of(knowledge));
        BaseResponse response = knowledgeService.getById(id);
        assertNotNull(response);
        assertEquals(knowledge, response.getData());
        verify(knowledgeRepository).findById(id);
    }

    @Test
    void shouldThrowResourceNotFoundExceptionWhenKnowledgeDoesNotExist() {
        Long id = 1L;
        when(knowledgeRepository.findById(id)).thenReturn(Optional.empty());
        ResourceNotFoundException ex = assertThrows(ResourceNotFoundException.class, () -> knowledgeService.getById(id));
        assertEquals(ExceptionConstants.INVALID_KNOWLEDGE_ID, ex.getMessage());
        verify(knowledgeRepository).findById(id);
    }

    @Test
    void shouldDeleteKnowledgeSuccessfully() {

        Long id = 1L;
        Knowledge knowledge = new TextKnowledge("Java", "desc", "content");
        when(knowledgeRepository.findById(id)).thenReturn(Optional.of(knowledge));
        BaseResponse response = knowledgeService.delete(id);
        assertNotNull(response);
        verify(knowledgeRepository).deleteById(id);
    }

    @Test
    void shouldThrowResourceNotFoundExceptionWhenDeletingNonExistingKnowledge() {

        Long id = 1L;
        when(knowledgeRepository.findById(id)).thenReturn(Optional.empty());
        ResourceNotFoundException ex = assertThrows(ResourceNotFoundException.class, () -> knowledgeService.delete(id));
        assertEquals(ExceptionConstants.INVALID_KNOWLEDGE_ID, ex.getMessage());
        verify(knowledgeRepository, never()).deleteById(any());
    }

    @Test
    void shouldReturnAllKnowledgeSuccessfully() {

        ReflectionTestUtils.setField(knowledgeService, "pageSize", 10);

        KnowledgePageRequest request = new KnowledgePageRequest();
        request.setPageNo(1);
        request.setSortBy("TITLE");
        request.setSortOrder("DESC");
        request.setType(null); // generic getAll path

        TextKnowledge text = new TextKnowledge("Java", "desc", "content");
        LinkKnowledge link = new LinkKnowledge("Spring", "desc", "https://spring.io");
        QuoteKnowledge quote = new QuoteKnowledge("Quote", "desc", "Stay hungry", "Jobs");

        List<Knowledge> knowledgeList = List.of(text, link, quote);
        Page<Knowledge> page = new PageImpl<>(knowledgeList);

        when(knowledgeRepository.count()).thenReturn(3L);
        when(knowledgeRepository.findAll(any(Pageable.class))).thenReturn(page);

        when(mapper.convertTextDomainToResponse(any(TextKnowledge.class))).thenReturn(new TextKnowledgeResponse());
        when(mapper.convertLinkDomainToResponse(any(LinkKnowledge.class))).thenReturn(new LinkKnowledgeResponse());
        when(mapper.convertQuoteDomainToResponse(any(QuoteKnowledge.class))).thenReturn(new QuoteKnowledgeResponse());

        BaseResponse response = knowledgeService.getAll(request);

        assertNotNull(response);
        verify(knowledgeRepository).count();
        verify(knowledgeRepository).findAll(any(Pageable.class));
    }


    @Test
    void shouldCreateCompositeSuccessfully() {

        Long childId1 = 1L;
        Long childId2 = 2L;

        Set<Long> childIds = Set.of(childId1, childId2);

        Knowledge child1 = new TextKnowledge("Java", "desc", "content");
        Knowledge child2 = new LinkKnowledge("Spring", "desc", "https://spring.io");

        when(knowledgeRepository.findById(childId1)).thenReturn(Optional.of(child1));
        when(knowledgeRepository.findById(childId2)).thenReturn(Optional.of(child2));
        when(knowledgeRepository.save(any(CompositeKnowledge.class))).thenAnswer(invocation -> invocation.getArgument(0));

        BaseResponse response = knowledgeService.createComposite("Composite", "desc", childIds);

        assertNotNull(response);
        CompositeKnowledge savedComposite = (CompositeKnowledge) response.getData();

        assertEquals(2, savedComposite.getItems().size());
        verify(knowledgeRepository).save(any(CompositeKnowledge.class));
    }


    @Test
    void shouldThrowNotFoundExceptionWhenAnyChildKnowledgeIdIsInvalid() {

        Long validId = 1L;
        Long invalidId = 99L;

        Set<Long> childIds = Set.of(validId, invalidId);

        Knowledge child = new TextKnowledge("Java", "desc", "content");

        when(knowledgeRepository.findById(validId)).thenReturn(Optional.of(child));
        when(knowledgeRepository.findById(invalidId)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> knowledgeService.createComposite("Composite", "desc", childIds));

        verify(knowledgeRepository, never()).save(any());
    }


}