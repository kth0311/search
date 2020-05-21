## 개발 프레임워크
- Java8
- Spring Boot
- Spring Cloud
- Spring Security
- JPA
- H2
- Junit


## 빌드 및 실행하기
```
$ ./gradlew clean build
$ java -jar build/libs/search-0.0.1-SNAPSHOT.jar
```

## API 테스트 방법
```
rest/search.http 실행 
```
## 외부 라이브러리 및 오픈소스
 ```
1. spring cloud oauth2
 -  oauth2 인증을 위한 목적
2. spring cloud openfeign
 - http client
 - http connection 실패시 retryer
3. spring cloud netflix-hystrix
 - latency and fault tolerance 목적
 - kakao api 검색 장애시 naver api 검색
4. caffeine
 - local cached 목적 

 ```
## 문제 해결전략
### 회원가입/로그인
 - spring cloud oauth2를 사용하여 인증 
### 책 검색
 - http connection 실패시 retryer - 최대 5번 시도 
 - netflix-hystrix를 사용하여 카카오 검색 장애시 네이버 책 검색
 - 검색시 키워드 저장시 optimistic lock을 이용하여 동시성 처리
   -  optimistic lock 발생시 다시 시도
   -  testcase 결과 동시성 이슈 발생하지 않음  
### 인기 키워드 목록
 - cached 사용하여 DB 사용을 최소화하여 대용량 트래픽에 대비
