package sqs.message.body;

import com.google.gson.Gson;

public abstract class MessageBody {

    public String toJson() {
        return new Gson().toJson(this);
    }

    public static <T extends MessageBody> T fromJson(String json, Class<T> clazz) {
        return new Gson().fromJson(json, clazz);
    }

    abstract boolean isValid();
}