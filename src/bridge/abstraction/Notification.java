package bridge.abstraction;

import bridge.implementor.Channel;
import java.util.Objects;

public abstract class Notification {
    private final String id;
    private final String recipient;
    private final String message;
    private Channel channel;

    protected Notification(String id, String recipient, String message, Channel channel) {
        this.id = id;
        this.recipient = recipient;
        this.message = message;
        this.channel = Objects.requireNonNull(channel);
    }

    public void setImplementation(Channel channel) {
        this.channel = Objects.requireNonNull(channel);
    }

    public abstract String execute();

    public String getId() {
        return id;
    }

    public String getRecipient() {
        return recipient;
    }

    public String getMessage() {
        return message;
    }

    protected String deliver(String priority, String text) {
        return channel.open(id, recipient, priority) + channel.format(text) + channel.close();
    }
}
