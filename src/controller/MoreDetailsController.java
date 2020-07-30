package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import model.*;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;


/*
* This is a controller class to handle the moreDetails button action for posts in a listView.
* */
public class MoreDetailsController implements Initializable {

    private Post post;
    private UniLink uniLink;
    @FXML private Label labelMain;
    @FXML private Label labelTitle;
    @FXML private Label labelDesc;
    @FXML private Label labelInfo1;
    @FXML private Label labelInfo2;
    @FXML private Label labelInfo3;
    @FXML private Label labelInfo4;
    @FXML private Label labelStatus;
    @FXML private ImageView photo;
    @FXML private Parent root;
    @FXML private TableView<Reply> tableView;
    @FXML private TableColumn<Reply, String> column1;
    @FXML private TableColumn<Reply, Double> column2;
    @FXML private Button editPost;
    @FXML private Button changeImage;
    private ObservableList<Reply>replies = FXCollections.observableArrayList();

    /*
    * Basic setters to get Current Post and Uni_Link objects.
    * */
    public void getCurrentPost(Post Obj){
        this.post = Obj;
    }
    public void getCurrentUniLink(UniLink Obj){
        this.uniLink = Obj;
    }
    public void handleDetails(){

                labelMain.setText("The Details for the Post: "+post.getId());
                labelTitle.setText("Title: "+ post.getTitle());
                labelStatus.setText("Status: "+post.getStatus());
                labelDesc.setText("Desc: "+post.getDescription());
                String image = post.getPhoto();
                photo.setImage(new Image("/Images/"+image));
                for(int i =0 ; i< post.getReplies().size(); i++){
                    replies.add(post.getReplies().get(i));
                }
                if(replies.size()== 0){
                    changeImage.setDisable(false);
                    editPost.setDisable(false);
                }else{
                    changeImage.setDisable(true);
                    editPost.setDisable(true);
                }
                if(post.getId().startsWith("EVE")) {
                    labelInfo1.setText("Date: "+ ((EventPost)post).getDate());
                    labelInfo2.setText("Venue: "+((EventPost)post).getVenue());
                    labelInfo3.setText("Capacity: "+((EventPost)post).getCapacity());
                    labelInfo4.setText("Attendee Count: "+((EventPost)post).getAttendeeCount());
                }
                else if(post.getId().startsWith("SAL")){
                    labelInfo1.setText("Asking Price: $"+ ((SalePost)post).getAskingPrice());
                    labelInfo2.setText("Highest Asking : $"+ ((SalePost)post).getHighestPrice());
                    labelInfo3.setText("Minimum Raise: $"+((SalePost)post).getMinRaise());
                    labelInfo4.setText("");
                }else{
                    labelInfo1.setText("Proposed Price : $"+((JobPost)post).getPropPrice());
                    labelInfo2.setText("Lowest Offer: $"+((JobPost)post).getLowestOffer());
                    labelInfo3.setText("");
                    labelInfo4.setText("");
                }
    }

    /*
    * Methods of each button in the more_details page:
    * 1) Edit Button 2) Close Button 3) Upload New Image Button 4) Delete Button 5) Go Back to main menu Button
    * */
    public void editPostButtonPushed(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/EditPostScene.fxml"));
        Parent NextSceneParent = loader.load();
        EditPostController editPostController = loader.getController();
        editPostController.setPost(post);
        editPostController.handleEdit();
        Scene newScene = new Scene(NextSceneParent);
        Stage stage1 = new Stage();
        stage1.setTitle("Post Edit");
        stage1.setScene(newScene);
        stage1.show();


    }
    public void closeButtonPushed(ActionEvent actionEvent) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation Dialog");
        alert.setContentText("Are you sure with this?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){
            post.setStatus("CLOSED");
            goBackToMainMenu(actionEvent);
        } else {
            // ... user chose CANCEL or closed the dialog
        }


    }
    public void uploadImagePushed(ActionEvent actionEvent) {
        FileChooser fc = new FileChooser();
        fc.getExtensionFilters().add(new FileChooser.ExtensionFilter("PNG Files", "*.png"));
        fc.getExtensionFilters().add(new FileChooser.ExtensionFilter("JPEG Files", "*.jpg"));
        File File1 = fc.showOpenDialog(null);
        if(File1!= null){
            String Pathname = File1.getName();
            post.setPhoto(Pathname);
        }else{
            post.setPhoto("Default.png");
        }
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setContentText("Upload is Successful..Please Go Back and load the post again!!");
        goBackToMainMenu(actionEvent);
    }
    public void goBackToMainMenu(ActionEvent actionEvent) {
            Stage stage1 = (Stage) root.getScene().getWindow();
            stage1.close();
    }
    public void deletePostPushed(ActionEvent actionEvent) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation Dialog");
        alert.setContentText("Are you sure with this?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){
            for(int i = 0; i<uniLink.allPosts.size(); i++){
                if(post.getId().equals(uniLink.allPosts.get(i).getId())){
                    uniLink.allPosts.remove(i);
                }
            }
            goBackToMainMenu(actionEvent);
        } else {
            // ... user chose CANCEL or closed the dialog
        }
    }

    /*
    * Initialize Method to create TableView of the replies.
    * */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        column1.setText("Responder_ID");
        column2.setText("Value");
        column1.setCellValueFactory(new PropertyValueFactory<Reply, String>("responderID"));
        column2.setCellValueFactory(new PropertyValueFactory<Reply, Double>("value"));
        if (tableView == null) {
            tableView.getColumns().addAll(column1, column2);
        }
        tableView.setItems(replies);

    }



}
