package com.medcaptain.productservice.dao.mongo;

import com.medcaptain.productservice.entity.dto.mongo.ProductTemplate;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductTemplateRepository extends MongoRepository<ProductTemplate, Long> {
    ProductTemplate findByProductCategory(String category);
}
