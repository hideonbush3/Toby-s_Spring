package com.hideonbush.vol1.ch1.ch1_7.dao;

import java.sql.SQLException;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class UserDaoConnectionCouningTest {
    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(CountingDaoFactory.class);

        UserDao userDao = context.getBean("userDao", UserDao.class);

        // 10회 작업
        for (int i = 0; i < 10; i++) {
            userDao.get("abc123");
        }

        CountingConnectionMaker counter = context.getBean("connectionMaker", CountingConnectionMaker.class);
        System.out.printf("userDao 사용횟수 -> %d회", counter.getCounter());
    }
}
