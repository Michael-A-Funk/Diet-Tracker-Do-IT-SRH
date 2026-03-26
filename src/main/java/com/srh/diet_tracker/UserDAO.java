package com.srh.diet_tracker;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDAO {

    private User user;


    // Frage : bzg. Controller: soll man sie separat halten wie unten, oder Methode zum setzten des Attributes user?
    //Constructor used when we want to insert new Data into DB
    public UserDAO(User user){
        this.user = user;
    }

    //Constructor user when we want to use methods update, delete and getLastId
    public UserDAO(){}

    public void insertUserData() {
        String url = "jdbc:sqlite:diet.db";

        String sql = "INSERT INTO user(height,age,weight,isMale,hasDiabetes) VALUES(?,?,?,?,?)";

        try ( var conn = DriverManager.getConnection(url);
              var pstmt = conn.prepareStatement(sql)){
            pstmt.setInt(1,user.getHeight());
            pstmt.setInt(2,user.getAge());
            pstmt.setInt(3,user.getWeight());
            pstmt.setBoolean(4,user.isMale());
            pstmt.setBoolean(5,user.hasDiabetes);
            pstmt.executeUpdate();

        }catch (SQLException e){
            System.err.println(e.getMessage());
        }
    }

    public void updateUserData() {

            String url = "jdbc:sqlite:diet.db";
            String sql = "UPDATE user SET height = ? ,"
                    + " age = ? , "
                    + " weight = ?, "
                    + " isMale = ? , "
                    + " hasDiabetes = ? "
                    + "WHERE id = 1";

            try (var conn = DriverManager.getConnection(url);
                 var pstmt = conn.prepareStatement(sql)) {
                pstmt.setInt(1, user.getHeight());
                pstmt.setInt(2, user.getAge());
                pstmt.setInt(3, user.getWeight());
                pstmt.setBoolean(4, user.isMale());
                pstmt.setBoolean(5, user.hasDiabetes());
                // id is always 1 because we have just one user!
                //pstmt.setInt(6, 1);
                pstmt.executeUpdate();
                System.err.println("SQL error");

            } catch (SQLException e) {

                System.err.println(e.getMessage());
            }

    }

    // Frage : Macht es Sinn Daten zu löschen oder lasst man User einfach Daten Bearbeiten?
    public void deleteEntryData (){

            String url = "jdbc:sqlite:diet.db";
            String sql = "DELETE FROM user WHERE id = 1";

            try (var conn = DriverManager.getConnection(url);
                 var pstmt = conn.prepareStatement(sql)) {
                pstmt.executeUpdate();
                System.out.println("User Daten wurden gelöscht");

            } catch (SQLException e) {

                System.err.println(e.getMessage());

            }
    }

    public User getUserData(){
            String url = "jdbc:sqlite:diet.db";
            String sql = "SELECT height,age,weight,isMale,hasDiabetes FROM user";

            try (var conn = DriverManager.getConnection(url);
                 var stmt = conn.createStatement();
                 ResultSet rs = stmt.executeQuery(sql);) {

                return new User(rs.getInt("height"),rs.getInt("age"),
                                rs.getInt("weight"),rs.getBoolean("isMale"),
                                rs.getBoolean("hasDiabetes"));

            } catch (SQLException e) {
                System.err.println(e.getMessage());
            }
            return null;
    }

}
