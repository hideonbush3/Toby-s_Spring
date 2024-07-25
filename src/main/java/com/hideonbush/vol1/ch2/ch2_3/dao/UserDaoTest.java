package com.hideonbush.vol1.ch2.ch2_3.dao;

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
                "com/hideonbush/vol1/ch2/ch2_3/applicationContext.xml");
        UserDao userDao = context.getBean("userDao", UserDao.class);

        // add() 작업 전 테이블의 데이터를 비운다
        userDao.deleteAll();
        // 비우기 후 레코드의 개수가 0이 됐는지 확인한다
        assertThat(userDao.getCount(), is(0));

        User user1 = new User();
        user1.setId("abc123");
        user1.setName("austin");
        user1.setPassword("austin123");

        userDao.add(user1);
        // 추가 이후 레코드의 개수가 1이 됐는지 확인한다
        assertThat(userDao.getCount(), is(1));

        User user2 = userDao.get("abc123");

        assertThat(user2.getName(), is(user1.getName()));
        assertThat(user2.getPassword(), is(user1.getPassword()));

    }
}