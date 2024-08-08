# 스프링의 JdbcTemplate
스프링은 JDBC를 이용하는 DAO에서 사용할 수 있도록 준비된 다양한 템플릿과 콜백을 제공한다.

스프링이 제공하는 JDBC 코드용 기본 템플릿은 JdbcTemplate이다.

이전까지 사용하던 JdbcContext는 버리고 JdbcTemplate를 사용하도록 바꾼다.

JdbcTemplate를 사용하려면 생성자의 파라미터로 DataSource를 주입하면 된다.

## queryForObject(PreparedStatementCreator psc, RowMapper rm)
조회 결과가 한 행이고 이것을 하나의 객체로 매핑하는 작업을 할때 주로 사용하는 템플릿 메서드

### 실행흐름
1. PreparedStatementCreator의 콜백 메서드는 PreparedStatement를 생성한다.
2. queryForObject() 는 결과 행이 한개일 거라고 기대하기 때문에 PreparedStatement가 실행되고나서 생성되는 ResultSet에서 next()를 호출하고 첫번째 행으로 이동시킨 후에 RowMapper 콜백을 호출한다.
3. RowMapper는 next() 할 필요없이 현재 로우의 내용을 User 객체에 담아 반환한다.

### 조회결과가 없다면?
SQL을 실행했을때 로우가 한개가 아니라면 EmptyResultDataAccessException라는 예외를 던지도록 만들어져 있다.