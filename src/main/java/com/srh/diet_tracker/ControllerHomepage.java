package com.srh.diet_tracker;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.RadioButton;

public class ControllerHomepage extends App {

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

    public void userEditGoBtn(ActionEvent actionEvent) {
        SceneManager.getInstance().loadScene(SceneType.USER, "User Daten eingeben/ändern", 600, 400);
    }

    public void onEntryGoBtn(ActionEvent actionEvent) {
        SceneManager.getInstance().loadScene(SceneType.ENTRY, "Neuer Eintrag oder Letztes Ändern", 600, 400);
    }

    public void onDayReviewGoBtn(ActionEvent actionEvent){
        SceneManager.getInstance().loadScene(SceneType.REVIEW, "Tagesübersicht und Einträge verwalten", 600, 400);

    }

    public void onGraphGoBtn(ActionEvent actionEvent) {
        SceneManager.getInstance().loadScene(SceneType.GRAPH, "Graphen erstellen mit Statistik", 600, 400);
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
