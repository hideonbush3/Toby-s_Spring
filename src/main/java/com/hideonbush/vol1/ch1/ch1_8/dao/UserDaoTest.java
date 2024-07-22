package com.hideonbush.vol1.ch1.ch1_8.dao;

import java.sql.SQLException;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;

import com.hideonbush.vol1.ch1.ch1_8.domain.User;

public class UserDaoTest {
    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        ApplicationContext context = new GenericXmlApplicationContext(
                "com/hideonbush/vol1/ch1/ch1_8/applicationContext.xml");

        UserDao userDao = context.getBean("userDao", UserDao.class);
        User user = userDao.get("abc123");

        System.out.println(user.getName());

    }
}