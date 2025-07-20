# spring_catch_up

## 시작

```shell
docker-compose up -d
```

## 주소

* http://localhost:3000/ 그라파다
* http://localhost:8081/ 레디스 데이터 확인용
* 스프링 부트 내장 url
    * http://localhost:8080/actuator/health
    * http://localhost:8080/actuator/loggers

* 테스트용 API
    1. 기본 로그 테스트 : http://localhost:8080/api/log/test
    2. 대량 로그 생성 : http://localhost:8080/api/log/generate?count=50
    3. 에러 로그 시뮬레이션 : http://localhost:8080/api/log/simulate-error
    4. 비즈니스 플로우 로그 : http://localhost:8080/api/log/business-flow?userId=USER123
    5. 에러가 발생하는 비즈니스 플로우 : http://localhost:8080/api/log/business-flow?userId=ERROR_USER