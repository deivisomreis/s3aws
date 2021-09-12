package br.com.deivison.s3aws.actions;

import java.io.File;
import java.nio.file.Path;
import java.util.Objects;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.Bucket;
import com.amazonaws.services.s3.model.DeleteObjectsRequest;
import com.amazonaws.services.s3.model.ObjectListing;
import com.amazonaws.services.s3.model.S3Object;

import br.com.deivison.s3aws.utils.FileUtils;

public class S3Actions {
	
	private AmazonS3 client;
	
	public static S3Actions builder(AmazonS3 client) {
		return new S3Actions(client);
	}
	
	public S3Actions(AmazonS3 client) {
		this.client = client;
	}
	
	public void uploadFolder(String bucketName, String pathFolder) {
		File[] files = getFiles(pathFolder);
		int count = 0;
		
		for(File f: files) {
			try {
				if(f.isFile()) {
					System.out.println("carregando o arquivo: " + f.getName());
					client.putObject(bucketName, "file/" + f.getName(), f);
					count++;
					}
			}catch (AmazonServiceException e) {
				e.printStackTrace();
			}
		}
		
		System.out.println(count + " arquivos carregados");
	}
	
	private File[] getFiles(String pathFolder){
		if(Objects.nonNull(pathFolder) && Path.of(pathFolder).toFile().exists()) {			
			return Path.of(pathFolder).toFile().listFiles();
		} else {
			throw new RuntimeException("This directory not received or not exists");
		}
	}
	
	@SuppressWarnings("deprecation")
	public Bucket createBucket(String bucketName) {
		if(!client.doesBucketExist(bucketName)) {
			return client.createBucket(bucketName, client.getRegion());
		} else {
			System.out.println("Já existe este bucket[" + bucketName + "]");
			
			return null;
		}
	}
	
	public boolean deleteBucket(String bucketName) {
		boolean statusDel = client.doesBucketExist(bucketName);
		
		if(statusDel) {
			try{
				client.deleteBucket(bucketName);
			} catch (AmazonServiceException e) {
				e.printStackTrace();
				
				statusDel = false;
			}
		}
		return statusDel;
	}
	
	public ObjectListing getObjectsForBucket(String bucketName) {
		if(client.doesBucketExist(bucketName)) {
			return  client.listObjects(bucketName);
		} else {
			return null;
		}
	}
	
	public void downloadObject(String bucketName, String filePath, String pathSave) {
		if(client.doesBucketExist(bucketName)) {
			try {
				S3Object object = client.getObject(bucketName, filePath);
				
				FileUtils.copyInputStringToFile(object.getObjectContent(), new File(pathSave));
			} catch (AmazonServiceException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void moveOrRenameObject(String bucketNameOrigin, String objectKeyOrigin, String bucketNameDestination, String objectKeyDestination) {
		try {
			client.copyObject(bucketNameOrigin, objectKeyOrigin, bucketNameDestination, objectKeyDestination);
		}catch (AmazonServiceException e) {
			e.printStackTrace();
		}
	}
	
	
	public void deleteObject(String bucketName, String objectKey) {
		try {
			client.deleteObject(bucketName, objectKey);
		}catch (AmazonServiceException e) {
			e.printStackTrace();
		}
	}
	
	public void deleteMultipeObjects(String bucketName, String[] objectKeys) {
		DeleteObjectsRequest deleteObjectsRequest = new DeleteObjectsRequest(bucketName);
		deleteObjectsRequest.withKeys(objectKeys);
		
		try {
			client.deleteObjects(deleteObjectsRequest);
		} catch (AmazonServiceException e) {
			e.printStackTrace();
		}
	}
}
