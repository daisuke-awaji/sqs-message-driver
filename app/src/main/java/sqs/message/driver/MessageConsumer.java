package sqs.message.driver;

import java.util.List;

import software.amazon.awssdk.services.sqs.model.DeleteMessageRequest;
import software.amazon.awssdk.services.sqs.model.Message;
import software.amazon.awssdk.services.sqs.model.ReceiveMessageRequest;
import software.amazon.awssdk.services.sqs.model.SqsException;
import sqs.message.config.SqsConfiguration;

public class MessageConsumer {
    final private SqsConfiguration config;
    final private Integer maxNumberOfMessages = 10;
    final private Integer waitTimeSeconds = 1;

    public MessageConsumer(SqsConfiguration config) {
        this.config = config;
    }

    public List<Message> receive() {
        try {
            ReceiveMessageRequest receiveMessageRequest = ReceiveMessageRequest.builder()
                    .queueUrl(this.config.queueUrl())
                    .maxNumberOfMessages(this.maxNumberOfMessages)
                    .waitTimeSeconds(this.waitTimeSeconds)
                    .build();

            return this.config.client().receiveMessage(receiveMessageRequest)
                    .messages();
        } catch (SqsException e) {
            System.err.println(e.awsErrorDetails().errorMessage());
            throw e;
        }
    }

    public void delete(Message msg) {
        try {
            DeleteMessageRequest deleteMessageRequest = DeleteMessageRequest.builder().queueUrl(this.config.queueUrl())
                    .receiptHandle(msg.receiptHandle()).build();
            this.config.client().deleteMessage(deleteMessageRequest);
        } catch (SqsException e) {
            System.err.println(e.awsErrorDetails().errorMessage());
            throw e;
        }

    }
}
