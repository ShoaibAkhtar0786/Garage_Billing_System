package config;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DbConfig {
    private static final String Url="jdbc:mysql://localhost:3306/garage";
    private static final String User="root";
    private static final String Pass="0786Shoaib@";

    public static Connection getConnection() throws SQLException{
        return DriverManager.getConnection(Url,User,Pass);
    }
}
