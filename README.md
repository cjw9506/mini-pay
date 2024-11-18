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
<img width="640" alt="Architecture" src="https://github.com/user-attachments/assets/e15198ad-a938-42ed-bc7b-208e3b025898">

## 2. ERD
<img width="633" alt="ERD" src="https://github.com/user-attachments/assets/981d3aa5-54db-4447-8776-5dd06b344d75">

## 3. API Document
[API 문서 링크](http://54.180.228.13:8080/swagger-ui/index.html#/)
<img width="979" alt="스크린샷 2024-08-21 오후 4 51 34" src="https://github.com/user-attachments/assets/fc689c30-8670-4993-8095-c4ce26443edb">

## 4. 프로젝트 중 고려사항

### 1. 이체 중 발생하는 동시성 문제 해결하기
이체 기능 수행 중 발생한 <strong>데드락(Deadlock)</strong>과 <strong>Lost Update</strong> 문제를 분석하고, 상황에 맞는 동시성 제어 방안을 선택하여 해결하였습니다.  
특히, 데이터 정합성과 트랜잭션 원자성이 중요한 금융 도메인의 특성을 고려하여 <strong>비관적 락(Pessimistic Lock)</strong>을 활용했습니다.  
비록 락을 사용하는 방식이 근본적인 해결책은 아니지만, 특정 시나리오에서 효과적으로 동시성 문제를 방지할 수 있음을 확인했습니다.

<br>

<strong>💡 문제 상황</strong><br>  
- <strong>데드락</strong>: 트랜잭션 간 교착 상태로 일부 요청 실패 발생.  
- <strong>Lost Update</strong>: 동시 업데이트 충돌로 데이터 손실 발생.  

<br>

<strong>🔧 해결 방법</strong><br>  
- <strong>비관적 락 적용</strong>: 데이터 접근 시 락을 걸어 충돌 방지.  
  금융 거래와 같이 데이터 일관성이 중요한 시나리오에서 적합.  
  비관적 락의 단점(성능 저하, 교착 상태 가능성)을 인지하고, 트랜잭션 범위를 최소화하여 활용.  

<br>

<strong>✅ 테스트 결과</strong><br>  
- <strong>조건</strong>: 100명의 사용자가 동시에 1명의 계좌로 10원 이체.  
- <strong>결과</strong>: 모든 트랜잭션 성공, 최종 계좌 잔액 1000원 확인.  

<br>
<p align="center">
  <img width="800" alt="Concurrency" src="https://github.com/user-attachments/assets/3e4efd0d-6904-4626-a31e-b30a73006677">
</p>
<br>

---

### 2. 전체조회를 페이징으로 변경하기
전체 데이터를 조회하던 방식에서 <strong>Offset Paging</strong>과 <strong>No Offset Paging</strong>의 특성을 비교한 뒤, 이 어플리케이션의 요구사항에 더 적합한 <strong>No Offset Paging</strong>을 적용하였습니다.  
이를 통해 페이지가 뒤로 갈수록 조회 속도가 느려지는 문제를 해결하고, 성능을 개선할 수 있었습니다.

<br>

<strong>💡 문제 상황</strong><br>  
- <strong>Offset Paging</strong>: 데이터가 많아질수록 뒤쪽 페이지 조회 시 쿼리 성능 저하 발생.  
- <strong>No Offset Paging</strong>: 데이터의 정렬 기준에 따라 성능을 최적화할 수 있지만, 특정 상황에서 구현 복잡도가 증가.  

<br>

<strong>🔧 해결 방법</strong><br>  
- 어플리케이션의 특성과 데이터 구조를 고려하여 <strong>No Offset Paging</strong>을 선택.  
- 페이지가 뒤로 갈수록 성능 저하 문제가 개선됨.  
- 정렬 기준을 명확히 하여 구현 복잡도를 최소화.  

<br>

<strong>✅ 테스트 결과</strong><br>  
- <strong>조건</strong>: 100만 개 이상의 데이터를 가진 테이블에서 뒤쪽 페이지 조회.  
- <strong>결과</strong>: 조회 속도 개선, 뒤쪽 페이지에서도 일관된 성능 유지.  

<br>
<p align="center">
  <img width="800" alt="Paging Performance" src="https://github.com/user-attachments/assets/9eb43164-9316-4866-8bb6-ab00c221c121">
</p>
<br>

  ### 3. 리플리케이션을 통한 DB 단일 장애 지점 방지하기 
 데이터베이스가 단일 노드로 구성되어 있어 <strong>단일 장애 지점(SPOF)</strong> 발생 가능성과 읽기 성능 저하 문제가 확인되었습니다.  
이를 해결하기 위해 다양한 스케일아웃 전략을 검토한 후, <strong>데이터베이스 리플리케이션</strong>을 도입하여 문제를 해결하였습니다.

<br>

<strong>💡 문제 상황</strong><br>  
- <strong>단일 장애 지점(SPOF)</strong>: 데이터베이스 장애 시 전체 서비스가 중단될 위험 존재.  
- <strong>읽기 성능 저하</strong>: 트래픽 증가로 인해 읽기 요청이 병목 현상을 유발.  

<br>

<strong>🔧 해결 방법</strong><br>  
- <strong>리플리케이션 도입</strong>: 읽기/쓰기 작업을 분리하여 성능과 안정성 개선.  
  - <strong>Primary Node</strong>: 쓰기 작업 처리.  
  - <strong>Replica Node</strong>: 읽기 작업 분담.  
- <strong>Failover 설정</strong>: 장애 발생 시 Replica가 Primary로 자동 승격.  
- <strong>동기 리플리케이션 방식 채택</strong>: 데이터 정합성이 무엇보다 중요.  

<br>

<strong>✅ 테스트 결과</strong><br>  
- <strong>읽기/쓰기 요청 정상 작동 확인</strong>: 읽기 요청은 Replica 노드로, 쓰기 요청은 Primary 노드로 정상 처리.    
- <strong>Failover 테스트 성공</strong>: Primary 장애 발생 시 Replica가 자동 승격되어 가용성 유지.  

<br>
<p align="center">
  <img width="800" alt="Replication" src="https://github.com/user-attachments/assets/e841b64c-90c3-486d-bbfd-87687b3bb7f9">
</p>
<br>

  ### 4. 대용량 처리를 배치작업으로 변경하기 
  대용량 데이터를 한 트랜잭션 안에서 처리하는 방식의 비효율성을 해결하기 위해  
<strong>Spring Batch</strong>를 활용하여 데이터를 청크 단위로 분리하여 처리하였습니다.  
이를 통해 <strong>트랜잭션 범위</strong>를 최적화하고, <strong>메모리 효율</strong>을 개선할 수 있었습니다.

<br>

<strong>💡 문제 상황</strong><br>  
- <strong>대용량 데이터 처리 비효율</strong>: 한 트랜잭션 안에서 모든 데이터를 처리하는 방식으로 인해 메모리 사용량 급증.  
- <strong>트랜잭션 범위 비효율</strong>: 트랜잭션 범위가 너무 넓어 처리 도중 장애가 발생하면 모든 작업이 롤백되어 작업 시간 증가 및 시스템 리소스 낭비.

<br>

<strong>🔧 해결 방법</strong><br>  
- <strong>Spring Batch</strong>를 사용해 데이터를 <strong>청크 단위로 분리</strong>하여 처리.  
  - <strong>트랜잭션 범위를 청크 단위로 설정</strong>하여 효율성 개선.  
  - 처리 완료된 데이터는 즉시 커밋하여 메모리 사용량 절감.  
- <strong>Step/Job 구성</strong>: 각 처리 단계를 분리하여 관리 가능하도록 설계.  
- <strong>Retry 및 Skip 설정</strong>: 실패한 데이터만 재처리하여 전체 작업의 안정성 확보.  

<br>

<strong>✅ 테스트 결과</strong><br>  
- <strong>처리 성능 개선</strong>: 메모리 사용량이 기존 200MiB에서 30MiB로 감소.  
- <strong>안정성 확보</strong>: 처리 실패 시에도 중단된 지점부터 작업 재시작 가능.  

  <br>
  <img width="1001" alt="Batch" src="https://github.com/user-attachments/assets/4caa8142-df56-410a-beaa-62d9cfff1c40">

  



