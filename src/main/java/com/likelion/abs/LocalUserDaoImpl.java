package com.likelion.abs;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Map;

public class LocalUserDaoImpl extends UserDaoAbstract {

    @Override
    public Connection makeConnection() throws SQLException, ClassNotFoundException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        Map<String, String> env = System.getenv();

        Connection c = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/likelion-db","root","12345678");
        return c;
    }
}
