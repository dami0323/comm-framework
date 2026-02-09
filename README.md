# COMM-Framework

분산 통신 프레임워크로, Java 백엔드와 Vue 프론트엔드로 구성된 모놀리식 모노레포(monorepo) 프로젝트입니다.

## 📁 프로젝트 구조

```
comm-framework/
├── java/                          # Java 백엔드 (Spring Boot)
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/co/kr/comm/
│   │   │   │   ├── CommFrameworkApplication.java
│   │   │   │   └── test/
│   │   │   │       ├── controller/
│   │   │   │       │   └── TextFileController.java
│   │   │   │       └── service/
│   │   │   │           └── TextFileService.java
│   │   │   └── resources/
│   │   │       ├── application.yml
│   │   │       ├── application-dev.yml
│   │   │       ├── application-local.yml
│   │   │       ├── application-test.yml
│   │   │       ├── static/
│   │   │       │   └── file-reader.html
│   │   │       └── log4j2/, logback/
│   │   └── test/
│   ├── pom.xml
│   ├── mvnw
│   ├── mvnw.cmd
│   └── README.md
│
├── vue/                           # Vue 프론트엔드 (Vite)
│   ├── src/
│   │   ├── App.vue               # 메인 컴포넌트 (파일 읽기)
│   │   └── main.js               # Vue 앱 진입점
│   ├── index.html
│   ├── package.json
│   ├── vite.config.js            # Vite 설정 + API 프록시
│   └── .gitignore
│
└── .git/
```

## 🚀 빠른 시작

### 필요 사항
- **Java**: JDK 11 이상
- **Node.js**: v16 이상
- **npm**: v8 이상

### 1️⃣ Java 백엔드 실행

#### 1. 의존성 설치 및 빌드
```bash
cd java
mvn clean install
```

#### 2. 애플리케이션 실행
```bash
# Maven으로 실행
mvn spring-boot:run

# 또는 IDE에서 CommFrameworkApplication.java를 직접 실행
```

**기본 포트**: `8080`

**사용 가능한 API**:
- `GET /api/files/read?path=<file-path>` - 파일 전체 내용을 문자열로 반환
- `GET /api/files/read-lines?path=<file-path>` - 파일을 라인 단위로 읽어 리스트로 반환

### 2️⃣ Vue 프론트엔드 실행

#### 1. 의존성 설치
```bash
cd vue
npm install
```

#### 2. 개발 서버 실행
```bash
npm run dev
```

**개발 서버 포트**: `5173`

**브라우저에서 접속**: `http://localhost:5173`

#### 3. 프로덕션 빌드
```bash
npm run build
npm run preview
```

## 📋 프로젝트 설정 상세

### Java 백엔드 (Spring Boot)

**주요 파일**:
- `pom.xml` - Maven 의존성 및 빌드 설정
- `src/main/resources/application.yml` - 기본 설정
- `src/main/resources/application-dev.yml` - 개발 환경 설정
- `src/main/resources/application-local.yml` - 로컬 환경 설정
- `src/main/resources/application-test.yml` - 테스트 환경 설정

**실행 프로필 선택**:
```bash
# 개발 환경
mvn spring-boot:run -Dspring-boot.run.arguments="--spring.profiles.active=dev"

# 로컬 환경
mvn spring-boot:run -Dspring-boot.run.arguments="--spring.profiles.active=local"

# 테스트 환경
mvn spring-boot:run -Dspring-boot.run.arguments="--spring.profiles.active=test"
```

**정적 파일**:
- `src/main/resources/static/` - HTML, CSS, JS 정적 파일 (선택사항)
- Spring Boot가 이 경로의 파일들을 자동으로 제공합니다

### Vue 프론트엔드 (Vite)

**주요 파일**:
- `vite.config.js` - Vite 설정 + API 프록시
  - API 프록시: `/api` 요청 → `http://localhost:8080`로 포워딩
- `package.json` - Node.js 의존성

**프록시 설정** (vite.config.js):
```javascript
server: {
  port: 5173,
  proxy: {
    '/api': {
      target: 'http://localhost:8080',
      changeOrigin: true
    }
  }
}
```

이 설정으로 Vue 앱에서 `/api/files/read-lines`로 요청하면 자동으로 `http://localhost:8080/api/files/read-lines`로 포워딩됩니다.

## 📝 기능 설명

### 파일 읽기 도구

Vue 프론트엔드에서 제공하는 파일 읽기 도구입니다.

**기능**:
- 파일 경로 입력
- 읽기 버튼 클릭으로 파일 내용 조회
- 엔터 키로도 파일 읽기 가능
- 로딩 상태 표시
- 성공/오류 메시지 표시
- 파일 크기 정보 표시

**백엔드 API 호출**:
```javascript
// Vue 컴포넌트에서
const response = await axios.get('/api/files/read-lines', {
  params: { path: 'C:/test/test.txt' }
})
```

## 🧪 테스트

### Java 백엔드 테스트
```bash
cd java
mvn test
```

### Vue 프론트엔드 테스트 (선택사항)
테스트 프레임워크는 필요에 따라 추가 설정 가능합니다.

## 🔧 개발 가이드

### 포트 충돌 해결

**Java 백엔드 포트 변경** (application.yml):
```yaml
server:
  port: 8081
```

**Vue 프론트엔드 포트 변경** (vite.config.js):
```javascript
server: {
  port: 5174
}
```

### 환경 변수 설정

Java 백엔드에서 특정 프로필을 사용하려면:
```bash
export SPRING_PROFILES_ACTIVE=dev
mvn spring-boot:run
```

## 📚 추가 리소스

- [Spring Boot 공식 문서](https://spring.io/projects/spring-boot)
- [Vue 3 공식 문서](https://v3.vuejs.org/)
- [Vite 공식 문서](https://vitejs.dev/)

## 📄 라이선스

이 프로젝트는 MIT 라이선스 하에 있습니다.