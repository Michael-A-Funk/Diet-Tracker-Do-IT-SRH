package com.srh.diet_tracker;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;


public class ControllerEntry {

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
    private Spinner hoursPicker;
    @FXML
    private Spinner minutesPicker;
    @FXML
    private Spinner secondsPicker;
    @FXML
    private Button saveEntryBtn;
    @FXML
    private Button editLastEntryBtn;
    @FXML
    private Button newEntryBtn;



    // FRAGE : Muss das Attribut sein?
    private EntryDAO entryDAO;

    public ControllerEntry() {
        Entry entry = new Entry();
    }

    public void onIsMealRadioBtn(ActionEvent actionEvent) {
    }

    public void onIsSportRadioBtn(ActionEvent actionEvent) {
    }

    public void onSelectActualTime(ActionEvent actionEvent) {
    }

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


