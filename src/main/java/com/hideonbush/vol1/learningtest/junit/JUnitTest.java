package com.hideonbush.vol1.learningtest.junit;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.sameInstance;
import static org.junit.Assert.assertThat;

import org.junit.Test;

public class JUnitTest {
    static JUnitTest testObject;

    // is()는 equals() 비교를 해서 같으면 성공, 다르면 실패
    // not()은 결과를 부정하는 매처
    // 즉, is(not())은 같으면 실패, 다르면 성공

    // JUnitTest는 equals()를 오버라이드 하지 않았기 때문에
    // 기본적으로 참조주소를 비교

    // sameInstance()는 실제로 같은 객체인지 비교
    // 객체참조를 비교하는 테스트의 의도를 명확히 드러내기위해
    // sameInstance()라는 동일성 비교 매처를 명시적으로 사용
    @Test
    public void test1() {
        assertThat(this, is(not(sameInstance(testObject))));
        // assertThat(this, not(is(testObject)));
        // assertThat(this, not(sameInstance(testObject)));
        testObject = this;
    }

    @Test
    public void test2() {
        assertThat(this, is(not(sameInstance(testObject))));
        testObject = this;
    }

    @Test
    public void test3() {
        assertThat(this, is(not(sameInstance(testObject))));
        testObject = this;
    }
}
