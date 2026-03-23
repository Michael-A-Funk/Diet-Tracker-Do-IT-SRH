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
        if (id!=0) {
            String url = "jdbc:sqlite:diet.db";
            String sql = "UPDATE entry SET isSport = ? , "
                    + " calories = ? , "
                    + " sugar = ? "
                    + "WHERE id = ?";

            try (var conn = DriverManager.getConnection(url);
                 var pstmt = conn.prepareStatement(sql)) {
                pstmt.setBoolean(1, entry.isSport());
                pstmt.setDouble(2, entry.getCalories());
                pstmt.setDouble(3, entry.getSugar());
                pstmt.setInt(4, id);
                pstmt.executeUpdate();

            } catch (SQLException e) {
                System.err.println(e.getMessage());
            }

        }
    }

    // id wird vom entsprechenden Controller übergeben (last id für ControllerUser, beliebige id für ControllerDayReview)
    public void deleteEntry (int id){
        if (id!=0) {
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

    public int getLastId(){
        String url = "jdbc:sqlite:diet.db";
        String sql = "SELECT MAX(id) AS max_id FROM entry;";

        try (var conn = DriverManager.getConnection(url);
             var stmt = conn.createStatement();
             var lastId= stmt.executeQuery(sql)) {
            if (lastId.wasNull()) {
                return lastId.getInt("max_id");
            }
            else {return 0;}

        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return 0;
    }

    // Querry for DayReview
    /*SELECT date, SUM(calories), SUM(sugar)
      FROM entry
      GROUP BY date;*/

}
