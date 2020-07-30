package controller;


import Exceptions.BlankInputException;
import Exceptions.InvalidInputException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import model.UniLink;


import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;

/*
*  This controller class is to handle the creation of new event posts.
* */
public class EventController implements Initializable {
    @FXML public Label createEventResult;
    @FXML private Parent root2;
    @FXML private Label fileAbsolutePath;
    @FXML private TextField eName;
    @FXML private TextField eDesc;
    @FXML private TextField eVenue;
    @FXML private TextField eDate;
    @FXML private TextField eCapacity;
    static private int eventCount = 1;
    private String username;
    UniLink obj;

    public void getUsername(UniLink obj){
        this.obj = obj;
        this.username = this.obj.getCurrentUser();
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
        File File1 = fc.showOpenDialog(null);
        if(File1!= null){
            String Pathname = File1.getName();
            fileAbsolutePath.setText(Pathname);
        }else{
            fileAbsolutePath.setText("Default.png");
        }
    }

    // This Method is to Create Event on provided data.
    public void eventCreation(ActionEvent actionEvent) throws InvalidInputException, ParseException {
        String title = eName.getText();
        String description = eDesc.getText();
        String venue = eVenue.getText();
        String date = eDate.getText();
        String capacity = eCapacity.getText();
        try {
            boolean result = obj.addEvent(username, title, description, venue, date, capacity, eventCount, fileAbsolutePath.getText());
            if (result == true) {
                createEventResult.setText("Success...Event Post is created successfully");
                eventCount = eventCount + 1;
            } else {
                createEventResult.setText("Failed to create this Event Post");
            }
        }catch (InvalidInputException | BlankInputException e){
             // Exception handled by throwing new exception.
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }


}
