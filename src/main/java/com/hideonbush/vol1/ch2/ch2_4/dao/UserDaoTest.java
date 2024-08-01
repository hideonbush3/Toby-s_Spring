package com.hideonbush.vol1.ch2.ch2_4.dao;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.sql.SQLException;

import javax.sql.DataSource;

import org.junit.Before;
import org.junit.Test;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.datasource.SingleConnectionDataSource;

import com.hideonbush.vol1.ch2.ch2_4.domain.User;

public class UserDaoTest {

    private UserDao userDao;

    private User user1;
    private User user2;
    private User user3;

    @Before
    public void setup() {
        user1 = new User("jisung", "박지성", "jisungman");
        user2 = new User("ddangchil", "김땡칠", "ddangchilman");
        user3 = new User("victor", "빅터 차", "victorman");

        // 수동으로 DI 한다
        userDao = new UserDao();
        DataSource dataSource = new SingleConnectionDataSource(
                "jdbc:mysql://localhost/testdb",
                "root",
                "root",
                true);

        userDao.setDataSource(dataSource);
    }

    @Test
    public void addAndGet() throws SQLException {
        userDao.deleteAll();
        assertThat(userDao.getCount(), is(0));

        userDao.add(user1);
        userDao.add(user2);
        assertThat(userDao.getCount(), is(2));

        User savedUser1 = userDao.get(user1.getId());
        assertThat(savedUser1.getName(), is(user1.getName()));
        assertThat(savedUser1.getPassword(), is(user1.getPassword()));

        User savedUser2 = userDao.get(user2.getId());
        assertThat(savedUser2.getName(), is(user2.getName()));
        assertThat(savedUser2.getPassword(), is(user2.getPassword()));
    }

    @Test
    public void count() throws SQLException {
        userDao.deleteAll();
        assertThat(userDao.getCount(), is(0));

        userDao.add(user1);
        assertThat(userDao.getCount(), is(1));
        userDao.add(user2);
        assertThat(userDao.getCount(), is(2));
        userDao.add(user3);
        assertThat(userDao.getCount(), is(3));
    }

    @Test(expected = EmptyResultDataAccessException.class)
    public void getUserFailure() throws SQLException {
        userDao.deleteAll();
        assertThat(userDao.getCount(), is(0));

        userDao.get("abc");
    }
}