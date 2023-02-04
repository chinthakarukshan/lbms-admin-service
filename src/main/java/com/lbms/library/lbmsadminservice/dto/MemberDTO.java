package com.lbms.library.lbmsadminservice.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@NoArgsConstructor
@Getter
@Setter
public class MemberDTO {
    private String userId;

    private String email;

    private String firstName;

    private String lastName;

    private Date dateOfBirth;
}
