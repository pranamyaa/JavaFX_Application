package Exceptions;


import javafx.scene.control.Alert;

public class InvalidInputException extends Exception {
    public InvalidInputException(String s) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Invalid Input Exception");
        alert.setHeaderText("Exception Occured");
        alert.setContentText(s);
        alert.showAndWait();
        System.out.println("");
    }

}
