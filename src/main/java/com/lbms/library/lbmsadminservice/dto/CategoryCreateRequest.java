package com.lbms.library.lbmsadminservice.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class CategoryCreateRequest {
    @NotBlank(message = "category is required")
    private String category;

    private String description;
}
