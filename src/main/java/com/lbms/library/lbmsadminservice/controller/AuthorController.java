package com.lbms.library.lbmsadminservice.controller;

import com.lbms.library.core.dto.author.AuthorSummaryDTO;
import com.lbms.library.lbmsadminservice.dto.AuthorCreateRequest;
import com.lbms.library.lbmsadminservice.service.AuthorService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/author")
public class AuthorController {

    @Autowired
    AuthorService authorService;

    @PostMapping
    public ResponseEntity addAuthor(@Valid @RequestBody AuthorCreateRequest authorCreateRequest) {
        authorService.addAuthor(authorCreateRequest);
        return ResponseEntity.created(null).build();
    }

    @GetMapping(produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<List<AuthorSummaryDTO>> getAuthorList() {
        List<AuthorSummaryDTO> authorSummaryDTOList = authorService.getAuthors();
        return ResponseEntity.ok(authorSummaryDTOList);
    }
}
