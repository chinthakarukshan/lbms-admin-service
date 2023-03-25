package com.lbms.library.lbmsadminservice.service.impl;

import com.lbms.library.core.util.constant.CategoryStatus;
import com.lbms.library.lbmsadminservice.dto.CategoryCreateRequest;
import com.lbms.library.lbmsadminservice.entity.nosql.Category;
import com.lbms.library.lbmsadminservice.repository.mongodb.CategoryRepository;
import com.lbms.library.lbmsadminservice.service.CategoryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CategoryServiceImplTest {

    @Mock
    CategoryRepository categoryRepository;

    @InjectMocks
    CategoryServiceImpl categoryServiceImpl;

    CategoryCreateRequest categoryCreateRequest;

    Category category;

    @BeforeEach
    public void setup() {
        categoryCreateRequest = new CategoryCreateRequest();
        categoryCreateRequest.setCategory("Novel");
        categoryCreateRequest.setDescription("Novel Category");

        category = new Category();
        category.setCategory("Novel");
        category.setDescription("Novel Category");
        category.setStatus(CategoryStatus.ACTIVE.getStatus());
        category.setCreatedBy("Admin");
        category.setCreatedDate(new Date());
    }

    @Test
    public void addCategoryTest_success() {
        List<Category> existingCategoryList= new ArrayList<>();

        when(categoryRepository.findByCategory("Novel")).thenReturn(existingCategoryList);
        when(categoryRepository.save(any(Category.class))).thenReturn(category);

        categoryServiceImpl.addCategory(categoryCreateRequest);

        verify(categoryRepository,times(1)).save(any(Category.class));
    }
}
