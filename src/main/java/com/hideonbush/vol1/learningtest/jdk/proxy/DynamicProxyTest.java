package com.hideonbush.vol1.learningtest.jdk.proxy;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.junit.Test;
import org.springframework.aop.framework.ProxyFactoryBean;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.aop.support.NameMatchMethodPointcut;

public class DynamicProxyTest {
    @Test
    public void simpleProxy() {
        // 타깃 테스트
        Hello hello = new HelloTarget();
        assertThat(hello.sayHello("Minsu"), is("Hello Minsu"));
        assertThat(hello.sayHi("Minsu"), is("Hi Minsu"));
        assertThat(hello.sayThankYou("Minsu"), is("Thank You Minsu"));

        // 다이내믹 프록시 생성
        Hello proxiedHello = (Hello) Proxy.newProxyInstance(
                getClass().getClassLoader(), // 동적 프록시 생성시 필요한 클래스 정보를 로드할 클래스 로더
                new Class[] { Hello.class }, // 동적 프록시가 구현해야할 인터페이스, 여러개가 될 수 있어서 배열
                new UppercaseHandler(new HelloTarget())); // 프록시 객체가 타깃의 메서드를 호출할때 이를 가로챌 InvocationHandler
        // Hello proxiedHello = new HelloUppercase(hello);
        assertThat(proxiedHello.sayHello("Minsu"), is("HELLO MINSU"));
        assertThat(proxiedHello.sayHi("Minsu"), is("HI MINSU"));
        assertThat(proxiedHello.sayThankYou("Minsu"), is("THANK YOU MINSU"));
    }

    @Test
    public void proxyFactoryBean() {
        ProxyFactoryBean pfBean = new ProxyFactoryBean();
        pfBean.setTarget(new HelloTarget()); // 타깃 설정
        pfBean.addAdvice(new UppercaseAdvice());// 부가기능을 담은 어드바이스, 여러개 추가 가능

        Hello proxiedHello = (Hello) pfBean.getObject();

        assertThat(proxiedHello.sayHello("Minsu"), is("HELLO MINSU"));
        assertThat(proxiedHello.sayHi("Minsu"), is("HI MINSU"));
        assertThat(proxiedHello.sayThankYou("Minsu"), is("THANK YOU MINSU"));
    }

    @Test
    public void pointcutAdvisor(){
        ProxyFactoryBean pfBean = new ProxyFactoryBean();
        pfBean.setTarget(new HelloTarget());

        // 메서드 이름을 비교해서 대상을 선정하는 알고리즘을 제공하는 포인트컷
        NameMatchMethodPointcut pointcut = new NameMatchMethodPointcut();
        pointcut.setMappedName("sayH*");

        // 포인트컷과 어드바이스를 Advisor로 묶어서 한번에 추가
        pfBean.addAdvisor(new DefaultPointcutAdvisor(pointcut, new UppercaseAdvice()));

        Hello proxiedHello = (Hello) pfBean.getObject();
        
        assertThat(proxiedHello.sayHello("Minsu"), is("HELLO MINSU"));
        assertThat(proxiedHello.sayHi("Minsu"), is("HI MINSU"));
        assertThat(proxiedHello.sayThankYou("Minsu"), is("Thank You Minsu"));
    }

    static class UppercaseAdvice implements MethodInterceptor {

        @Override
        public Object invoke(MethodInvocation invocation) throws Throwable {
            String ret = (String) invocation.proceed(); // 리플렉션의 Method와 달리 메서드 실행 시 타깃
                                                        // 오브젝트를 전달할 필요가 없다
                                                        // MethodInvocation은 메서드 정보와 타깃
                                                        // 오브젝트를 알고 있기 때문이다
            return ret.toUpperCase();
        }

    }

    static class HelloUppercase implements Hello {
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

    // 다이내믹 프록시가 타깃의 메서드를 호출할때 이를 가로챌 InvocationHandler
    // 타깃의 반환값을 대문자로 바꾸는 부가기능과 요청 위임 코드를 담고있다
    static class UppercaseHandler implements InvocationHandler {
        Object target;

        private UppercaseHandler(Object target) {
            this.target = target;
        }

        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            Object ret = method.invoke(target, args);
            // 타깃의 반환타입, 호출하는 메서드명이 특정할때만 부가기능을 제공하도록 제한할 수 있다
            if (ret instanceof String && method.getName().startsWith("say")) {
                return ((String) ret).toUpperCase();
            } else {
                return ret;
            }
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
