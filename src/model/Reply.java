package model;

public class Reply {
	
	//Reply class variables. (PostID, Value and Responder_ID)
	private String postID;
	private double value;
	private String responderID;
	
	//Accessors and Mutators of class variables
	public String getPostID() {
		return postID;
	}
	public double getValue() {
		return value;
	}
	public String getResponderID() {
		return responderID;
	}
	
	//Constructor to initialize the Reply Objects.
	public Reply(String postId, double value, String responderId) {
		this.postID = postId;
		this.value = value;
		this.responderID = responderId;
	}
	
	
}
