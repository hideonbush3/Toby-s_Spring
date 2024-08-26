package com.hideonbush.vol1.ch5.ch5_3.service;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "/com/hideonbush/vol1/ch5/test-applicationContext.xml")
public class UserServiceTest {
    @Autowired
    UserService userService;

    @Test
    public void bean() {
        assertThat(userService, is(notNullValue()));
    }
}
