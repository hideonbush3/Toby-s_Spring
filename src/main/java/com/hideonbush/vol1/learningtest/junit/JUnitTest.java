package com.hideonbush.vol1.learningtest.junit;

import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.junit.matchers.JUnitMatchers.either;

import java.util.HashSet;
import java.util.Set;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "/com/hideonbush/vol1/learningtest/junit/junit.xml")
public class JUnitTest {
    // JUnit은 기본적으로 매 테스트 메서드마다 새로운 테스트 객체를 만들어서 테스트를 한다
    // 매 테스트마다 새로운 객체들이 생성되는것이 맞는지 확인해보기위한 학습테스트
    static Set<JUnitTest> testObjects = new HashSet<>();

    // JUnit과 다르게 스프링 테스트 컨텍스트 프레임워크는
    // 테스트용 애플리케이션 컨텍스트를 한개만 만들고
    // 그 컨텍스트 객체를 여러 테스트에서 공유하도록 한다
    @Autowired
    ApplicationContext context;
    static ApplicationContext contextObject = null;

    @Test
    public void test1() {
        assertThat(testObjects, not(hasItem(this)));
        testObjects.add(this);

        // 첫번째 테스트이거나
        // 컨테이너로부터 주입받아서 저장해둔 컨텍스트와 현재 컨텍스트 객체가 일치하면 true
        // 그 결과를 is() 매처를 사용해 true와 비교
        // is()는 타입만 일치하면 어떤 값이든 검증 가능
        assertThat(contextObject == null || contextObject == this.context, is(true));
        contextObject = this.context;
    }

    @Test
    public void test2() {
        assertThat(testObjects, not(hasItem(this)));
        testObjects.add(this);

        // 조건문을 받아서 true면 통과시키는 검증용 메서드 assertTrue()
        assertTrue(contextObject == null || contextObject == this.context);
        contextObject = this.context;
    }

    @Test
    public void test3() {
        assertThat(testObjects, not(hasItem(this)));
        testObjects.add(this);

        // 매처의 조합을 이용한 검증
        // either()는 뒤에 이어서 나오는 or() 매처와 함께
        // 두 매처의 결과를 OR 조건으로 비교
        assertThat(contextObject,
                either(is(nullValue(ApplicationContext.class))).or(is(this.context)));
        contextObject = this.context;
    }
}
