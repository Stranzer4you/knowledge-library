package com.knowledge.library.controller;

import com.knowledge.library.domain.Knowledge;
import com.knowledge.library.dto.request.CompositeKnowledgeRequest;
import com.knowledge.library.dto.request.LinkKnowledgeRequest;
import com.knowledge.library.dto.request.QuoteKnowledgeRequest;
import com.knowledge.library.dto.request.TextKnowledgeRequest;
import com.knowledge.library.service.KnowledgeService;
import com.knowledge.library.util.BaseResponse;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/knowledge")
public class KnowledgeController {

    private final KnowledgeService knowledgeService;

    public KnowledgeController(KnowledgeService knowledgeService) {
        this.knowledgeService = knowledgeService;
    }


    @PostMapping("/text")
    public BaseResponse createText(@Valid @RequestBody TextKnowledgeRequest request) {
        return knowledgeService.createText(
                request.getTitle(),
                request.getDescription(),
                request.getContent()
        );
    }

    @PostMapping("/link")
    public BaseResponse createLink(@Valid @RequestBody LinkKnowledgeRequest request) {
        return knowledgeService.createLink(
                request.getTitle(),
                request.getDescription(),
                request.getUrl()
        );
    }

    @PostMapping("/quote")
    public BaseResponse createQuote(@Valid @RequestBody QuoteKnowledgeRequest request) {
        return knowledgeService.createQuote(
                request.getTitle(),
                request.getDescription(),
                request.getQuoteText(),
                request.getAuthor()
        );
    }

    @PostMapping("/composite")
    public BaseResponse createComposite(
            @Valid @RequestBody CompositeKnowledgeRequest request) {

        return knowledgeService.createComposite(
                request.getTitle(),
                request.getDescription(),
                request.getKnowledgeIds()
        );
    }

    @GetMapping
    public BaseResponse getAll() {
        return knowledgeService.getAll();
    }

    @GetMapping("/{id}")
    public BaseResponse getById(@PathVariable Long id) {
        return knowledgeService.getById(id);
    }

    @DeleteMapping("/{id}")
    public BaseResponse delete(@PathVariable Long id) {
        return knowledgeService.delete(id);
    }
}
