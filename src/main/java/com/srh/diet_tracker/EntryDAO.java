package com.srh.diet_tracker;

import java.sql.DriverManager;
import java.sql.SQLException;

public class EntryDAO {

    private Entry entry;

    public EntryDAO(Entry entry){
        this.entry = entry;
    }

    public void insertEntryData() {
        String url = "jdbc:sqlite:diet.db";

        String sql = "INSERT INTO entry(isSport,calories,sugar) VALUES(?,?,?)";

        try (var conn = DriverManager.getConnection(url);
             var pstmt = conn.prepareStatement(sql)){
            pstmt.setBoolean(1,entry.isSport());
            pstmt.setDouble(2,entry.getCalories());
            pstmt.setDouble(3,entry.getSugar());
            pstmt.executeUpdate();

        }catch (SQLException e){
            System.err.println(e.getMessage());
        }
    }

    // id wird vom entsprechenden Controller übergeben (last id für ControllerEntry, beliebige id für ControllerDayReview)
    public void updateEntryData(int id){
        String url = "jdbc:sqlite:diet.db";
        String sql = "UPDATE entry SET isSport = ? , "
                + " calories = ? , "
                + " sugar = ? "
                + "WHERE id = ?";

        try (var conn = DriverManager.getConnection(url);
             var pstmt = conn.prepareStatement(sql)){
            pstmt.setBoolean(1,entry.isSport());
            pstmt.setDouble(2,entry.getCalories());
            pstmt.setDouble(3,entry.getSugar());
            pstmt.setInt(4,id);
            pstmt.executeUpdate();

        }catch (SQLException e){
            System.err.println(e.getMessage());
        }
    }

    // id wird vom entsprechenden Controller übergeben (last id für ControllerUser, beliebige id für ControllerDayReview)
    public void deleteEntry (int id){
        String url = "jdbc:sqlite:diet.db";
        String sql = "DELETE FROM entry WHERE id = ?";

        try (var conn = DriverManager.getConnection(url);
             var pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            pstmt.executeUpdate();

        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }






}
