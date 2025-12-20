package com.knowledge.library.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import com.knowledge.library.dto.request.*;
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
    public ResponseEntity<BaseResponse> createText(@Valid @RequestBody TextKnowledgeRequest request) {
        BaseResponse response = knowledgeService.createText(request.getTitle(), request.getDescription(), request.getContent());
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("/link")
    public ResponseEntity<BaseResponse> createLink(@Valid @RequestBody LinkKnowledgeRequest request) {
        BaseResponse response = knowledgeService.createLink(request.getTitle(), request.getDescription(), request.getUrl());
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("/quote")
    public ResponseEntity<BaseResponse> createQuote(@Valid @RequestBody QuoteKnowledgeRequest request) {
        BaseResponse response = knowledgeService.createQuote(request.getTitle(), request.getDescription(), request.getQuoteText(), request.getAuthor());
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("/composite")
    public ResponseEntity<BaseResponse> createComposite(@Valid @RequestBody CompositeKnowledgeRequest request) {
        BaseResponse response = knowledgeService.createComposite(request.getTitle(), request.getDescription(), request.getKnowledgeIds());
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public ResponseEntity<BaseResponse> getAll(@Valid @ModelAttribute KnowledgePageRequest request) {
        BaseResponse response =  knowledgeService.getAll(request);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BaseResponse> getById(@PathVariable Long id) {
        BaseResponse response =  knowledgeService.getById(id);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<BaseResponse> delete(@PathVariable Long id) {
        BaseResponse response =  knowledgeService.delete(id);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
