# UniLink-system---Java-FX-UI-Implementation-and-Data-Persistence


In this project, we have designed and implemented Java Application called UniLink. This application allows all students in a university to create
and publish various types of posts and reply to those posts. Users of the UniLink system can also view all the posts, manage their own posts, review all replies to their posts,
close and delete their own posts. 
We have implemented the User Interace using Java FX and Scene Builder and Data Persistance using embedded HSQLDB.

After successfully signing in the main dashboard page looks loke this: 

![Main Dashboard](/Images/P1.PNG)

A student can create 3 types of posts :

1. Event Post 
2. Sale Post
3. Job Post

You can search and sort the posts by various options like type of post, Status of posts and creator of the post

student have to provide various details to successfully create a post and share it on the UniLink dashboard.

1.  Event Post

Event post details can see on the more details tab of the dashboard. Only creator of the event can edit,close or delete his own posts.

![Event Creation](/Images/P3.PNG)

Creator of the event can see the responder list of the event in the more details tab

![Event Details](/Images/P2.PNG)


2.  Sale Post

Sale post details can see on the more details tab of the dashboard. Only creator of the Sale Post can edit,close or delete his own posts.

![Sale Creation](/Images/P4.PNG)

Other students can place a bid on the sale and the owner of the Sale Post can see the list of responders and can sort it accordingly. Student should place a bid atleast more than a minimum raise price than a previous Highest Asking Price.

![Sale Details](/Images/P7.PNG)


2.  Job Post

Job post details can see on the more details tab of the dashboard. Only creator of the Job Post can edit,close or delete his own posts or can have primary access to his own posts.

![Job Creation](/Images/P5.PNG)

Other students can place a bid on the lowest offer proposed for this job. The proposed offer should always be less than the price asked by the creator of the Job. 

![Job Details](/Images/P6.PNG)
