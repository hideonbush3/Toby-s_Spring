package com.hideonbush.vol1.ch3.ch3_2.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.sql.DataSource;

import org.springframework.dao.EmptyResultDataAccessException;

import com.hideonbush.vol1.ch3.ch3_2.domain.User;

public abstract class UserDao {
    private DataSource dataSource;

    public void setDataSource(DataSource d) {
        this.dataSource = d;
    }

    public void add(User user) throws SQLException {
        Connection c = null;
        PreparedStatement ps = null;
        try {
            c = dataSource.getConnection();
            ps = c.prepareStatement(
                    "insert into users(id, name, password) values(?,?,?)");
            ps.setString(1, user.getId());
            ps.setString(2, user.getName());
            ps.setString(3, user.getPassword());

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

    // 변한다, 변하지 않는다?
    // 매 메서드마다 중복된다 -> 변하지 않는다
    // 매 메서드마다 내용이 달라진다 -> 변한다

    // 전략패턴
    // Context의 contextMethod()에서 일정한 구조(변하지 않는 부분 즉, 여기선 try/catch/finally)를
    // 갖고 동작하다가 특정 확장기능은 Strategy 인터페이스를 통해 외부의 독립된 클래스에 위임하는 패턴구조

    // Context에 해당하는것 -> UserDao
    // 특정 확장기능(변하는 부분) -> PreparedStatement를 만드는 부분
    // Strategy에 해당하는것 -> StatementStrategy 인터페이스
    // 외부의 독립된 클래스에 해당하는것 -> DeleteAllStatement
    public void deleteAll() throws SQLException {
        Connection c = null;
        PreparedStatement ps = null;
        try {
            c = dataSource.getConnection();

            StatementStrategy strategy = new DeleteAllStatement();
            ps = strategy.makePreparedStatement(c);

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

    abstract protected PreparedStatement makeStatement(Connection c) throws SQLException;

    public int getCount() throws SQLException {
        Connection c = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            c = dataSource.getConnection();
            ps = c.prepareStatement("select count(*) from users");
            rs = ps.executeQuery();
            rs.next();
            return rs.getInt(1);
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

}