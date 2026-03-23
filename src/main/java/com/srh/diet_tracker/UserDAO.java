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

    // id wird vom entsprechenden Controller übergeben (last id für ControllerUser, beliebige id für ControllerDayReview)
    public void updateEntryData(int id) {
        if (id!=0) {
            String url = "jdbc:sqlite:diet.db";
            String sql = "UPDATE entry SET age = ? , "
                    + " height = ? , "
                    + " weight = ? "
                    + " isMale = ? , "
                    + " hasDiabetes = ? "
                    + "WHERE id = ?";

            try (var conn = DriverManager.getConnection(url);
                 var pstmt = conn.prepareStatement(sql)) {
                pstmt.setInt(1, user.getAge());
                pstmt.setInt(2, user.getHeight());
                pstmt.setInt(3, user.getWeight());
                pstmt.setBoolean(4, user.isMale());
                pstmt.setBoolean(5, user.hasDiabetes);
                pstmt.setInt(6, id);
                pstmt.executeUpdate();

            } catch (SQLException e) {
                System.err.println(e.getMessage());
            }
        }
    }


    // id wird vom entsprechenden Controller übergeben (last id für ControllerUser, beliebige id für ControllerDayReview)
    public void deleteEntryData (int id){
        if (id!=0) {
            String url = "jdbc:sqlite:diet.db";
            String sql = "DELETE FROM user WHERE id = ?";

            try (var conn = DriverManager.getConnection(url);
                 var pstmt = conn.prepareStatement(sql)) {
                pstmt.setInt(1, user.getAge());
                pstmt.setInt(2, user.getHeight());
                pstmt.setInt(3, user.getWeight());
                pstmt.setBoolean(4, user.isMale());
                pstmt.setBoolean(5, user.hasDiabetes);
                pstmt.setInt(6, id);
                pstmt.executeUpdate();

            } catch (SQLException e) {
                System.err.println(e.getMessage());
            }
        }
    }

    public int getLastId(){
        String url = "jdbc:sqlite:diet.db";
        String sql = "SELECT MAX(id) AS max_id FROM user;";

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

    void setUser (User user){
        this.user = user;
    }

}
