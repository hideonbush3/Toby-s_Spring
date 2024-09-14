package com.hideonbush.vol1.learningtest.proxy;

// 프록시 클래스
public class HelloUppercase implements Hello {
    // 요청을 위임할 타깃 오브젝트
    // 다른 프록시를 추가할 수도 있으므로 인터페이스로 접근한다
    Hello hello;

    public HelloUppercase(Hello hello) {
        this.hello = hello;
    }

    @Override
    public String sayHello(String name) {
        return hello.sayHello(name).toUpperCase();
    }

    @Override
    public String sayHi(String name) {
        return hello.sayHi(name).toUpperCase();
    }

    @Override
    public String sayThankYou(String name) {
        return hello.sayThankYou(name).toUpperCase();
    }

}
