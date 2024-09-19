package com.hideonbush.vol1.learningtest.spring.factorybean;

// Message는 생성자의 접근제어자가 private이라 객체를 생성하려면 스태틱 팩토리 메서드를 사용해야 한다
// 사실은 Message를 빈으로 등록해도 스프링은 객체를 생성해준다
// 리플렉션은 접근 규약을 무시하는 강력한 기능이 있기 때문이다

// 하지만 일반적으로 생성자를 private으로 선언했다는 말은 중요한 이유가 있기 때문이므로
// 이를 무시하고 private 생성자를 가진 클래스를 빈으로 등록하는것은 위험하고 권장되지 않는다
public class Message {
    String text;

    // 접근 제어자가 private이라 외부에서 생성자를 호출할 수 없다
    private Message(String text) {
        this.text = text;
    }

    public String getText() {
        return this.text;
    }

    // 생성자 호출 대신 스태틱 팩토리 메서드를 호출해서 객체를 생성
    public static Message newMessage(String text) {
        return new Message(text);
    }
}
