import bridge.abstraction.Notification;
import bridge.abstraction.Reminder;
import bridge.abstraction.UrgentAlert;
import bridge.implementor.Channel;
import bridge.implementor.EmailChannel;
import bridge.implementor.PushChannel;
import bridge.implementor.SmsChannel;

public class Main {
    private static final String RECIPIENT = "Beka";
    private static final String REMINDER_TEXT = "Lecture at 10:00";
    private static final String ALERT_TEXT = "Server is down";

    private static final String REMINDER_EMAIL =
            "EMAIL<to=Beka; id=R-1; priority=NORMAL> body=\"Reminder: Lecture at 10:00\" </EMAIL>";
    private static final String REMINDER_SMS =
            "SMS[id=R-1; priority=NORMAL] to=Beka: Reminder: Lecture at 10:00";
    private static final String ALERT_EMAIL =
            "EMAIL<to=Beka; id=U-1; priority=URGENT> body=\"URGENT: Server is down\" </EMAIL>";
    private static final String ALERT_SMS =
            "SMS[id=U-1; priority=URGENT] to=Beka: URGENT: Server is down";

    private static final String REMINDER_PUSH =
            "PUSH{device=Beka; id=R-1; priority=NORMAL} alert=\"Reminder: Lecture at 10:00\"";
    private static final String ALERT_PUSH =
            "PUSH{device=Beka; id=U-1; priority=URGENT} alert=\"URGENT: Server is down\"";

    private static int passed;
    private static int total;

    public static void main(String[] args) {
        if (args.length == 1 && args[0].equals("--demo")) {
            runDemo();
        } else {
            System.out.println("Usage: java -cp out Main --demo");
        }
    }

    private static void runDemo() {
        Channel email = new EmailChannel();
        Channel sms = new SmsChannel();
        Channel push = new PushChannel();

        checkCombination("T1", new Reminder("R-1", RECIPIENT, REMINDER_TEXT, email), email, REMINDER_EMAIL);
        checkCombination("T2", new Reminder("R-1", RECIPIENT, REMINDER_TEXT, sms), sms, REMINDER_SMS);
        checkCombination("T3", new UrgentAlert("U-1", RECIPIENT, ALERT_TEXT, email), email, ALERT_EMAIL);
        checkCombination("T4", new UrgentAlert("U-1", RECIPIENT, ALERT_TEXT, sms), sms, ALERT_SMS);
        checkRuntimeSwitch("T5", email, sms);
        checkCombination("T6", new Reminder("R-1", RECIPIENT, REMINDER_TEXT, push), push, REMINDER_PUSH);
        checkCombination("T7", new UrgentAlert("U-1", RECIPIENT, ALERT_TEXT, push), push, ALERT_PUSH);

        System.out.println("SUMMARY: " + passed + "/" + total + " PASS");
    }

    private static void checkCombination(String id, Notification notification, Channel channel, String expected) {
        String actual = notification.execute();
        String participants = notification.getClass().getSimpleName() + " + " + channel.getClass().getSimpleName();
        report(id, expected.equals(actual), participants, "result=" + actual, "expected=" + expected);
    }

    private static void checkRuntimeSwitch(String id, Channel first, Channel second) {
        Notification original = new Reminder("R-1", RECIPIENT, REMINDER_TEXT, first);
        String stateBefore = stateOf(original);
        String before = original.execute();

        Notification current = switchImplementation(original, second);
        String after = current.execute();

        boolean sameObject = original == current;
        boolean stateUnchanged = stateBefore.equals(stateOf(current));
        boolean passedCheck = sameObject && stateUnchanged && REMINDER_EMAIL.equals(before) && REMINDER_SMS.equals(after);

        String details = "stateUnchanged=" + stateUnchanged + " before=" + before + " | after=" + after;
        String expected = "before=" + REMINDER_EMAIL + " | after=" + REMINDER_SMS;
        report(id, passedCheck, "sameObject=" + sameObject, details, "expected=" + expected);
    }

    private static Notification switchImplementation(Notification notification, Channel channel) {
        notification.setImplementation(channel);
        return notification;
    }

    private static String stateOf(Notification notification) {
        return notification.getId() + "|" + notification.getRecipient() + "|" + notification.getMessage();
    }

    private static void report(String id, boolean ok, String participants, String details, String expected) {
        total++;
        if (ok) {
            passed++;
        }
        String line = id + " " + (ok ? "PASS" : "FAIL") + " | " + participants + " | " + details;
        System.out.println(ok ? line : line + " | " + expected);
    }
}
