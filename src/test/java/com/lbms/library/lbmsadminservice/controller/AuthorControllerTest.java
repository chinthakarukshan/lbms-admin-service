package com.lbms.library.lbmsadminservice.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lbms.library.lbmsadminservice.dto.AuthorCreateRequest;
import com.lbms.library.lbmsadminservice.service.AuthorService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AuthorController.class)
public class AuthorControllerTest {

    @MockBean
    AuthorService authorService;

    @Autowired
    MockMvc mockMvc;

    @BeforeEach
    public void createModels() {

    }

    @Test
    public void addAuthor_success() throws Exception {
        AuthorCreateRequest authorCreateRequest = new AuthorCreateRequest();
        authorCreateRequest.setFirstName("Paul");
        authorCreateRequest.setLastName("Lemann");

        ObjectMapper objectMapper = new ObjectMapper();

        String authorCreateJson = objectMapper.writeValueAsString(authorCreateRequest);

        mockMvc.perform(post("/author").contentType(MediaType.APPLICATION_JSON)
                                       .content(authorCreateJson))
               .andExpect(status().isCreated());
    }

    @Test
    public void addAuthor_firstnameEmpty_failure() throws Exception {
        AuthorCreateRequest authorCreateRequest = new AuthorCreateRequest();
        authorCreateRequest.setLastName("Lemann");

        ObjectMapper objectMapper = new ObjectMapper();

        String authorCreateJson = objectMapper.writeValueAsString(authorCreateRequest);

        mockMvc.perform(post("/author").contentType(MediaType.APPLICATION_JSON)
                                       .content(authorCreateJson))
               .andExpect(status().isBadRequest())
               .andExpect(content().string(containsString("Author first name is required")));
    }
}
