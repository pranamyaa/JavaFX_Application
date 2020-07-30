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
* This controller class os to handle the creation of new sale posts.
* */
public class SaleController implements Initializable {
    @FXML public Label createSaleResult;
    @FXML private Parent root2;
    @FXML private Label fileAbsolutePath;
    @FXML private TextField sName;
    @FXML private TextField sDesc;
    @FXML private TextField sAskingPrice;
    @FXML private TextField sMinRaise;
    private static int saleCount = 1;
    private String username;
    UniLink obj;

    public void getUsername(UniLink obj){
        this.obj = obj;
        this.username = obj.getCurrentUser();
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
        }
        else{
            fileAbsolutePath.setText("Default.png");
        }
    }

    // This Method is to create Sale Post on the provided data.
    public void saleCreation(ActionEvent actionEvent) {
        String title = sName.getText();
        String description = sDesc.getText();
        String sAskingPriceText = sAskingPrice.getText();
        String sMinRaiseText = sMinRaise.getText();
        try {
            boolean result = obj.addSale(username, title, description, sAskingPriceText, sMinRaiseText, saleCount, fileAbsolutePath.getText());
            if (result == true) {
                createSaleResult.setText("Success...Sale Post is created successfully");
                saleCount = saleCount + 1;
            } else {
                createSaleResult.setText("Sale Post Creation Failed");
            }
        }catch (InvalidInputException | BlankInputException e) {
            System.out.println("Exception handled in case of Sale Creation");
        }
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }


}
