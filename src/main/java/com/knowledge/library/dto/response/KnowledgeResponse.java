package com.knowledge.library.dto.response;

import com.knowledge.library.domain.LinkKnowledge;
import com.knowledge.library.domain.QuoteKnowledge;
import com.knowledge.library.domain.TextKnowledge;
import lombok.Data;

import java.util.List;

@Data
public class KnowledgeResponse {
    private Long totalPages;
    private Integer pageNumber;
    private List<LinkKnowledgeResponse> linkKnowledgeList;
    private List<TextKnowledgeResponse> textKnowledgeList;
    private List<QuoteKnowledgeResponse> quoteKnowledgeList;
}
