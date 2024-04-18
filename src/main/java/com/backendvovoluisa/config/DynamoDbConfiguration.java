package com.backendvovoluisa.config;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;



@Configuration
public class DynamoDbConfiguration {

    @Value("${dynamoDBSourcePath}")
    private String dynamoDBSourcePath;

    @Value("${dynamoDBRegion}")
    private String dynamoDBRegion;

    @Value("${awsCredentialUser}")
    private String awsCredentialUser;

    @Value("${awsCredentialsPassWord}")
    private String awsCredentialsPassWord;

    @Bean
    public DynamoDBMapper dynamoDBMapper() {
        return new DynamoDBMapper(buildAmazonDynamoDB());
    }

    private AmazonDynamoDB buildAmazonDynamoDB() {
        return AmazonDynamoDBClientBuilder
                .standard()
                .withEndpointConfiguration(
                        new AwsClientBuilder.EndpointConfiguration(
                                dynamoDBSourcePath,
                                dynamoDBRegion)
                )
                .withCredentials(
                      new AWSStaticCredentialsProvider(
                              new BasicAWSCredentials(
                                      awsCredentialUser,
                                      awsCredentialsPassWord
                              )
                      )
                )
    .build();
    }
}