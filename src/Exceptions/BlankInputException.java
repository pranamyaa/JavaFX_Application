package Exceptions;

import javafx.scene.control.Alert;

public class BlankInputException extends Exception {
    public BlankInputException(String S) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Blank Input Exception");
        alert.setHeaderText("Exception Occured...!!");
        alert.setContentText(S);
        alert.showAndWait();
    }
}
