package controller;

import Exceptions.InvalidInputException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.EventPost;
import model.JobPost;
import model.Post;
import model.SalePost;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Optional;


/*
* This controller class handles the Edition of a particular post after owner choose to edit that post.
* */

public class EditPostController {
    private Post post;
    @FXML private Parent root;
    @FXML private Label label1;
    @FXML private TextField text1;
    @FXML private Label label2;
    @FXML private TextField text2;
    @FXML private Label label3;
    @FXML private TextField text3;
    @FXML private Label label4;
    @FXML private TextField text4;
    @FXML private Label label5;
    @FXML private TextField text5;

    public void setPost(Post post){
        this.post = post;
    }

    // This method is to set visibility of attributes on the Edit Post scene.
    public void handleEdit() {
        label1.setText("Title: ");
        label2.setText("Description: ");
        if(post.getId().startsWith("EVE")){
            label3.setText("Venue: ");
            label4.setText("Date: ");
            label5.setText("Capacity: ");
        }
        if(post.getId().startsWith("SAL")){
            label3.setText("Asking Price: ");
            label4.setText("Minimum Raise: ");
            label5.setVisible(false);
            text5.setVisible(false);
        }
        if(post.getId().startsWith("JOB")){
            label3.setText("Proposed Price: ");
            label4.setVisible(false);
            text4.setVisible(false);
            label5.setVisible(false);
            text5.setVisible(false);
        }
    }

    // This method is to update the post with edited new attributes with checks.
    public void submitButtonPushed(ActionEvent actionEvent) throws InvalidInputException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setContentText("Once Submitted User cant go back to original details. Are you sure with this?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){
                if (!text1.getText().isEmpty()) {
                    post.setTitle(text1.getText());
                }
                if (!text2.getText().isEmpty()) {
                    post.setDescription(text2.getText());
                }
                if (post.getId().startsWith("EVE")) {
                    if (!text3.getText().isEmpty()) {
                        ((EventPost) post).setVenue(text3.getText());
                    }
                    if (!text4.getText().isEmpty()) {
                        try{
                            SimpleDateFormat format = new SimpleDateFormat("dd/MM/YYYY");
                            format.parse(text4.getText());
                            ((EventPost) post).setDate(text4.getText());
                        }catch (ParseException e){
                            Alert alert2 = new Alert(Alert.AlertType.ERROR);
                            alert2.setHeaderText("Exception Occured..");
                            alert2.setContentText("Wrong Date Format. Date Format should be in 'DD/MM/YYYY'");
                            alert2.showAndWait();
                            return;
                        }

                    }
                    if (!text5.getText().isEmpty()) {
                        String capacity = text5.getText();
                        for(int i =0; i< capacity.length(); i++){
                            if(!(Character.isDigit(capacity.charAt(i)))){
                                throw new InvalidInputException("Capacity must be an Integer value");
                            }
                        }
                        ((EventPost) post).setCapacity(Integer.parseInt(capacity));
                    }
                }
                if (post.getId().startsWith("SAL")) {
                    if (!text3.getText().isEmpty()) {
                        String askingPrice = text3.getText();
                        for(int i =0; i< askingPrice.length(); i++){
                            if((Character.isLetter(askingPrice.charAt(i)))){
                                throw new InvalidInputException("Asking Price must be in digits");
                            }
                        }
                        ((SalePost) post).setAskingPrice(Double.parseDouble(askingPrice));
                    }
                    if (!text4.getText().isEmpty()) {
                        String minRaise = text4.getText();
                        for(int i =0; i< minRaise.length(); i++){
                            if((Character.isLetter(minRaise.charAt(i)))){
                                throw new InvalidInputException("Minimum Raise must be in digits");
                            }
                        }
                        ((SalePost) post).setMinRaise(Double.parseDouble(minRaise));
                    }
                }
                if (post.getId().startsWith("JOB")) {
                    if (!text3.getText().isEmpty()) {
                        String propPrice = text3.getText();
                        for(int i =0; i< propPrice.length(); i++){
                            if((Character.isLetter(propPrice.charAt(i)))){
                                throw new InvalidInputException("Proposed Price must be in digits");
                            }
                        }
                        ((JobPost) post).setPropPrice(Double.parseDouble(propPrice));
                    }
                }
                Alert alert1 = new Alert(Alert.AlertType.INFORMATION);
                alert1.setTitle("Successful..!!");
                alert1.setContentText("Post is Edited Successfully. Please Go Back and Load the post again");
                alert1.showAndWait();
                goBacktoDetails();
        }
    }

    // This method is to go back to previous window after editing is done.
    @FXML private void goBacktoDetails() {
        Stage stage1 = (Stage) root.getScene().getWindow();
        stage1.close();
    }
}
