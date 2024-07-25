package com.hideonbush.vol1.ch2.ch2_4.domain;

public class User {
    String id;
    String name;
    String password;

    // 기본 생성자 없을때 기본 생성자가 호출되면
    // 조상의 기본 생성자가 호출된다 - 주의
    public User() {
    }

    public User(String id, String name, String password) {
        this.id = id;
        this.name = name;
        this.password = password;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
