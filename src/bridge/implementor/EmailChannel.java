package bridge.implementor;

public class EmailChannel implements Channel {
    @Override
    public String open(String id, String recipient, String priority) {
        return "EMAIL<to=" + recipient + "; id=" + id + "; priority=" + priority + ">";
    }

    @Override
    public String format(String text) {
        return " body=\"" + text + "\"";
    }

    @Override
    public String close() {
        return " </EMAIL>";
    }
}
