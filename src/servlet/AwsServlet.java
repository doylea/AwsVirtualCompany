package servlet;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import com.amazonaws.services.ec2.model.Instance;
import com.amazonaws.services.s3.model.PutObjectRequest;

import ec2.create_ec2;
public class AwsServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	// set time format;
	final DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("hh:mm a");

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// get request parameters hw1.jsp;
		String number = request.getParameter("number");
		String start = request.getParameter("start");
		String end = request.getParameter("end");
		String bucket = request.getParameter("bucket");
		String secret = request.getParameter("secret");

		// convert String parameters to LocalTime;
		LocalTime start_time = LocalTime.parse((start.length() == 8 ? start : "0" + start), timeFormatter);
		LocalTime end_time = LocalTime.parse((end.length() == 8 ? end : "0" + end), timeFormatter);

		// check if end_time is after start_time;
		if (end_time.isBefore(start_time)) {
			request.setAttribute("msg", "start_time shall be before end_time");
			request.getRequestDispatcher("/hw1.jsp").forward(request, response);
			return;
		}

		// convert String number to Integer;
		int n = Integer.parseInt(number);

		// check if number of employees is 1,2,or 3;
		if (n > 3 || n == 0) {
			request.setAttribute("msg", "# of employees is 1,2 or 3.");
			request.getRequestDispatcher("/hw1.jsp").forward(request, response);
			return;
		}

		// get the current time;
		LocalTime l = LocalTime.now();

		// if it is not work time, go back to the hw1.jsp;
		if ((start_time.isAfter(l)) || end_time.isBefore(l)) {
			request.setAttribute("msg", "It is not work time, cannot start instances");
			request.getRequestDispatcher("/hw1.jsp").forward(request, response);
			return;
		}
		// if it is work time, start the instances;
		else {
			/* the following code is used to create a security group 'cs9223' and a key 'cs9223',
			 * but I have already run it;
			
			 * CreateSecurityGroupRequest csgr = new
			 * CreateSecurityGroupRequest();
			 * 
			 * csgr.withGroupName("cs9223").withDescription("My security
			 * group");
			 * 
			 * CreateSecurityGroupResult createSecurityGroupResult =
			 * ec2.createSecurityGroup(csgr); IpPermission ipPermission = new
			 * IpPermission();
			 * 
			 * ipPermission.withIpRanges("111.111.111.111/32",
			 * "150.150.150.150/32").withIpProtocol("tcp").withFromPort(22)
			 * .withToPort(22);
			 * 
			 * AuthorizeSecurityGroupIngressRequest
			 * authorizeSecurityGroupIngressRequest = new
			 * AuthorizeSecurityGroupIngressRequest();
			 * 
			 * authorizeSecurityGroupIngressRequest.withGroupName("cs9223").
			 * withIpPermissions(ipPermission);
			 * ec2.authorizeSecurityGroupIngress(
			 * authorizeSecurityGroupIngressRequest);
			 * 
			 * CreateKeyPairRequest createKeyPairRequest = new
			 * CreateKeyPairRequest();
			 * 
			 * createKeyPairRequest.withKeyName("cs9223");
			 * 
			 * CreateKeyPairResult createKeyPairResult =
			 * ec2.createKeyPair(createKeyPairRequest);
			 * 
			 * KeyPair keyPair = new KeyPair();
			 * 
			 * keyPair = createKeyPairResult.getKeyPair();
			 * 
			 * String privateKey = keyPair.getKeyMaterial();
			 * 
			 * RunInstancesRequest runInstancesRequest = new
			 * RunInstancesRequest();
			 * runInstancesRequest.withImageId("ami-c481fad3").withInstanceType(
			 * "t2.micro").withMinCount(1).withMaxCount(1)
			 * .withKeyName("cs9223").withSecurityGroups("cs9223");
			 * RunInstancesResult runInstancesResult =
			 * ec2.runInstances(runInstancesRequest); try {
			 * DescribeAvailabilityZonesResult availabilityZonesResult =
			 * ec2.describeAvailabilityZones();
			 * System.out.println("You have access to " +
			 * availabilityZonesResult.getAvailabilityZones().size() +
			 * " Availability Zones.");
			 * 
			 * DescribeInstancesResult describeInstancesRequest =
			 * ec2.describeInstances(); List<Reservation> reservations =
			 * describeInstancesRequest.getReservations(); Set<Instance>
			 * instances = new HashSet<Instance>();
			 * 
			 * for (Reservation reservation : reservations) {
			 * instances.addAll(reservation.getInstances()); }
			 * 
			 * System.out.println("You have " + instances.size() + " Amazon EC2
			 * instance(s) running."); } catch (AmazonServiceException ase) {
			 * System.out.println("Caught Exception: " + ase.getMessage());
			 * System.out.println("Reponse Status Code: " +
			 * ase.getStatusCode()); System.out.println("Error Code: " +
			 * ase.getErrorCode()); System.out.println("Request ID: " +
			 * ase.getRequestId()); }
			 */

			
			try {
				// call create function of class create_ec2;
				create_ec2.create(n); 
				
				// call status method returns instance object;
				Set<Instance> instance= create_ec2.status();
				
				//set the number of instances;
				number = Integer.toString(instance.size());
				request.setAttribute("number", number);
				
				//iterate the set instance and print the instance id by method getInstance() and state by method getState().
				String instancestate = "Instance id:<br>";
				for (Instance Instance : instance) {
					instancestate = instancestate +Instance.getInstanceId() + " instance state:"+ Instance.getState()+"<br>";
				}
				
				//set state of instances;
				request.setAttribute("state", instancestate);
				
				// call s3 method to create a bucket;
				String[] bucketname = create_ec2.s3(bucket);
				request.setAttribute("bucketname", bucketname[1]);
				
				//create a file that shows the instances states, and writes it to the created bucket;
				File file = File.createTempFile("status", ".log");
		        file.deleteOnExit();
		        Writer writer = new OutputStreamWriter(new FileOutputStream(file));
		        
		        //write secret text and status information to the file;
		        writer.write(secret+"\n");
		        for (Instance Instance : instance) {
		        	writer.write("Instance Id:"+Instance.getInstanceId()+"state:"+Instance.getState()+"\n");
		        }
		        writer.close();
		        create_ec2.bucketStateFile(bucketname[0],bucketname[0],file);
				
				// set secret text;
				request.setAttribute("secret", secret);
				
				// redirect to status.jsp;
				request.getRequestDispatcher("/status.jsp").forward(request, response);
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
