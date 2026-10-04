package bridge.implementor;

public class SmsChannel implements Channel {
    @Override
    public String open(String id, String recipient, String priority) {
        return "SMS[id=" + id + "; priority=" + priority + "] to=" + recipient + ": ";
    }

    @Override
    public String format(String text) {
        return text.replaceAll("\\s+", " ").trim();
    }

    @Override
    public String close() {
        return "";
    }
}
