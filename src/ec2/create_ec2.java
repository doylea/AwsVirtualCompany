package ec2;

import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.ClasspathPropertiesFileCredentialsProvider;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.ec2.AmazonEC2;
import com.amazonaws.services.ec2.AmazonEC2Client;
import com.amazonaws.services.ec2.model.DescribeAvailabilityZonesResult;
import com.amazonaws.services.ec2.model.DescribeInstancesResult;
import com.amazonaws.services.ec2.model.Instance;
import com.amazonaws.services.ec2.model.Reservation;
import com.amazonaws.services.ec2.model.RunInstancesRequest;
import com.amazonaws.services.ec2.model.RunInstancesResult;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.Bucket;
import com.amazonaws.services.s3.model.PutObjectRequest;


/* This class contains five methods
 * The first is init(), which initializes the credentials setting.
 * The second is create(), which starts EC2 instances.
 * The third is status(), which returns the status of virtual office.
 * The forth is s3(), which create a new bucket.
 * The fifth is bucketStateFile(), which upload files to the buckets.
 * */
public class create_ec2 {
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

	// method to create instance;
	public static void create(int number) throws Exception {
		// initialize;
		init();
		// run the instance;
		for (int i = 0; i < number; i++) {
			RunInstancesRequest runInstancesRequest = new RunInstancesRequest();
			runInstancesRequest.withImageId("ami-c481fad3").withInstanceType("t2.micro").withMinCount(1).withMaxCount(1)
					.withKeyName("cs9223").withSecurityGroups("cs9223");
			RunInstancesResult runInstancesResult = ec2.runInstances(runInstancesRequest);
		}
	}

	// method to return the status of instances;
	public static Set<Instance> status() {
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
	//method to create the S3 bucket;
	public static String[] s3(String bucketName) throws Exception{
		init();
		bucketName = bucketName+UUID.randomUUID();
        s3.createBucket(bucketName);
        String bucketname = "bucketname is:";
        for (Bucket bucket : s3.listBuckets()) {
            bucketname = bucketname + "<br>" + bucket.getName();
        }
        String[] bucket = new String[]{bucketName,bucketname};
        return bucket;
	}
	
	//method to upload the log file to the bucket;
	public static void bucketStateFile(String bucketName,String logFileName,File file){
		s3.putObject(new PutObjectRequest(bucketName,logFileName,file));
	}
}
