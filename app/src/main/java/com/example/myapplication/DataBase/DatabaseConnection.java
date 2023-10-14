package com.example.myapplication.DataBase;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseConnection {

    private static final String URL = "jdbc:sqlserver://47.102.213.36;databaseName=QiPing;encrypt=true;trustServerCertificate=true";
    private static final String USERNAME = "sa";
    private static final String PASSWORD = "JIAheng2021";

    public static Connection getConnection() throws SQLException, ClassNotFoundException {
        // 加载驱动程序
        Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");

        // 连接到数据库
        Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
        return DriverManager.getConnection(URL, USERNAME, PASSWORD);
    }
}
