package controller;

import hsql_db.ConnectionTest;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.*;
import javafx.geometry.Pos;
import javafx.scene.*;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.VBox;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;
import model.*;


import java.io.*;
import java.net.URL;
import java.sql.*;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.Scanner;
import java.util.stream.Collectors;

public class MainSceneController implements Initializable {

    @FXML public Label currentUserID;
    @FXML public VBox vMain;
    @FXML public ComboBox comboType;
    @FXML public ComboBox comboStatus;
    @FXML public ComboBox comboCreator;
    @FXML private Parent root;
    @FXML public ListView<model.Post> listView = new ListView<>();
    ObservableList <model.Post> studentObservablelist;
     public UniLink obj;

     //-----------------------------------------------------------------------------------------------------------------
    /*
     *  Menu Bar Related Methods.  1) Developer Info & Quit.  2) Import and Export.
     * */
    // This method is to Quit the Application.
    public void quitButtonPushed(ActionEvent actionEvent) throws IOException, SQLException, ClassNotFoundException {
        ConnectionTest conObj = new ConnectionTest();
        conObj.updateDatabase(obj);
        Stage stage1 = (Stage) root.getScene().getWindow();
        stage1.close();
    }

    //This Method is to show new Window of a Developer Information.
    public void developerInfoPushed(ActionEvent actionEvent) {
        Stage popUp = new Stage();
        popUp.initModality(Modality.APPLICATION_MODAL);
        Label developerName = new Label();
        Label studentNumber = new Label();
        developerName.setText("Name: Pranamya Korde");
        studentNumber.setText("Student Number: S3779009");
        VBox layout = new VBox(10);
        layout.getChildren().addAll(developerName, studentNumber);
        layout.setSpacing(10);
        layout.setAlignment(Pos.CENTER);
        Scene scene = new Scene(layout);
        popUp.setScene(scene);
        popUp.setWidth(200);
        popUp.setHeight(300);
        popUp.showAndWait();
    }


    // This Method is to Export the data into the Text file
    public void exportData(ActionEvent actionEvent) {
        try {
            DirectoryChooser dr = new DirectoryChooser();
            File pf = dr.showDialog(null);
            if(pf != null) {
                String path = pf.getAbsolutePath();
                File F1 = new File(path + "/Export_data.txt");
                PrintWriter writer = new PrintWriter(F1);
                for (Post post : obj.allPosts) {
                    String s1 = post.getPostDetails() + post.getReplyDetails();
                    writer.println(s1);
                    System.out.println("1 Object written");
                }
                writer.close();
            }
        }catch (IOException e){
            System.out.println("File Cannot be created or opened");
        }
    }

    // This Method is to Import the Data from the file and store it into system.
    public void importData(ActionEvent actionEvent){
            Scanner input = null;
            String line = null;
            try{
                FileChooser fc = new FileChooser();
                fc.getExtensionFilters().add(new FileChooser.ExtensionFilter("txt Files", "*.txt"));
                File pf = fc.showOpenDialog(null);
                if(pf != null) {
                    String fileName = pf.getAbsolutePath();
                    input = new Scanner(new FileInputStream(fileName));
                }
            }
            catch(FileNotFoundException e){
                System.err.println("No such file");
                System.exit(0);
            }
            while(input.hasNext()){
                line = input.nextLine();
                String [] arr = line.split("\\|");
                boolean result = createPost(arr);
            }
    }

    private boolean createPost(String[] arr) {
        String ID = arr[0];             String Title = arr[1];
        String desc = arr[2];           String Creator_id = arr[3];
        String Status = arr[4];         String Image = arr[5];

        for(Post post : obj.allPosts){
            if(ID.equals(post.getId())){
                return false;
            }
        }
        if(arr[0].startsWith("EVE")){
            String Venue = arr[6];      String Date = arr[7];
            int capacity = Integer.parseInt(arr[8]);
            int attendee_count = Integer.parseInt(arr[9]);
            EventPost eventpost = new EventPost(ID,Title,desc,Creator_id,Venue,Date,capacity,Image);
            eventpost.setAttendeeCount(attendee_count);
            eventpost.setStatus(Status);
            obj.allPosts.add(eventpost);
            for(int i=10; i<arr.length; i=i+2){
                Reply reply = new Reply(ID,Double.parseDouble(arr[i+1]),arr[i]);
                eventpost.getReplies().add(reply);
            }
        }
        if(arr[0].startsWith("SAL")){
            double Asking_Price = Double.parseDouble(arr[6]);
            double Min_Raise = Double.parseDouble(arr[7]);
            double Highest_Offer = Double.parseDouble(arr[8]);
            SalePost salepost = new SalePost(ID,Title,desc,Creator_id,Asking_Price,Min_Raise,Image);
            salepost.setHighestPrice(Highest_Offer);
            salepost.setStatus(Status);
            obj.allPosts.add(salepost);
            for(int i=9; i<arr.length; i=i+2){
                Reply reply = new Reply(ID,Double.parseDouble(arr[i+1]),arr[i]);
                salepost.getReplies().add(reply);
            }
        }
        if(arr[0].startsWith("JOB")){
            double Prop_Price = Double.parseDouble(arr[6]);
            double Lowest_Offer = Double.parseDouble(arr[7]);
            JobPost jobpost = new JobPost(ID,Title,desc,Creator_id,Prop_Price,Image);
            jobpost.setLowestOffer(Lowest_Offer);
            jobpost.setStatus(Status);
            for(int i=8; i<arr.length; i=i+2){
                Reply reply = new Reply(ID,Double.parseDouble(arr[i+1]),arr[i]);
                jobpost.getReplies().add(reply);
            }
        }
        return true;
    }


