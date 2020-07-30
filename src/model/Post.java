package model;

import Exceptions.InvalidInputException;
import Exceptions.PostClosedException;

import java.util.ArrayList;

public abstract class Post {

	//Post class variables. (Common fields to all kinds of Posts: ID, Title, Description, Creator_ID, Status and Photo).
	private String id, title, description, creatorID, status, photo;
	private ArrayList<Reply>replies = new ArrayList<Reply>();

	//Accessors and Mutators for class variables
	public ArrayList<Reply> getReplies() {
		return replies;
	}
	public String getId() {
		return id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getCreatorID() {
		return creatorID;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getPhoto() {
		return photo;
	}
	public void setPhoto(String photo) {
		this.photo = photo;
	}

	
	//Constructor to initialize the class variables.
	public Post(String ID, String Title,String Description,String Creator_ID, String Photo) {
		this.id = ID;
		this.title = Title;
		this.description = Description;
		this.creatorID = Creator_ID;
		this.status = "OPEN";
		this.photo = Photo;
		}


	// Method to Create String of the Object values.
	public String getPostDetails() {
		String String1 = this.id +"|"+this.title +"|"+this.description +"|"+this.creatorID +"|"+this.status +"|"+this.photo;
		return String1;
	}

	//Abstract methods to handle Replies and Create reply object strings.
	public abstract String handleReply(Reply reply) throws PostClosedException, InvalidInputException;
	public abstract String getReplyDetails();
}
