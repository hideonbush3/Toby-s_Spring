package com.hideonbush.vol1.ch6.ch6_4.service;

import java.lang.reflect.Proxy;

import org.springframework.beans.factory.FactoryBean;
import org.springframework.transaction.PlatformTransactionManager;

// UserService 외에도 트랜잭션 부가기능이 필요한 오브젝트를 위한
// 프록시를 만들 때 얼마든지 재사용 가능하다 
public class TxProxyFactoryBean implements FactoryBean<Object> {
    // TransactionHandler를 생성할때 필요한 리스트
    Object target;
    PlatformTransactionManager transactionManager;
    String pattern;

    // 다이내믹 프록시를 생성할때 필요
    Class<?> serviceInterface;

    public void setTarget(Object target) {
        this.target = target;
    }

    public void setTransactionManager(PlatformTransactionManager transactionManager) {
        this.transactionManager = transactionManager;
    }

    public void setPattern(String pattern) {
        this.pattern = pattern;
    }

    public void setServiceInterface(Class<?> serviceInterface) {
        this.serviceInterface = serviceInterface;
    }

    // DI 받은 정보를 이용해서 TransactionHandler를 사용하는
    // 다이내믹 프록시를 생성한다
    @Override
    public Object getObject() throws Exception {
        TransactionHandler txHandler = new TransactionHandler();
        txHandler.setTarget(target);
        txHandler.setTransactionManager(transactionManager);
        txHandler.setPattern(pattern);
        return Proxy.newProxyInstance(
                getClass().getClassLoader(),
                new Class[] { serviceInterface },
                txHandler);
    }

    @Override
    public Class<?> getObjectType() {
        return this.serviceInterface;
    }

    @Override
    public boolean isSingleton() {
        return false; // 싱글톤이 아니라는 뜻이 아니라 getObject()는 매번 다른 오브젝트를 리턴한다
    }

}
