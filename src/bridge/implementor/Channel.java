package bridge.implementor;

public interface Channel {
    String open(String id, String recipient, String priority);

    String format(String text);

    String close();
}
