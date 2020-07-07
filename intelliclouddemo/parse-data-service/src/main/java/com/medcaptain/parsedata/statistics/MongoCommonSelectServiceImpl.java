package com.medcaptain.parsedata.statistics;

import org.bson.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * MongoDB通用查询
 *
 * @author bingwen.ai
 */
@Service
public class MongoCommonSelectServiceImpl implements CommonSelectService {
    private Logger logger = LoggerFactory.getLogger(MongoCommonSelectServiceImpl.class);

    @Autowired
    MongoTemplate mongoTemplate;

    @Override
    public List select(String sql) throws Exception {
        Document document = Document.parse(sql);
        Document resultDocument = mongoTemplate.executeCommand(document);
        if (resultDocument != null && resultDocument.size() > 0) {
            return (ArrayList) ((Document) resultDocument.get("cursor")).get("firstBatch");
        }
        return null;
    }
}
