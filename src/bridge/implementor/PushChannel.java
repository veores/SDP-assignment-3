package bridge.implementor;

public class PushChannel implements Channel {
    @Override
    public String open(String id, String recipient, String priority) {
        return "PUSH{device=" + recipient + "; id=" + id + "; priority=" + priority + "}";
    }

    @Override
    public String format(String text) {
        return " alert=\"" + text + "\"";
    }

    @Override
    public String close() {
        return "";
    }
}
