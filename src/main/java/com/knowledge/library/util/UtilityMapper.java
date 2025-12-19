package com.knowledge.library.util;
import com.knowledge.library.domain.LinkKnowledge;
import com.knowledge.library.domain.QuoteKnowledge;
import com.knowledge.library.domain.TextKnowledge;
import com.knowledge.library.dto.response.LinkKnowledgeResponse;
import com.knowledge.library.dto.response.QuoteKnowledgeResponse;
import com.knowledge.library.dto.response.TextKnowledgeResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UtilityMapper {
    LinkKnowledgeResponse convertLinkDomainToResponse(LinkKnowledge knowledge);

    QuoteKnowledgeResponse convertQuoteDomainToResponse(QuoteKnowledge knowledge);

    TextKnowledgeResponse convertTextDomainToResponse(TextKnowledge knowledge);
}
