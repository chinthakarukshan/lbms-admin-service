package com.lbms.library.lbmsadminservice.service.impl;

import com.lbms.library.lbmsadminservice.dto.AuthorCreateRequest;
import com.lbms.library.lbmsadminservice.entity.nosql.Author;
import com.lbms.library.lbmsadminservice.repository.mongodb.AuthorRepository;
import com.lbms.library.lbmsadminservice.service.AuthorService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AuthorServiceImplTest {

    @Mock
    AuthorRepository authorRepository;

    @InjectMocks
    AuthorServiceImpl authorServiceImpl;

    @Test
    public void addAuthor_Success() {
        AuthorCreateRequest authorCreateRequest = new AuthorCreateRequest();
        authorCreateRequest.setFirstName("Paul");
        authorCreateRequest.setLastName("Lemann");

        authorServiceImpl.addAuthor(authorCreateRequest);

        verify(authorRepository, times(1)).save(any(Author.class));
    }

    @Test
    public void getAuthorList_success() {
        Author chinthaka = new Author();
        chinthaka.setId("1");
        chinthaka.setFirstName("Chinthaka");
        chinthaka.setLastName("Weerakkody");

        Author kanchana = new Author();
        kanchana.setId("2");
        kanchana.setFirstName("Kanchana");
        kanchana.setLastName("Wijerathna");

        List<Author> authorList = new ArrayList<>();
        authorList.add(chinthaka);
        authorList.add(kanchana);

        when(authorRepository.findAll()).thenReturn(authorList);

        List<Author> returnedAuthorList = authorRepository.findAll();

        assert(returnedAuthorList.size() == authorList.size());


    }
}
