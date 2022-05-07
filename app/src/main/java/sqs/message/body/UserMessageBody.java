package sqs.message.body;

import java.util.Date;

public class UserMessageBody extends MessageBody {
    final private String userId;
    final private Date createdAt;

    public UserMessageBody(String userId, Date createdAt) {
        this.userId = userId;
        this.createdAt = createdAt;

        if (!this.isValid()) {
            throw new IllegalArgumentException("UserMessageBody is invalid: " + this.toJson());
        }
    }

    public String userId() {
        return userId;
    }

    public Date createdAt() {
        return createdAt;
    }

    public static UserMessageBody fromJson(String json) {
        UserMessageBody body = MessageBody.fromJson(json, UserMessageBody.class);
        if (!body.isValid()) {
            throw new IllegalArgumentException("Invalid message body: " + body.toJson());
        }
        return body;
    }

    boolean isValid() {
        return this.userId != null && !this.userId.isEmpty() && this.createdAt != null;
    }
}