package com.srh.diet_tracker;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.RadioButton;
import javafx.stage.Stage;

import java.io.IOException;

public class Homepage extends App {

    public Button userEditGoBtn;
    public Button entryGoBtn;
    public Button dayReviewGoBtn;
    public RadioButton caloriesRadioBtn;
    public RadioButton sugarRadioBtn;
    public CheckBox meanCheckBox;
    public RadioButton sumRadioBtn;
    public RadioButton percentageRadioBtn;
    public DatePicker olderDatePicker;
    public DatePicker newerDatePicker;
    public Button graphMakeBtn;

    public void start(Stage stage){

    }

    public void userEditGoBtn(ActionEvent actionEvent) {
         SceneManager.getInstance().loadScene(SceneType.USER, "User", 900, 600);
    }

    public void onEntryGoBtn(ActionEvent actionEvent) {
    }

    public void onDayReviewGoBtn(ActionEvent actionEvent) throws IOException {
        DbManager dbManager = new DbManager();
        dbManager.setDataBase();

        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("day_review.fxml"));


        Scene scene = new Scene(fxmlLoader.load(), 1000, 500);

        // FRAGE: Wie "springe" ich zwischen Stages mit Buttons?
        /*stage.setTitle("Einträge für bestimmten Tag sehen oder ändern.");
        stage.setScene(scene);
        stage.show();*/
    }

    public void onGraphMakeBtn(ActionEvent actionEvent) {
    }




    //First we have to use method returnRegisteredDays. The values of this array, will be used in a for cycle
    // to get corresponding sum of calories of each day, for further representation in Graph.

    // Here will be, using UserDAO and EntryDAO represented and calculated things like:
    // - Maximum of Calories and Sugar of the day (represented as line in graph)
    // - percentage achieved of Calories and Sugar of the day, relative to max calories/sugar, as graph (calculated)

    // TEST methods

    /*public void representData() {
        UserDAO userDAO = new UserDAO();
        User user = userDAO.getUserData();
        EntryDAO entryDAO = new EntryDAO();
        LocalDate forAllDates = LocalDate.parse("2000-12-01");
        LocalDate olderDate = LocalDate.parse("2026-03-24");
        LocalDate newerDate = LocalDate.parse("2026-03-25");
        ArrayList<LocalDate> dateList = entryDAO.returnRegisteredDates(false, newerDate, olderDate);

        for (int i=0;i<dateList.size();i++){
            System.out.println("Datum: " + dateList.get(i) + ", Summe Kalorien: " +
                                + entryDAO.returnCaloriesSumByDate(dateList.get(i)) + ", Prozent von Max Kalorien:" +
                                + ((entryDAO.returnCaloriesSumByDate(dateList.get(i)) / user.getBMR()) * 100) +
                                + entryDAO.returnCaloriesSumByDate(dateList.get(i)) + ", Summe Zucker: " +
                                + entryDAO.returnSugarSumByDate(dateList.get(i)) + ", Prozent von Max Zucker: " +
                                + (entryDAO.returnSugarSumByDate(dateList.get(i)) / 50) * 100);
        }
    }*/
}
