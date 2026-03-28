package com.srh.diet_tracker;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;


public class ControllerEntry {

    public Label warningLabel;
    @FXML
    private RadioButton isMealRadioBtn;
    @FXML
    private RadioButton isSportRadioBtn;
    @FXML
    private TextField caloriesTextField;
    @FXML
    private TextField sugarTextField;
    @FXML
    private CheckBox selectActualTime;
    @FXML
    private DatePicker datePicker;
    @FXML
    private Spinner hoursSpinner;
    @FXML
    private Spinner minutesSpinner;
    @FXML
    private Spinner secondsSpinner;
    @FXML
    private Button saveEntryBtn;
    @FXML
    private Button editLastEntryBtn;
    @FXML
    private Button newEntryBtn;


    Entry entry = new Entry();


    // FRAGE : Muss das Attribut sein?
    private EntryDAO entryDAO;

    public ControllerEntry() {}


    public void onIsMealRadioBtn(ActionEvent actionEvent) {
        entry.setSport(false);
        isSportRadioBtn.setSelected(false);
    }

    public void onIsSportRadioBtn(ActionEvent actionEvent) {
        entry.setSport(true);
        isMealRadioBtn.setSelected(false);
    }

    public void onSelectActualTime(ActionEvent actionEvent) {
        if (selectActualTime.isSelected()){
            datePicker.setDisable(true);
            hoursSpinner.setDisable(true);
            minutesSpinner.setDisable(true);
            secondsSpinner.setDisable(true);
        }else{
            datePicker.setDisable(false);
            hoursSpinner.setDisable(false);
            minutesSpinner.setDisable(false);
            secondsSpinner.setDisable(false);
        }
    }

    public void onSaveEntryBtn(ActionEvent actionEvent) {
        String caloriesText = caloriesTextField.getText();
        String sugarText = sugarTextField.getText();
        boolean isCaloriesEntryAdequate;
        boolean isSugarEntryAdequate;

        try {
            double calories = Double.parseDouble(caloriesText);
            isCaloriesEntryAdequate = true;
            entry.setCalories(calories);

        } catch (NumberFormatException e){
            isCaloriesEntryAdequate = false;
        }

        try {
            double sugar = Double.parseDouble(sugarText);
            isSugarEntryAdequate = true;
            entry.setSugar(sugar);

        } catch (NumberFormatException e){
            isSugarEntryAdequate = false;

        }

        //Check and warn if calories and sugar entries are adequate.
        if (isSugarEntryAdequate == false || isCaloriesEntryAdequate == false ){
            warningLabel.setText("Bitte geben sie ein adequaten\nNumerischen Wert für\nKalorien und Zucker!\n(für Kommastelle '.').");
        }
        else if (isCaloriesEntryAdequate==false){
            warningLabel.setText("Bitte geben sie ein adequaten\nNumerischen Wert für \nKalorien! Falls sie ','\n(für Kommastelle '.')");
        }
        else if (isSugarEntryAdequate == false){
            warningLabel.setText("Bitte geben sie ein adequaten\nNumerischen Wert für \nSugar! Falls sie ','\n(für Kommastelle '.')");
        }
        // if entry values adequate, then save all Entry values.
        else {
            //check if user wants to save actual time or other time.
//            if (selectActualTime.isSelected()){
                ControllerEntry controllerEntry = new ControllerEntry();
                LocalDate date;
                date = LocalDate.now();
                LocalTime time;
                time = LocalTime.now();
                time = controllerEntry.parseTime(time);

                entry.setDay(date);
                entry.setTime(time);
                warningLabel.setText("Eintrag gespeichert.\nSie können es bearbeiten\noder ein neuen speichern.");
//            }
            /*else{
                LocalDate date = datePicker.getValue();
                LocalTime time;
                String timeTextToParse = hoursSpinner.getValue() + ":" + minutesSpinner.getValue().
            }*/

            EntryDAO entryDAO = new EntryDAO(entry);
            entryDAO.insertEntryData();
         }


    }

    public void onEditLastEntryBtn(ActionEvent actionEvent) {
    }

    public void onNewEntryBtn(ActionEvent actionEvent) {
    }

    public LocalDate parseDate (LocalDate date){
        DateTimeFormatter parserDate = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String dateText = date.format(parserDate);
        return LocalDate.parse(dateText, parserDate);

    }

    public  LocalTime parseTime (LocalTime time){
        DateTimeFormatter parserTime = DateTimeFormatter.ofPattern("HH:mm:ss");
        String timeText = time.format(parserTime);
        return LocalTime.parse(timeText, parserTime);
    }


    /* for SaveButton
        LocalDate date;
        date = LocalDate.now();
        LocalTime time;
        time = LocalTime.now();

        entry.setDay(date);
        entry.setTime(time);
     */

    // TEST METHODS

    // What happens when "Bestätigen" Button is clicked !!! Parameters are now just for testing purposes!!!
    /*public void saveEntry(boolean isSport,double calories, double sugar){

        ControllerEntry controllerEntry = new ControllerEntry();

        // Frage : Kann ich es auch außerhalb der methode machen um es nicht zu wiederholen?
        LocalDate date;
        LocalTime time;

        // Date and Time will further be dependent, with an if-clause, from the boolean coming from checkbox "Jetzige Zeit"
        date = LocalDate.now();
        date = controllerEntry.parseDate(date);
        time = LocalTime.now();
        time = controllerEntry.parseTime(time);

        Entry entry = new Entry(isSport,calories,sugar,date,time);
        EntryDAO entryDAO = new EntryDAO(entry);
        entryDAO.insertEntryData();
        System.out.println("Neuer Eintrag wurde gesetzt");
    }

    public void updateLastEntry(boolean isSport, double calories, double sugar){

        ControllerEntry controllerEntry = new ControllerEntry();

        // Frage : Kann ich es auch außerhalb der methode machen um es nicht zu wiederholen?
        LocalDate date;
        LocalTime time;

        // Date and Time will further be dependent, with an if-clause, from the boolean coming from checkbox "Jetzige Zeit"
        date = LocalDate.now();
        date = controllerEntry.parseDate(date);
        time = LocalTime.now();
        time = controllerEntry.parseTime(time);


        Entry entry = new Entry(isSport,calories, sugar, date, time);
        EntryDAO entryDAO = new EntryDAO(entry);
        entryDAO.updateEntryData(0);
        System.out.println("Daten von letzten Eintrag wurden aktualisiert");

    }


    // Frage : Macht es Sinn Daten zu löschen oder lasst man User einfach Eintrag Bearbeiten?

    // waits for event from Button "Löschen".
    /*public void eliminateLastEntry(int id){
        EntryDAO entryDAO = new EntryDAO();
        entryDAO.deleteEntry(id);
        /*int lastId = entryDAO.getLastId();
        System.out.println("Resultat von getLastId" + )
        System.out.println("Letzter Eintrag wurde gelöscht");

    }*/

}


