package controller;

import Exceptions.InvalidInputException;
import javafx.event.ActionEvent;
import javafx.fxml.*;
import javafx.scene.*;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.UniLink;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class WelcomeSceneController implements Initializable {

   @FXML private TextField UsernameTextField;
   private UniLink obj;
    public void setUniLink (UniLink object){
        this.obj = object;
    }

   // This Method is to go to the Main Window after NextSceneButtonPushed is Pushed.
    public void NextSceneButtonPushed(ActionEvent actionEvent) throws IOException, InvalidInputException {
        try {
            String userID = (String) UsernameTextField.getText();
            obj.validateUser(userID);
            obj.setCurrentUser(userID);
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/Main_Scene.fxml"));
            Parent NextSceneParent = (Parent) loader.load();
            MainSceneController mainViewController = loader.getController();
            Scene newScene = new Scene(NextSceneParent);
            Stage stage1 = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            stage1.setTitle("Main Window");
            stage1.setScene(newScene);
            mainViewController.displayCurrentUser(obj);
            stage1.show();
        }catch (InvalidInputException e){
            System.out.println("Exception handled");
        }
     }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
