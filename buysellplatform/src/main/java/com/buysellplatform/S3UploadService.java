package com.buysellplatform;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;

import java.io.File;
import java.io.InputStream;
import java.io.IOException;

public class S3UploadService {

    // Replace with your AWS credentials and region
    private static final String ACCESS_KEY = "AKIATJHQD5XLZBVO5KCN";
    private static final String SECRET_KEY = "LJXuGwn4IdTWGFQ4XUlKwRE+kaoJhvw41I0mTxTB";
    private static final String REGION = "us-east-1"; // Example region
    private static final String BUCKET_NAME = "justbid";

    private final AmazonS3 s3Client;

    public S3UploadService() {
        BasicAWSCredentials awsCredentials = new BasicAWSCredentials(ACCESS_KEY, SECRET_KEY);
        s3Client = AmazonS3ClientBuilder.standard()
                .withRegion(REGION)
                .withCredentials(new AWSStaticCredentialsProvider(awsCredentials))
                .build();
    }

    // Existing method for File upload
    public void uploadFile(String keyName, File file) {
        s3Client.putObject(new PutObjectRequest(BUCKET_NAME, keyName, file));
    }

    // New method for InputStream upload
    public void uploadFile(String keyName, InputStream inputStream, long contentLength) {
        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentLength(contentLength);
        s3Client.putObject(new PutObjectRequest(BUCKET_NAME, keyName, inputStream, metadata));
    }

    public InputStream downloadFile(String keyName) {
        S3Object s3Object = s3Client.getObject(new GetObjectRequest(BUCKET_NAME, keyName));
        return s3Object.getObjectContent();
    }

    public void deleteFile(String keyName) {
        s3Client.deleteObject(BUCKET_NAME, keyName);
    }
}
