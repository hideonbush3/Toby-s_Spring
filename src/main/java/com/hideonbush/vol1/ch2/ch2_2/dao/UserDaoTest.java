package com.hideonbush.vol1.ch2.ch2_2.dao;

import java.sql.SQLException;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;

import com.hideonbush.vol1.ch1.ch1_8.domain.User;

public class UserDaoTest {
    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        ApplicationContext context = new GenericXmlApplicationContext(
                "com/hideonbush/vol1/ch2/ch2_2/applicationContext.xml");
        UserDao userDao = context.getBean("userDao", UserDao.class);

        User user1 = new User();
        user1.setId("abc123");
        user1.setName("austin");
        user1.setPassword("austin123");

        userDao.add(user1);

        User user2 = userDao.get("abc123");

        if (!user1.getName().equals(user2.getName())) {
            System.out.println("테스트 실패 - name");
        } else if (!user1.getPassword().equals(user2.getPassword())) {
            System.out.println("테스트 실패 - password");
        } else {
            System.out.println("조회 테스트 성공");
        }
    }
}