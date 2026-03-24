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
//        controllerUser.eliminateUserData();

        //Insert new data into entry table in DB from Controller->DAO->DB
        ControllerEntry controllerEntry = new ControllerEntry();
//        controllerEntry.saveEntry(false, 500, 10);
//        controllerEntry.saveEntry(true, 800, 20);

//        ControllerEntry controllerEntry = new ControllerEntry();
        controllerEntry.eliminateLastEntry();

        controllerUser.eliminateUserData();

        //Eliminate row of table with certain id from table user. Path: DAO->DB


        //Save user data into user table in DB from Controller->DAO->DB
//        controllerUser.saveUser(38,90, 105,true,true);



       // Insert new data into user table in DB from DAO->DB
       /*UserDAO userDAO = new UserDAO();
       userDAO.insertUserData(38,90,true,true);

       // Insert new data into user entry in DB from DAO->DB
       EntryDAO entryDAO = new EntryDAO();
       entryDAO.insertEntryData(false,200,5);

        // FXMLLoader EXAMPLE
        /*FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("user_profile.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1000, 500);
        stage.setTitle("Erstellung des User Profiles");
        stage.setScene(scene);
        stage.show();*/
    }
}
