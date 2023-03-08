package com.lbms.library.lbmsadminservice.entity.nosql;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Category {

    @Id
    private String id;

    private String category;

    private String description;

    private boolean active;

    private Date createdDate;

    private String createdBy;

    private Date updatedDate;

    private String updatedBy;
}
