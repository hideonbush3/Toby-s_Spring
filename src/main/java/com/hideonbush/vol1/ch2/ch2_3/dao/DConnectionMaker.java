package com.hideonbush.vol1.ch2.ch2_3.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

// D사의 자체적인 커넥션 객체 정보
public class DConnectionMaker implements ConnectionMaker {
    @Override
    public Connection newConnection() throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        return DriverManager.getConnection(
                "jdbc:mysql://localhost/spring", "root", "root");
    }
}