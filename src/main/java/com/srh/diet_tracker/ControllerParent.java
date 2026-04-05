package com.srh.diet_tracker;

import javafx.scene.control.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;


public class ControllerParent {

public  ControllerParent(){}

    public boolean checkTextFieldData(DatePicker datePicker, TextField caloriesTextField, TextField sugarTextField, Label warningLabel) {
        String caloriesText;
        String sugarText;
        LocalDate currentDate = LocalDate.now();

        if (datePicker.getValue().isAfter(currentDate)){
            warningLabel.setText("Datum darf nicht in\nder Zukunft sein!");
            return false;
        }

        try {
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

        } catch (NumberFormatException e) {
            isCaloriesEntryAdequate = false;
        }

        try {
            double sugar = Double.parseDouble(sugarText);
            isSugarEntryAdequate = true;

        } catch (NumberFormatException e) {
            isSugarEntryAdequate = false;

        }

        //Check and warn if calories and sugar entries are adequate.
        if (isSugarEntryAdequate == false && isCaloriesEntryAdequate == false) {
            warningLabel.setText("Warnung: geben sie ein adequaten\nNumerischen Wert für\nKalorien und Zucker!\n(für Kommastelle '.').");
            return false;
        } else if (!isCaloriesEntryAdequate) {
            warningLabel.setText("Warnung: geben sie ein adequaten\nNumerischen Wert für \nKalorien! Falls sie ','\n(für Kommastelle '.')");
            return false;
        } else if (isSugarEntryAdequate == false) {
            warningLabel.setText("Warnung: geben sie ein adequaten\nNumerischen Wert für \nSugar! Falls sie ','\n(für Kommastelle '.')");
            return false;
        }
        return true;
    }

    public Entry returnEntryFromFields(boolean validTextFieldDate, String activity, TextField caloriesTextField, TextField sugarTextField,
                                         CheckBox selectActualTime, DatePicker datePicker, Spinner<Integer> hoursSpinner, Spinner<Integer> minutesSpinner,
                                         Spinner<Integer> secondsSpinner, Label warningLabel) {
        String caloriesText;
        String sugarText;
        LocalTime time;
        LocalDate date;
        Entry entry = new Entry();

        if (validTextFieldDate) {

            entry.setActivity(activity);

            caloriesText = caloriesTextField.getText();
            sugarText = sugarTextField.getText();


            double calories = Double.parseDouble(caloriesText);
            entry.setCalories(Math.abs(calories));

            double sugar = Double.parseDouble(sugarText);
            entry.setSugar(sugar);

            if (selectActualTime==null || !selectActualTime.isSelected())
            {
                date = datePicker.getValue();
                int hours = hoursSpinner.getValue();
                int minutes = minutesSpinner.getValue();
                int seconds = secondsSpinner.getValue();
                time = LocalTime.of(hours, minutes, seconds);
                entry.setDay(date);
                entry.setTime(time);
            }
            else if(selectActualTime.isSelected()) {
                date = LocalDate.now();
                time = LocalTime.now();
                time.format(DateTimeFormatter.ofPattern("HH:mm:ss"));
                entry.setDay(date);
                entry.setTime(time);
            }
            return entry;
        }
        return null;
    }
}



