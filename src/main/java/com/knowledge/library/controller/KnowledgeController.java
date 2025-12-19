package com.knowledge.library.controller;

import com.knowledge.library.domain.Knowledge;
import com.knowledge.library.dto.CompositeKnowledgeRequest;
import com.knowledge.library.dto.LinkKnowledgeRequest;
import com.knowledge.library.dto.QuoteKnowledgeRequest;
import com.knowledge.library.dto.TextKnowledgeRequest;
import com.knowledge.library.service.KnowledgeService;
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
    public Knowledge createText(@Valid @RequestBody TextKnowledgeRequest request) {
        return knowledgeService.createText(
                request.getTitle(),
                request.getDescription(),
                request.getContent()
        );
    }

    @PostMapping("/link")
    public Knowledge createLink(@Valid @RequestBody LinkKnowledgeRequest request) {
        return knowledgeService.createLink(
                request.getTitle(),
                request.getDescription(),
                request.getUrl()
        );
    }

    @PostMapping("/quote")
    public Knowledge createQuote(@Valid @RequestBody QuoteKnowledgeRequest request) {
        return knowledgeService.createQuote(
                request.getTitle(),
                request.getDescription(),
                request.getQuoteText(),
                request.getAuthor()
        );
    }

    @PostMapping("/composite")
    public Knowledge createComposite(
            @Valid @RequestBody CompositeKnowledgeRequest request) {

        return knowledgeService.createComposite(
                request.getTitle(),
                request.getDescription(),
                request.getKnowledgeIds()
        );
    }

    @GetMapping
    public List<Knowledge> getAll() {
        return knowledgeService.getAll();
    }

    @GetMapping("/{id}")
    public Knowledge getById(@PathVariable Long id) {
        return knowledgeService.getById(id);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        knowledgeService.delete(id);
    }
}
