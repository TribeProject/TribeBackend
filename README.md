# Tribe API

- Spring Boot 3.3.5, Java 17, JPA, QueryDSL, Spring Security, JWT
- PostgreSQL
- Gradle
- Swagger (SpringDoc OpenAPI)

## 디렉터리 구조

```
src/main/java/com/unity/tribe/
├── TribeApplication.java
├── common/                          # 공통 모듈
│   ├── aop/                        # AOP 관련
│   ├── config/                     # 설정 클래스
│   │   ├── JwtAuthenticationFilter.java
│   │   ├── SecurityConfig.java
│   │   ├── QueryDslConfig.java
│   │   └── security/
│   ├── exception/                  # 예외 처리
│   ├── model/                      # 공통 모델
│   └── util/                       # 유틸리티
└── domain/                         # 도메인별 패키지
    ├── auth/                      # 인증/인가
    ├── user/                      # 사용자
    ├── group/                     # 그룹 관리
    ├── member/                    # 멤버 관리
    ├── feed/                      # 피드
    ├── comment/                   # 댓글
    ├── report/                    # 신고
    ├── hide/                      # 숨김
    ├── block/                     # 차단
    ├── banner/                    # 배너
    └── admin/                     # 관리자
```

### 기능 구현

#### 1. 인증/인가
- `src/main/java/com/unity/tribe/domain/auth/`
- CRUD operations
- JWT 토큰 인증
- 소셜 로그인
- 리프레시 토큰 관리

#### 2. 그룹
- `src/main/java/com/unity/tribe/domain/group/`
- CRUD operations
- 그룹 카테고리별 조회
- 그룹 검색
- 그룹 가입/탈퇴
- 목표(Goal) 관리

#### 3. 피드
- `src/main/java/com/unity/tribe/domain/feed/`
- CRUD operations
- 피드 검색 기능
- 사용자별 피드 조회

#### 4. 댓글
- `src/main/java/com/unity/tribe/domain/comment/`
- CRUD operations

#### 5. 멤버 관리
- `src/main/java/com/unity/tribe/domain/member/`
- 멤버 역할 변경
- 멤버 추방
- 멤버 목록 조회

#### 6. 신고/차단/숨김
- Report (`src/main/java/com/unity/tribe/domain/report/`)
- Block (`src/main/java/com/unity/tribe/domain/block/`)
- Hide (`src/main/java/com/unity/tribe/domain/hide/`)

## 기술적 구현

### 1. DB 설정
- PostgreSQL
- ORM: JPA + QueryDSL
- Config (`src/main/resources/application.yml`)

### 2. 보안 설정
- JWT 토큰 관리 (`JwtTokenProvider`)
- Spring Security (`SecurityConfig`)
- 인증 필터 (`JwtAuthenticationFilter`)

### 3. Swagger API Doc
- SpringDoc OpenAPI 3.x
- 접근 경로: `/swagger-ui/index.html`
- API 문서: 각 도메인별 `docs/` 패키지에 정의

### 4. 예외 처리
- 글로벌 예외 처리: `GlobalExceptionHandler`
- 커스텀 예외: `TribeApiException`, `TribeAuthenticateException`
- 에러 코드: `ErrorCode` enum 관리

### TODO
1. **테스트 코드 작성**
2. **API 성능 최적화**: 쿼리 최적화 및 캐싱
3. **파일 업로드 기능**: 이미지/파일 업로드 API
4. **실시간 알림**