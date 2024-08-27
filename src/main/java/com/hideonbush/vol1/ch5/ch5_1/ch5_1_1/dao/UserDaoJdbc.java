package com.hideonbush.vol1.ch5.ch5_1.ch5_1_1.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import com.hideonbush.vol1.ch5.ch5_1.ch5_1_1.domain.Level;
import com.hideonbush.vol1.ch5.ch5_1.ch5_1_1.domain.User;

public class UserDaoJdbc implements UserDao {
    private JdbcTemplate jdbcTemplate;
    private RowMapper<User> userMapper = new RowMapper<User>() {
        @Override
        public User mapRow(ResultSet rs, int rowNum) throws SQLException {
            return new User(
                    rs.getString("id"),
                    rs.getString("name"),
                    rs.getString("password"),
                    Level.valueOf(rs.getInt("level")),
                    rs.getInt("login"),
                    rs.getInt("recommend"));
        }

    };

    public void setDataSource(DataSource d) {
        this.jdbcTemplate = new JdbcTemplate(d);
    }

    public void add(final User user) {
        this.jdbcTemplate.update(
                "insert into " +
                        "users(id, name, password, level, login, recommend) " +
                        "values(?, ?, ?, ?, ? ,?)",
                user.getId(),
                user.getName(),
                user.getPassword(),
                user.getLevel().intValue(),
                user.getLogin(),
                user.getRecommend());
    }

    public User get(String id) {
        return this.jdbcTemplate.queryForObject(
                "select * from users where id = ?",
                new Object[] { id },
                this.userMapper);
    }

    public List<User> getAll() {
        return this.jdbcTemplate.query(
                "select * from users order by id",
                this.userMapper);
    }

    public void deleteAll() {
        this.jdbcTemplate.update("delete from users");
    }

    public int getCount() {
        return this.jdbcTemplate.queryForObject(
                "select count(*) from users", Integer.class);
    }

}