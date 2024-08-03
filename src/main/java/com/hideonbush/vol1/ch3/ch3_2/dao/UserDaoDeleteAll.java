package com.hideonbush.vol1.ch3.ch3_2.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

// 템플릿 메서드 패턴의 예시
// UserDao의 기능을 확장하고 싶을때마다 상속을 통해 자유롭게 확장
// 그러나 큰 단점이 있으니,
// 1. dao 로직마다 서브 클래스를 만들어야 한다는 점
// 2. 확장구조가 클래스를 설계하는 시점에서 고정된다
public class UserDaoDeleteAll extends UserDao {

    @Override
    protected PreparedStatement makeStatement(Connection c) throws SQLException {
        PreparedStatement ps = c.prepareStatement("delete from users");
        return ps;
    }

}
