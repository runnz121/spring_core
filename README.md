# Spring Core

### MultiModule 설정


### kuku-java
1. try catch 벗어나기 (https://dev.gmarket.com/24)
- middleWare 패턴
  - 흐름을 이어가거나, 중간에 끼어넣는 변경이 용이 (https://dev.gmarket.com/22)


### kuku-rx-java
1. rx-java
- 리액티브 프로그래밍
- 데이터가 변경될 떄마다 이벤트 발생 -> 지속적 전달(변경)
- push : 데이터 변화 발생시 발생한 곳에서 데이터 전달
- pull : 변경 데이터 확인 후 변경된 데이터를 갖고옴 


### kuku-main
- request scope bean을 async 에서 사용하기
- https://medium.com/soobr/harnessing-the-power-of-request-scoped-beans-requestscope-in-a-non-web-based-request-dc09b1b46373

- spring event
- https://mangkyu.tistory.com/292

- spring event transactionalEventListener
- https://cheese10yun.github.io/event-transaction/
- @TransactionalEventListener + async 동작한다
- publish.event 하는 메서드에 @Trnasactional 붙어 있으면 해당 트랜잭션이 commit이 되고 실행한다
- 에러 발생시 event publish 안됨