package com.hideonbush.vol1.ch4.ch4_1.dao;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.hideonbush.vol1.ch4.ch4_1.domain.User;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "/com/hideonbush/vol1/ch4/test-applicationContext.xml")
public class UserDaoTest {
    @Autowired
    private UserDao userDao;

    private User user1;
    private User user2;
    private User user3;

    @Before
    public void setup() {
        user1 = new User("jisung", "박지성", "jisungman");
        user2 = new User("ddangchil", "김땡칠", "ddangchilman");
        user3 = new User("victor", "빅터 차", "victorman");
    }

    @Test
    public void addAndGet() {
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
    public void count() {
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
    public void getUserFailure() {
        userDao.deleteAll();
        assertThat(userDao.getCount(), is(0));

        userDao.get("abc");
    }

    @Test
    public void getAll() {
        userDao.deleteAll();

        // userDao.getAll()에 대한 테스트 코드인 동시에
        // 기능을 설명해주는 코드
        // 여기서 기능이란, 아무런 데이터가 없을때 userDao.getAll()를
        // 호출하면 크기가 0인 List<T>를 반환한다는 것을 뜻한다
        List<User> users0 = userDao.getAll();
        assertThat(users0.size(), is(0));

        userDao.add(user1);
        List<User> users1 = userDao.getAll();
        assertThat(users1.size(), is(1));
        checkSameUser(user1, users1.get(0));

        userDao.add(user2);
        List<User> users2 = userDao.getAll();
        assertThat(users2.size(), is(2));
        checkSameUser(user2, users2.get(0));
        checkSameUser(user1, users2.get(1));

        userDao.add(user3);
        List<User> users3 = userDao.getAll();
        assertThat(users3.size(), is(3));
        checkSameUser(user2, users3.get(0));
        checkSameUser(user1, users3.get(1));
        checkSameUser(user3, users3.get(2));
    }

    private void checkSameUser(User user1, User user2) {
        assertThat(user1.getId(), is(user2.getId()));
        assertThat(user1.getName(), is(user2.getName()));
        assertThat(user1.getPassword(), is(user2.getPassword()));
    }
}