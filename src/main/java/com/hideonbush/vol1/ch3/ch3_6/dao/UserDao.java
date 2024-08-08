package com.hideonbush.vol1.ch3.ch3_6.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.sql.DataSource;

import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.ResultSetExtractor;

import com.hideonbush.vol1.ch3.ch3_6.domain.User;

public class UserDao {
    private DataSource dataSource;
    private JdbcTemplate jdbcTemplate;

    public void setDataSource(DataSource d) {
        this.jdbcTemplate = new JdbcTemplate(d);

        // 아직 JdbcContext를 적용하지 않은 메서드들을 위해 남겨뒀을뿐 사실상 필요없다
        this.dataSource = d;
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
        Connection c = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            c = dataSource.getConnection();
            ps = c.prepareStatement(
                    "select * from users where id = (?)");
            ps.setString(1, id);
            rs = ps.executeQuery();

            User user = null;

            if (rs.next()) {
                user = new User(
                        rs.getString("id"),
                        rs.getString("name"),
                        rs.getString("password"));
            }
            if (user == null) {
                throw new EmptyResultDataAccessException(1);
            }
            return user;
        } catch (SQLException e) {
            throw e;
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                }
            }
            if (ps != null) {
                try {
                    ps.clearParameters();
                } catch (SQLException e) {
                }
            }
            if (c != null) {
                try {
                    c.close();
                } catch (SQLException e) {
                }
            }
        }

    }

    // 콜백을 직접 만들어서 템플릿 메서드로 전달
    // public void deleteAll() throws SQLException {
    // this.jdbcTemplate.update(
    // new PreparedStatementCreator() {
    // public PreparedStatement createPreparedStatement(Connection c)
    // throws SQLException {
    // return c.prepareStatement("delete from users");
    // };
    // });
    // }

    // 내장 콜백을 사용한다
    public void deleteAll() throws SQLException {
        this.jdbcTemplate.update("delete from users");
    }

    public int getCount() throws SQLException {
        return this.jdbcTemplate.query(
                new PreparedStatementCreator() {
                    public PreparedStatement createPreparedStatement(Connection con)
                            throws SQLException {
                        return con.prepareStatement("select count(*) from users");
                    }
                },
                new ResultSetExtractor<Integer>() {
                    public Integer extractData(ResultSet rs) throws SQLException, DataAccessException {
                        rs.next();
                        return rs.getInt(1);
                    }
                });
    }

}