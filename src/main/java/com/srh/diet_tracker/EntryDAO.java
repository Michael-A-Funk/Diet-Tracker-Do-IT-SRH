package com.srh.diet_tracker;

import java.sql.DriverManager;
import java.sql.SQLException;

public class EntryDAO {

    private Entry entry;

    public EntryDAO(Entry entry){
        this.entry = entry;
    }

    public void insertEntryData(boolean isSport, double calories, double sugar) {
        String url = "jdbc:sqlite:diet.db";

        String sql = "INSERT INTO entry(isSport,calories,sugar) VALUES(?,?,?)";



        try (var conn = DriverManager.getConnection(url);
             var pstmt = conn.prepareStatement(sql)){
            pstmt.setBoolean(1,isSport);
            pstmt.setDouble(2,calories);
            pstmt.setDouble(3,sugar);
            pstmt.executeUpdate();

        }catch (SQLException e){
            System.err.println(e.getMessage());
        }

    }


}
