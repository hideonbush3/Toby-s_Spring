package com.hideonbush.vol1.ch2.ch2_4.dao;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.sql.SQLException;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.hideonbush.vol1.ch2.ch2_4.domain.User;

// 스프링 테스트 컨텍스트 프레임워크
// @RunWith - JUnit 프레임워크의 테스트 실행 방법을 확잘할때 사용
// SpringJUnit4ClassRunner이라는 JUnit용 테스트 컨텍스트 프레임워크 확장 클래스
// 이로 인해 JUnit이 테스트를 진행하는 중에 사용할 애플리케이션 컨텍스트를 생성, 관리하는 작업을 진행해준다

// @ContextConfiguration - 만들 애플리케이션 컨텍스트의 설정파일 경로
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "/com/hideonbush/vol1/ch2/ch2_4/applicationContext.xml")
public class UserDaoTest {
    @Autowired
    private ApplicationContext context;

    private UserDao userDao;
    private User user1;
    private User user2;
    private User user3;

    @Before
    public void setup() {
        // 스프링의 JUnit 확장기능은
        // 애플리케이션 컨텍스트를 테스트 실행전에 미리 만들어두고
        // 테스트 객체가 생성될때마다 context 필드에 주입해준다
        // 출력해보면 테스트 객체는 매 생성마다 주소값이 다른반면
        // 컨텍스트는 한번 만들어진걸 계속 주입받아서 사용한다

        // 스프링 테스트 컨텍스트 프레임워크 덕분에
        // 한번 생성해둔 애플리케이션 컨텍스트를
        // 매 테스트마다 재사용할 수 있게돼서 테스트 속도가 빨라짐
        System.out.println();
        System.out.println("애플리케이션 컨텍스트 -> " + this.context);
        System.out.println();
        System.out.println("UserDaoTest 객체 -> " + this);
        System.out.println();
        this.userDao = context.getBean("userDao", UserDao.class);

        user1 = new User("jisung", "박지성", "jisungman");
        user2 = new User("ddangchil", "김땡칠", "ddangchilman");
        user3 = new User("victor", "빅터 차", "victorman");
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