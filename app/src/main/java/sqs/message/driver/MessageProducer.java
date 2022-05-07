package sqs.message.driver;

import java.util.List;

import software.amazon.awssdk.services.sqs.model.SendMessageRequest;
import software.amazon.awssdk.services.sqs.model.SqsException;
import sqs.message.config.SqsConfiguration;

public class MessageProducer {
    final private SqsConfiguration config;
    final private Integer delaySeconds = 5;

    public MessageProducer(SqsConfiguration config) {
        this.config = config;
    }

    public void send(String message) {
        try {
            SendMessageRequest req = SendMessageRequest.builder()
                    .queueUrl(this.config.queueUrl())
                    .messageBody(message)
                    .delaySeconds(this.delaySeconds)
                    .build();

            this.config.client().sendMessage(req);

        } catch (SqsException e) {
            System.err.println(e.awsErrorDetails().errorMessage());
            throw e;
        }
    }

    public void send(List<String> messages) {
        for (String message : messages) {
            this.send(message);
        }
    }
}
