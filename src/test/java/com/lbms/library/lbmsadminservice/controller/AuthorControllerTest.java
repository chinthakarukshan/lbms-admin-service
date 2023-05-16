package com.lbms.library.lbmsadminservice.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lbms.library.core.dto.author.AuthorSummaryDTO;
import com.lbms.library.lbmsadminservice.dto.AuthorCreateRequest;
import com.lbms.library.lbmsadminservice.service.AuthorService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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

    @Test
    public void getAuthorList_success() throws Exception {
        AuthorSummaryDTO authorSummaryDTO1 = new AuthorSummaryDTO();
        authorSummaryDTO1.setId("1");
        authorSummaryDTO1.setFirstName("Chinthaka");
        authorSummaryDTO1.setLastName("Weerakkody");

        AuthorSummaryDTO authorSummaryDTO2 = new AuthorSummaryDTO();
        authorSummaryDTO2.setId("2");
        authorSummaryDTO2.setFirstName("Kanchana");
        authorSummaryDTO2.setLastName("Wijerathna");

        List<AuthorSummaryDTO> authorSummaryDTOList = new ArrayList<>();
        authorSummaryDTOList.add(authorSummaryDTO1);
        authorSummaryDTOList.add(authorSummaryDTO2);

        when(authorService.getAuthors()).thenReturn(authorSummaryDTOList);

        mockMvc.perform(get("/author"))
               .andExpect(status().isOk())
               .andExpect(content().contentType(MediaType.APPLICATION_JSON))
               .andExpect(content().string(containsString("Chinthaka")));
    }
}
