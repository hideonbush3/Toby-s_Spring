package com.hideonbush.vol1.ch2.ch2_4.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.sql.DataSource;

import org.springframework.dao.EmptyResultDataAccessException;

import com.hideonbush.vol1.ch2.ch2_4.domain.User;

public class UserDao {
    private DataSource dataSource;

    public void setDataSource(DataSource d) {
        this.dataSource = d;
    }

    public void add(User user) throws SQLException {
        Connection c = dataSource.getConnection();

        PreparedStatement ps = c.prepareStatement(
                "insert into users(id, name, password) values(?,?,?)");
        ps.setString(1, user.getId());
        ps.setString(2, user.getName());
        ps.setString(3, user.getPassword());

        ps.executeUpdate();

        ps.close();
        c.close();
    }

    public User get(String id) throws SQLException {
        Connection c = dataSource.getConnection();

        PreparedStatement ps = c.prepareStatement(
                "select * from users where id = (?)");
        ps.setString(1, id);

        ResultSet rs = ps.executeQuery();
        User user = null;

        if (rs.next()) {
            user = new User(
                    rs.getString("id"),
                    rs.getString("name"),
                    rs.getString("password"));
        }

        rs.close();
        ps.close();
        c.close();

        // 아이디에 맞는 유저가 존재하지 않으면 EmptyResultDataAccessException 예외 발생
        if (user == null)
            throw new EmptyResultDataAccessException(1);

        return user;
    }

    // 여태껏 테스트시 불편했던점
    // 최초 테스트 이 후 두번째 테스트 부터 add()로 전달하는 유저 정보의
    // id값을 최초 테스트때와 똑같이 똑같이하면 기본키이기 에러가 발생
    // 새로운 유저의 정보를 생성할때 id값을 이전 테스트에서 사용했던 값과 다르게 하던지
    // 이전 테스트로 생성된 레코드를 테이블에서 직접 지우고나서 테스트를 실시해야 했다

    // 이것들을 해결하기 위해 테스트 add, get 이전에 테이블의 데이터를 비우는 deleteAll()과
    // sql실행 이후 테이블에 제대로 반영됐는지 레코드의 개수를 세는 getCount() 메서드들을 추가함
    public void deleteAll() throws SQLException {
        Connection c = dataSource.getConnection();

        PreparedStatement ps = c.prepareStatement("delete from users");

        ps.executeUpdate();

        ps.close();
        c.close();
    }

    public int getCount() throws SQLException {
        Connection c = dataSource.getConnection();

        PreparedStatement ps = c.prepareStatement("select count(*) from users");

        ResultSet rs = ps.executeQuery();
        rs.next();
        int count = rs.getInt(1);

        rs.close();
        ps.close();
        c.close();

        return count;
    }
}