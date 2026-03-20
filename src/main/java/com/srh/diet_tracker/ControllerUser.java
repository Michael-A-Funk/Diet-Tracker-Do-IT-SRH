package com.srh.diet_tracker;

public class ControllerUser {

    private UserDAO userDAO = new UserDAO();

    public ControllerUser(){}

    public void saveUser(int age, int height, int weight, boolean isMale, boolean hasDiabetes){
        User user = new User (age,height,weight,isMale,hasDiabetes); //this line wil be replaced with the values
                                                                     // that come from the TextFields
        userDAO.setUser(user);
        userDAO.insertUserData();
    }
}
