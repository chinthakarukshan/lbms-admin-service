package com.lbms.library.lbmsadminservice.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class MemberSummaryDTO {
    private String userId;

    private String email;

    private String firstName;

    private String lastName;
}
