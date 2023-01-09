package com.lbms.library.lbmsadminservice.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
public class MemberRequest {
    private String email;

    private String firstName;

    private String lastName;

    private Date dateOfBirth;
}
