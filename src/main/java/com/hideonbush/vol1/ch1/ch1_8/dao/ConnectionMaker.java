package com.hideonbush.vol1.ch1.ch1_8.dao;

import java.sql.Connection;
import java.sql.SQLException;

public interface ConnectionMaker {
    public Connection newConnection() throws ClassNotFoundException, SQLException;
}