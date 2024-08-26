package com.hideonbush.vol1.ch5.ch5_5.dao;

import java.util.List;

import com.hideonbush.vol1.ch5.ch5_5.domain.User;

public interface UserDao {
    void add(User user);

    User get(String id);

    List<User> getAll();

    void deleteAll();

    int getCount();

    void update(User user);
}