package com.lbms.library.lbmsadminservice.service.impl;

import com.lbms.library.lbmsadminservice.dto.AuthorCreateRequest;
import com.lbms.library.lbmsadminservice.entity.nosql.Author;
import com.lbms.library.lbmsadminservice.repository.mongodb.AuthorRepository;
import com.lbms.library.lbmsadminservice.service.AuthorService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthorServiceImpl implements AuthorService {

    @Autowired
    AuthorRepository authorRepository;

    @Override
    public void addAuthor(AuthorCreateRequest authorCreateRequest) {
        ModelMapper modelMapper = new ModelMapper();

        Author author = modelMapper.map(authorCreateRequest,Author.class);

        authorRepository.save(author);
    }
}
