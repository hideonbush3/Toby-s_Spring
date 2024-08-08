# 스프링의 JdbcTemplate
스프링은 JDBC를 이용하는 DAO에서 사용할 수 있도록 준비된 다양한 템플릿과 콜백을 제공한다.

스프링이 제공하는 JDBC 코드용 기본 템플릿은 JdbcTemplate이다.

이전까지 사용하던 JdbcContext는 버리고 JdbcTemplate를 사용하도록 바꾼다.

JdbcTemplate를 사용하려면 생성자의 파라미터로 DataSource를 주입하면 된다.