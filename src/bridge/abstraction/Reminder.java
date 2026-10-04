package bridge.abstraction;

import bridge.implementor.Channel;

public class Reminder extends Notification {
    private static final String PRIORITY = "NORMAL";
    private static final String PREFIX = "Reminder: ";

    public Reminder(String id, String recipient, String message, Channel channel) {
        super(id, recipient, message, channel);
    }

    @Override
    public String execute() {
        return deliver(PRIORITY, PREFIX + getMessage());
    }
}
