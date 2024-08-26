package com.hideonbush.vol1.ch5.ch5_3.service;

import java.util.List;

import com.hideonbush.vol1.ch5.ch5_3.dao.UserDao;
import com.hideonbush.vol1.ch5.ch5_3.domain.Level;
import com.hideonbush.vol1.ch5.ch5_3.domain.User;

public class UserService {
    UserDao userDao;

    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }

    public void upgradeLevels() {
        List<User> users = userDao.getAll();
        for (User user : users) {
            Boolean changed = null; // 유저정보 변경 플래그
            if (user.getLevel() == Level.BASIC && user.getLogin() >= 50) {
                user.setLevel(Level.SILVER);
                changed = true;
            } else if (user.getLevel() == Level.SILVER && user.getRecommend() >= 30) {
                user.setLevel(Level.GOLD);
                changed = true;
            } else if (user.getLevel() == Level.GOLD) {
                changed = false;
            } else {
                changed = false;
            }

            // 변경이 일어났을때만 DB의 유저 정보를 업데이트한다
            if (changed == true) {
                userDao.update(user);
            }
        }

    }
}
