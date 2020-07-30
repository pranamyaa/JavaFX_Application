package Exceptions;

import javafx.scene.control.Alert;

public class PostClosedException extends Throwable {
    public PostClosedException(String s) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Post Already Closed Exception");
        alert.setHeaderText("Exception Occured..!");
        alert.setContentText(s);
        alert.showAndWait();

    }
}
