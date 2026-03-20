package com.srh.diet_tracker;

import java.sql.DriverManager;
import java.sql.SQLException;

public class UserDAO {

    private User user;

    public UserDAO(User user){
        this.user = user;
    }

    public UserDAO(){}

    public void insertUserData() {
        String url = "jdbc:sqlite:diet.db";

        String sql = "INSERT INTO user(age,height,weight,isMale,hasDiabetes) VALUES(?,?,?,?,?)";

        try ( var conn = DriverManager.getConnection(url);
              var pstmt = conn.prepareStatement(sql)){
            pstmt.setInt(1,user.getAge());
            pstmt.setInt(2,user.getHeight());
            pstmt.setInt(3,user.getWeight());
            pstmt.setBoolean(4,user.isMale());
            pstmt.setBoolean(5,user.hasDiabetes);
            pstmt.executeUpdate();

        }catch (SQLException e){
            System.err.println(e.getMessage());
        }
    }

    void setUser (User user){
        this.user = user;
    }
}
