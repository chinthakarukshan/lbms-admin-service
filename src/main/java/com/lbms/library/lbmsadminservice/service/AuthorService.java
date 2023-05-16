package com.lbms.library.lbmsadminservice.service;

import com.lbms.library.core.dto.author.AuthorSummaryDTO;
import com.lbms.library.lbmsadminservice.dto.AuthorCreateRequest;

import java.util.List;

public interface AuthorService {
    void addAuthor(AuthorCreateRequest authorCreateRequest);

    List<AuthorSummaryDTO> getAuthors();
}
