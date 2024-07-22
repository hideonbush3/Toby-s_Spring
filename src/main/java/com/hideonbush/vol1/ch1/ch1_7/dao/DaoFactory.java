package com.hideonbush.vol1.ch1.ch1_7.dao;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

// 팩토리 클래스
// 객체의 생성방법을 결정하고 생성한 객체를 반환하는 클래스

// 스프링에게 객체생성, 객체간 관계맺기 제어권을 넘겨주기 위한 설정정보를 담은 클래스
// @Configuration - 애플리케이션 컨텍스트의 설정정보 클래스라는걸 스프링에게 알려주는 표시
// @Bean - 객체 생성을 담당하는 IoC용 메서드라는 것을 스프링에게 알려주는 표시
@Configuration
public class DaoFactory {
    @Bean
    public UserDao userDao() {
        return new UserDao(connectionMaker());
    }

    @Bean
    public ConnectionMaker connectionMaker() {
        return new DConnectionMaker();
    }
}