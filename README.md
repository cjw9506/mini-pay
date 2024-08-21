# Minipay - 입출금, 송금 서비스

<br>

<div align="center">
<img src="https://img.shields.io/badge/Spring Boot -6DB33F?style=for-the-badge&logo=spring&logoColor=white"/></a>
<img src="https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white"/></a>
<img src="https://img.shields.io/badge/Spring Data JPA-gray?style=for-the-badge&logoColor=white"/></a>
<img src="https://img.shields.io/badge/Junit-25A162?style=for-the-badge&logo=JUnit5&logoColor=white"/></a>
</div>
<div align="center">

<img src="https://img.shields.io/badge/MySQL 8-4479A1?style=for-the-badge&logo=MySQL&logoColor=white"/></a>
<img src="https://img.shields.io/badge/GitHub-100000?style=for-the-badge&logo=github&logoColor=white"/></a>
<img src="https://img.shields.io/badge/Spring Batch -6DB33F?style=for-the-badge&logo=spring&logoColor=white"/></a>
</div>

Minipay는 입출금, 송금, 그리고 내역 관리를 제공하는 금융 시스템입니다. 유저는 메인 계좌와 적금 계좌를 생성할 수 있으며, 적금 계좌로 자동이체 및 자유이체가 가능합니다. 
정해진 날짜에 이자가 지급되며, 다른 유저에게 송금할 수도 있습니다.

