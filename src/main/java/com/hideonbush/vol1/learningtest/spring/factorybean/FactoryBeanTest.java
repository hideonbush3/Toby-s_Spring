package com.hideonbush.vol1.learningtest.spring.factorybean;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration // 빈 설정 파일을 등록하지 않으면 "클래스명" + "-context.xml" 이 기본으로 적용된다
public class FactoryBeanTest {
    @Autowired
    ApplicationContext context;

    @Test
    public void getMessageFromFactoryBean() {
        Object message = context.getBean("message");
        assertThat(message, instanceOf(Message.class));
        assertThat(((Message) message).getText(), is("Factory Bean"));
    }

    // 드물지만 팩토리 빈을 가져오고 싶은 경우도 있다
    // 빈 이름 앞에 "&"을 붙이면 팩토리 빈 자체를 가져올 수 있다
    @Test
    public void getFactoryBean() {
        Object factory = context.getBean("&message");
        assertThat(factory, instanceOf(MessageFactoryBean.class));
    }
}
