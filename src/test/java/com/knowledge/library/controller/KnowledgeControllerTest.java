package com.knowledge.library.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.knowledge.library.domain.Knowledge;
import com.knowledge.library.domain.TextKnowledge;
import com.knowledge.library.dto.request.TextKnowledgeRequest;
import com.knowledge.library.dto.response.TextKnowledgeResponse;
import com.knowledge.library.service.KnowledgeService;
import com.knowledge.library.util.BaseResponse;
import com.knowledge.library.util.BaseResponseUtility;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(KnowledgeController.class)
@AutoConfigureMockMvc
class KnowledgeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private KnowledgeService knowledgeService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void shouldReturn401WhenCreateTextIsCalledWithoutAuthentication() throws Exception {

        TextKnowledgeRequest request =
                new TextKnowledgeRequest("Java", "desc", "content");

        mockMvc.perform(post("/api/knowledge/text")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser(roles = "USER")
    void shouldCreateTextSuccessfullyWhenAuthenticated() throws Exception {

        TextKnowledgeResponse response =
                new TextKnowledgeResponse("Java", "desc", "content");

        BaseResponse baseResponse =
                BaseResponseUtility.getBaseResponse(response);

        when(knowledgeService.createText(any(), any(), any()))
                .thenReturn(baseResponse);

        TextKnowledgeRequest request =
                new TextKnowledgeRequest("Java", "desc", "content");

        mockMvc.perform(post("/knowledge/text")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.data.title").value("Java"));
    }


    @Test
    @WithMockUser(roles = "USER")
    void shouldReturnKnowledgeByIdWhenAuthenticated() throws Exception {

        Knowledge knowledge =
                new TextKnowledge("Java", "desc", "content");

        BaseResponse baseResponse =
                BaseResponseUtility.getBaseResponse(knowledge);

        when(knowledgeService.getById(1L))
                .thenReturn(baseResponse);

        mockMvc.perform(get("/knowledge/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.title").value("Java"));
    }


    @Test
    @WithMockUser(roles = "ADMIN")
    void shouldDeleteKnowledgeWhenAuthenticated() throws Exception {

        when(knowledgeService.delete(1L))
                .thenReturn(BaseResponseUtility.getBaseResponse());

        mockMvc.perform(delete("/knowledge/1"))
                .andExpect(status().isOk());
    }

    @Test
    void shouldReturnKnowledgeByIdSuccessfully() throws Exception {

        Knowledge knowledge = new TextKnowledge("Java", "desc", "content");
        BaseResponse baseResponse = BaseResponseUtility.getBaseResponse(knowledge);

        when(knowledgeService.getById(1L)).thenReturn(baseResponse);

        mockMvc.perform(get("/knowledge/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.title").value("Java"));
    }


    @TestConfiguration
    static class TestSecurityConfig {

        @Bean
        SecurityFilterChain testSecurityFilterChain(HttpSecurity http) throws Exception {
            http
                    .csrf(AbstractHttpConfigurer::disable)
                    .anonymous(AbstractHttpConfigurer::disable)
                    .authorizeHttpRequests(auth -> auth
                            .requestMatchers(HttpMethod.GET, "/knowledge/**").permitAll()
                            .anyRequest().authenticated()
                    )
                    .httpBasic(Customizer.withDefaults());

            return http.build();
        }
    }

}

