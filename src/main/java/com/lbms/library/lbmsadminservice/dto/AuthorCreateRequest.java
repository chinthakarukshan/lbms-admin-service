package com.lbms.library.lbmsadminservice.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class AuthorCreateRequest {
    @NotBlank(message = "Author first name is required")
    private String firstName;

    private String lastName;
}
