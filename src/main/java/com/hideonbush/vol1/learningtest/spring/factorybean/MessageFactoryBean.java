package com.hideonbush.vol1.learningtest.spring.factorybean;

import org.springframework.beans.factory.FactoryBean;

// Message 오브젝트를 만들어주는 팩토리 빈
public class MessageFactoryBean implements FactoryBean<Message> {
    String text;

    // 필요한 정보를 팩토리 빈의 프로퍼티로 설정해서 DI 받도록
    public void setText(String text) {
        this.text = text;
    }

    @Override
    public Message getObject() throws Exception {
        return Message.newMessage(text);
    }

    @Override
    public Class<? extends Message> getObjectType() {
        return Message.class;
    }

    // 이 팩토리 빈은 매번 요청마다 새로운 오브젝트를 만들어주기 때문에 false를 반환하도록 한다
    // 이것은 팩토리 빈의 동작방식에 관한 설명이며 만들어진 빈 오브젝트는 싱글톤으로 스프링이 관리해줄 수 있다
    @Override
    public boolean isSingleton() {
        return false;
    }
}
