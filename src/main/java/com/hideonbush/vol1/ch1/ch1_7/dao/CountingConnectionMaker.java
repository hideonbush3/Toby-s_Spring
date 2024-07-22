package com.hideonbush.vol1.ch1.ch1_7.dao;

import java.sql.Connection;
import java.sql.SQLException;

public class CountingConnectionMaker implements ConnectionMaker {
    private int counter = 0;

    private ConnectionMaker realConnectionMaker;

    public CountingConnectionMaker(ConnectionMaker realConnectionMaker) {
        this.realConnectionMaker = realConnectionMaker;
    }

    @Override
    public Connection newConnection() throws ClassNotFoundException, SQLException {
        this.counter++;
        return realConnectionMaker.newConnection();
    }

    public int getCounter() {
        return counter;
    }

}
