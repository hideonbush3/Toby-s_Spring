package com.hideonbush.vol1.ch3.ch3_6.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.sql.DataSource;

public class JdbcContext {
    private DataSource dataSource;

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public void workWithStatementStrategy(StatementStrategy stmt) throws SQLException {
        Connection c = null;
        PreparedStatement ps = null;
        try {
            c = dataSource.getConnection();

            ps = stmt.makePreparedStatement(c);

            ps.executeUpdate();
        } catch (SQLException e) {
            throw e;
        } finally {
            if (ps != null) {
                try {
                    ps.close();
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

    // 자바 8 이전에는 익명 내부 클래스는 외부 클래스의 변수를 참조할 때,
    // 해당 변수는 final로 선언돼야 했었다
    // 자바 8 이후로는 final을 생략 가능
    // 하지만 해당 변수가 변경되지 않는다는 의도를 명확히 하기 위해
    // 명시적으로 final로 선언하는게 좋은 습관
    public void executeQuery(final String query) throws SQLException {
        workWithStatementStrategy(
                new StatementStrategy() {
                    public PreparedStatement makePreparedStatement(Connection c)
                            throws SQLException {
                        return c.prepareStatement(query);
                    }
                });
    }
}
