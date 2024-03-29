package com.lbms.library.lbmsadminservice.service.impl;

import com.lbms.library.core.dto.category.CategorySummaryDTO;
import com.lbms.library.core.exception.LBMSException;
import com.lbms.library.core.util.constant.CategoryStatus;
import com.lbms.library.lbmsadminservice.dto.CategoryCreateRequest;
import com.lbms.library.lbmsadminservice.dto.CategoryPatchRequest;
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
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CategoryServiceImplTest {

    @Mock
    CategoryRepository categoryRepository;

    @InjectMocks
    CategoryServiceImpl categoryServiceImpl;

    CategoryCreateRequest categoryCreateRequest;

    Category category;

    Category category2;

    Category category3;

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

        category2 = new Category();
        category2.setCategory("Science Fiction");
        category2.setDescription("Science Fiction Category");
        category2.setStatus(CategoryStatus.ACTIVE.getStatus());
        category2.setCreatedBy("Admin");
        category2.setCreatedDate(new Date());

        category3 = new Category();
        category3.setCategory("Education");
        category3.setDescription("Education Category");
        category3.setStatus(CategoryStatus.ACTIVE.getStatus());
        category3.setCreatedBy("Admin");
        category3.setCreatedDate(new Date());
    }

    @Test
    public void addCategoryTest_save_success() {
        categoryServiceImpl.addCategory(categoryCreateRequest);

        verify(categoryRepository,times(1)).save(any(Category.class));
    }

    @Test
    public void addCategoryTest_findExsisting_success() {
        categoryServiceImpl.addCategory(categoryCreateRequest);

        verify(categoryRepository,times(1)).findByCategory(any(String.class));
    }

    @Test
    public void addCategoryTest_failure_categoryExists() {
        List<Category> existingCategoryList= new ArrayList<>();
        existingCategoryList.add(category);

        when(categoryRepository.findByCategory(any(String.class))).thenReturn(existingCategoryList);

        assertThrows(LBMSException.class, () -> categoryServiceImpl.addCategory(categoryCreateRequest));
    }

    @Test
    public void getCategoryList_success() {
        List<Category> categoryList = new ArrayList<>();
        categoryList.add(category);
        categoryList.add(category2);
        categoryList.add(category3);

        when(categoryRepository.findAll()).thenReturn(categoryList);

        List<CategorySummaryDTO> categorySummaryDTOList = categoryServiceImpl.getCategoryList();

        assert(categorySummaryDTOList.size() == 3);
    }

    @Test
    public void patchCategory_success() {
        CategoryPatchRequest categoryPatchRequest = new CategoryPatchRequest();
        categoryPatchRequest.setStatus("Inactive");

        Category patchCategory = new Category();
        patchCategory.setId("1qaz2wsx3edc");
        patchCategory.setStatus("Active");
        patchCategory.setCategory("Non-Fiction");
        patchCategory.setDescription("Non-fiction books");
        patchCategory.setCreatedBy("Admin");
        patchCategory.setCreatedDate(new Date());

        when(categoryRepository.findById("1qaz2wsx3edc")).thenReturn(Optional.of(patchCategory));

        categoryServiceImpl.patchCategory("1qaz2wsx3edc",categoryPatchRequest);

        verify(categoryRepository,times(1)).save(any(Category.class));
    }

    @Test
    public void patchCategory_failure_invalidCategory() {
        CategoryPatchRequest categoryPatchRequest = new CategoryPatchRequest();
        categoryPatchRequest.setStatus("Inactive");

        when(categoryRepository.findById("1qaz2wsx3edc")).thenReturn(Optional.ofNullable(null));

        assertThrows(LBMSException.class,() -> categoryServiceImpl.patchCategory("1qaz2wsx3edc",categoryPatchRequest));
    }
}
