package com.hideonbush.vol1.learningtest.junit;

import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.assertThat;

import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

// JUnit은 기본적으로 매 테스트 메서드마다 새로운 테스트 객체를 만들어서 테스트를 한다
// 매 테스트마다 새로운 객체들이 생성되는것이 맞는지 확인해보기위한 학습테스트
public class JUnitTest {
    static Set<JUnitTest> testObjects = new HashSet<>();

    @Test
    public void test1() {
        assertThat(testObjects, not(hasItem(this)));
        testObjects.add(this);
    }

    @Test
    public void test2() {
        assertThat(testObjects, not(hasItem(this)));
        testObjects.add(this);
    }

    @Test
    public void test3() {
        assertThat(testObjects, not(hasItem(this)));
        testObjects.add(this);
    }
}
