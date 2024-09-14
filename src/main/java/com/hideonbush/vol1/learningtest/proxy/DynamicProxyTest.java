package com.hideonbush.vol1.learningtest.proxy;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;

public class DynamicProxyTest {
    @Test
    public void simpleProxy() {
        // 타깃 테스트
        Hello hello = new HelloTarget();
        assertThat(hello.sayHello("Minsu"), is("Hello Minsu"));
        assertThat(hello.sayHi("Minsu"), is("Hi Minsu"));
        assertThat(hello.sayThankYou("Minsu"), is("Thank You Minsu"));

        // 프록시 테스트
        Hello proxiedHello = new HelloUppercase(hello);
        assertThat(proxiedHello.sayHello("Minsu"), is("HELLO MINSU"));
        assertThat(proxiedHello.sayHi("Minsu"), is("HI MINSU"));
        assertThat(proxiedHello.sayThankYou("Minsu"), is("THANK YOU MINSU"));

    }
}
