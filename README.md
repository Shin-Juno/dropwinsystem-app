# -DropWin-
DropWin은 *Drop(드랍)*과 *Win(이기다)*를 결합한 이름으로, 스포츠 장비를 실시간으로 입찰하고 경쟁 속에서 낙찰 받을 수 있는 스포츠 특화 경매 플랫폼이자 커뮤니티형 웹사이트


# DropWinSystem 경매 애플리케이션

Spring Boot, JPA, MyBatis를 사용하여 개발한 경매 시스템입니다.

## ⚙️ 주요 기술
- **Backend**: Java 21, Spring Boot 3.x, Spring Security
- **Database**: MySQL, JPA, MyBatis
- **Frontend**: Thymeleaf, HTML, CSS, JavaScript
- **Build**: Gradle

---

## 🚀 시작하기

이 프로젝트를 로컬 환경에서 설정하고 실행하는 방법입니다.

### 1. 필수 설치 항목 (Prerequisites)

- **Java 21**: [OpenJDK 21](https://jdk.java.net/21/) 또는 다른 JDK 배포판을 설치하세요.
- **MySQL**: 데이터베이스 서버를 설치하세요. [MySQL Community Server](https://dev.mysql.com/downloads/mysql/)
- **Git**: 소스 코드를 복제하기 위해 필요합니다.

### 2. 설치 및 설정 (Installation & Setup)

#### 1) 소스 코드 복제

```bash
git clone https://github.com/YOUR_USERNAME/dropwinsystem-app.git
cd dropwinsystem-app
```
> **Note**: `YOUR_USERNAME` 부분은 실제 GitHub 사용자 이름으로 변경해주세요.

#### 2) 데이터베이스 생성 및 설정

MySQL에 접속하여 `dropwin` 데이터베이스를 생성합니다.

```sql
CREATE DATABASE dropwin CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;
```

데이터베이스가 생성되면, 아래 SQL 스크립트들을 **순서대로** 실행하여 테이블과 초기 데이터를 생성해야 합니다. MySQL Workbench, DBeaver 같은 GUI 도구를 사용하거나 터미널에서 직접 실행할 수 있습니다.

**실행해야 할 SQL 파일 목록:**
- `resources/SQL/memberDB.sql`
- `resources/SQL/auctionDB.sql`
- `resources/SQL/ItemDB.sql`
- `resources/SQL/boardSQL.sql`
- `resources/SQL/noticeDB.sql`
- `resources/SQL/calendarDB.sql`
- `resources/SQL/favoritesDB.sql`
- `resources/SQL/informationDB.sql`
- `resources/SQL/bidsSQL.spl.sql`
- `resources/SQL/itemsSQL.sql`

> **Warning**: 위 목록은 일반적인 순서이며, 스크립트 간의 의존성에 따라 순서 조정이 필요할 수 있습니다.

#### 3) 애플리케이션 설정

`src/main/resources/application.properties` 파일을 열어 본인의 로컬 환경에 맞게 수정합니다.

- **데이터베이스 연결 정보**

```properties
spring.datasource.username=YOUR_MYSQL_USERNAME
spring.datasource.password=YOUR_MYSQL_PASSWORD
```

- **파일 업로드 경로 (중요)**
  로컬 PC의 특정 경로가 하드코딩되어 있으므로, 이 부분을 자신의 환경에 맞는 경로로 반드시 수정해야 합니다.

```properties
# 아래 경로를 자신의 PC에 맞게 수정하세요.
app.upload.dir=C:/your/upload/directory/
spring.web.resources.static-locations=classpath:/static/,file:C:/your/upload/directory/
```

### 3. 빌드 및 실행 (Build & Run)

프로젝트 루트 디렉토리에서 아래 명령어를 실행합니다.

- **Windows:**
  ```shell
  # 빌드
  gradlew.bat build

  # 실행
  gradlew.bat bootRun
  ```

- **macOS / Linux:**
  ```shell
  # 빌드
  ./gradlew build

  # 실행
  ./gradlew bootRun
  ```

애플리케이션이 성공적으로 실행되면, 웹 브라우저에서 `http://localhost:8080`으로 접속할 수 있습니다.

---

## 🧪 테스트 (Testing)

현재 프로젝트의 JUnit 테스트는 로컬 설정 의존성 문제로 인해 기본적으로 비활성화되어 있습니다. (`build.gradle`에서 `test.enabled = false`)

테스트를 다시 활성화하고 실행하려면 다음을 확인해야 합니다.

1.  **테스트 환경**: 테스트는 H2 인메모리 데이터베이스를 사용하도록 설정되어 있습니다. (`src/test/resources/application.properties`)
2.  **Bean 생성 오류**: 테스트 실행 시 `NoSuchBeanDefinitionException` 오류가 발생하며, 이는 테스트 환경에서 특정 Bean(객체)을 생성하지 못하기 때문입니다. 이 문제를 해결해야만 테스트가 정상적으로 통과됩니다.

