package com.bso.companycob.infrastructure.aws;

import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.DefaultAWSCredentialsProviderChain;
import org.springframework.cloud.aws.core.region.RegionProvider;
import org.springframework.cloud.aws.core.region.StaticRegionProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.AwsCredentialsProvider;
import software.amazon.awssdk.auth.credentials.DefaultCredentialsProvider;
import software.amazon.awssdk.regions.Region;

@Configuration
public class AwsCredentialsConfiguration {

    @Bean
    public AwsCredentialsProvider awsCredentialsProviderOldSdk() {
        return DefaultCredentialsProvider.create();
    }

    @Bean
    public Region region() {
        return Region.US_EAST_1;
    }

    @Bean
    public AWSCredentialsProvider awsCredentialsProvider() {
        return DefaultAWSCredentialsProviderChain.getInstance();
    }

    @Bean
    public RegionProvider regionProvider() {
        return new StaticRegionProvider(Region.US_EAST_1.id());
    }

}
