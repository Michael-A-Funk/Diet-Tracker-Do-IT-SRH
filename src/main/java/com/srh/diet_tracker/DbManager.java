package com.srh.diet_tracker;

// import the classes from java.sql package
import java.sql.DriverManager;
import java.sql.SQLException;

public class DbManager {

    public void setDataBase(){
        // SQLite connection string
        var url = "jdbc:sqlite:diet.db";

        // SQL statement for creating a new table
        var userSql = "CREATE TABLE IF NOT EXISTS user("
                + "	id INTEGER PRIMARY KEY,"
                + "	age INTEGER NOT NULL,"
                + " weight INTEGER NOT NULL,"
                + "	isMale BOOLEAN NOT NULL,"
                + " hasDiabetes BOOLEAN NOT NULL"
                + ");";

        var entrySql = "CREATE TABLE IF NOT EXISTS entry("
                + "	id INTEGER PRIMARY KEY,"
                + "	isSport BOOLEAN NOT NULL,"
                + " calories REAL NOT NULL,"
                + "	sugar REAL NOT NULL"
                + ");";

        var dayReviewSql = "CREATE TABLE IF NOT EXISTS dayReview("
                + "	id INTEGER PRIMARY KEY,"
                + "	dailyCaloriesBrutto REAL NOT NULL,"
                + "	dailyCaloriesPercentage REAL NOT NULL,"
                + "	dailySugarBrutto REAL NOT NULL,"
                + "	dailySugarPercentage REAL NOT NULL"
                + ");";



        try (var conn = DriverManager.getConnection(url);
             var stmt = conn.createStatement()) {
            // create a new table
            stmt.execute(userSql);
            stmt.execute(entrySql);
            stmt.execute(dayReviewSql);
            System.out.println("Databases 'user', 'entry' and 'dayReview' were created.");

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }


}
