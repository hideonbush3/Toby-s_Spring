package com.hideonbush.vol1.ch1.ch1_8.dao;

import java.sql.SQLException;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;

import com.hideonbush.vol1.ch1.ch1_8.domain.User;

public class UserDaoTest {
    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        ApplicationContext context = new AnnotationConfigApplicationContext(DaoFactory.class);
        UserDao userDao = context.getBean("userDao", UserDao.class);
        User user = userDao.get("abc123");
        System.out.println(user.getName());

        ApplicationContext xmlContext = new GenericXmlApplicationContext(
                "com/hideonbush/vol1/ch1/ch1_8/applicationContext.xml");
        UserDao userDao2 = xmlContext.getBean("userDao", UserDao.class);
        User user2 = userDao2.get("abc123");
        System.out.println(user2.getName());
    }
}