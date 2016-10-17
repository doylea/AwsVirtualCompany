package rest;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.ClasspathPropertiesFileCredentialsProvider;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.ec2.AmazonEC2;
import com.amazonaws.services.ec2.AmazonEC2Client;
import com.amazonaws.services.ec2.model.DescribeInstancesResult;
import com.amazonaws.services.ec2.model.Instance;
import com.amazonaws.services.ec2.model.Reservation;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.Bucket;




public class GetStatus {
	static AmazonEC2 ec2;
	static AmazonS3 s3;

	// initialize the credentials setting;
	private static void init() throws Exception {
		// retrieves the credentials AWSCredentials.properties;
		AWSCredentialsProvider credentials = new ClasspathPropertiesFileCredentialsProvider(
				"/AWSCredentials.properties");
		ec2 = new AmazonEC2Client(credentials);
		s3 = new AmazonS3Client(credentials);
		Region usEast = Region.getRegion(Regions.US_EAST_1);
		s3.setRegion(usEast);
	}
	public static Set<Instance> status() throws Exception {
		init();
		try {
			DescribeInstancesResult describeInstancesRequest = ec2.describeInstances();
			List<Reservation> reservations = describeInstancesRequest.getReservations();
			Set<Instance> instances = new HashSet<Instance>();

			for (Reservation reservation : reservations) {
				instances.addAll(reservation.getInstances());
			}
			return instances;
		} catch (AmazonServiceException ase) {
			System.out.println("Caught Exception: " + ase.getMessage());
			System.out.println("Reponse Status Code: " + ase.getStatusCode());
			System.out.println("Error Code: " + ase.getErrorCode());
			System.out.println("Request ID: " + ase.getRequestId());
		}
		return null;

	}
	public static List<String> bucket() throws Exception{
		init();
		List<String> buckets = new ArrayList<String>();
		
		for (Bucket bucket : s3.listBuckets()) {
             buckets.add(bucket.getName());
         }

		return buckets;
	}


}
