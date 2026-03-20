package com.srh.diet_tracker;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class App extends Application {
    @Override
    public void start(Stage stage) throws IOException {


        DbManager dbManager = new DbManager();
        dbManager.setDataBase();

        ControllerUser controllerUser = new ControllerUser();
        controllerUser.saveUser(38,90, 105,true,true);

       /*UserDAO userDAO = new UserDAO();
       userDAO.insertUserData(38,90,true,true);

       EntryDAO entryDAO = new EntryDAO();
       entryDAO.insertEntryData(false,200,5);

       DayReviewDAO dayReviewDAO = new DayReviewDAO();
       dayReviewDAO.insertDayReview(2000,100,30,100);*/



        /*FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("user_profile.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1000, 500);
        stage.setTitle("Erstellung des User Profiles");
        stage.setScene(scene);
        stage.show();*/
    }
}
