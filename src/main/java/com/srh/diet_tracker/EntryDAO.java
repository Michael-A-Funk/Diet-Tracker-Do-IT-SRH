package com.srh.diet_tracker;


import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class EntryDAO {

    String url = "jdbc:sqlite:diet.db";
    DateTimeFormatter parserDate = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    DateTimeFormatter parserTime = DateTimeFormatter.ofPattern("HH:mm:ss");

    private Entry entry;

    // Frage : bzg. Controller: soll man sie separat halten wie unten, oder Methode zum setzten des Attributes entry?
    //Constructor used when we want to insert new Data into DB
    public EntryDAO(Entry entry) {
        this.entry = entry;
    }

    //Constructor user when we want to use methods update, delete and getLastId
    public EntryDAO() {
    }

    public void insertEntryData() {

        //For a first entry, the date and time should be automatically set to the current time.
        // This SQL command is one possibility, but not necessarily the final solution.
        //Later, functionality may be added to change the time.
        // Later, a controller should be used when instantiating the Entry class.

        String sql = "INSERT INTO entry(activity,calories,sugar,date,time) VALUES(?,?,?,?,?)";

        try (var conn = DriverManager.getConnection(url);
             var pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, entry.getActivity());
            pstmt.setDouble(2, entry.getCalories());

            // Presumely sugar is already forced in DB
            /*if (entry.isSport()) {
                pstmt.setDouble(3, 0);
            } else {
                pstmt.setDouble(3, entry.getSugar());
            }*/
            pstmt.setDouble(3, entry.getSugar());
            pstmt.setString(4, (entry.getDay()).format(parserDate));
            pstmt.setString(5, (entry.getTime()).format(parserTime));

            pstmt.executeUpdate();

        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    // This method will be used by two different controllers. The `isLastEntry` variable differentiates between them.
    //  The ID is passed by the corresponding `ControllerDayReview` (last ID for `ControllerEntry`, any ID for `ControllerDayReview`)
    public void updateEntryData(int id) {
        String sql = "UPDATE entry SET activity = ? , "
                + " calories = ? , "
                + " sugar = ?, "
                + " date = ?, "
                + " time = ? "
                + "WHERE id = ?";

        try (var conn = DriverManager.getConnection(url);
             var pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, entry.getActivity());
            pstmt.setDouble(2, entry.getCalories());
            pstmt.setDouble(3, entry.getSugar());
            pstmt.setString(4, (entry.getDay()).format(parserDate));
            pstmt.setString(5, (entry.getTime()).format(parserTime));
            // id=0 wird gesetzt vom ControllerEntry um zu diferenzieren von ControllerDayReview, wo beliebige
            // ids geändert werden, und nicht nur die letzte (forciert)
            if (id == 0) {
                pstmt.setInt(6, getLastId());
            } else {
                pstmt.setInt(6, id);
            }
            pstmt.executeUpdate();

        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }


    // The ID is passed by the corresponding controller (last ID for Controller User, any ID for ControllerDayReview)
    public void deleteEntry(int id) {
        String sql = "DELETE FROM entry WHERE id = ?";

        try (var conn = DriverManager.getConnection(url);
             var pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            pstmt.executeUpdate();

        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    public int getLastId() {
        var sql = "SELECT MAX(id) AS max_id FROM entry;";

        try (var conn = DriverManager.getConnection(url);
             var stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            System.out.println("Letzte id von entry ist: " + rs.getInt("max_id"));
            return rs.getInt("max_id");

        } catch (SQLException e) {

            System.err.println(e.getMessage());
        }
        System.out.println("Keine id selektiert!!!");
        return 0;
    }


    public double returnCaloriesSumByDate(LocalDate date) {
        String sql = "SELECT SUM(CASE WHEN activity = 'sport' THEN -calories ELSE calories END) AS sum_calories_day  " +
                "FROM entry WHERE date = ?;";

        try (var conn = DriverManager.getConnection(url);
             var stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, date.format(parserDate));
            ResultSet rs = stmt.executeQuery();
            return rs.getDouble("sum_calories_day");

        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return 0;
    }


    // Will be implemented in ControllerDayReview and ControllerGraph.  !! HAVE TO THINK ABOUT WHAT IT REPRESENTS !!
    public double returnCaloriesTotalSum() {
        String sql = "SELECT SUM(CASE WHEN activity = 'sport' THEN -calories ELSE calories END) AS sum_calories_total FROM entry;";

        try (var conn = DriverManager.getConnection(url);
             var stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            return rs.getDouble("sum_calories_total");

        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return 0;
    }

    public double returnSugarSumByDate(LocalDate date) {
        String sql = "SELECT SUM(sugar) AS sum_sugar_day FROM entry WHERE date= ?;";

        try (var conn = DriverManager.getConnection(url);
             var stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, date.format(parserDate));
            ResultSet rs = stmt.executeQuery();
            return rs.getDouble("sum_sugar_day");

        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return 0;
    }


    // Will be implemented in ControllerDayReview and ControllerGraph.  !! HAVE TO THINK ABOUT WHAT IT REPRESENTS !!
    public double returnSugarTotalSum() {
        String sql = "SELECT SUM(sugar) AS sum_sugar_total FROM entry";

        try (var conn = DriverManager.getConnection(url);
             var stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            return rs.getDouble("sum_sugar_total");

        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return 0;
    }

    // Will be implemented in ControllerDayReview and ControllerGraph.  !! HAVE TO THINK ABOUT WHAT IT REPRESENTS !!
    public double returnNumberOfDays() {
        String sql = "SELECT COUNT(DISTINCT date) AS count_days FROM entry";

        try (var conn = DriverManager.getConnection(url);
             var stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            return rs.getDouble("count_days");

        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return 0;
    }

    public ArrayList<Entry> returnEntriesDay(LocalDate date){
        String sql = "SELECT id, activity, calories, sugar, date, time FROM entry WHERE date = ? ORDER BY time ASC";
        ArrayList<Entry> entryList = new ArrayList<>();

        try (var conn = DriverManager.getConnection(url);
             var stmt = conn.prepareStatement(sql)) {

            stmt.setString(1,date.format(parserDate));
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                LocalDate localDate = LocalDate.parse(rs.getString("date"), parserDate);
                LocalTime localTime = LocalTime.parse(rs.getString("time"), parserTime);

                Entry entry = new Entry(rs.getInt("id"),rs.getString("activity"), rs.getDouble("calories"),
                        rs.getDouble("sugar"), localDate, localTime);
                entryList.add(entry);

            }
            System.out.println("Nicht null!"); // Even when DB doesn't have entry for date, it will make a empty List.
            return entryList;
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        System.out.println("Ist Null"); // only if Error in try, for example if trying to parse incorrect Time format (like 00:00)
        return null;
    }

    public ArrayList<Integer> returnEntriesDayCorrespondingIds(LocalDate date){
        String sql = "SELECT id FROM entry WHERE date = ? ORDER BY time ASC";
        ArrayList<Integer> idList = new ArrayList<Integer>();

        try (var conn = DriverManager.getConnection(url);
             var stmt = conn.prepareStatement(sql)) {

            stmt.setString(1,date.format(parserDate));
            ResultSet rs = stmt.executeQuery();


            while (rs.next()) {
                int id = rs.getInt("id");
                idList.add(id);

            }
            return idList;
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return null;
    }

    public ArrayList<LocalDate> returnRegisteredDates(boolean allDates, LocalDate olderDate, LocalDate newerDate){
        String sql;
        if (allDates){
            sql = "SELECT DISTINCT date FROM entry ORDER BY date ASC";
        }
        else {
            sql = "SELECT DISTINCT date FROM entry WHERE date>=? AND date<=? ORDER BY date ASC";
        }
        ArrayList<LocalDate> dateList = new ArrayList<>();

        try (var conn = DriverManager.getConnection(url);
             var stmt = conn.prepareStatement(sql)) {
            if (!allDates) {
                stmt.setString(1, olderDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
                stmt.setString(2, newerDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
            }
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                LocalDate localDate = LocalDate.parse(rs.getString("date"));
                dateList.add(localDate);

            }
            return dateList;

        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return null;
    }

    public Entry getLastEntry(){
        String sql = "SELECT activity, calories, sugar, date, time FROM entry WHERE id=?";

        try (var conn = DriverManager.getConnection(url);
             var stmt = conn.prepareStatement(sql)) {
                stmt.setInt(1,getLastId());
                ResultSet rs = stmt.executeQuery();


                LocalDate localDate = LocalDate.parse(rs.getString("date"), parserDate);
                LocalTime localTime = LocalTime.parse(rs.getString("time"), parserTime);

            return new Entry(rs.getString("activity"), rs.getDouble("calories"),
                rs.getDouble("sugar"), localDate, localTime);


        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return null;
    }

    public double getMaxCalories() {
        var sql = "SELECT MAX(calories) AS max_calories FROM entry;";

        try (var conn = DriverManager.getConnection(url);
             var stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            return rs.getInt("max_calories");

        } catch (SQLException e) {

            System.err.println(e.getMessage());
        }
        return 2000;
    }

    public double getMaxSugar() {
        var sql = "SELECT MAX(sugar) AS max_sugar FROM entry;";

        try (var conn = DriverManager.getConnection(url);
             var stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            return rs.getInt("max_sugar");

        } catch (SQLException e) {

            System.err.println(e.getMessage());
        }
        return 50;
    }

    public ArrayList<Double> returnSUMCaloriesForDateRange(boolean allDates, LocalDate olderDate, LocalDate newerDate){
        String sql;
        if (allDates){
            sql = "SELECT SUM(CASE WHEN activity = 'sport' THEN -calories ELSE calories END) as sum_calories " +
                    "FROM entry GROUP by date ORDER BY date ASC";
        }
        else {
            sql = "SELECT SUM(calories) sum_calories FROM entry WHERE date>=? AND date<=?  GROUP by date ORDER BY date ASC;";
        }

        try (var conn = DriverManager.getConnection(url);
             var stmt = conn.prepareStatement(sql)) {
            if (!allDates) {
                stmt.setString(1, olderDate.format(parserDate));
                stmt.setString(2, newerDate.format(parserTime));
            }
            ResultSet rs = stmt.executeQuery();
            ArrayList<Double> caloriesSumList=new ArrayList<>();
            while (rs.next()) {
                caloriesSumList.add(rs.getDouble("sum_calories"));

            }
            return caloriesSumList;

        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return null;
    }

    public ArrayList<Double> returnSUMSugarForDateRange(boolean allDates, LocalDate olderDate, LocalDate newerDate){
        String sql;
        if (allDates){
            sql = "SELECT SUM(sugar) as sum_sugar FROM entry GROUP by date ORDER BY date ASC";
        }
        else {
            sql = "SELECT SUM(sugar) sum_sugar FROM entry WHERE date>=? AND date<=?  GROUP by date ORDER BY date ASC;";
        }

        try (var conn = DriverManager.getConnection(url);
             var stmt = conn.prepareStatement(sql)) {
            if (!allDates) {
                stmt.setString(1, olderDate.format(parserDate)); //DateTimeFormat
                stmt.setString(2, newerDate.format(parserTime));
            }
            ResultSet rs = stmt.executeQuery();
            ArrayList<Double> caloriesSumList=new ArrayList<>();
            while (rs.next()) {
                caloriesSumList.add(rs.getDouble("sum_sugar"));

            }
            return caloriesSumList;

        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return null;
    }


}
