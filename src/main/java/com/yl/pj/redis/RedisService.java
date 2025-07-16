package com.yl.pj.redis;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.Map;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class RedisService {

    private final RedisTemplate<String, Object> redisTemplate;

    // 기본 SET/GET 작업
    public void setValue(String key, Object value) {
        redisTemplate.opsForValue().set(key, value);
    }

    public void setValue(String key, Object value, Duration timeout) {
        redisTemplate.opsForValue().set(key, value, timeout);
    }

    public Object getValue(String key) {
        return redisTemplate.opsForValue().get(key);
    }

    // 키 존재 여부 확인
    public boolean hasKey(String key) {
        return redisTemplate.hasKey(key);
    }

    // 키 삭제
    public boolean deleteKey(String key) {
        return redisTemplate.delete(key);
    }

    // 만료 시간 설정
    public boolean expire(String key, Duration timeout) {
        return Boolean.TRUE.equals(redisTemplate.expire(key, timeout));
    }

    // Hash 작업
    public void setHashValue(String key, String hashKey, Object value) {
        redisTemplate.opsForHash().put(key, hashKey, value);
    }

    public Object getHashValue(String key, String hashKey) {
        return redisTemplate.opsForHash().get(key, hashKey);
    }

    public Map<Object, Object> getHashValues(String key) {
        return redisTemplate.opsForHash().entries(key);
    }

    // List 작업
    public void pushToList(String key, Object value) {
        redisTemplate.opsForList().rightPush(key, value);
    }

    public Object popFromList(String key) {
        return redisTemplate.opsForList().leftPop(key);
    }

    // Set 작업
    public void addToSet(String key, Object value) {
        redisTemplate.opsForSet().add(key, value);
    }

    public Set<Object> getSetMembers(String key) {
        return redisTemplate.opsForSet().members(key);
    }

    // 캐시 패턴 - 데이터가 없으면 DB에서 조회 후 캐시 저장
    public <T> T getFromCacheOrElse(String key, Class<T> type, java.util.function.Supplier<T> supplier, Duration timeout) {
        Object cached = getValue(key);
        if (cached != null) {
            return type.cast(cached);
        }

        T result = supplier.get();
        if (result != null) {
            setValue(key, result, timeout);
        }
        return result;
    }
}