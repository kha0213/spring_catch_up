package com.yl.pj.config;

import com.mongodb.client.MongoClients;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;

@Configuration
public class MongoDBConfig {
    @Value("${spring.data.mongodb.uri}")
    private String mongoUri;
    @Value("${spring.data.mongodb.database}")
    private String database;

    @Bean
    public MongoTemplate mongoTemplate() {
        String username = System.getenv("MONGO_USERNAME");
        String password = System.getenv("MONGO_PASSWORD");

        if (username == null || password == null) {
            throw new IllegalStateException("MongoDB credentials are not set in environment variables.");
        }

        // 인증 정보를 포함한 MongoDB URI 구성
        String fullUri = String.format(
                "mongodb://%s:%s@%s",
                username,
                password,
                mongoUri.replace("mongodb://", "")
        );
        return new MongoTemplate(MongoClients.create(fullUri), database);
    }
}
