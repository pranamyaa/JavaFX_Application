
* UNILINK is a console based Java application which allows all students in a university to create and publish 3 types of posts and reply to those posts.
* It is a simplified version of any real time system and not 100% accurate to any system.
  1. Event Posts   2. Sale Posts   3. Job Posts

Assumptions made in the Main Menu of the application : 

*  To make it easier for testing, we have assumed that only student number is required for students to log in. Passwords are not required.
*  We have assumed that, only one user can log in into system at a time.
*  We have further assumed that all student numbers start with letter 'S' and application could accept any student number starting with 'S'.
*  We have assumed that Post ID is an unique ID associated with every post, even when post gets deleted from the application.
   And, application cannot use same post ID of a deleted post to create a new post.


Assumptions made in the Even Post creation or reply :

*  Currently, we have assumed that, Event date should be in format (dd/mm/yyyy) so, it getting stored in 'String' data type than a 'Date' data type.
*  We have assumed that, Application will not check the validity of an event based on the date (if the event is expired or not).
*  Currently, we have assumed that, two or more events with same event details are accepted by the application with different event IDs.
*  We have assumed that, once a student is replied to an event, both owner as well as that student cannot remove themselves from attendee list.
*  Owner of an event cannot reply to his/her own posted events. Once owner creates an event, he/she will not be able to edit or change the event details.
*  we have assumed that, one student cannot reply to a same event twice and the application will show proper message in that situation.

Assumptions made in the Sale Post creation or reply :

*  We have assumed that for a particular Sale Post, the very first entered reply value should be greater than or equal to the Minimum Raise entered by owner of a Post.
*  After that, newly entered offer price should be  greater than or equal to sum of last Highest offered price and Minimum Raise.
*  Here,  we have assumed that, each student can reply to a certain Sale Post multiple times.
*  We have assumed here that creator of a post cannot reply to his/her own post.
*  We have assumed here that student can create a post with $0.00 asking price.

Assumptions made in the Job Post creation or reply :

*  We have assumed in the Job class that, if a certain student reply $0.00 as a offer price to a particular job post, the application will accept the post as a lowest price. 
*  We have have assumed that job creator will not be able to reply to his own post.
*  We have assumed that the job post will not automatically get closed if someone reply to it with $0.00 as offer price.    
*  We have assumed that, each student can reply to a certain Job post multiple times.
*  We have assumed that, only creator of a Job post can CLOSE the post and it will not automatically get closed. 