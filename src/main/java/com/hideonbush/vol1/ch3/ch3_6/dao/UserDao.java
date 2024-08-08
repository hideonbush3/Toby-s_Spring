package com.hideonbush.vol1.ch3.ch3_6.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import com.hideonbush.vol1.ch3.ch3_6.domain.User;

public class UserDao {
    private JdbcTemplate jdbcTemplate;

    public void setDataSource(DataSource d) {
        this.jdbcTemplate = new JdbcTemplate(d);
    }

    // 치환자를 가진 SQL로 PreparedStatement를 만들고
    // 함께 제공하는 파라미터를 순서대로 바운딩해준다
    public void add(final User user) throws SQLException {
        this.jdbcTemplate.update(
                "insert into users(id, name, password) values(?, ?, ?)",
                user.getId(),
                user.getName(),
                user.getPassword());
    }

    public User get(String id) throws SQLException {
        return this.jdbcTemplate.queryForObject(
                "select * from users where id = ?",
                new Object[] { id }, // 쿼리에 바운딩할 파라미터, 가변인자가 아닌 Object 배열로 생성
                new RowMapper<User>() {
                    public User mapRow(ResultSet rs, int rowNum) throws SQLException {
                        return new User(
                                rs.getString("id"),
                                rs.getString("name"),
                                rs.getString("password"));
                    }
                });
    }

    // 내장 콜백을 사용한다
    public void deleteAll() throws SQLException {
        this.jdbcTemplate.update("delete from users");
    }

    public int getCount() throws SQLException {
        return this.jdbcTemplate.queryForObject(
                "select count(*) from users", Integer.class);
    }

}