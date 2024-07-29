package com.hideonbush.vol1.ch2.ch2_4.dao;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.sql.SQLException;

import javax.sql.DataSource;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.datasource.SingleConnectionDataSource;
import org.springframework.test.annotation.DirtiesContext;
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
@DirtiesContext
public class UserDaoTest {
    // @Autowired
    // 클래스를 인스턴스 변수로 두고 Autowired 어노테이션을 설정하면
    // 애플리케이션 컨텍스트는 컨텍스트 내에서 타입과 일치하는 빈 객체를 주입해준다

    // 만약,
    // 타입과 일치하는 빈이 여러개일 경우
    // 변수명과 빈 이름(id)이 같은 빈 객체를 주입해준다
    // 변수명으로도 빈을 찾을 수 없는 경우 예외가 발생함

    // UserDao도 빈으로 등록해뒀기 때문에 DL(getBean() 메서드 호출 방식)
    // 방식으로 하지않고 바로 주입받을 수 있음
    @Autowired
    private UserDao userDao;

    private User user1;
    private User user2;
    private User user3;

    @Before
    public void setup() {
        // 스프링의 JUnit 확장기능은
        // 애플리케이션 컨텍스트를 테스트 실행전에 미리 만들어두고
        // 테스트 객체가 생성될때마다 context 필드에 주입해준다

        // 스프링 테스트 컨텍스트 프레임워크 덕분에
        // 한번 생성해둔 애플리케이션 컨텍스트를
        // 매 테스트마다 재사용할 수 있게돼서 테스트 속도가 빨라짐
        user1 = new User("jisung", "박지성", "jisungman");
        user2 = new User("ddangchil", "김땡칠", "ddangchilman");
        user3 = new User("victor", "빅터 차", "victorman");

        DataSource dataSource = new SingleConnectionDataSource(
                "jdbc:mysql://localhost/testdb", "root", "root", true);
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