package com.hideonbush.vol1.ch6.ch6_2.service;

import com.hideonbush.vol1.ch6.ch6_2.domain.User;

public interface UserService {
    void add(User user);

    void upgradeLevels();
}