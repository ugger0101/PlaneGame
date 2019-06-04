package com.demo.game;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 */
public class DbUtil extends GamePanel {
    static String className = "com.mysql.jdbc.Driver";
    static String url = "jdbc:mysql://127.0.0.1:3306/ugger?useSSL=true";
    static String user = "root";
    static String password = "123456789";
    static Connection connection = null;
    // 1、获取数据连接

    public static Connection getConnection() {
        try {
            Class.forName(className);
            connection = DriverManager.getConnection(url, user, password);
        } catch (ClassNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return connection;
    }

    // 2、关闭数据库连接

    public static void close(Connection conn, Statement st, ResultSet rs) {
        try {
            if (rs != null) {
                rs.close();
            }
            if (st != null) {
                st.close();
            }
            if (conn != null) {
                conn.close();
            }

        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

}
