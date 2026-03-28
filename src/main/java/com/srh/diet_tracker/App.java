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

        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("add_calories_sugar.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1000, 500);
        stage.setTitle("Essen oder Sport Eintrag eingeben");
        stage.setScene(scene);
        stage.show();

        /*XMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("user_profile.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1000, 500);
        stage.setTitle("Erstellung des User Profiles");
        stage.setScene(scene);
        stage.show();

        ControllerGraph controllerGraph = new ControllerGraph();
        controllerGraph.representData();
        System.out.println();*/

    }
}

        /*FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("user_profile.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1000, 500);
        stage.setTitle("Erstellung des User Profiles");
        stage.setScene(scene);
        stage.show();*/

//        ControllerUser controllerUser = new ControllerUser();
//        controllerUser.eliminateUserData();

        //Insert new data into entry table in DB from Controller->DAO->DB
//            ControllerEntry controllerEntry = new ControllerEntry();
//            controllerEntry.saveEntry(false, 1000, 50);
//            controllerEntry.saveEntry(true, 500, 20);
//            controllerEntry.saveEntry(false, 300, 10);

//        ControllerEntry controllerEntry = new ControllerEntry();
//        controllerEntry.eliminateLastEntry();
//
//        controllerUser.eliminateUserData();

        //Eliminate row of table with certain id from table user. Path: DAO->DB


        //Save user data into user table in DB from Controller->DAO->DB
//        controllerUser.saveUser(166,38,105, true,true);

        //Update User Data
//        controllerUser.updateUser(166,40,70,true,false);

        //ControllerEntry controllerEntry = new ControllerEntry();
        //Update Entry Data
//        controllerEntry.updateLastEntry(false, 1000, 1000000);

        //ControllerDayReview Testing
//        ControllerDayReview controllerDayReview = new ControllerDayReview();
        /*controllerDayReview.showCaloriesSumByDate();
        controllerDayReview.showSugarSumByDate();
        controllerDayReview.showTotalCaloriesSum();
        controllerDayReview.showTotalSugarSum();
        controllerDayReview.showCaloriesMean();
        controllerDayReview.showSugarMean();
        controllerDayReview.representData()
        controllerDayReview.representData();*/




       // Insert new data into user table in DB from DAO->DB
       /*UserDAO userDAO = new UserDAO();
       userDAO.insertUserData(38,90,true,true);

       // Insert new data into user entry in DB from DAO->DB
       EntryDAO entryDAO = new EntryDAO();
       entryDAO.insertEntryData(false,200,5);*/

        // FXMLLoader EXAMPLE


