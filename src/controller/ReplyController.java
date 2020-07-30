package controller;

import Exceptions.InvalidInputException;
import Exceptions.PostClosedException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import model.Post;
import model.UniLink;

import java.io.IOException;

/*
* This Controller class used to handle replies for event, sale, and job posts.
* */
public class ReplyController {

    @FXML public TextField jobOffer;
    @FXML public Label eventResult;
    @FXML public Label jobResult;
    @FXML public Label saleResult;
    private Post post;
    private UniLink uniLink;
    @FXML TextField offer;

    // This method is to set UniLink object for local use.
    public void setUniLink(UniLink Obj) {
        this.uniLink = Obj;
    }

    // This method is to get current Post on which the user replied.
    public void getCurrentPost(Post post){
        this.post = post;
    }

    // This method is to call handleReply method of the events
    public void joinEventButtonPushed(ActionEvent actionEvent) throws IOException {
        String result = null;
        try {
            result = uniLink.respondEvent(post, 1);
            eventResult.setText(result);
        } catch (PostClosedException | InvalidInputException e) {
            System.out.println("Exception handled");
        }
    }

    // This method is to call handleReply method of the sale.
    public void replySaleButtonPushed(ActionEvent actionEvent) {
        String Value = offer.getText();
        String result = null;
        try {
            result = uniLink.respondSale(post, Value);
            saleResult.setText(result);
        } catch (InvalidInputException | PostClosedException e) {
            System.out.println("exception handled");
        }


    }

    // This method is to call handleReply method of the job
    public void replyJobButtonPushed(ActionEvent actionEvent) {
        String value = jobOffer.getText();
        String result = null;
        try {
            result = uniLink.respondJob(post,value);
            jobResult.setText(result);
        } catch (PostClosedException | InvalidInputException e) {
            System.out.println("Exception handled");
        }

    }
    
}
