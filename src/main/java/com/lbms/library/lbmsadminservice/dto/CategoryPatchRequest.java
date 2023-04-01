package com.lbms.library.lbmsadminservice.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class CategoryPatchRequest {

    @NotBlank(message = "Status is required")
    private String status;
}
