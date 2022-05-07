package sqs.message.config;

import software.amazon.awssdk.services.sqs.SqsClient;

public class SqsConfiguration {
    private SqsClient client;
    private String queueUrl;

    public SqsConfiguration(SqsClient client, String queueUrl) {
        this.setClient(client);
        this.setQueueUrl(queueUrl);
    }

    final public SqsClient client() {
        return client;
    }

    private void setClient(SqsClient client) {
        this.client = client;
    }

    final public String queueUrl() {
        return queueUrl;
    }

    private void setQueueUrl(String queueUrl) {
        this.queueUrl = queueUrl;
    }
}
