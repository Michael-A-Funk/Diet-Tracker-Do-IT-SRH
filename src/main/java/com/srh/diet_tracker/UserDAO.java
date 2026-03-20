package com.srh.diet_tracker;

import java.sql.DriverManager;
import java.sql.SQLException;

public class UserDAO {

    public void insertUserData(int age, int weight, boolean isMale, boolean hasDiabetes) {
        String url = "jdbc:sqlite:diet.db";

        String sql = "INSERT INTO user(age,weight,isMale,hasDiabetes) VALUES(?,?,?,?)";

        try ( var conn = DriverManager.getConnection(url);
              var pstmt = conn.prepareStatement(sql)){
            pstmt.setInt(1,age);
            pstmt.setInt(2,weight);
            pstmt.setBoolean(3,isMale);
            pstmt.setBoolean(4,hasDiabetes);
            pstmt.executeUpdate();

        }catch (SQLException e){
            System.err.println(e.getMessage());
        }

    }
}
