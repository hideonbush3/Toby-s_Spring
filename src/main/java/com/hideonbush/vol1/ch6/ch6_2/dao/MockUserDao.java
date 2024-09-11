package com.hideonbush.vol1.ch6.ch6_2.dao;

import java.util.ArrayList;
import java.util.List;

import com.hideonbush.vol1.ch6.ch6_2.domain.User;

// 테스트에서 사용될 외부환경에 고립된 UserDao의 테스트 대역
public class MockUserDao implements UserDao {
    private List<User> users; // 전체 유저 목록
    private List<User> updated = new ArrayList<>(); // 업그레이드 대상

    // 생성자의 파라미터로 전체 유저 목록을 받음
    public MockUserDao(List<User> users) {
        this.users = users;
    }

    public List<User> getUpdated() {
        return this.updated;
    }

    // 스텁 기능
    @Override
    public List<User> getAll() {
        return this.users;
    }

    // 목 오브젝트 기능
    // 검증을 위해서 업그레이드 대상을 저장해둔다
    @Override
    public void update(User user) {
        updated.add(user);
    }

    // 아래는 테스트에 사용되지 않는 메서드들
    @Override
    public void add(User user) {
    }

    @Override
    public User get(String id) {
        throw new UnsupportedOperationException("Unimplemented method 'get'");
    }

    @Override
    public void deleteAll() {
        throw new UnsupportedOperationException("Unimplemented method 'deleteAll'");
    }

    @Override
    public int getCount() {
        throw new UnsupportedOperationException("Unimplemented method 'getCount'");
    }
}
