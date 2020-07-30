package model;

import Exceptions.BlankInputException;
import Exceptions.InvalidInputException;
import Exceptions.PostClosedException;
import javafx.scene.control.Alert;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class UniLink {

	/*
	*  Declare class variables All_posts : ArrayList of Posts. and Currentuser : current logged in user.
	* */
	public ArrayList<Post> allPosts = new ArrayList<Post>();
	private String currentUser;

	// Getter and Setter for currentUser.
	public String getCurrentUser() {
		return currentUser;
	}
	public void setCurrentUser(String currentUser) {
		this.currentUser = currentUser;
	}

	// Method to validate the username : Username should start with 'S' followed by digits only.
	public void validateUser(String S1) throws InvalidInputException {
		if(!S1.startsWith("S")){
			throw new InvalidInputException("Username must start with 'S' followed by numbers only...!!");
		}
		for(int i = 1; i<S1.length(); i++){
			if(!(Character.isDigit(S1.charAt(i)))){
				throw new InvalidInputException("Username must start with 'S' followed by numbers only..!!");
			}
		}
	}

	//----------------------------------------------------------------------------------------------------------------

	/*
	* Methods to create new Eventpost, Salepost and Jobpost objects and add them to array list All_posts.
	* */

	// Method to create and add Events
	public boolean addEvent(String userName, String title, String description, String venue, String date, String capacity,int eventCount, String photo) throws InvalidInputException, BlankInputException
	{
		// if Image is not selected "Default.png" will get selected as a default image.
		if(photo.equals(" ")){
			photo = "Default.png";
		}
		//Check for Capacity should be Integer value.
		for(int i =0; i< capacity.length(); i++){
			if(!(Character.isDigit(capacity.charAt(i)))){
				throw new InvalidInputException("Capacity must be an Integer value");
			}
		}
		try{
			SimpleDateFormat format = new SimpleDateFormat("DD/MM/YYYY");
			format.parse(date);
		}catch (ParseException e){
			Alert alert = new Alert(Alert.AlertType.ERROR);
			alert.setHeaderText("Exception Occured");
			alert.setContentText("Wrong Date Format. Date Format should be in 'DD/MM/YYYY'");
			alert.showAndWait();
			return false;
		}
		//Check for all the fields should not be null or blank.
		if(userName.equals("") || title.equals("") || description.equals("") || venue.equals("") || date.equals("") || capacity.equals("")) {
			throw new BlankInputException("Please Fill all the fields");
		}else {
			int cap = Integer.parseInt(capacity);
			String ID = "EVE" + String.format("%03d", eventCount);
			// This is to check if the Post ID already exists in the All_posts list.
			for(Post post : allPosts){
				if(post.getId().equals(ID)){
					eventCount++;
					ID = "EVE" + String.format("%03d", eventCount);
				}
			}
			EventPost obj = new EventPost(ID, title, description, userName, venue, date, cap, photo); // Creating Event Object
			allPosts.add(obj); // Adding it to the All_posts arrayList.
			return true;
		}
	}

	//Method to create and add Sales
	public boolean addSale(String userName,String title, String description,String askingPrice, String minRaise, int saleCount, String photo) throws InvalidInputException, BlankInputException
	{
		// if Image is not selected "Default.png" will get selected as a default image.
		if (photo.equals(" ")){
			photo = "Default.png";
		}
		//Check for Asking_Price and Min_Raise should be Double values.
		for(int i =0; i< askingPrice.length(); i++){
			if((Character.isLetter(askingPrice.charAt(i)))){
				throw new InvalidInputException("Asking Price must be in digits");
			}
		}
		for(int i =0; i< minRaise.length(); i++){
			if((Character.isLetter(minRaise.charAt(i)))){
				throw new InvalidInputException("Minimum Raise must be in digits");
			}
		}
		//Check for all the fields should not be null or blank.
		if(userName.equals("") || title.equals("") || description.equals("") || askingPrice.equals("") || minRaise.equals("")){
			throw new BlankInputException("Please Fill all the fields");
		}
		else {
			String id = "SAL" + String.format("%03d", saleCount);
			double askingP = Double.parseDouble(askingPrice);
			double minR = Double.parseDouble(minRaise);
			// This is to check if the Post ID already exists in the All_posts list.
			for(Post post : allPosts){
				if(post.getId().equals(id)){
					saleCount++;
					id = "SAL" + String.format("%03d", saleCount);
				}
			}
			// This is to check if Min_Raise Price is less than or equal to Asking_Price.
			if(minR>askingP){
				throw new InvalidInputException("Minimum price should be less than Asking Price");
			}else {
				SalePost obj = new SalePost(id, title, description, userName, askingP, minR, photo); // Creating Salepost object
				allPosts.add(obj); // Adding it to All_posts arrayList.
				return true;
			}
		}
	}

	//Method to create and add Jobs
	public boolean addJob(String userName,String title, String description, String propPrice, int jobCount,String photo) throws InvalidInputException, BlankInputException
	{
		// if Image is not selected "Default.png" will get selected as a default image.
		if (photo.equals(" ")){
			photo = "Default.png";
		}
		//Check for Proposed_Price should be Double value.
		for(int i =0; i< propPrice.length(); i++){
			if((Character.isLetter(propPrice.charAt(i)))){
				throw new InvalidInputException("Proposed Price must be in digits");
			}
		}
		//Check for all the fields should not be null or blank.
		if(userName.equals("") || title.equals("") || description.equals("") || propPrice.equals("")){
			throw new BlankInputException("Please Fill all the fields");
		}else {
			String id = "JOB" + String.format("%03d", jobCount);
			double propP = Double.parseDouble(propPrice);
			// This is to check if the Post ID already exists in the All_posts list.
			for(Post post : allPosts){
				if(post.getId().equals(id)){
					jobCount++;
					id = "JOB" + String.format("%03d", jobCount);
				}
			}
			JobPost obj = new JobPost(id, title, description, userName, propP, photo); // Creating new Jobpost object
			allPosts.add(obj); //Adding it to the All_posts arrayList.
			return true;
		}
	}

	//-----------------------------------------------------------------------------------------------------------------

	/*
	*  Methods to handle the Replies to Event, Sale and Job Posts.
	* */

	//Method to accept response of an Event post
	public String respondEvent(Post post, double value) throws PostClosedException, InvalidInputException
	{
		String replyResult;
		Reply reply = new Reply(post.getId(),1,this.getCurrentUser()); // Creating Reply object
		replyResult = post.handleReply(reply); // Calling Method handleReply() to handle the reply object (Accept or Reject).
		return replyResult;
	}
	//Method to accept response of a Sale post
	public String respondSale(Post post, String value) throws InvalidInputException, PostClosedException
	{
		//Check if Offered Price is a double value and in digits
		for(int i =0; i< value.length(); i++){
			if((Character.isLetter(value.charAt(i)))||value.equals("")){
				throw new InvalidInputException("Offered Price must be in digits");
			}
		}
		double val = Double.parseDouble(value);
		Reply reply = new Reply(post.getId(), val, this.getCurrentUser()); // Creating Reply Object
		String Reply_result = post.handleReply(reply); // Calling Method handleReply() to handle the reply object (Accept or Reject).
		return Reply_result;
	}
	// Method to accept response of a Job post
	public String respondJob(Post post, String value) throws PostClosedException, InvalidInputException
	{
		//Check if the offered_price is a double value and in digits.
		for(int i =0; i< value.length(); i++){
			if((Character.isLetter(value.charAt(i)))||value.equals("")){
				throw new InvalidInputException("Offered Price must be in digits");
			}
		}
		double Val = Double.parseDouble(value);
		Reply reply = new Reply(post.getId(),Val,this.getCurrentUser());// Creating a Reply Object.
		String replyResult = post.handleReply(reply); // Calling Method handleReply() to handle the reply object (Accept or Reject).
		return replyResult;
	}

	//-----------------------------------------------------------------------------------------------------------------
}
