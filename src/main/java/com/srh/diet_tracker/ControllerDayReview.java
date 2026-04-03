package com.srh.diet_tracker;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.DragEvent;
import javafx.util.Callback;

import java.lang.reflect.Array;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class ControllerDayReview extends ControllerParent{

    public Button deleteBtn;
    //Controls
    @FXML
    private DatePicker datePicker = new DatePicker();
    @FXML
    private Button saveEntryBtn;
    @FXML
    private Spinner<Integer> spinnerEntryNr;
    @FXML
    private RadioButton isSportRadioBtn = new RadioButton();
    @FXML
    private RadioButton isMealRadioBtn = new RadioButton();
    @FXML
    private TextField caloriesTextField = new TextField();
    @FXML
    private TextField sugarTextField = new TextField();
    @FXML
    private Spinner<Integer> hoursSpinner = new Spinner<>();
    @FXML
    private Spinner<Integer> minutesSpinner = new Spinner<>();
    @FXML
    private Spinner<Integer> secondsSpinner = new Spinner<>();;
    @FXML
    private Label sugarPercentageLabel = new Label();
    @FXML
    private Label caloriesPercentageLabel = new Label();
    @FXML
    private DatePicker datePickerChanges=new DatePicker();
    @FXML
    private Label warningLabel = new Label();

    // Table and its Columns
    @FXML
    private TableView<TableRow> table;
    @FXML
    private TableColumn<TableRow,String> entryNrColumn;
    @FXML
    private TableColumn<TableRow,String> activityColumn;
    @FXML
    private TableColumn<TableRow,String> caloriesColumn;
    @FXML
    private TableColumn<TableRow,String> sugarColumn;
    @FXML
    private TableColumn<TableRow,String> timeColumn;


    // FRAGE : Müssen die Attribute sein?
    private EntryDAO entryDAO;
    private UserDAO userDAO;
    private boolean isSport;
    private boolean datePickedOn = false;


    public ControllerDayReview() {}

    public void initialize (){
        spinnerEntryNr.valueProperty().addListener((obs, oldValue, newValue) ->
            {
                System.out.println("Spinner Method");
                EntryDAO entryDAO = new EntryDAO();
                LocalDate date = datePicker.getValue();
                // here I get a List with all the info from entries for one day, even its id!! can use it to update any one.
                ArrayList<Entry> entryList = entryDAO.returnEntriesDay(date);
                ControllerDayReview controllerDayReview = new ControllerDayReview();
                controllerDayReview.setFieldsByEntryNr(newValue-1,entryList,date,datePickerChanges, isSportRadioBtn, isMealRadioBtn, caloriesTextField,
                sugarTextField, hoursSpinner, minutesSpinner, secondsSpinner);
            });
    }


    public void onActionDatePicker(ActionEvent actionEvent) {
        LocalDate date = datePicker.getValue();
        EntryDAO entryDAO = new EntryDAO();
        ControllerDayReview controllerDayReview = new ControllerDayReview();
        ArrayList<Entry> entryList = entryDAO.returnEntriesDay(date);

        if (entryList == null || entryList.isEmpty()){
            warningLabel.setText("Kein Eintrag an gewählten Tag!\nKeine neue Einträge angezeigt.");
        }
        else {
            int entryNr = entryList.size()-1;
            isSportRadioBtn.setSelected(entryList.get(entryNr).isSport());
            isMealRadioBtn.setSelected(!entryList.get(entryNr).isSport());
            String calories = Double.toString(entryList.get(entryNr).getCalories());
            caloriesTextField.setText(calories);
            String sugar = Double.toString(entryList.get(entryNr).getSugar());
            sugarTextField.setText(sugar);

            spinnerEntryNr.setValueFactory(
                    new SpinnerValueFactory.IntegerSpinnerValueFactory(
                            1,
                            entryList.size()
                    )
            );
            spinnerEntryNr.getValueFactory().setValue(entryList.size());
            hoursSpinner.setValueFactory(
                    new SpinnerValueFactory.IntegerSpinnerValueFactory(
                            0,
                            23
                    )
            );
            hoursSpinner.getValueFactory().setValue(entryList.get(entryNr).getTime().getHour());
            minutesSpinner.setValueFactory(
                    new SpinnerValueFactory.IntegerSpinnerValueFactory(
                            0,
                            59
                    )
            );
            minutesSpinner.getValueFactory().setValue(entryList.get(entryNr).getTime().getMinute());
            secondsSpinner.setValueFactory(
                    new SpinnerValueFactory.IntegerSpinnerValueFactory(
                            0,
                            59
                    )
            );
            secondsSpinner.getValueFactory().setValue(entryList.get(entryNr).getTime().getSecond());
        }


        // For representing day entries in Table
       controllerDayReview.representDataInTableAndPercentageLabels(table,datePicker,entryNrColumn,activityColumn,caloriesColumn,sugarColumn,timeColumn,
                            caloriesPercentageLabel,sugarPercentageLabel);

//        saveEntryBtn.setDisable(false);
//        deleteBtn.setDisable(false);

    }

    /*
    * (TableView<Entry> table, DatePicker datePicker, TableColumn<Integer, String> entryNrColumn,
                                      TableColumn<Entry, String> activityColumn, TableColumn<Entry, String> caloriesColumn,
                                      TableColumn<Entry,String> sugarColumn, TableColumn<Entry,String> timeColumn)*/

    public void onIsSportRadioBtn(ActionEvent actionEvent) {
        isMealRadioBtn.setSelected(false);
        isSport = true;
    }

    public void onIsMealRadioBtn(ActionEvent actionEvent) {
        isSportRadioBtn.setSelected(false);
        isSport = false;
    }

    public void onSaveEntryBtn(ActionEvent actionEvent) {
        if(isSportRadioBtn.isDisable()){isSport=false;}
        else if(isMealRadioBtn.isDisable()){isSport=true;}
        Entry entry = returnEntryFromFields(checkTextFieldData(datePickerChanges, caloriesTextField,sugarTextField,warningLabel),
                isSport,caloriesTextField,sugarTextField,null,datePickerChanges,
                hoursSpinner,minutesSpinner,secondsSpinner,warningLabel);

        EntryDAO entryDAO = new EntryDAO(entry);
        ArrayList<Integer> idList= entryDAO.returnEntriesDayCorrespodingIds(datePicker.getValue());
        // SET IN METHOD LATER - used on delete Data button
        for (int i=0; i<idList.size();i++){
            int spinnerNr= spinnerEntryNr.getValueFactory().getValue();
            if (i==spinnerNr-1){
                int id= idList.get(spinnerNr-1);
                entryDAO.updateEntryData(id);
                // For representing day entries in Table from day from entry that was changed
                representDataInTableAndPercentageLabels(table,datePickerChanges,entryNrColumn,
                        activityColumn,caloriesColumn,sugarColumn,timeColumn,
                        caloriesPercentageLabel,sugarPercentageLabel);
                datePicker.setValue(datePickerChanges.getValue());
//                saveEntryBtn.setDisable(true);
//                deleteBtn.setDisable(true);
                break;
            }
        }

    }

    private void setFieldsByEntryNr (int entryNr, ArrayList<Entry> entryList, LocalDate date, DatePicker datePickerChanges, RadioButton isSportRadioBtn,
                             RadioButton isMealRadioBtn, TextField caloriesTextField, TextField sugarTextField,
                             Spinner<Integer> hoursSpinner, Spinner<Integer> minutesSpinner, Spinner<Integer> second) {

        if (entryList == null ||entryList.isEmpty()) {
            warningLabel.setText("Kein Eintrag an gewählten Tag!\nKeine neue Einträge angezeigt.");
        } else {
            datePickerChanges.setValue(date);
            isSportRadioBtn.setSelected(entryList.get(entryNr).isSport());
            isMealRadioBtn.setSelected(!entryList.get(entryNr).isSport());
            isSport = entryList.get(entryNr).isSport();
            String calories = Double.toString(entryList.get(entryNr).getCalories());
            caloriesTextField.setText(calories);
            String sugar = Double.toString(entryList.get(entryNr).getSugar());
            sugarTextField.setText(sugar);
            hoursSpinner.setValueFactory(
                    new SpinnerValueFactory.IntegerSpinnerValueFactory(
                            0,
                            23
                    )
            );
            hoursSpinner.getValueFactory().setValue(entryList.get(entryNr).getTime().getHour());
            minutesSpinner.setValueFactory(
                    new SpinnerValueFactory.IntegerSpinnerValueFactory(
                            0,
                            59
                    )
            );
            minutesSpinner.getValueFactory().setValue(entryList.get(entryNr).getTime().getMinute());
            secondsSpinner.setValueFactory(
                    new SpinnerValueFactory.IntegerSpinnerValueFactory(
                            0,
                            59
                    )
            );
            secondsSpinner.getValueFactory().setValue(entryList.get(entryNr).getTime().getSecond());
        }
    }

    private void representDataInTableAndPercentageLabels(TableView<TableRow> table, DatePicker datePicker, TableColumn<TableRow, String> entryNrColumn,
                                      TableColumn<TableRow, String> activityColumn, TableColumn<TableRow, String> caloriesColumn,
                                      TableColumn<TableRow,String> sugarColumn, TableColumn<TableRow,String> timeColumn,
                                      Label caloriesPercentageLabel, Label sugarPercentageLabel ) {

        //Represent data in Table Code
        ObservableList<TableRow> observableEntryList = FXCollections.observableArrayList();
        EntryDAO entryDAO = new EntryDAO();
        ArrayList<Entry> entryList = entryDAO.returnEntriesDay(datePicker.getValue());
        String activity;
        for (int i = entryList.size() - 1; i >= 0; i--) {
            if (entryList.get(i).isSport()) {
                activity = "Sport";
            } else {
                activity = "Essen";
            }
            TableRow tableRow = new TableRow(i + 1, activity, entryList.get(i).getCalories(), entryList.get(i).getSugar(), entryList.get(i).getTime());
            observableEntryList.add(tableRow);
        }


        entryNrColumn.setCellValueFactory(new PropertyValueFactory<TableRow, String>("entryNr"));
        activityColumn.setCellValueFactory(new PropertyValueFactory<TableRow, String>("activity"));
        caloriesColumn.setCellValueFactory(new PropertyValueFactory<TableRow, String>("calories"));
        sugarColumn.setCellValueFactory(new PropertyValueFactory<TableRow, String>("sugar"));
        timeColumn.setCellValueFactory(new PropertyValueFactory<TableRow, String>("time"));

        table.setItems(observableEntryList);

        //Code for calculating and representing achieved percentages of calories and sugar
        UserDAO userDAO = new UserDAO();
        User user = userDAO.getUserData();

        double percentageCalories = Math.floor((entryDAO.returnCaloriesSumByDate(datePicker.getValue()) / user.getBMR()) * 100);
        double percentageSugar = Math.floor((entryDAO.returnSugarSumByDate(datePicker.getValue()) / 50) * 100);
        caloriesPercentageLabel.setText("Kalorien: "+percentageCalories+"%");
        sugarPercentageLabel.setText("Zucker: "+percentageSugar+"%");
    }

    public void onReturnHomepage(ActionEvent actionEvent) {
        SceneManager.getInstance().loadScene(SceneType.HOMEPAGE, "Homepage", 600, 500);
    }

    public void onDeleteBtn(ActionEvent actionEvent) {
        EntryDAO entryDAO = new EntryDAO();
        ArrayList<Integer> idList= entryDAO.returnEntriesDayCorrespodingIds(datePicker.getValue());

        for (int i=0; i<idList.size();i++){
            int spinnerNr= spinnerEntryNr.getValueFactory().getValue();
            if (i==spinnerNr-1){
                int id= idList.get(spinnerNr-1);
                int newCountOfEntries = idList.size()-1;
                entryDAO.deleteEntry(id);
                // For representing day entries in Table from day from entry that was changed
                representDataInTableAndPercentageLabels(table,datePickerChanges,entryNrColumn,
                        activityColumn,caloriesColumn,sugarColumn,timeColumn,
                        caloriesPercentageLabel,sugarPercentageLabel);
                datePicker.setValue(datePickerChanges.getValue());

                //Set Spinner to new entry count of Day
                spinnerEntryNr.setValueFactory(
                        new SpinnerValueFactory.IntegerSpinnerValueFactory(
                                1,
                                newCountOfEntries
                        )
                );
                spinnerEntryNr.getValueFactory().setValue(newCountOfEntries);
                break;
            }
        }
    }


    // TEST METHODS

    /*public void showCaloriesSumByDate(){

        // Actual Date only used for test purposes
        LocalDate date;
        date = LocalDate.now();

        EntryDAO entryDAO = new EntryDAO();
        System.out.println("Calories Sum for asked day: " + entryDAO.returnCaloriesSumByDate(date));
    }

    public void showSugarSumByDate(){

        // Actual Date only used for test purposes
        LocalDate date;
        date = LocalDate.now();

        EntryDAO entryDAO = new EntryDAO();
        System.out.println("Sugar Sum for asked day: "+ entryDAO.returnSugarSumByDate(date));
    }

    public void showTotalCaloriesSum(){

        EntryDAO entryDAO = new EntryDAO();
        System.out.println("Total Calories Sum: " + entryDAO.returnCaloriesTotalSum());
    }

    public void showTotalSugarSum(){

        EntryDAO entryDAO = new EntryDAO();
        System.out.println("Total Sugar Sum: "+ entryDAO.returnSugarTotalSum());
    }

    public void showCaloriesMean(){
        EntryDAO entryDAO = new EntryDAO();
        System.out.println("Calories Mean: "+ entryDAO.returnCaloriesTotalSum()/entryDAO.returnNumberOfDays());
    }

    public void showSugarMean(){
        EntryDAO entryDAO = new EntryDAO();
        System.out.println("Sugar Mean: "+ entryDAO.returnSugarTotalSum()/entryDAO.returnNumberOfDays());
    }

    public void representData() {
        UserDAO userDAO = new UserDAO();
        User user = userDAO.getUserData();
        System.out.println("Height " + user.getHeight() + "cm " + ", Age: " + user.getAge() + ", Weight: " + user.getWeight() +
                "kg" + ", Male:" + user.isMale() + ",Has Diabetes: " + user.hasDiabetes());

        // Actual Date only used for test purposes
        LocalDate date;
        date = LocalDate.now();

        EntryDAO entryDAO = new EntryDAO();

        double percentageCalories = (entryDAO.returnCaloriesSumByDate(date) / user.getBMR()) * 100;
        double percentageSugar = (entryDAO.returnCaloriesSumByDate(date) / 50) * 100;
        System.out.println("Du hast " + percentageCalories + " % von deinen Maximalen Tageskaloriengehalt erreicht.");
        System.out.println("Du hast " + percentageSugar + " % von deinen Maximalen Tageszuckergehalt erreicht.");

        ArrayList<Entry> entryList = entryDAO.returnEntriesDay(date);
        for (int i = 0; i < entryList.size(); i++) {
            System.out.println(i + "-Eintrag :");
            System.out.println("Sport: " + entryList.get(i).isSport());
            System.out.println("Kaloriengewinn: " + entryList.get(i).getCalories());
            System.out.println("Zuckegewinn: " + entryList.get(i).getSugar());
            System.out.println("Datum: " + entryList.get(i).getDay());
            System.out.println("Uhrzeit: " + entryList.get(i).getTime());
            System.out.println("----------------------");

        }

    }/*




    //


    // Here will be, using UserDAO and EntryDAO represented and calculated things like:
    // - the Entries (represented)
    // - the maximum of Calories and Sugar of the day (represented)
    // - Sum of Calories and Sugar of the day (calculated)
    // - percentage achieved of Calories and Sugar of the day, relative to max calories/sugar (calculated)

    // waits for event from Button "Löschen".
    /*public void eliminateLastEntry(int id){
        EntryDAO entryDAO = new EntryDAO();
        entryDAO.deleteEntry(id);
        /*int lastId = entryDAO.getLastId();
        System.out.println("Resultat von getLastId" + )
        System.out.println("Letzter Eintrag wurde gelöscht");

    }*/

}
