
## 📝 개요. 서비스 기획서

## 🚀 1. 요구사항 명세서 - GoogleSheets

## 🚀 2. 테스트 계획서  - GoogleSheets

## 🚀 3. ERD (Entity Relationship Diagram, 객체관계도) - DBdiagram.io
<img width="1100" alt="스크린샷 2025-04-01 오후 5 48 02" src="https://github.com/user-attachments/assets/2d63d405-3822-4f46-8702-c723f939e422" />

## 🚀 4. WBS(Work Breakdown Structure) - Jira
<img width="1100" alt="스크린샷 2025-04-01 오후 5 18 08" src="https://github.com/user-attachments/assets/5914a2a1-cbf6-4338-8138-42e6d45c8cf7" />

## 🚀 5. 화면 정의서 

## 🚀 6. 테스트 결과서 - GoogleSheets

## 🚀 7. 빌드 및 배포 개요(시스템 아키텍쳐)
<img width="1100" alt="system" src="https://github.com/user-attachments/assets/2a8a488d-c5a4-4346-9ac7-79a64b9e8d92" />

### 📌 사용 기술 및 도구
- **운영체제:** AWS(EC2 Ubuntu22.04)
- **언어:** Java (Spring Boot)
- **빌드 도구:** Gradle
- **컨테이너화:** Docker
- **CI/CD 도구:** Jenkins, ArgoCD
- **배포 인프라:** Kubernetes
- **데이터베이스:** AWS RDS(MySQL), AWS EC2(MONGODB)

### 🔄 CI/CD 개요

1. **Jenkins**: 소스 코드 관리 및 CI/CD 트리거
2. **Jenkins**: 자동화된 빌드 및 Docker 이미지 생성, Docker Hub에 푸시
3. **Docker Hub**: 빌드된 이미지를 저장하고 Kubernetes에서 사용
4. **ArgoCD**: Kubernetes 클러스터에 자동 배포


## 🎯 프로젝트 정보
📌 **InsaHi 레포지토리:** [GitHub - PlayData Final](https://github.com/05Daul/insahi)

