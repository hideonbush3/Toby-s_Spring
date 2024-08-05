package com.hideonbush.vol1.ch3.ch3_4.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public interface StatementStrategy {
    public PreparedStatement makePreparedStatement(Connection c) throws SQLException;
}
