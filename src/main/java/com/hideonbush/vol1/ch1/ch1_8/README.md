# ch1.8 XML을 이용한 DI 의존관계 설정
스프링은 @Configure 어노테이션을 붙인 자바 클래스를 이용하는 것 외에도
다양한 방법을 통해 DI 의존관계 설정정보를 만들 수 있다.
그 중 대표적인게 XML이다.

단순한 텍스트 파일이며
컴파일같은 별도의 빌드가 필요없으며
변경사항을 빠르게 적용할 수 있고
DTD나 스키마를 통해 정해진 포맷으로 작성됐는지 쉽게 확인할 수 있다.