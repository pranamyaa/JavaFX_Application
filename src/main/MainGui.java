package main;

import controller.WelcomeSceneController;
import hsql_db.ConnectionTest;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.*;

public class MainGui extends Application {

    public static void main(String[] args) {
        launch(args);
    }
    @Override
    public void start(Stage stage) throws Exception {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/Welcome_Scene.fxml"));
            Parent root = loader.load();
            WelcomeSceneController welcomeSceneController = loader.getController();
            UniLink object = new UniLink();
            welcomeSceneController.setUniLink(object);
            ConnectionTest conObj = new ConnectionTest();
            conObj.loadDatabase(object);
            Scene scene = new Scene(root);
            stage.setTitle("Main View Window");
            stage.setScene(scene);
            stage.show();
    }



}
