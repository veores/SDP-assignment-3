package bridge.abstraction;

import bridge.implementor.Channel;

public class UrgentAlert extends Notification {
    private static final String PRIORITY = "URGENT";
    private static final String PREFIX = "URGENT: ";

    public UrgentAlert(String id, String recipient, String message, Channel channel) {
        super(id, recipient, message, channel);
    }

    @Override
    public String execute() {
        return deliver(PRIORITY, PREFIX + getMessage());
    }
}
