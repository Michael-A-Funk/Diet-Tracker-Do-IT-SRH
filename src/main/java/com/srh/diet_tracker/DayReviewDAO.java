package com.srh.diet_tracker;

import java.sql.DriverManager;
import java.sql.SQLException;

public class DayReviewDAO {

    public void insertDayReview(double dailyCaloriesBrutto, double dailyCaloriesPercentage,
                                double dailySugarBrutto, double dailySugarPercentage){
        String url = "jdbc:sqlite:diet.db";

        String sql = "INSERT INTO dayReview(dailyCaloriesBrutto,dailyCaloriesPercentage," +
                     "dailySugarBrutto,dailySugarPercentage) VALUES(?,?,?,?)";

        try (var conn = DriverManager.getConnection(url);
             var pstmt = conn.prepareStatement(sql)){
            pstmt.setDouble(1,dailyCaloriesBrutto);
            pstmt.setDouble(2,dailyCaloriesPercentage);
            pstmt.setDouble(3,dailySugarBrutto);
            pstmt.setDouble(4,dailySugarPercentage);
            pstmt.executeUpdate();

        }catch (SQLException e){
            System.err.println(e.getMessage());
        }

    }

}
