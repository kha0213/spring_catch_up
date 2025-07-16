package com.yl.pj.config;

import com.yl.pj.redis.RedisService;
import com.yl.pj.redis.dto.RedisData;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/redis")
public class RedisController {

    private final RedisService redisService;

    @PostMapping("/set")
    public ResponseEntity<String> setValue(@RequestBody RedisData data) {
        redisService.setValue(data.key(), data.value());
        return ResponseEntity.ok("Key-Value pair saved successfully");
    }

    @PostMapping("/set-with-ttl")
    public ResponseEntity<String> setValueWithTTL(@RequestBody RedisData data) {
        redisService.setValue(data.key(), data.value(), Duration.ofSeconds(data.seconds()));
        return ResponseEntity.ok("Key-Value pair saved with TTL: " + data.seconds() + " seconds");
    }

    @GetMapping("/get")
    public ResponseEntity<Object> getValue(@RequestParam String key) {
        Object value = redisService.getValue(key);
        if (value != null) {
            return ResponseEntity.ok(value);
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/delete/{key}")
    public ResponseEntity<String> deleteKey(@PathVariable("key") String key) {
        boolean deleted = redisService.deleteKey(key);
        if (deleted) {
            return ResponseEntity.ok("Key deleted successfully");
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping("/hash/set")
    public ResponseEntity<String> setHashValue(@RequestParam String key,
                                               @RequestParam String hashKey,
                                               @RequestParam String value) {
        redisService.setHashValue(key, hashKey, value);
        return ResponseEntity.ok("Hash value saved successfully");
    }

    @GetMapping("/hash/get")
    public ResponseEntity<Object> getHashValue(@RequestParam String key,
                                               @RequestParam String hashKey) {
        Object value = redisService.getHashValue(key, hashKey);
        if (value != null) {
            return ResponseEntity.ok(value);
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/hash/all")
    public ResponseEntity<Map<Object, Object>> getAllHashValues(@RequestParam String key) {
        Map<Object, Object> values = redisService.getHashValues(key);
        return ResponseEntity.ok(values);
    }

    @PostMapping("/list/push")
    public ResponseEntity<String> pushToList(@RequestParam String key, @RequestParam String value) {
        redisService.pushToList(key, value);
        return ResponseEntity.ok("Value pushed to list successfully");
    }

    @GetMapping("/list/pop")
    public ResponseEntity<Object> popFromList(@RequestParam String key) {
        Object value = redisService.popFromList(key);
        if (value != null) {
            return ResponseEntity.ok(value);
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/health")
    public ResponseEntity<Map<String, String>> healthCheck() {
        Map<String, String> status = new HashMap<>();
        try {
            redisService.setValue("health_check", "ok", Duration.ofSeconds(10));
            Object value = redisService.getValue("health_check");
            if ("ok".equals(value)) {
                status.put("redis", "connected");
                status.put("status", "healthy");
            } else {
                status.put("redis", "error");
                status.put("status", "unhealthy");
            }
        } catch (Exception e) {
            status.put("redis", "error");
            status.put("status", "unhealthy");
            status.put("error", e.getMessage());
        }
        return ResponseEntity.ok(status);
    }
}