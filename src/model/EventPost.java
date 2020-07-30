package model;

import Exceptions.PostClosedException;

public class EventPost extends Post {
	
	// Eventpost class variables (Variables specific to Event class : Venue, Date, Capacity and Attendee_count)
	private String venue;
	private String date;
	private int capacity;
	private int attendeeCount;

	// Accessors and Mutators for Eventpost variables
	public String getVenue() {
		return this.venue;
	}
	public void setVenue(String venue) {
		this.venue = venue;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public int getCapacity() {
		return capacity;
	}
	public void setCapacity(int capacity) {
		this.capacity = capacity;
	}
	public int getAttendeeCount() {
		return attendeeCount;
	}
	public void setAttendeeCount(int attendeeCount) {
		this.attendeeCount = attendeeCount;
	}

	// Constructor to Initialize the Object class variables.
	public EventPost(String ID, String Title, String Description, String Creator_ID, String Venue, String date, int Capacity, String Photo) {
		super(ID,Title,Description,Creator_ID,Photo);
		attendeeCount = 0;
		this.venue = Venue;
		this.date = date;
		this.capacity = Capacity;
	}

	//---------------------------------------------------------------------------------------------------------------------------------------------

	/*
	* Overridden methods
	* 1. To generate Event Object Details
	* 2. To handle the Replies to a particular Event Object (Accept or Reject)
	* 3. To generate Reply Object Details of Event.
	* */

	// Generate Event Object Details
	@Override
	public String getPostDetails() {
		String String1 = super.getPostDetails()+"|"+this.venue +"|"+this.date+"|"+this.capacity +"|"+this.attendeeCount;
		return String1;
	}

	// To handle Replies (Accept or Reject).
	@Override
	public String handleReply(Reply reply) throws PostClosedException {
		if(this.getStatus().equals("OPEN")) {
			for(int i = 0; i< this.getReplies().size(); i++) {
				if(reply.getResponderID().equals(this.getReplies().get(i).getResponderID())) {
					String msg= ("ERROR: Response is already submitted for this post with username: "+ reply.getResponderID());
					return msg;
				}
			}
			if(this.getAttendeeCount()<this.getCapacity()) {
					this.getReplies().add(reply);
					this.setAttendeeCount((this.getAttendeeCount()+1));
					if(this.getAttendeeCount()==this.getCapacity()) {
						this.setStatus("CLOSED");
					}
					String msg = ("Success....Response is accepted for the Post "+ this.getId()+"\n");
					return msg;
			}
			else {
				throw new PostClosedException("ERROR: Event is full and currently in CLOSED status");
			}
		}
		else {
			throw new PostClosedException("ERROR: Event is full and currently in CLOSED status");
		}
	}

	// Generate Reply Object Details of the Event.
	@Override
	public String getReplyDetails() {
		String s1 = "|";
		for (Reply reply: this.getReplies()){
			 s1= s1+reply.getResponderID()+"|"+reply.getValue()+"|";
		}
		return s1;
	}
	
}
