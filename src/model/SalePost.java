package model;

import Exceptions.PostClosedException;

public class SalePost extends Post {
	
	// Salepost class variables (Class Variables specific to Sale: Asking_Price, Min_Raise and Highest_offer)
	private double askingPrice, highestPrice, minRaise;
	
	// Accessors and Mutators for Salepost variables
	public double getAskingPrice() {
		return askingPrice;
	}
	public void setAskingPrice(double askingPrice) {
		this.askingPrice = askingPrice;
	}
	public double getHighestPrice() {
		return highestPrice;
	}
	public void setHighestPrice(double highestPrice) {
		this.highestPrice = highestPrice;
	}
	public double getMinRaise() {
		return minRaise;
	}
	public void setMinRaise(double minRaise) {
		this.minRaise = minRaise;
	}
	
	// Constructor to initialize class variables.
	public SalePost(String ID, String Title, String Description, String username, double Asking_Price, double Min_Raise, String Photo) {
		super(ID, Title, Description, username, Photo);
		this.askingPrice = Asking_Price;
		this.minRaise = Min_Raise;
		this.highestPrice = 0;
	}

	//----------------------------------------------------------------------------------------------------------------------------------------

	/*
	* Overridden methods :
	* 1. To generate Sale Post Object Details
	* 2. Handle replies to the Sale object (Accept or Reject)
	* 3. To generate Reply Object details
	* */

	// Generate Post Object Details
	public String getPostDetails() {
			String String1 = super.getPostDetails()+"|"+this.askingPrice +"|"+this.minRaise +"|"+this.highestPrice;
			return String1;

	}

	// Handle Replies to the Sale Object (Accept or Reject)
	@Override
	public String handleReply(Reply reply) throws PostClosedException {
		// TODO Auto-generated method stub
		if (this.getStatus().equals("OPEN")) {
			if(reply.getValue() >= (this.getHighestPrice() + this.getMinRaise())){
				if(reply.getValue()>= this.getAskingPrice()){
					this.getReplies().add(reply);
					this.setHighestPrice(reply.getValue());
					this.setStatus("CLOSED");
					String msg = ("Congratulation!!Offer Successful. please contact owner " + this.getCreatorID());
					return msg;
				}
				else{
					this.getReplies().add(reply);
					this.setHighestPrice(reply.getValue());
					String msg = ("OFFER has been ACCEPTED for user: " + reply.getResponderID() + ". However,your offer is below the asking price.");
					return msg;
				}
			}else{
				String msg = ("Offer DENIED.....!!");
				return msg;
			}
		} else {
			throw new PostClosedException("ERROR: This SalePost is currently in CLOSED status.");
		}
	}

	//Generate Reply object details of a Sale
	@Override
	public String getReplyDetails() {
		String s1 = "|";
		for (Reply reply: this.getReplies()){
			s1= s1+reply.getResponderID()+"|"+reply.getValue()+"|";
		}
		return s1;
	}
}
