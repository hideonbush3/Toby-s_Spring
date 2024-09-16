package com.hideonbush.vol1.learningtest.jdk.proxy;

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

    public class HelloUppercase implements Hello {
        Hello hello;

        public HelloUppercase(Hello hello) {
            this.hello = hello;
        }

        public String sayHello(String name) {
            return hello.sayHello(name).toUpperCase();
        }

        public String sayHi(String name) {
            return hello.sayHi(name).toUpperCase();
        }

        public String sayThankYou(String name) {
            return hello.sayThankYou(name).toUpperCase();
        }

    }

    static interface Hello {
        String sayHello(String name);

        String sayHi(String name);

        String sayThankYou(String name);
    }

    static class HelloTarget implements Hello {
        public String sayHello(String name) {
            return "Hello " + name;
        }

        public String sayHi(String name) {
            return "Hi " + name;
        }

        public String sayThankYou(String name) {
            return "Thank You " + name;
        }
    }
}
