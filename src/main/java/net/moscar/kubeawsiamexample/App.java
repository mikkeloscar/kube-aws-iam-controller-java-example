package net.moscar.kubeawsiamexample;

import com.amazonaws.services.ec2.AmazonEC2;
import com.amazonaws.services.ec2.AmazonEC2ClientBuilder;
import com.amazonaws.services.ec2.model.DescribeInstancesRequest;
import com.amazonaws.services.ec2.model.DescribeInstancesResult;
import com.amazonaws.services.ec2.model.Instance;
import com.amazonaws.services.ec2.model.Reservation;
import java.util.logging.Logger;

public class App {

    private static final Logger log = Logger.getLogger(App.class.getName());


    public static void main( String[] args ) throws InterruptedException {
        log.info("Hello World!");
        final AmazonEC2 ec2 = AmazonEC2ClientBuilder.defaultClient();
        while(true) {
            describeEC2(ec2);
            // sleep for 5 min.
            Thread.sleep(1000 * 60 * 5);
        }
    }

    private static void describeEC2(AmazonEC2 ec2) {
        boolean done = false;
        DescribeInstancesRequest request = new DescribeInstancesRequest();
        while(!done) {
            DescribeInstancesResult response = ec2.describeInstances(request);

            log.info("Getting instances:");
            for (Reservation reservation : response.getReservations()) {
                for (Instance instance : reservation.getInstances()) {
                    System.out.printf(
                            "Found instance with id %s, " +
                            "AMI %s, " +
                            "type %s, " +
                            "state %s " +
                            "and monitoring state %s\n",
                            instance.getInstanceId(),
                            instance.getImageId(),
                            instance.getInstanceType(),
                            instance.getState().getName(),
                            instance.getMonitoring().getState());
                }
            }

            request.setNextToken(response.getNextToken());

            if (response.getNextToken() == null) {
                done = true;
            }
        }
    }
}
