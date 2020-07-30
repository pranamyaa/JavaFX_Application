package controller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import model.*;

import java.io.IOException;

/*
* This Class represents a Cell in the listView and its attributes.
* */
public class StudentListViewCell extends ListCell<Post> {
    @FXML private HBox hMain;
    @FXML private ImageView image;
    @FXML private Label label1;
    @FXML private Label label2 ;
    @FXML private Label label3 ;
    @FXML private Label label4 ;
    @FXML private Label label5 ;
    @FXML private Label label6 ;
    @FXML private Label dlabel;
    @FXML private Button reply ;
    @FXML private Button moreDetails;
    public UniLink uniLink;
    FXMLLoader loader;

    // This method is to set the UniLink object
    StudentListViewCell(UniLink obj){
            uniLink = obj;
    }

    // This is main method which sets the value for all labels in the post cell and set action events for the buttons.
    protected void updateItem(Post post, boolean empty) {
        super.updateItem(post, empty);
        if (empty){
            setGraphic(null);
        }
        else {
            if(loader == null) {
                try {
                    loader = new FXMLLoader(getClass().getResource("/view/ListViewCell.fxml"));
                    loader.setController(this);
                    loader.load();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            /*
            *  if the PostID and Current Logged in UserID matches, we are disabling the Reply button for them
            *  and Enabling the more_details button only to them.
            * */
            if(post.getCreatorID().equals(uniLink.getCurrentUser())) {
                moreDetails.setDisable(false);
            } else{ moreDetails.setDisable(true);}
            if(post.getCreatorID().equals(uniLink.getCurrentUser())){
                reply.setDisable(true);
            }else{ reply.setDisable(false);}
            moreDetails.setOnAction(new EventHandler<ActionEvent>() {
                @Override public void handle(ActionEvent actionEvent) {
                    try {
                        handleDetails(post);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });
            reply.setOnAction(new EventHandler<ActionEvent>() {
                @Override public void handle(ActionEvent actionEvent) {
                    try {
                        if (post.getId().startsWith("EVE")) {
                            handleEventReply(post);
                        } else if (post.getId().startsWith("SAL")) {
                            handleSaleReply(post);
                        } else if (post.getId().startsWith("JOB")) {
                            handleJobReply(post);
                        }
                    }
                    catch (Exception e){
                        e.printStackTrace();
                    }
                }
            });
            // This method is written to set all the labels for a particular post cell.
            setDetails(post);
            // This is the code to give background color to each type of post.
            if(post.getId().startsWith("EVE")){
                hMain.setBackground(new Background(new BackgroundFill(Color.LINEN,CornerRadii.EMPTY, Insets.EMPTY)));
            }else if(post.getId().startsWith("SAL")){
                hMain.setBackground(new Background(new BackgroundFill(Color.rgb(229,255,204),CornerRadii.EMPTY, Insets.EMPTY)));
            }else{
                hMain.setBackground(new Background(new BackgroundFill(Color.rgb(255, 255, 204),CornerRadii.EMPTY, Insets.EMPTY)));
            }
            setGraphic(hMain);
        }
    }

    // This method is to set all the labels for a particular post cell.
    private void setDetails(Post post){
        label1.setText("Title: "+post.getTitle());
        label2.setText("Status: "+post.getStatus());
        label3.setText("Creator: "+post.getCreatorID());
        dlabel.setText("Details: "+post.getDescription());
        if(post.getId().startsWith("EVE")){
            label4.setText("Interested: "+((EventPost)post).getAttendeeCount()+"/"+((EventPost)post).getCapacity());
            label5.setText("Venue: "+((EventPost)post).getVenue());
            label6.setText("Date: "+((EventPost)post).getDate());
            reply.setText("Join");
        }else if(post.getId().startsWith("SAL")){
            label4.setText("Min Raise: $"+((SalePost)post).getMinRaise());
            label5.setText("Highest Offer: $"+((SalePost)post).getHighestPrice());
            label6.setText("");
            reply.setText("reply");
        }else if(post.getId().startsWith("JOB")){
            label4.setText("Pay: $"+((JobPost)post).getPropPrice());
            label5.setText("Lowest Offer: $"+((JobPost)post).getLowestOffer());
            label6.setText("");
            reply.setText("reply");
        }
        String photo = post.getPhoto();
        image.setImage(new Image("/Images/"+photo));
    }
    // This method is to create new scene when user wants to see more details of the post.
    private void handleDetails(Post post) throws IOException {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/MoreDetails.fxml"));
            Parent moreDetailParent = fxmlLoader.load();
            MoreDetailsController moreDetailsController = fxmlLoader.getController();
            moreDetailsController.getCurrentPost(post);
            moreDetailsController.getCurrentUniLink(uniLink);
            moreDetailsController.handleDetails();
            Scene newScene = new Scene(moreDetailParent);
            Stage stage1 = new Stage();
            stage1.setTitle("More Details of a Post");
            stage1.setScene(newScene);
            stage1.show();
    }
    // This method is to popup JobReply screen
    private void handleJobReply(Post post) throws IOException {
        FXMLLoader mlloader = new FXMLLoader(getClass().getResource("/view/ReplyJobScene.fxml"));
        Parent NextSceneParent = mlloader.load();
        ReplyController replyController = mlloader.getController();
        replyController.getCurrentPost(post);
        replyController.setUniLink(uniLink);
        Scene newScene = new Scene(NextSceneParent);
        Stage stage1 = new Stage();
        stage1.setTitle("Job Reply");
        stage1.setScene(newScene);
        stage1.show();
    }
    // This method is to popup the SaleReply screen
    private void handleSaleReply(Post post) throws IOException {
        FXMLLoader mlloader = new FXMLLoader(getClass().getResource("/view/ReplySaleScene.fxml"));
        Parent NextSceneParent = mlloader.load();
        ReplyController replyController = mlloader.getController();
        replyController.getCurrentPost(post);
        replyController.setUniLink(uniLink);
        Scene newScene = new Scene(NextSceneParent);
        Stage stage1 = new Stage();
        stage1.setTitle("Sale Reply");
        stage1.setScene(newScene);
        stage1.show();
    }
    // This method is to popup the EventReply screen
    private void handleEventReply(Post post) throws IOException {

        FXMLLoader mlloader = new FXMLLoader(getClass().getResource("/view/ReplyEventScene.fxml"));
        Parent NextSceneParent = mlloader.load();
        ReplyController replyController = mlloader.getController();
        replyController.getCurrentPost(post);
        replyController.setUniLink(uniLink);
        Scene newScene = new Scene(NextSceneParent);
        Stage stage1 = new Stage();
        stage1.setTitle("Event Reply");
        stage1.setScene(newScene);
        stage1.show();
    }

}
