package com.bso.companycob.infrastructure.aws;

import com.amazonaws.auth.DefaultAWSCredentialsProviderChain;
import com.amazonaws.services.sqs.AmazonSQSAsync;
import com.amazonaws.services.sqs.AmazonSQSAsyncClientBuilder;
import com.bso.companycob.application.model.message.MessageSender;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.aws.messaging.core.QueueMessagingTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import software.amazon.awssdk.auth.credentials.AwsCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.sqs.SqsAsyncClient;
import software.amazon.awssdk.services.sqs.SqsClient;

import java.net.URI;
import java.net.URISyntaxException;

@Configuration
public class SqsConfiguration {

    @Configuration
    @Profile(EnvironmentAws.LOCAL)
    public static class SqsLocalConfiguration {
        @Value("${aws.endpoint:}") private String awsEndpoint;

        @Bean
        public SqsClient sqsClient(AwsCredentialsProvider awsCredentialsProvider, Region region) {
            validateAwsEndpoint();
            return SqsClient.builder().endpointOverride(newUri(awsEndpoint)).build();
        }

        @Bean
        public SqsAsyncClient sqsAsyncClient(AwsCredentialsProvider awsCredentialsProvider, Region region) {
            validateAwsEndpoint();
            return SqsAsyncClient.builder().endpointOverride(newUri(awsEndpoint)).build();
        }

        private void validateAwsEndpoint() {
            if (awsEndpoint == null || awsEndpoint.isEmpty()) {
                throw new IllegalArgumentException("Property 'aws.endpoint' or AWS_ENDPOINT environment variable must be set in 'local' profile");
            }
        }

        private URI newUri(String uri) {
            try {
                return new URI(uri);
            } catch (URISyntaxException e) {
                throw new IllegalStateException(String.format("Aws endpoint is not a valid url: '%s'", awsEndpoint));
            }
        }
    }

    @Configuration
    @Profile(EnvironmentAws.NOT_LOCAL)
    public static class SqsAwsConfiguration {
        @Bean
        public SqsClient sqsClient(AwsCredentialsProvider awsCredentialsProvider, Region region) {
            return SqsClient.builder()
                    .credentialsProvider(awsCredentialsProvider)
                    .region(region)
                    .build();
        }

        @Bean
        public SqsAsyncClient sqsAsyncClient(AwsCredentialsProvider awsCredentialsProvider, Region region) {
            return SqsAsyncClient.builder()
                    .credentialsProvider(awsCredentialsProvider)
                    .region(region)
                    .build();
        }
    }

    @Bean
    public QueueMessagingTemplate queueMessagingTemplate(AmazonSQSAsync amazonSQSAsync) {
        return new QueueMessagingTemplate(amazonSQSAsync);
    }

    @Bean
    @Primary
    public AmazonSQSAsync amazonSqsAsync(Region region) {
        return AmazonSQSAsyncClientBuilder.standard()
                .withRegion(region.toString())
                .withCredentials(new DefaultAWSCredentialsProviderChain())
                .build();
    }
}
