package com.srh.diet_tracker;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.atomic.AtomicReference;


public class ControllerEntry {

    Entry entry = new Entry();

    String caloriesInitialValue = Integer.toString(0);
    String sugarInitialValue = Integer.toString(0);
    LocalDate currentDate = LocalDate.now();

    public Label warningLabel = new Label();
    @FXML
    private RadioButton isMealRadioBtn = new RadioButton();
    @FXML
    private RadioButton isSportRadioBtn = new RadioButton();;
    @FXML
    private TextField caloriesTextField = new TextField(caloriesInitialValue);
    @FXML
    private TextField sugarTextField = new TextField(sugarInitialValue);
    @FXML
    private CheckBox selectActualTime = new CheckBox();
    @FXML
    private DatePicker datePicker = new DatePicker(currentDate);
    @FXML
    private Spinner<Integer> hoursSpinner = new Spinner<Integer>(0,24,7);
    @FXML
    private Spinner<Integer> minutesSpinner = new Spinner<Integer>(0,60,0);;
    @FXML
    private Spinner<Integer> secondsSpinner = new Spinner<Integer>(0,60,0);;
    @FXML
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

    // FRAGE : Muss das Attribut sein?
    private EntryDAO entryDAO;




    public void onIsMealRadioBtn(ActionEvent actionEvent) {
        entry.setSport(false);
        isSportRadioBtn.setSelected(false);
    }

    public void onIsSportRadioBtn(ActionEvent actionEvent) {
        entry.setSport(true);
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
        boolean isCaloriesEntryAdequate;
        boolean isSugarEntryAdequate;
        ControllerEntry controllerEntry = new ControllerEntry();

        try {
            double calories = Double.parseDouble(caloriesText);
            isCaloriesEntryAdequate = true;
            entry.setCalories(calories);

        } catch (NumberFormatException e) {
            isCaloriesEntryAdequate = false;
        }

        try {
            double sugar = Double.parseDouble(sugarText);
            isSugarEntryAdequate = true;
            entry.setSugar(sugar);

        } catch (NumberFormatException e) {
            isSugarEntryAdequate = false;

        }

        //Check and warn if calories and sugar entries are adequate.
        if (isSugarEntryAdequate == false && isCaloriesEntryAdequate == false) {
            warningLabel.setText("Bitte geben sie ein adequaten\nNumerischen Wert für\nKalorien und Zucker!\n(für Kommastelle '.').");
        } else if (!isCaloriesEntryAdequate) {
            warningLabel.setText("Bitte geben sie ein adequaten\nNumerischen Wert für \nKalorien! Falls sie ','\n(für Kommastelle '.')");
        } else if (isSugarEntryAdequate == false) {
            warningLabel.setText("Bitte geben sie ein adequaten\nNumerischen Wert für \nSugar! Falls sie ','\n(für Kommastelle '.')");
        }
        // if entry values adequate, then save all Entry values.
        else {
            //check if user wants to save actual time or other time.
            if (selectActualTime.isSelected()) {
                LocalDate date;
                date = LocalDate.now();
                LocalTime time;
                time = LocalTime.now();
                time.format(DateTimeFormatter.ofPattern("HH:mm:ss"));

                entry.setDay(date);
                entry.setTime(time);
            } else {

                int hours = hoursSpinner.getValue();
                int minutes = minutesSpinner.getValue();
                int seconds = secondsSpinner.getValue();
                LocalTime time = LocalTime.of(hours, minutes, seconds);

                entry.setTime(time);
                System.out.println("Eintrag vom: " + time);

            }

            EntryDAO entryDAO = new EntryDAO(entry);
            if (isNewEntry) {
                entryDAO.insertEntryData();
            }
            else {
                entryDAO.updateEntryData(entryDAO.getLastId());
            }
            warningLabel.setText("Eintrag gespeichert.\nSie können es bearbeiten\noder ein neuen speichern.");
            saveEntryBtn.setDisable(true);
            editLastEntryBtn.setDisable(false);
            newEntryBtn.setDisable(false);
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

    private boolean checkAndFillData() {
        String caloriesText;
        String sugarText;
        try{
            caloriesText = caloriesTextField.getText();
            sugarText = sugarTextField.getText();

        } catch (Exception e) {
            warningLabel.setText("Bitte geben sie ein adequaten\nNumerischen Wert für\nKalorien und Zucker!\nDarf nicht leer sein!");
            return false;
        }

        boolean isCaloriesEntryAdequate;
        boolean isSugarEntryAdequate;

        try {
            double calories = Double.parseDouble(caloriesText);
            isCaloriesEntryAdequate = true;
            entry.setCalories(calories);

        } catch (NumberFormatException e) {
            isCaloriesEntryAdequate = false;
        }

        try {
            double sugar = Double.parseDouble(sugarText);
            isSugarEntryAdequate = true;
            entry.setSugar(sugar);

        } catch (NumberFormatException e) {
            isSugarEntryAdequate = false;

        }

        //Check and warn if calories and sugar entries are adequate.
        if (isSugarEntryAdequate == false && isCaloriesEntryAdequate == false) {
            warningLabel.setText("Bitte geben sie ein adequaten\nNumerischen Wert für\nKalorien und Zucker!\n(für Kommastelle '.').");
            return false;
        } else if (!isCaloriesEntryAdequate) {
            warningLabel.setText("Bitte geben sie ein adequaten\nNumerischen Wert für \nKalorien! Falls sie ','\n(für Kommastelle '.')");
            return false;
        } else if (isSugarEntryAdequate == false) {
            warningLabel.setText("Bitte geben sie ein adequaten\nNumerischen Wert für \nSugar! Falls sie ','\n(für Kommastelle '.')");
            return false;
        }
        // if entry values adequate, then save all Entry values.
        else {
            //check if user wants to save actual time or other time.
            if (selectActualTime.isSelected()) {
                LocalDate date;
                date = LocalDate.now();
                LocalTime time;
                time = LocalTime.now();
                time.format(DateTimeFormatter.ofPattern("HH:mm:ss"));

                entry.setDay(date);
                entry.setTime(time);
            } else {
                int hours = hoursSpinner.getValue();
                int minutes = minutesSpinner.getValue();
                int seconds = secondsSpinner.getValue();
                LocalTime time = LocalTime.of(hours, minutes, seconds);

                entry.setTime(time);
                System.out.println("Eintrag vom: " + time);

                return true;
            }
        }
        return false;
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
