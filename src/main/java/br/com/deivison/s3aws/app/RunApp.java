package br.com.deivison.s3aws.app;

import java.util.List;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.Bucket;

import br.com.deivison.s3aws.actions.S3UploadAction;
import br.com.deivison.s3aws.config.AWSCredentialsConfig;
import br.com.deivison.s3aws.utils.EnvUtils;

public class RunApp {
	
	public static void main(String[] args) {
		
		AmazonS3 s3 = AWSCredentialsConfig.getS3(EnvUtils.getEnv().get("accessKey"), EnvUtils.getEnv().get("secretKey"));
		
		List<Bucket> listBuckets = s3.listBuckets();
		
		S3UploadAction uploadAction = new S3UploadAction(s3);
		
		uploadAction.uploadFolder(listBuckets.get(0).getName(), "/tmp/deivison.reis");
	}
}
