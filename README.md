# Archiveat-Android

Archiveat Android 앱 프로젝트입니다.
Jetpack Compose 기반으로 UI를 구성하고, Retrofit/OkHttp + Hilt + DataStore를 사용해 네트워크/DI/인증 상태를 관리합니다.

## 💕 주요 기능

- 이메일 기반 회원가입/로그인
- 온보딩 (직무/시간대, 관심사 설정)
- 홈/탐색/리포트/기타 탭 기반 메인 화면
- 뉴스레터 상세(요약/원문 웹뷰/컬렉션)
- 인증 토큰 자동 처리
  - 요청 시 `Authorization: Bearer ...` 자동 부착
  - `401` 발생 시 refresh token으로 재발급 후 1회 재시도
- 로그인 상태 유지
  - `DataStore(Preferences)`에 `access_token`, `refresh_token` 저장
  - 앱 재실행 시 토큰 존재 여부로 시작 라우트 결정

## 🧑‍💻 기술 스택

- **Language**: Kotlin
- **UI**: Jetpack Compose, Material 3
- **Navigation**: Navigation Compose
- **DI**: Hilt
- **Network**: Retrofit2, OkHttp3, Kotlinx Serialization
- **Image**: Coil
- **Local Storage**: DataStore Preferences
- **Build**: Gradle Kotlin DSL, Version Catalog

## 📁 프로젝트 구조 (요약)
```
app/src/main/java/com/kuit/archiveatproject
├─ data
│  ├─ dto / service / repositoryimpl
│  ├─ network (AuthInterceptor, AuthAuthenticator 등)
│  └─ local (TokenLocalDataSourceImpl)
├─ domain
│  ├─ entity
│  └─ repository
├─ presentation
│  ├─ navigation
│  ├─ home / explore / report / login / onboarding / etc ...
│  └─ ...screen, ...viewmodel
└─ di (NetworkModule, RepositoryModule 등)
```

## 🚶‍♂️ 시작하기

### 1) 요구 환경

- Android Studio (최신 안정 버전 권장)
- JDK 11
- Android SDK
    - `compileSdk = 36`
    - `targetSdk = 36`
    - `minSdk = 26`

### 2) `local.properties` 설정

프로젝트 루트 `local.properties`에 API 서버 주소를 넣어주세요.
앱에서 `BuildConfig.BASE_URL`로 주입됩니다.

### 3) 실행

- Android Studio에서 프로젝트 열기
- Gradle Sync
- 에뮬레이터/디바이스 선택
- `app` 실행

## 📌 브랜치 가이드
main : 배포 가능 상태 유지

develop : 개발 통합 브랜치

feature/* : 기능 개발 브랜치

## 📌 Commit 규칙
### Commit Message Convention
- **feat** : 새로운 기능 추가
- **fix** : 버그 수정
- **refactor** : 리팩토링 (기능 변경 없음)

- style : 코드 스타일, 포맷 변경
- docs : 문서 수정 (README 등)
- chore : 빌드, 설정 파일 수정
- test : 테스트 코드 추가/수정
---
**✏️ 예시**
- feat: 홈 화면 UI 추가
- fix: Retrofit baseUrl 설정 오류 수정
- docs: README 초기 작성
- refactor: Repository 구조 개선

## Naming Convention

- 변수/함수: `camelCase`
- 클래스: `PascalCase`
- 상수: `UPPER_SNAKE_CASE`

## PR 템플릿
### 📌 변경 사항
- 무엇을 했는지 간단히 설명

### 🧪 테스트
- [ ] 로컬 테스트 완료
- [ ] 빌드 성공

### ⚠️ 참고 사항
- 리뷰어가 알아야 할 내용
- 고민했던 부분
