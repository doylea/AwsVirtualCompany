Assignment 1
====================================================================================================
All the instances created in this project are t2.micro, there is no method that passes instance type to create different type of instance since it is not required in the requirements. 

The codes to create security group and key is in the AwsServlet.java, but they are annotations since security group and key have already created.

All the processes run in the US\_EAST_1.

I only be able to make the Get cluster state REST service works.

====================================================================================================


##### Usage
1.It is successfully run on Tomcat v7.0.<br>
2.Main page: http://localhost:8080/assignment1/hw1.jsp<br> 
3.Page to Set start time and stop time: http://localhost:8080/assignment1/worktime.jsp<br>
4.Page to get real-time status:http://localhost:8080/assignment1/status_real-time.jsp<br>
5.GET cluster states with a REST service: http://localhost:8080/assignment1/rest/cluster.jsp


-------------------------------------------------------------------------------------------------------------------
##### JSP page
1.hw1.jsp takes 5 input parameters, and sends the request to the Servlet AwsServlet.java. Please enter the parameters in the right format because same exceptions may not be considered and handled.<br>
2.worktime.jsp takes 2 input parameters, and sends the request to the Servlet WorkTime.java. The format of parameters is the same with the hw1.jsp.<br>
3.status.jsp is the page that shows the status of cluster, the running instances, bucket name and secret text.

-------------------------------------------------------------------------------------------------------------------
##### Classes and method
1.servlet.AwsServlet; Servlet class to create instances, buckets and secret text.<br>
	doPost() <br>
	doGet()
	
2.servlet.WorkTime.java; Sevrlet class to start and stop instances according to the input parameters.<br>
	doPost() <br>
	doGet()

3.servlet.RealTimeServlet; Sevrlet class for the real-time status.<br>
	doPost() <br>
	doGet()
	
4.ec2.create_ec2.java; the class where most of the logic runs. <br>
	init(): retrieves the credentials AWSCredentials.properties;<br>
	create():create instances;<br>
	status():return the status of instance;<br>
	s3(String bucketName):create s3 buckets;<br>
	bucketStateFile(String bucketName,File file):put the log file 'states' to the bucket that is created.

5.rest.RestApplication.java; the class to register JSON converter.<br>
	
6.rest.GetStatus.java; the class to get instances objects<br>
	init():retrieves the credentials AWSCredentials.properties;<br>
	status():return instance object.
	
7.rest.bean.status.java; javaBean for status object.<br>
	
8.rest.resources.StateResource.java<br>
	getInstanceStatus():define a GET request to get a JSON file of instance status.
	
9.startandstop.Start.java<br>
	init():retrieves the credentials AWSCredentials.properties;<br>
	start():start the instances;

10.startandstop.Stop.java<br>
	init():retrieves the credentials AWSCredentials.properties;<br>
	stop():stop the running instances;