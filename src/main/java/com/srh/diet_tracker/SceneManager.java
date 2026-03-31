package com.srh.diet_tracker;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class SceneManager {

    private SceneManager(){};
    private static SceneManager instance;

    private String[] sceneFiles = {
           "homepage.fxml",
            "user_profile.fxml"

    };

    public static SceneManager getInstance(){
        if(instance == null){
            instance = new SceneManager();
        }

        return instance;
    }

    public void loadScene( SceneType type, String title, int width, int height){

        String path = sceneFiles[ type.ordinal()];
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(path));

        Scene scene = null;
        try {
            scene = new Scene(fxmlLoader.load(), width, height);
            Stage stage = App.getCurrentStage();
            stage.setTitle(title);
            stage.setScene(scene);
            stage.show();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }




    }



}
