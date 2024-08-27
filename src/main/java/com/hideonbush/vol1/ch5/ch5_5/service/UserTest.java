package com.hideonbush.vol1.ch5.ch5_5.service;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Test;

import com.hideonbush.vol1.ch5.ch5_5.domain.Level;
import com.hideonbush.vol1.ch5.ch5_5.domain.User;

public class UserTest {
    User user;

    @Before
    public void setUp() {
        user = new User();
    }

    @Test
    public void upgradeLevel() {
        Level[] levels = Level.values();
        for (Level level : levels) {
            if (level.nextLevel() == null)
                continue;
            user.setLevel(level);
            user.upgradeLevel();
            assertThat(user.getLevel(), is(level.nextLevel()));
        }
    }

    @Test(expected = IllegalStateException.class)
    public void cannotUpgradeLevel() {
        Level[] levels = Level.values();
        for (Level level : levels) {
            if (level.nextLevel() != null)
                continue;
            user.setLevel(level);
            user.upgradeLevel();
        }
    }

}
