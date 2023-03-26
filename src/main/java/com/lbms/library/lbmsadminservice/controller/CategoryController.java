package com.lbms.library.lbmsadminservice.controller;

import com.lbms.library.core.dto.category.CategoryDTO;
import com.lbms.library.core.dto.category.CategorySummaryDTO;
import com.lbms.library.lbmsadminservice.dto.CategoryCreateRequest;
import com.lbms.library.lbmsadminservice.service.CategoryService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/category")
public class CategoryController {

    @Autowired
    CategoryService categoryService;

    @PostMapping
    public ResponseEntity addCategory(@Valid @RequestBody CategoryCreateRequest categoryCreateRequest) {
        categoryService.addCategory(categoryCreateRequest);
        return ResponseEntity.created(null).build();
    }

    @GetMapping
    public ResponseEntity<List<CategorySummaryDTO>> getCategoryList() {
        List<CategorySummaryDTO> categorySummaryDTOList = categoryService.getCategoryList();
        return ResponseEntity.ok(categorySummaryDTOList);
    }

    @GetMapping("/{categoryId}")
    public ResponseEntity<CategoryDTO> getCategoryById(@PathVariable String categoryId) {
        CategoryDTO categoryDTO = categoryService.getCategoryById(categoryId);

        return ResponseEntity.ok(categoryDTO);
    }
}
