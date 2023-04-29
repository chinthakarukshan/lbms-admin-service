package com.lbms.library.lbmsadminservice.controller;

import com.lbms.library.lbmsadminservice.dto.AuthorCreateRequest;
import com.lbms.library.lbmsadminservice.service.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/author")
public class AuthorController {

    @Autowired
    AuthorService authorService;

    @PostMapping
    public ResponseEntity addAuthor(@RequestBody AuthorCreateRequest authorCreateRequest) {
        authorService.addAuthor(authorCreateRequest);
        return ResponseEntity.created(null).build();
    }
}
