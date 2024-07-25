package com.hideonbush.vol1.ch2.ch2_2.dao;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.sql.SQLException;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;

import com.hideonbush.vol1.ch1.ch1_8.domain.User;

public class UserDaoTest {

    @Test
    public void addAndGet() throws SQLException {
        ApplicationContext context = new GenericXmlApplicationContext(
                "com/hideonbush/vol1/ch2/ch2_2/applicationContext.xml");
        UserDao userDao = context.getBean("userDao", UserDao.class);

        User user1 = new User();
        user1.setId("abc123");
        user1.setName("austin");
        user1.setPassword("austin123");

        userDao.add(user1);

        User user2 = userDao.get("abc123");

        assertThat(user2.getName(), is(user1.getName()));
        assertThat(user2.getPassword(), is(user1.getPassword()));
    }
}