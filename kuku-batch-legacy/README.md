# 배치 정리

1. 기존의 sample job 실행
-  --job.name=fileJob requestDate=20240101

### 이슈 해결
1. Unknown column 'JOB_CONFIGURATION_LOCATION' in 'field list'
원인: 스프링 버전에 따른 테이블 스키마가 제대로 적용되지 않음
해결 : 스프링 메타 데이터 테이블을 버전에 맞게 재 생성
참고 : https://github.com/krams915/spring-batch-tutorial/issues/1 