package com.demo.game;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public class CRUD {
    public static void update1(int b) {
        Connection connection=DbUtil.getConnection();
        String sql="update hihi set score="+b+" where name='1'";
        try {
            Statement st=connection.createStatement();
            st.execute(sql);
            DbUtil.close(connection, st, null);
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    public static int select() {
        Connection connection=DbUtil.getConnection();
        String sql="select * from hihi ";
        int score=0;
        try {
            Statement st=connection.createStatement();
            ResultSet rs=st.executeQuery(sql);
            while(rs.next()) {
                score=rs.getInt("score");
            }
            DbUtil.close(connection, st, null);
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return score;
    }
}
