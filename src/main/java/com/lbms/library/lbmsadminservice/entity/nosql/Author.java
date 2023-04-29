package com.lbms.library.lbmsadminservice.entity.nosql;

import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Author {

    @Id
    private String id;

    private String firstName;

    private String lastName;
}
