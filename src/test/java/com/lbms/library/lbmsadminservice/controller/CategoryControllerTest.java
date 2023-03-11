package com.lbms.library.lbmsadminservice.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lbms.library.core.error.LBMSError;
import com.lbms.library.core.exception.LBMSException;
import com.lbms.library.lbmsadminservice.dto.CategoryCreateRequest;
import com.lbms.library.lbmsadminservice.service.CategoryService;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.willThrow;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CategoryController.class)
public class CategoryControllerTest {

    @MockBean
    CategoryService categoryService;

    @Autowired
    MockMvc mockMvc;

    @Test
    public void addCategory_success() throws Exception {
        CategoryCreateRequest categoryCreateRequest = new CategoryCreateRequest();
        categoryCreateRequest.setCategory("Novel");
        categoryCreateRequest.setDescription("Novel Category");

        ObjectMapper objectMapper = new ObjectMapper();
        String categoryCreateRequestJson = objectMapper.writeValueAsString(categoryCreateRequest);

        this.mockMvc.perform(post("/category").content(categoryCreateRequestJson)
                                              .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isCreated());
    }

    @Test
    public void addCategory_categoryEmpty_ValidationFailure() throws Exception {
        CategoryCreateRequest categoryCreateRequest = new CategoryCreateRequest();
        categoryCreateRequest.setDescription("Novel Category");

        ObjectMapper objectMapper = new ObjectMapper();
        String categoryCreateRequestJson = objectMapper.writeValueAsString(categoryCreateRequest);

        this.mockMvc.perform(post("/category").contentType(MediaType.APPLICATION_JSON)
                                              .content(categoryCreateRequestJson))
                    .andExpect(status().isBadRequest())
                    .andExpect(content().string(containsString("category is required")));
    }

    @Test
    public void addCategory_categoryExists() throws Exception {
        CategoryCreateRequest categoryCreateRequest = new CategoryCreateRequest();
        categoryCreateRequest.setCategory("Novel");
        categoryCreateRequest.setDescription("Novel Category");

        ObjectMapper objectMapper = new ObjectMapper();
        String categoryCreateRequestJson = objectMapper.writeValueAsString(categoryCreateRequest);

        doThrow(new LBMSException(LBMSError.CATEGORY_EXISTS)).when(categoryService)
                                                             .addCategory(any());

        this.mockMvc.perform(post("/category").contentType(MediaType.APPLICATION_JSON)
                                              .content(categoryCreateRequestJson))
                    .andExpect(status().isBadRequest())
                    .andExpect(content().string(containsString(LBMSError.CATEGORY_EXISTS.getCode())));
    }
}
