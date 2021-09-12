package br.com.deivison.s3aws.app;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectListing;
import com.amazonaws.services.s3.model.S3ObjectSummary;

import br.com.deivison.s3aws.actions.S3Actions;
import br.com.deivison.s3aws.config.AWSCredentialsConfig;
import br.com.deivison.s3aws.utils.EnvUtils;

public class RunApp {
	
	public static void main(String[] args) {
		
		AmazonS3 s3 = AWSCredentialsConfig.getS3(EnvUtils.getEnv().get("accessKey"), EnvUtils.getEnv().get("secretKey"));
		
		S3Actions action = S3Actions.builder(s3);
		
//		action.uploadFolder("deivison.reis", "/home/deivisonreis/Imagens");
		
//		action.createBucket("deivison.reis");
		
		ObjectListing objectsForBucket = action.getObjectsForBucket("deivison.reis");
		
		for(S3ObjectSummary objectSummary : objectsForBucket.getObjectSummaries()) {
			action.deleteObject("deivison.reis", objectSummary.getKey());
		}
		
		action.deleteBucket("deivison.reis");
	}
}