    //----------------------------------------------------------------------------------------------------------------
    /*
     *  HBox Related Methods 1) Display Current User. 2) LogOut 3) Load the data
     *
     * */
    // This Method is to show the current user on the Main Window.
    public void displayCurrentUser(UniLink obj) {
        this.obj = obj;
        currentUserID.setText(this.obj.getCurrentUser());
    }

    // This Method is to go back to Start Scene when Log Out Button is pushed.
    public void logOutButtonPushed(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/Welcome_Scene.fxml"));
        Parent nextSceneParent = loader.load();
        WelcomeSceneController welcomeSceneController = loader.getController();
        welcomeSceneController.setUniLink(obj);
        Scene newScene = new Scene(nextSceneParent);
        Stage stage1 = (Stage) root.getScene().getWindow();
        stage1.setScene(newScene);
        stage1.show();
    }

    //This Methods is to load the listView when load Data button is pushed.
    public void loadDataButtonPushed(ActionEvent actionEvent) throws IOException {

                ArrayList <Post> filterList=  filter1List();
                filterList = filter2List(filterList);
                filterList = filter3List(filterList);
                studentObservablelist = FXCollections.observableArrayList();
                for(Post post : filterList){
                    studentObservablelist.add(post);
                }
                listView.setItems(studentObservablelist);
                listView.setCellFactory(new Callback<ListView<Post>, ListCell<Post>>() {
                    @Override
                    public ListCell<Post> call(ListView<Post> studentListView) {
                        return new StudentListViewCell(obj);
                    }
                });

    }

    //-----------------------------------------------------------------------------------------------------------------
    /*
     *  ToolBar Related Methods 1)Event 2)Sale 3)Job 4) Filter by Type, status, creator.
     *
     * */
    // This Method is to go to Event creation scene after CreateNewEvent Button is pushed.
    public void createNewEvent(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/CreateEventScene.fxml"));
        Parent nextSceneParent = loader.load();
        EventController eventController = loader.getController();
        eventController.getUsername(obj);
        Scene newScene = new Scene(nextSceneParent);
        Stage stage1 = new Stage();
        stage1.setTitle("Event Creation");
        stage1.setScene(newScene);
        stage1.show();
    }

    // This Method is to go to Sale creation scene after CreateNewSale Button is pushed.
    public void createNewSale(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/CreateSaleScene.fxml"));
        Parent nextSceneParent = loader.load();
        SaleController saleController = loader.getController();
        saleController.getUsername(obj);
        Scene newScene = new Scene(nextSceneParent);
        Stage stage1 = new Stage();
        stage1.setTitle("Sale Creation");
        stage1.setScene(newScene);
        stage1.show();
    }

    // This Method is to go to Job creation scene after CreateNewJob Button is pushed.
    public void createNewJob(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/CreateJobScene.fxml"));
        Parent nextSceneParent = loader.load();
        JobController jobController = loader.getController();
        jobController.getUsername(obj);
        Scene newScene = new Scene(nextSceneParent);
        Stage stage1 = new Stage();
        stage1.setTitle("Job Creation");
        stage1.setScene(newScene);
        stage1.show();
    }

    // These Methods are to filter the Uni_Link Posts lists according to the ComboBoxes values.(Type, Status, Creator)
    public ArrayList<Post> filter1List(){
        ArrayList<Post> postsList = new ArrayList<Post>();
        for (int i = 0; i < obj.allPosts.size(); i++){
            postsList.add(obj.allPosts.get(i));
        }
        String filter1 = comboType.getValue().toString();
        if(filter1.equals("Event")) {
            postsList = (ArrayList<Post>) postsList
                    .stream().filter(p -> p.getId().startsWith("EVE")).collect(Collectors.toList());
        }
        if(filter1.equals("Sale")){
            postsList = (ArrayList<Post>) postsList
                    .stream().filter(p -> p.getId().startsWith("SAL")).collect(Collectors.toList());
        }
        if(filter1.equals("Job")){
            postsList = (ArrayList<Post>) postsList
                    .stream().filter(p -> p.getId().startsWith("JOB")).collect(Collectors.toList());
        }
        if(filter1.equals("All")){
            return  postsList;
        }
        return postsList;
    }

    private ArrayList<Post> filter2List(ArrayList<Post> postsList) {
        String filter2 = comboStatus.getValue().toString();
        if(filter2.equals("Open")){
            postsList = (ArrayList<Post>) postsList
                                .stream().filter(p -> p.getStatus().equals("OPEN")).collect(Collectors.toList());
        }
        if(filter2.equals("Closed")){
            postsList = (ArrayList<Post>) postsList
                    .stream().filter(p -> p.getStatus().equals("CLOSED")).collect(Collectors.toList());
        }
        if(filter2.equals("All")){
            return  postsList;
        }
        return postsList;
    }

    private ArrayList<Post> filter3List(ArrayList<Post> postsList) {
        String filter3 = comboCreator.getValue().toString();
        if(filter3.equals("My Posts")){
            postsList = (ArrayList<Post>) postsList
                                .stream().filter(p -> p.getCreatorID().equals(obj.getCurrentUser()))
                                .collect(Collectors.toList());
        }
        if(filter3.equals("All")){
            return  postsList;
        }
        return postsList;
    }

    /*
     * Initialize Components.
     * */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        comboType.setValue("All");
        comboStatus.setValue("All");
        comboCreator.setValue("All");
        comboCreator.getItems().addAll( "All", "My Posts");
        comboStatus.getItems().addAll("All", "Open", "Closed");
        comboType.getItems().addAll("All", "Event", "Sale", "Job");
    }

}