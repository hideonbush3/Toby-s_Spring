package com.hideonbush.vol1.ch1.ch1_7.dao;

import java.sql.SQLException;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.hideonbush.vol1.ch1.ch1_7.domain.User;

public class UserDaoTest {
    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        // DaoFactory를 설정정보로 사용하는
        // ApplicationContext타입의 객체인 애플리케이션 컨텍스트
        // ApplicationContext을 구현한 클래스는 여러가지가 있다
        // 그 중, @Configuration이 붙은 자바 코드를 설정정보로 사용하려면
        // AnnotationConfigApplicationContext를 사용하면 된다
        // 생성자의 매개변수로는 @Configuration을 붙인 클래스를 넣는다
        ApplicationContext context = new AnnotationConfigApplicationContext(DaoFactory.class);

        // getBean()은 애플리케이션 컨텍스트가 관리하는 객체를 요청하는 메서드
        // 첫번째 매개변수는 @Bean으로 등록해둔 객체 반환메서드의 이름
        // getBean은 기본적으로 Object 타입의 객체를 반환하기 때문에
        // 형변환을 해줘야하는 불편함이 있었지만
        // 자바 5부터 제네릭 메서드 방식을 사용해
        // 두번째 매개변수로 반환타입을 지정할 수 있다
        UserDao userDao = context.getBean("userDao", UserDao.class);
        UserDao userDao2 = context.getBean("userDao", UserDao.class);

        User user = userDao.get("abc123");
        System.out.println(user.getName());
        System.out.println(userDao == userDao2);
    }
}