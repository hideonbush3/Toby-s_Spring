package com.hideonbush.vol1.ch3.ch3_2.dao;

import java.sql.Connection;
import java.sql.SQLException;

public interface ConnectionMaker {
    public Connection newConnection() throws ClassNotFoundException, SQLException;
}