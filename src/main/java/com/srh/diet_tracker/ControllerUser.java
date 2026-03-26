package com.srh.diet_tracker;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class ControllerUser {

    @FXML
    private Spinner<Integer> spinnerHeight;
    @FXML
    private Spinner<Integer> spinnerAge;
    @FXML
    private Spinner<Integer> spinnerWeight;
    @FXML
    private CheckBox hasDiabetes;
    @FXML
    private MenuButton gender;
    @FXML
    private Button save_user;

    // FRAGE : Muss das Attribut sein?
    private UserDAO userDAO;
    private User user = new User(160,20,60,false,false);
    private boolean diabetesButtonChecked;
    ToggleGroup group = new ToggleGroup();
    RadioButton femaleRadioBtn= new RadioButton("Weiblich");
    RadioButton maleRadioBtn = new RadioButton("Männlich");

    public ControllerUser(){
        femaleRadioBtn.setToggleGroup(group);
        femaleRadioBtn.setSelected(true);
        maleRadioBtn.setToggleGroup(group);
        System.out.println("Fick dich!");
    }


    // waits for event from Button "Bestätigen"
    public void saveUser(){ //this line wil be replaced with the values
                                                                     // that come from the TextFields
        UserDAO userDAO = new UserDAO(user);
        userDAO.insertUserData();
        System.out.println("Neuer User wurde gesetzt");
    }

    // waits for event from Button "Bestätigen" oder "Bearbeiten"
    public void updateUser(int height,int weight, int age, boolean isMale, boolean hasDiabetes){
        User user = new User (height,weight,age,isMale,hasDiabetes);
        UserDAO userDAO = new UserDAO(user);
        userDAO.updateUserData();
        System.out.println("User Daten wurden aktualisiert");

    }

    // Frage : Macht es Sinn Daten zu löschen oder lasst man User einfach User Daten bearbeiten?
    // waits for event from Button "Löschen"
    public void eliminateUserData(){
        UserDAO userDAO = new UserDAO();
        userDAO.deleteEntryData();
    }

    public void onDiabetes(ActionEvent actionEvent) {
        boolean currentValue = user.hasDiabetes();
        user.setHasDiabetes(!currentValue);
    }

    public void onSave(ActionEvent actionEvent) {
        user.setAge(spinnerAge.getValue());
        user.setHeight(spinnerHeight.getValue());
        user.setWeight(spinnerWeight.getValue());
        UserDAO userDAO = new UserDAO(user);
        userDAO.insertUserData();
    }

    public void setRadioBtnFemale(ActionEvent actionEvent) {user.setMale(false);
    }

    public void setRadioBtnMale(ActionEvent actionEvent) {user.setMale(true);}

    //For Actions after selecting a gender of Gender Menu
    /*
    */
}
