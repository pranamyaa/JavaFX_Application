package model;

import Exceptions.InvalidInputException;
import Exceptions.PostClosedException;

public class JobPost extends Post {

	//Jobpost class variables (specific to Job posts : Prop_Price, Lowest_offer).
	private double propPrice;
	private double lowestOffer;

	// Accessors and Mutators for class variables
	public double getPropPrice() {
		return propPrice;
	}
	public void setPropPrice(double propPrice) {
		this.propPrice = propPrice;
	}
	public double getLowestOffer() {
		return lowestOffer;
	}
	public void setLowestOffer(double lowestOffer) {
		this.lowestOffer = lowestOffer;
	}

	//Constructor to initialize class variables.
	public JobPost(String ID, String Title, String Description, String Creator_ID, double Prop_price, String Photo) {
		super(ID,Title,Description,Creator_ID,Photo);
		this.propPrice = Prop_price;
		this.lowestOffer = 0;
	}

	//---------------------------------------------------------------------------------------------------------------------

	/*
	* Overridden Methods:
	* 1. To generate Job Post Object details.
	* 2. To handle Replies to the Job Post(Accept or Reject).
	* 3. To generate Reply Object details.
	* */

	// Generate Job Post Object details
	public String getPostDetails() {
			String s1 = super.getPostDetails()+"|"+this.propPrice +"|"+this.lowestOffer;
			return s1;
	}

	// Handle Replies to the job Post Object(Accept or Reject).
	@Override
	public String handleReply(Reply reply) throws PostClosedException, InvalidInputException {
		// TODO Auto-generated method stub
		if(this.getStatus().equals("OPEN")) {
				if(reply.getValue()>this.getPropPrice()) {
					String msg = ("ERROR: Request not accpted as requested offer is greater than proposed price..!!");
					return msg;
				}else {
						if(this.getLowestOffer()==0) {
							this.getReplies().add(reply);
							this.setLowestOffer(reply.getValue());
							String msg = ("Success...Offer accepted!!");
							return msg;
						}
						else {
							if(reply.getValue()<=this.getLowestOffer()) {
								this.getReplies().add(reply);
								this.setLowestOffer(reply.getValue());
								String msg = ("Success...Offer accepted!!");
								return msg;
							}
							else {
								throw new InvalidInputException("ERROR: Request not accpeted as Offered price is greater than the previous Lowest Offered Price..!!");
							}
						}
				}	
			}
		else {
			throw new PostClosedException("ERROR: This SalePost is currently in CLOSED status.");
		}
	}

	// Generate reply details of a object.
	@Override
	public String getReplyDetails() {
		String s1 = "|";
		for (Reply reply: this.getReplies()){
			s1= s1+reply.getResponderID()+"|"+reply.getValue()+"|";
		}
		return s1;
	}

}
