package com.srh.diet_tracker;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.atomic.AtomicReference;


public class ControllerEntry extends ControllerParent {

    Entry entry;

    LocalDate currentDate = LocalDate.now();
    private boolean isSport;

    public Label warningLabel = new Label();
    @FXML
    private RadioButton isMealRadioBtn = new RadioButton();
    @FXML
    private RadioButton isSportRadioBtn = new RadioButton();;
    @FXML
    private TextField caloriesTextField = new TextField("0");
    @FXML
    private TextField sugarTextField = new TextField("0");
    @FXML
    private CheckBox selectActualTime = new CheckBox();
    @FXML
    private DatePicker datePicker = new DatePicker(currentDate);
    @FXML
    private Spinner<Integer> hoursSpinner = new Spinner<Integer>(0,23,7);
    @FXML
    private Spinner<Integer> minutesSpinner = new Spinner<Integer>(0,59,0);
    @FXML
    private Spinner<Integer> secondsSpinner = new Spinner<Integer>(0,59,0);    @FXML
    private Button saveEntryBtn;
    @FXML
    private Button editLastEntryBtn;
    @FXML
    private Button newEntryBtn;

    private boolean isNewEntry;

    public ControllerEntry() {
        isMealRadioBtn.setSelected(true);
        isSportRadioBtn.setSelected(false);
        selectActualTime.setSelected(true);
        this.isNewEntry = true;
    }

    public void initialize() {
        caloriesTextField.setText("0");
        sugarTextField.setText("0");
    }

    // FRAGE : Muss das Attribut sein?
    private EntryDAO entryDAO;




    public void onIsMealRadioBtn(ActionEvent actionEvent) {
        isSport = false;
        entry.setSport(false);
        isSportRadioBtn.setSelected(false);
    }

    public void onIsSportRadioBtn(ActionEvent actionEvent) {
        isSport = true;
        isMealRadioBtn.setSelected(false);
    }

    public void onSelectActualTime(ActionEvent actionEvent) {
        if (selectActualTime.isSelected()) {
            datePicker.setDisable(true);
            hoursSpinner.setDisable(true);
            minutesSpinner.setDisable(true);
            secondsSpinner.setDisable(true);
        } else {
            datePicker.setDisable(false);
            datePicker.setValue(LocalDate.now());
            hoursSpinner.setDisable(false);
            minutesSpinner.setDisable(false);
            secondsSpinner.setDisable(false);

        }
    }

    public void onDatePicker(ActionEvent actionEvent) {
        LocalDate date = datePicker.getValue();
        entry.setDay(date);
    }

    public void onSaveEntryBtn(ActionEvent actionEvent) {
        String caloriesText = caloriesTextField.getText();
        String sugarText = sugarTextField.getText();

        ControllerEntry controllerEntry = new ControllerEntry();

        Entry entry = returnEntryFromFields(controllerEntry.checkTextFieldData(datePicker,caloriesTextField,sugarTextField,warningLabel),
                isSport,caloriesTextField,sugarTextField,selectActualTime,datePicker,
                hoursSpinner,minutesSpinner,secondsSpinner,warningLabel);

        EntryDAO entryDAO = new EntryDAO(entry);
        try {
            if (isNewEntry) {
                entryDAO.insertEntryData();
            } else {
                entryDAO.updateEntryData(entryDAO.getLastId());
            }
            warningLabel.setText("Eintrag gespeichert.\nSie können es bearbeiten\noder ein neuen speichern.");
            saveEntryBtn.setDisable(true);
            editLastEntryBtn.setDisable(false);
            newEntryBtn.setDisable(false);
        } catch (Exception e) {
            System.err.println("User hat in Textfelder ungültige Werte eingegeben und es wurde in Label gemeldet.");
        }
    }

    public void onEditLastEntryBtn(ActionEvent actionEvent) {

        EntryDAO entryDAO = new EntryDAO();
        entry = entryDAO.getLastEntry();

        //Setting all fields to values of last Entry
        // Activity: Meal or Sport
        if (entry.isSport()) {
            isSportRadioBtn.setSelected(true);
            isMealRadioBtn.setSelected(false);
        } else {
            isSportRadioBtn.setSelected(false);
            isMealRadioBtn.setSelected(true);
        }
        // Textfields calories and sugar
        String calories = Double.toString(entry.getCalories());
        String sugar = Double.toString(entry.getSugar());

        caloriesTextField.setText(calories);

        sugarTextField.setText(sugar);

        //Date and Time fields
        selectActualTime.setSelected(false);
        datePicker.setDisable(false);
        hoursSpinner.setDisable(false);
        minutesSpinner.setDisable(false);
        secondsSpinner.setDisable(false);


        datePicker.setValue(entry.getDay());
        int hour = entry.getTime().getHour();
        int minutes = entry.getTime().getMinute();
        int seconds = entry.getTime().getSecond();

        hoursSpinner.getValueFactory().setValue(hour);
        minutesSpinner.getValueFactory().setValue(minutes);
        secondsSpinner.getValueFactory().setValue(seconds);

        isNewEntry = false;
        saveEntryBtn.setDisable(false);

    }

    public void onNewEntryBtn(ActionEvent actionEvent) {
        saveEntryBtn.setDisable(false);
        isSportRadioBtn.setSelected(false);
        isMealRadioBtn.setSelected(true);
        caloriesTextField.setText("");
        sugarTextField.setText("");
        selectActualTime.setSelected(true);
        hoursSpinner.getValueFactory().setValue(7);
        minutesSpinner.getValueFactory().setValue(0);
        secondsSpinner.getValueFactory().setValue(0);
        datePicker.setValue(LocalDate.now());
        datePicker.setDisable(true);
        hoursSpinner.setDisable(true);
        minutesSpinner.setDisable(true);
        secondsSpinner.setDisable(true);

        isNewEntry = true;
    }


    //Do
    public LocalTime parseTime(LocalTime time) {
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
