package com.srh.diet_tracker;

public class ControllerUser {

    private UserDAO userDAO;

    public ControllerUser(){}

    // waits for event from Button "Bestätigen"
    public void saveUser(int age, int height, int weight, boolean isMale, boolean hasDiabetes){
        User user = new User (age,height,weight,isMale,hasDiabetes); //this line wil be replaced with the values
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
}
