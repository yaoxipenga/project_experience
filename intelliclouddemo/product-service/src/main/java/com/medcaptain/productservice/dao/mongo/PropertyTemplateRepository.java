package com.medcaptain.productservice.dao.mongo;


import com.medcaptain.productservice.entity.dto.mongo.ProductProperty;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PropertyTemplateRepository extends MongoRepository<ProductProperty, Long> {
    List<ProductProperty> findAll();
    ProductProperty findById(ObjectId id);
}
