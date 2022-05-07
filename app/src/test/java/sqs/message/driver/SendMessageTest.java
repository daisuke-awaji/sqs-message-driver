package sqs.message.driver;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.sqs.SqsClient;
import software.amazon.awssdk.services.sqs.model.CreateQueueRequest;
import software.amazon.awssdk.services.sqs.model.Message;
import sqs.message.body.UserMessageBody;
import sqs.message.config.SqsConfiguration;

import java.net.URI;
import java.util.Date;
import java.util.List;

public class SendMessageTest {

    // LocalStack Cloud: https://app.localstack.cloud/resources/sqs/test
    final String endpoint = "http://localhost:4566";
    final String QUEUE_URL = endpoint + "/000000000000/test";
    final URI url = URI.create(endpoint);
    final SqsClient client = SqsClient.builder().region(Region.AP_NORTHEAST_1)
            .endpointOverride(url)
            .build();
    final SqsConfiguration config = new SqsConfiguration(client, QUEUE_URL);

    @Test
    public void sendAndReceiveMessageSucceed() {

        final String message = new UserMessageBody("123456", new Date()).toJson();
        new MessageProducer(config).send(message);

        MessageConsumer consumer = new MessageConsumer(config);
        List<Message> receivedMessages = consumer.receive();
        for (Message msg : receivedMessages) {
            UserMessageBody m = UserMessageBody.fromJson(msg.body());
            Assert.assertEquals(m.userId(), "123456");
            System.out.println("Received message: " + m.toJson());
            consumer.delete(msg);
        }
    }

    @Before
    public void beforeAll() {
        client.createQueue(CreateQueueRequest.builder().queueName("test").build());
    }

    @After
    public void cleanUp() {
        while (true) {
            MessageConsumer consumer = new MessageConsumer(config);
            List<Message> receivedMessages = consumer.receive();

            if (receivedMessages.isEmpty()) {
                break;
            }

            for (Message msg : receivedMessages) {
                consumer.delete(msg);
                System.out.println("Delete Message Request: " + msg.messageId());
            }
        }

        client.close();
    }
}
