package com.lbms.library.lbmsadminservice.dto;

import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@Data
public class MemberRequest {

    @NotBlank(message="Email is Required")
    @Email(message = "Email address is invalid", flags = {Pattern.Flag.CASE_INSENSITIVE})
    private String email;

    @NotBlank(message="First Name is Required")
    private String firstName;

    @NotBlank(message = "Last Name is Required")
    private String lastName;

    @NotNull(message = "Date of Birth is Required")
    @Past(message = "Date of Birth Must be in the past")
    private Date dateOfBirth;
}
