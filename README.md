# wanted-pre-onboarding-backend

___

## 프로젝트 개요

- 본 서비스는 기업의 채용을 위한 웹 서비스 백엔드 API 입니다.
- 회사는 채용공고를 생성하고, 이에 사용자는 지원합니다.

## 기술 스택

- Java 17
- SpringBoot 3.0.4
- Spring Data JPA
- Querydsl 5.0.0
- JUnit5
- MySQL 8.0.31

<details>
  <summary style="font-size: 21px; font-weight: bold">ERD</summary>

<div markdown="1">
<img src="https://github.com/jhva/wanted-pre-onboarding-backend/blob/main/docs/%EC%8A%A4%ED%81%AC%EB%A6%B0%EC%83%B7%202023-10-13%20%EC%98%A4%ED%9B%84%206.
50.16.png"/>

</div>
</details>

## application-production.yaml

```yaml
spring:
  jpa:
    hibernate:
      ddl-auto: update
    naming:
      # camelCase -> snake_case
      physical-strategy: org.hibernate.boot.model.naming.CamelCaseToUnderscoresNamingStrategy
    properties:
      hibernate:
        format_sql: true # hibernate query 포맷 정리
        show_sql: true # hibernate query를 콘솔에 출력
  datasource:
    driver-class-name: org.mariadb.jdbc.Driver
    url: jdbc:mariadb://localhost:3306/wanted_onboarding_db
    username: root
    password: 1234
```

----

## 프로젝트 요구사항

- [x] ~~[사용자 등록](##사용자-등록)~~
- [x] ~~[회사 등록](#회사-등록)~~
- [x] ~~[채용공고 등록](#채용공고-등록)~~
- [x] ~~[채용공고 수정](##채용공고 수정)~~
- [x] ~~[채용공고 삭제](##채용공고 삭제)~~
- [x] ~~[채용공고 목록 조회](##채용공고 목록 조회)~~
- [x] ~~[채용공고 검색 기능](##채용공고 검색 기능)~~
- [x] ~~[채용 상세 페이지 조회](##채용 상세 페이지 조회)~~
- [x] ~~[사용자가 채용 공고에 등록](##사용자가 채용 공고에 등록)~~

## 사용자 등록

`POST` `http://localhost:9000/api/v1/create-user`

```json
{
  "userName": "김정훈"
}
```

```json
{
  "msg": "success",
  "statusCode": 201
}
```

## 회사 등록

`POST` `http://localhost:9000/api/v1/create-company`

```json
{
  "companyName": "원티드랩",
  "companyArea": "판교",
  "companyCountry": ""
}
```

```json
{
  "msg": "success",
  "statusCode": 201
}
```

- 예외

```json
{
  "msg": "유효성 검증 실패",
  "statusCode": 400,
  "data": "[companyCountry](은)는 사용 기술을 입력해주세요. 입력된 값: []"
}
```

## 채용공고 등록

`POST` `http://localhost:9000/api/v1/create-job`

```json
{
  "jobPosition": "백엔드 주니어 개발자",
  "jobDescription": "원티드랩에서 백엔드 주니어 개발자를 채용합니다.",
  "jobCompensation": 1000000,
  "jobTech": "",
  "company": "75e486bc-6bd1-46b5-b738-19fb288911ac"
}
```

```json
{
  "msg": "success",
  "statusCode": 201
}
```

- 예외

```json
{
  "msg": "유효성 검증 실패",
  "statusCode": 400,
  "data": "[jobTech](은)는 사용 기술을 입력해주세요. 입력된 값: []"
}
```

## 채용공고 수정

`PATCH` `http://localhost:9000/api/v1/update-job/{jobId}`

```json
{
  "jobPosition": "프론트엔드 주니어 개발자",
  "jobDescription": "원티드랩에서 프론트엔드 주니어 개발자를 채용합니다.",
  "jobCompensation": 1000000,
  "jobTech": "Java"
}
```

```json
{
  "msg": "success",
  "statusCode": 200
}
```

- 예외

```json
{
  "message": "NOT_EXIST_JOB",
  "reason": "채용공고가 존재하지 않습니다."
}
```

## 채용공고 삭제

`Delete` `http://localhost:9000/api/v1/delete-job/{jobId}`

```json
{
  "jobPosition": "프론트엔드 주니어 개발자",
  "jobDescription": "원티드랩에서 프론트엔드 주니어 개발자를 채용합니다.",
  "jobCompensation": 1000000,
  "jobTech": "Java"
}
```

```json
{
  "msg": "success",
  "statusCode": 200
}
```

- 예외

```json
{
  "message": "NOT_EXIST_JOB",
  "reason": "채용공고가 존재하지 않습니다."
}
```

## 채용공고 목록 조회

`GET` `http://localhost:9000/api/v1/all-job`

```json
{
  "msg": "success",
  "statusCode": 200,
  "data": [
    {
      "jobTech": "Java",
      "jobPosition": "백엔드 포지션",
      "jobDescription": "채용",
      "jobCompensation": 1000000,
      "companyName": "원티드랩",
      "companyArea": "판교",
      "companyCountry": "한국"
    }
  ]
}
```

## 채용공고 검색 기능

`GET` `http://localhost:9000/api/v1/all-job/search?=Java`

```json
{
  "msg": "success",
  "statusCode": 200,
  "data": [
    {
      "jobTech": "Java",
      "jobPosition": "백엔드 포지션",
      "jobDescription": "채용",
      "jobCompensation": 1000000,
      "companyName": "원티드랩",
      "companyArea": "판교",
      "companyCountry": "한국"
    }
  ]
}
```

- 예외: 해당 search 내용이 jobPost,company에 없을경우

```json
{
  "msg": "success",
  "statusCode": 200,
  "data": []
}
```

## 채용 상세 페이지 조회

`GET` `http://localhost:9000/api/v1/detail-job/{jobId}`

`jobId` - `Param`

```json
{
  "msg": "success",
  "statusCode": 200,
  "data": {
    "id": "a771e750-280f-4ec3-bb07-f9ee632a9384",
    "jobTech": "Java",
    "jobPosition": "백엔드 포지션",
    "jobDescription": "채용",
    "jobCompensation": 1000000,
    "companyName": "원티드랩",
    "companyArea": "판교",
    "companyCountry": "한국",
    "otherJobDocs": [
      "437efdf6-52f7-44b5-81e7-05008d736e8a",
      "a771e750-280f-4ec3-bb07-f9ee632a9384"
    ]
  }
}
```

- 예외: jobPostId 없을경우

```json
{
  "message": "NOT_EXIST_JOB",
  "reason": "채용공고가 존재하지 않습니다."
}
```

## 사용자가 채용 공고에 등록

`GET` `http://localhost:9000/api/v1/apply`

`jobId` - `Param`

```json
{
  "jobId": "ed0fb338-2ea0-430a-9268-ca9cc392cf78",
  "userId": "1de8e83a-8674-4d23-8739-7827645c236f"
}
```

```json
{
  "msg": "success",
  "statusCode": 200
}
```

- 예외: 이미 사용자가 채용공고에 지원했을경우

```json
{
  "message": "DUPLICATE_APPLY",
  "reason": "이미 해당 직무에 지원한 기록이 있습니다."
}
```