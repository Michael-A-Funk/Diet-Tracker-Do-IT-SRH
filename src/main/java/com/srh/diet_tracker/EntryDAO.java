package com.srh.diet_tracker;

import javax.xml.transform.Result;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

public class EntryDAO {

    private Entry entry;

    // FRAGE bzg. Controller: soll man sie separat halten wie unten, oder Methode zum setzten des Attributes entry?
    //Constructor used when we want to insert new Data into DB
    public EntryDAO(Entry entry){
        this.entry = entry;
    }

    //Constructor user when we want to use methods update, delete and getLastId
    public EntryDAO(){}

    public void insertEntryData() {
        String url = "jdbc:sqlite:diet.db";

        //For a first entry, the date and time should be automatically set to the current time.
        // This SQL command is one possibility, but not necessarily the final solution.
        //Later, functionality may be added to change the time.
        // Later, a controller should be used when instantiating the Entry class.

        String sql = "INSERT INTO entry(isSport,calories,sugar,date,time) VALUES(?,?,?,?,?)";

        try (var conn = DriverManager.getConnection(url);
             var pstmt = conn.prepareStatement(sql)){
            pstmt.setBoolean(1,entry.isSport());
            pstmt.setDouble(2,entry.getCalories());
            pstmt.setDouble(3,entry.getSugar());
            pstmt.setString(4,(entry.getDay()).toString());
            pstmt.setString(5,(entry.getTime()).toString());

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

    // The ID is passed by the corresponding controller (last ID for Controller User, any ID for ControllerDayReview)
    public void deleteEntry (){
            String url = "jdbc:sqlite:diet.db";
            String sql = "DELETE FROM entry WHERE id = ?";

            try (var conn = DriverManager.getConnection(url);
                 var pstmt = conn.prepareStatement(sql)) {
                pstmt.setInt(1, getLastId());
                pstmt.executeUpdate();

            } catch (SQLException e) {
                System.err.println(e.getMessage());
            }
    }

    public int getLastId(){
        String url = "jdbc:sqlite:diet.db";

        try (var conn = DriverManager.getConnection(url);
             var stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT MAX(id) AS max_id FROM entry;")) {
//                if (rs.next()) {
                    System.out.println("Letzte id von entry ist: " + rs.getInt("max_id") + " und Eintrag wird gelöscht.");
                    return rs.getInt("max_id");

//                } else {
//                    System.out.println("Letzte id von entry ist: und Eintrag wird NICHT gelöscht.");
//                    return 0;
//                }
            } catch (SQLException e) {

                System.err.println(e.getMessage());
            }
        System.out.println("Keine id selektiert!!!");
        return 0;
    }

    // Querry for DayReview
    /*SELECT date, SUM(calories), SUM(sugar)
      FROM entry
      GROUP BY date;*/

}
