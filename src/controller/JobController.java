package controller;

import Exceptions.BlankInputException;
import Exceptions.InvalidInputException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import model.UniLink;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/*
* This controller class is used to handle the creation of new Job post.
* */
public class JobController implements Initializable {
    @FXML public Label createJobResult;
    @FXML private Parent root2;
    @FXML private Label fileAbsolutePath;
    @FXML private TextField jName;
    @FXML private TextField jDesc;
    @FXML private TextField jPropPrice;
    private String username;
    private static int jobCount = 1;
    UniLink obj;

    //This Method is to get Current Username.
    public void getUsername(UniLink Obj){
        this.obj = Obj;
        this.username = Obj.getCurrentUser();
    }

    // This method is to Go back to Main Window after BackToMainMenu is pushed.
    public void backToMainMenu(ActionEvent actionEvent) throws IOException {
        Stage s1 = (Stage)root2.getScene().getWindow();
        s1.close();
    }

    // This Method is to Upload Event Image.
    public void uploadImageButtonPushed(ActionEvent actionEvent) {
        FileChooser fc = new FileChooser();
        fc.getExtensionFilters().add(new FileChooser.ExtensionFilter("PNG Files", "*.png"));
        fc.getExtensionFilters().add(new FileChooser.ExtensionFilter("JPEG Files", "*.jpg"));
        File file1 = fc.showOpenDialog(null);
        if(file1!= null){
            String Pathname = file1.getName();
            fileAbsolutePath.setText(Pathname);
        }
        else{
            fileAbsolutePath.setText("Default.png");
        }
    }

    // This Method is to create Job on provided data.
    public void JobCreation(ActionEvent actionEvent) {
        String title = jName.getText();
        String description = jDesc.getText();
        String jPropPriceText = jPropPrice.getText();
        try {
            boolean result = obj.addJob(username, title, description, jPropPriceText, jobCount, fileAbsolutePath.getText());
            //System.out.println(result);
            if (result == true) {
                createJobResult.setText("Success...Job Post is created successfully");
                jobCount = jobCount + 1;
            } else {
                createJobResult.setText("Job Post Creation Failed");
            }
        } catch (InvalidInputException | BlankInputException e) {
            System.out.println("Exception handled while creating job");
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }


}
