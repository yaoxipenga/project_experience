package com.medcaptain.productservice.dao.mongo;

import com.medcaptain.productservice.entity.dto.mongo.ProductEvent;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EventTemplateRepository extends MongoRepository<ProductEvent, Long> {
    List<ProductEvent> findAll();
    ProductEvent findById(ObjectId id);
}
