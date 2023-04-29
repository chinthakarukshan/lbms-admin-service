package com.lbms.library.lbmsadminservice.repository.mongodb;

import com.lbms.library.lbmsadminservice.entity.nosql.Author;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface AuthorRepository extends MongoRepository<Author,String> {
}
