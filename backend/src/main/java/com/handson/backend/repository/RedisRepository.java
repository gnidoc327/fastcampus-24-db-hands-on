package com.handson.backend.repository;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.concurrent.TimeUnit;

@Repository
public class RedisRepository {
    private final RedisTemplate<String, Object> redisTemplate;
    private final ObjectMapper objectMapper;

    public RedisRepository(RedisTemplate<String, Object> redisTemplate, ObjectMapper objectMapper) {
        this.redisTemplate = redisTemplate;
        this.objectMapper = objectMapper;
    }

    public void save(String key, Object value) {
        redisTemplate.opsForValue().set(key, value, 7, TimeUnit.DAYS);
    }

    public <T> T find(String key, Class<T> convertClass) {
        return objectMapper.convertValue(redisTemplate.opsForValue().get(key), convertClass);
    }

    public void delete(String key) {
        redisTemplate.delete(key);
    }
}
