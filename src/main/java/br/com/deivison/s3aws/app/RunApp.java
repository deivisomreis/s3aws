package br.com.deivison.s3aws.app;

import com.amazonaws.services.s3.AmazonS3;

import br.com.deivison.s3aws.actions.S3Actions;
import br.com.deivison.s3aws.config.AWSCredentialsConfig;
import br.com.deivison.s3aws.utils.EnvUtils;

public class RunApp {
	
	public static void main(String[] args) {
		
		AmazonS3 s3 = AWSCredentialsConfig.getS3(EnvUtils.getEnv().get("accessKey"), EnvUtils.getEnv().get("secretKey"));
		
		S3Actions action = S3Actions.builder(s3);
		
	}
}
