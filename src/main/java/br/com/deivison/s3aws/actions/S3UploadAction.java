package br.com.deivison.s3aws.actions;

import java.io.File;
import java.nio.file.Path;
import java.util.Objects;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.AmazonS3;

public class S3UploadAction {
	
	private AmazonS3 client;
	
	public S3UploadAction(AmazonS3 client) {
		this.client = client;
	}
	
	public void uploadFolder(String bucketName, String pathFolder) {
		File[] files = getFiles(pathFolder);
		
		for(File f: files) {
			try {
				if(f.isFile())
					client.putObject(bucketName, "file/" + f.getName(), f);
			} catch (AmazonServiceException e) {
				e.printStackTrace();
			}
		}
	}
	
	private File[] getFiles(String pathFolder){
		if(Objects.nonNull(pathFolder) && Path.of(pathFolder).toFile().exists()) {			
			return Path.of(pathFolder).toFile().listFiles();
		} else {
			throw new RuntimeException("This directory not received or not exists");
		}
	}
}
