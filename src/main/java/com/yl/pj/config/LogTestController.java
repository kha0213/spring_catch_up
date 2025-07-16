package com.yl.pj.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/api/log")
public class LogTestController {

    @GetMapping("/test")
    public Map<String, String> logTest() {
        log.trace("TRACE 레벨 로그 - 가장 상세한 로그");
        log.debug("DEBUG 레벨 로그 - 디버깅 정보");
        log.info("INFO 레벨 로그 - 일반 정보");
        log.warn("WARN 레벨 로그 - 경고 메시지");
        log.error("ERROR 레벨 로그 - 에러 발생");
        
        Map<String, String> response = new HashMap<>();
        response.put("message", "다양한 로그 레벨이 기록되었습니다");
        response.put("timestamp", String.valueOf(System.currentTimeMillis()));
        
        return response;
    }
    
    @PostMapping("/generate")
    public Map<String, String> generateLogs(@RequestParam(defaultValue = "10") int count) {
        log.info("로그 생성 시작 - 총 {} 개의 로그를 생성합니다", count);
        
        for (int i = 1; i <= count; i++) {
            if (i % 5 == 0) {
                log.error("에러 로그 {} - 샘플 에러 메시지", i);
            } else if (i % 3 == 0) {
                log.warn("경고 로그 {} - 샘플 경고 메시지", i);
            } else {
                log.info("정보 로그 {} - 샘플 정보 메시지", i);
            }
            
            // 약간의 지연으로 시간 차이 만들기
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                log.error("Thread 중단됨", e);
            }
        }
        
        log.info("로그 생성 완료 - 총 {} 개의 로그가 생성되었습니다", count);
        
        Map<String, String> response = new HashMap<>();
        response.put("message", count + "개의 로그가 생성되었습니다");
        response.put("status", "success");
        
        return response;
    }
    
    @GetMapping("/simulate-error")
    public Map<String, String> simulateError() {
        log.info("에러 시뮬레이션 시작");
        
        try {
            // 의도적으로 에러 발생
            int result = 10 / 0;
        } catch (ArithmeticException e) {
            log.error("산술 에러 발생: {}", e.getMessage(), e);
        }
        
        try {
            // 의도적으로 NullPointerException 발생
            String nullString = null;
            nullString.length();
        } catch (NullPointerException e) {
            log.error("Null Pointer 에러 발생: {}", e.getMessage(), e);
        }
        
        Map<String, String> response = new HashMap<>();
        response.put("message", "에러 시뮬레이션이 완료되었습니다");
        response.put("status", "completed");
        
        return response;
    }
    
    @GetMapping("/business-flow")
    public Map<String, String> businessFlow(@RequestParam(defaultValue = "USER123") String userId) {
        log.info("비즈니스 플로우 시작 - 사용자: {}", userId);
        
        log.debug("사용자 검증 중 - userId: {}", userId);
        // 사용자 검증 로직 시뮬레이션
        
        log.info("데이터베이스 조회 중 - userId: {}", userId);
        // DB 조회 로직 시뮬레이션
        
        log.debug("비즈니스 로직 처리 중");
        // 비즈니스 로직 처리 시뮬레이션
        
        if (userId.contains("ERROR")) {
            log.error("비즈니스 로직 처리 실패 - userId: {}", userId);
            throw new RuntimeException("비즈니스 로직 실패");
        }
        
        log.info("비즈니스 플로우 완료 - 사용자: {}", userId);
        
        Map<String, String> response = new HashMap<>();
        response.put("message", "비즈니스 플로우가 성공적으로 완료되었습니다");
        response.put("userId", userId);
        response.put("status", "success");
        
        return response;
    }
}