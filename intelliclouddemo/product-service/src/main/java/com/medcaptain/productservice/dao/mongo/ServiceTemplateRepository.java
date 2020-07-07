package com.medcaptain.productservice.dao.mongo;

import com.medcaptain.productservice.entity.dto.mongo.ProductServiceEntity;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ServiceTemplateRepository extends MongoRepository<ProductServiceEntity, Long> {
    List<ProductServiceEntity> findAll();
    ProductServiceEntity findById(ObjectId id);
}
