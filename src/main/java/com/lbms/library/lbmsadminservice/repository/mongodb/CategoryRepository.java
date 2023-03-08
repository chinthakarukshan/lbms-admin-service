package com.lbms.library.lbmsadminservice.repository.mongodb;

import com.lbms.library.lbmsadminservice.entity.nosql.Category;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface CategoryRepository extends MongoRepository<Category, String> {

    public List<Category> findByCategory(String category);
}