## 0. 목차
- [1.프로젝트 아키텍처](#1-아키텍처)
- [2.ERD](#2-erd)
- [3.API 문서](#3-api-document)
- [4.프로젝트 중 고려사항](#4-프로젝트-중-고려사항)

## 1. 아키텍처
<img width="100%" alt="Architecture" src="https://private-user-images.githubusercontent.com/63503519/359353840-e15198ad-a938-42ed-bc7b-208e3b025898.png?jwt=eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJnaXRodWIuY29tIiwiYXVkIjoicmF3LmdpdGh1YnVzZXJjb250ZW50LmNvbSIsImtleSI6ImtleTUiLCJleHAiOjE3MjQyMjcwOTEsIm5iZiI6MTcyNDIyNjc5MSwicGF0aCI6Ii82MzUwMzUxOS8zNTkzNTM4NDAtZTE1MTk4YWQtYTkzOC00MmVkLWJjN2ItMjA4ZTNiMDI1ODk4LnBuZz9YLUFtei1BbGdvcml0aG09QVdTNC1ITUFDLVNIQTI1NiZYLUFtei1DcmVkZW50aWFsPUFLSUFWQ09EWUxTQTUzUFFLNFpBJTJGMjAyNDA4MjElMkZ1cy1lYXN0LTElMkZzMyUyRmF3czRfcmVxdWVzdCZYLUFtei1EYXRlPTIwMjQwODIxVDA3NTMxMVomWC1BbXotRXhwaXJlcz0zMDAmWC1BbXotU2lnbmF0dXJlPWVhOWZhZDM4YzgwMTI0Y2I2NWZhNzU4YTYyZjgzMTdiNWM5NWZmYmNmMTQ4MTU2YjZkMDBlYTU0NWYxODc2NjAmWC1BbXotU2lnbmVkSGVhZGVycz1ob3N0JmFjdG9yX2lkPTAma2V5X2lkPTAmcmVwb19pZD0wIn0.M8Qq1A8xk7fPZVEvj2EuTkoNOKIo0eaz9wwDCjUQEZI">

## 2. ERD
<img width="100%" alt="ERD" src="https://private-user-images.githubusercontent.com/63503519/359353821-981d3aa5-54db-4447-8776-5dd06b344d75.png?jwt=eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJnaXRodWIuY29tIiwiYXVkIjoicmF3LmdpdGh1YnVzZXJjb250ZW50LmNvbSIsImtleSI6ImtleTUiLCJleHAiOjE3MjQyMjcwOTEsIm5iZiI6MTcyNDIyNjc5MSwicGF0aCI6Ii82MzUwMzUxOS8zNTkzNTM4MjEtOTgxZDNhYTUtNTRkYi00NDQ3LTg3NzYtNWRkMDZiMzQ0ZDc1LnBuZz9YLUFtei1BbGdvcml0aG09QVdTNC1ITUFDLVNIQTI1NiZYLUFtei1DcmVkZW50aWFsPUFLSUFWQ09EWUxTQTUzUFFLNFpBJTJGMjAyNDA4MjElMkZ1cy1lYXN0LTElMkZzMyUyRmF3czRfcmVxdWVzdCZYLUFtei1EYXRlPTIwMjQwODIxVDA3NTMxMVomWC1BbXotRXhwaXJlcz0zMDAmWC1BbXotU2lnbmF0dXJlPTk5MWFlOTVmZjFjMmYxY2JkZTMzOGEyN2I2ZjIyZTVjYTc0NzRhN2M1Zjk1MzU1MjhjZDI1YzM1OGU3YWFlYWYmWC1BbXotU2lnbmVkSGVhZGVycz1ob3N0JmFjdG9yX2lkPTAma2V5X2lkPTAmcmVwb19pZD0wIn0.LHVVJX2RawXQzIVz8z7ifB7SiE0t1pqn78eTqTNK77I">

## 3. API Document
[API 문서 링크](http://54.180.228.13:8080/swagger-ui/index.html#/)
<img width="100%" alt="API DOCS" src="https://private-user-images.githubusercontent.com/63503519/359802701-fc689c30-8670-4993-8095-c4ce26443edb.png?jwt=eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJnaXRodWIuY29tIiwiYXVkIjoicmF3LmdpdGh1YnVzZXJjb250ZW50LmNvbSIsImtleSI6ImtleTUiLCJleHAiOjE3MjQyMjcwMDcsIm5iZiI6MTcyNDIyNjcwNywicGF0aCI6Ii82MzUwMzUxOS8zNTk4MDI3MDEtZmM2ODljMzAtODY3MC00OTkzLTgwOTUtYzRjZTI2NDQzZWRiLnBuZz9YLUFtei1BbGdvcml0aG09QVdTNC1ITUFDLVNIQTI1NiZYLUFtei1DcmVkZW50aWFsPUFLSUFWQ09EWUxTQTUzUFFLNFpBJTJGMjAyNDA4MjElMkZ1cy1lYXN0LTElMkZzMyUyRmF3czRfcmVxdWVzdCZYLUFtei1EYXRlPTIwMjQwODIxVDA3NTE0N1omWC1BbXotRXhwaXJlcz0zMDAmWC1BbXotU2lnbmF0dXJlPTY1MWU3N2YxNDU4OWI0ODcwNmJkODU0OTBhZjQwOWZkZGMzNTgzODk1ODE2ODE3MGQ0ZmRkYzZkMTU3YjM3YWImWC1BbXotU2lnbmVkSGVhZGVycz1ob3N0JmFjdG9yX2lkPTAma2V5X2lkPTAmcmVwb19pZD0wIn0.vjjMWoS4rPCYP_lDGdwWV_NXipgnNf-Z5YaWbgA5GmE">

## 4. 프로젝트 중 고려사항

  ### 1. 이체 중 발생하는 동시성 문제 해결하기 [링크](https://glacier-beef-911.notion.site/143681fce2c540cb91f01dd96dfbac9a?pvs=74)
  이체 기능 수행중에 동시성 이슈가 발생하는지에 대해 확인해보고, 어떤 문제가 있어 발생했으며 어떻게 해결할지에 대해 생각해보았습니다.
  <br>
  <img width="100%" alt="Concurrency" src="https://private-user-images.githubusercontent.com/63503519/359353853-3e4efd0d-6904-4626-a31e-b30a73006677.png?jwt=eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJnaXRodWIuY29tIiwiYXVkIjoicmF3LmdpdGh1YnVzZXJjb250ZW50LmNvbSIsImtleSI6ImtleTUiLCJleHAiOjE3MjQyMjcwOTEsIm5iZiI6MTcyNDIyNjc5MSwicGF0aCI6Ii82MzUwMzUxOS8zNTkzNTM4NTMtM2U0ZWZkMGQtNjkwNC00NjI2LWEzMWUtYjMwYTczMDA2Njc3LnBuZz9YLUFtei1BbGdvcml0aG09QVdTNC1ITUFDLVNIQTI1NiZYLUFtei1DcmVkZW50aWFsPUFLSUFWQ09EWUxTQTUzUFFLNFpBJTJGMjAyNDA4MjElMkZ1cy1lYXN0LTElMkZzMyUyRmF3czRfcmVxdWVzdCZYLUFtei1EYXRlPTIwMjQwODIxVDA3NTMxMVomWC1BbXotRXhwaXJlcz0zMDAmWC1BbXotU2lnbmF0dXJlPTc3ODE1ZGNmOTNkNWFiMmRkN2RkMWYzODY0Mzg3Mjg4YjU5ZDE2MmUwNmNkMDM1YzJhNDk4MzQyOWMwMzlmOTYmWC1BbXotU2lnbmVkSGVhZGVycz1ob3N0JmFjdG9yX2lkPTAma2V5X2lkPTAmcmVwb19pZD0wIn0.W0O_4JyV2aEd4vqUHNptz2-4p-x_BKQXJgI7uZHAa8U">
  <br>
  <br>
  ### 2. 전체조회를 페이징으로 변경하기 [링크](https://glacier-beef-911.notion.site/7d0abdebcfa7453aa8c5915f79a83c09?pvs=74)
  전체조회를 페이징으로 변경하면서 전체조회에 대한 부담은 덜었지만, 뒷페이지를 읽을수록 느려진다는 문제를 발견하고 이를 어떻게 해결할가에 대해 고민해보았습니다.
  <br>
  <img width="100%" alt="Paging" src="https://private-user-images.githubusercontent.com/63503519/359353867-c6fd5f7d-5a73-4db9-a85c-2b5b7ade695f.png?jwt=eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJnaXRodWIuY29tIiwiYXVkIjoicmF3LmdpdGh1YnVzZXJjb250ZW50LmNvbSIsImtleSI6ImtleTUiLCJleHAiOjE3MjQyMjcwOTEsIm5iZiI6MTcyNDIyNjc5MSwicGF0aCI6Ii82MzUwMzUxOS8zNTkzNTM4NjctYzZmZDVmN2QtNWE3My00ZGI5LWE4NWMtMmI1YjdhZGU2OTVmLnBuZz9YLUFtei1BbGdvcml0aG09QVdTNC1ITUFDLVNIQTI1NiZYLUFtei1DcmVkZW50aWFsPUFLSUFWQ09EWUxTQTUzUFFLNFpBJTJGMjAyNDA4MjElMkZ1cy1lYXN0LTElMkZzMyUyRmF3czRfcmVxdWVzdCZYLUFtei1EYXRlPTIwMjQwODIxVDA3NTMxMVomWC1BbXotRXhwaXJlcz0zMDAmWC1BbXotU2lnbmF0dXJlPTM1NTBlNGQyOTJlYWVkZWNhM2U5ZGQzOWI4MDdhNTE3ZjNlZWU1NjVjZmVkYTc0YzUyMjZjMWMxMTU4ZWJhNTYmWC1BbXotU2lnbmVkSGVhZGVycz1ob3N0JmFjdG9yX2lkPTAma2V5X2lkPTAmcmVwb19pZD0wIn0.VoXS05KOB7zjmbzg0gUUKtJ0tgIxXCF6h7rMdeNrDGo">
  <br>
  <br>
  ### 3. 리플리케이션을 통한 DB 단일 장애 지점 방지하기 [링크](https://glacier-beef-911.notion.site/DB-e0ccc5dabae34cf882afe238f11209e9?pvs=74)
  읽기성능에 대한 개선과 단일 장애 지점이라는 리스크를 어떻게 방지할 수 있을지에 대해 다양한 스케일아웃 전략을 살펴보고 적용하였습니다.
  <br>
  <img width="100%" alt="Replication" src="https://private-user-images.githubusercontent.com/63503519/359353874-e841b64c-90c3-486d-bbfd-87687b3bb7f9.png?jwt=eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJnaXRodWIuY29tIiwiYXVkIjoicmF3LmdpdGh1YnVzZXJjb250ZW50LmNvbSIsImtleSI6ImtleTUiLCJleHAiOjE3MjQyMjcwOTEsIm5iZiI6MTcyNDIyNjc5MSwicGF0aCI6Ii82MzUwMzUxOS8zNTkzNTM4NzQtZTg0MWI2NGMtOTBjMy00ODZkLWJiZmQtODc2ODdiM2JiN2Y5LnBuZz9YLUFtei1BbGdvcml0aG09QVdTNC1ITUFDLVNIQTI1NiZYLUFtei1DcmVkZW50aWFsPUFLSUFWQ09EWUxTQTUzUFFLNFpBJTJGMjAyNDA4MjElMkZ1cy1lYXN0LTElMkZzMyUyRmF3czRfcmVxdWVzdCZYLUFtei1EYXRlPTIwMjQwODIxVDA3NTMxMVomWC1BbXotRXhwaXJlcz0zMDAmWC1BbXotU2lnbmF0dXJlPTBiZTE4ODQwNjFiY2MzZTk2ZWM5NWZlZGVlNzhhZWZhYzQ0YmFhNzBjMDU3ZTc0NmY1ZGU2ZDc4MmU2ZDkzZWImWC1BbXotU2lnbmVkSGVhZGVycz1ob3N0JmFjdG9yX2lkPTAma2V5X2lkPTAmcmVwb19pZD0wIn0.NE720ROItSnAnLQJLCIq9D-YN1NPQ9KIFuC9HcEglK0">
  <br>
  <br>
  ### 4. 대용량 처리를 배치작업으로 변경하기 [링크](https://glacier-beef-911.notion.site/Batch-82e141c2bfe1433e9ddf23b8d2199790)
  대용량의 데이터를 한 트랜잭션 안에서 처리하는 것이 너무 비효율적임을 깨닫고, 이를 해결하기 위해 배치를 사용해 청크 단위로 처리를 할 수 있었습니다.
  <br>
  <img width="100%" alt="Batch" src="https://private-user-images.githubusercontent.com/63503519/359353888-4caa8142-df56-410a-beaa-62d9cfff1c40.png?jwt=eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJnaXRodWIuY29tIiwiYXVkIjoicmF3LmdpdGh1YnVzZXJjb250ZW50LmNvbSIsImtleSI6ImtleTUiLCJleHAiOjE3MjQyMjcwOTEsIm5iZiI6MTcyNDIyNjc5MSwicGF0aCI6Ii82MzUwMzUxOS8zNTkzNTM4ODgtNGNhYTgxNDItZGY1Ni00MTBhLWJlYWEtNjJkOWNmZmYxYzQwLnBuZz9YLUFtei1BbGdvcml0aG09QVdTNC1ITUFDLVNIQTI1NiZYLUFtei1DcmVkZW50aWFsPUFLSUFWQ09EWUxTQTUzUFFLNFpBJTJGMjAyNDA4MjElMkZ1cy1lYXN0LTElMkZzMyUyRmF3czRfcmVxdWVzdCZYLUFtei1EYXRlPTIwMjQwODIxVDA3NTMxMVomWC1BbXotRXhwaXJlcz0zMDAmWC1BbXotU2lnbmF0dXJlPTBjOGY5NDYzZjBiMjA1MDJiOGU2Y2ZlMTE4ZjVhOGJjM2UzNjI5YWE3NjUyYjU5N2E2YTg5NjJmYjE0NzVmNmQmWC1BbXotU2lnbmVkSGVhZGVycz1ob3N0JmFjdG9yX2lkPTAma2V5X2lkPTAmcmVwb19pZD0wIn0.gptkq058hhoUbvACpsVSVI8PdtFcM2M7rFnsM2pnA-E">




