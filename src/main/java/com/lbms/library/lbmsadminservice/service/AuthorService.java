package com.lbms.library.lbmsadminservice.service;

import com.lbms.library.lbmsadminservice.dto.AuthorCreateRequest;

public interface AuthorService {
    void addAuthor(AuthorCreateRequest authorCreateRequest);
}
