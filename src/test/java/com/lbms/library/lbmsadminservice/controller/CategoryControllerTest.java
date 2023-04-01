package com.lbms.library.lbmsadminservice.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lbms.library.core.dto.category.CategoryDTO;
import com.lbms.library.core.dto.category.CategorySummaryDTO;
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

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.willThrow;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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

    @Test
    public void getCategoryList_Success() throws Exception {
        List<CategorySummaryDTO> responseList = new ArrayList<>();

        CategorySummaryDTO categorySummaryDTO01 = new CategorySummaryDTO();
        categorySummaryDTO01.setCategory("Novel");
        categorySummaryDTO01.setId("01");
        categorySummaryDTO01.setCreatedBy("Admin");
        categorySummaryDTO01.setCreatedDate(new Date());

        CategorySummaryDTO categorySummaryDTO02 = new CategorySummaryDTO();
        categorySummaryDTO02.setCategory("Science Fiction");
        categorySummaryDTO02.setId("02");
        categorySummaryDTO02.setCreatedBy("Admin");
        categorySummaryDTO02.setCreatedDate(new Date());

        CategorySummaryDTO categorySummaryDTO03 = new CategorySummaryDTO();
        categorySummaryDTO03.setCategory("Academic");
        categorySummaryDTO03.setId("03");
        categorySummaryDTO03.setCreatedBy("Admin");
        categorySummaryDTO03.setCreatedDate(new Date());

        responseList.add(categorySummaryDTO01);
        responseList.add(categorySummaryDTO02);
        responseList.add(categorySummaryDTO03);

        when(categoryService.getCategoryList()).thenReturn(responseList);

        mockMvc.perform(get("/category"))
               .andExpect(status().isOk())
               .andExpect(content().string(containsString("Novel")));
    }

    @Test
    public void getCategoryById_Success() throws Exception {
        CategoryDTO categoryDTO = new CategoryDTO();

        categoryDTO.setId("1qaz2wsx3edc");
        categoryDTO.setCategory("Novel");
        categoryDTO.setCreatedBy("Admin");
        categoryDTO.setCreatedDate(new Date());

        when(categoryService.getCategoryById("1qaz2wsx3edc")).thenReturn(categoryDTO);

        mockMvc.perform(get("/category/1qaz2wsx3edc"))
               .andExpect(status().isOk())
               .andExpect(content().string(containsString("1qaz2wsx3edc")));
    }

    @Test
    public void getCategoryById_invalidCategoryId() throws Exception {

        when(categoryService.getCategoryById("1qaz2wsx3edc")).thenThrow(new LBMSException(LBMSError.CATEGORY_WITH_ID_DOES_NOT_EXIST));

        mockMvc.perform(get("/category/1qaz2wsx3edc"))
               .andExpect(status().isBadRequest())
               .andExpect(content().string(containsString(LBMSError.CATEGORY_WITH_ID_DOES_NOT_EXIST.getCode())));
    }
}
