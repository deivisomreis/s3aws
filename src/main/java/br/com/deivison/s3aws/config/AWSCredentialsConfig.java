package br.com.deivison.s3aws.config;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;

public class AWSCredentialsConfig {
	
	private AWSCredentialsConfig() {}
	
	public static AmazonS3 getS3(String accessKey, String secretKey) {
		return AmazonS3ClientBuilder
				.standard()
				.withCredentials(new AWSStaticCredentialsProvider(authCredentials(accessKey, secretKey)))
				.withRegion(Regions.US_EAST_2)
				.build();
	}
	
	private static AWSCredentials authCredentials(String accessKey, String secretKey) {
		return new BasicAWSCredentials(accessKey, secretKey);
	}
}
