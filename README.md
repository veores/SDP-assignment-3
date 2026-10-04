# Assignment 3 – Bridge Pattern (Notifications)

- **Name:** Bekarys Zharylgassyn
- **Group:** SE-2526
- **Topic:** B – Notifications (A1 Reminder, A2 UrgentAlert; I1 EmailChannel, I2 SmsChannel, I3 PushChannel)
- **Repository:** https://github.com/veores/SDP-assignment-3
- **Base commit (working I1/I2 version):** `f88c6548bc37cd78f46226d7384fc5e721f1a2da`

## Role map

| Role | Class | Source path |
|---|---|---|
| Abstraction | Notification | src/bridge/abstraction/Notification.java |
| A1 | Reminder | src/bridge/abstraction/Reminder.java |
| A2 | UrgentAlert | src/bridge/abstraction/UrgentAlert.java |
| Implementor | Channel | src/bridge/implementor/Channel.java |
| I1 | EmailChannel | src/bridge/implementor/EmailChannel.java |
| I2 | SmsChannel | src/bridge/implementor/SmsChannel.java |
| I3 | PushChannel | src/bridge/implementor/PushChannel.java |
| Client | Main | src/Main.java |

Key locations:

- Bridge field: `private Channel channel` in Notification (set in the constructor).
- `execute()`: abstract in Notification, implemented in Reminder and UrgentAlert; both call `deliver(...)`, which calls `channel.open/format/close`.
- `setImplementation(Channel)`: Notification.
- T5 check: `Main.checkRuntimeSwitch`.

## Commands

```
javac --release 17 -encoding UTF-8 -d out "@sources.txt"
java -cp out Main --demo
```

## Expected results

| Check | Setup | Expected result |
|---|---|---|
| T1 | Reminder + EmailChannel | `EMAIL<to=Beka; id=R-1; priority=NORMAL> body="Reminder: Team meeting at 10:00" </EMAIL>` |
| T2 | Reminder + SmsChannel | `SMS[id=R-1; priority=NORMAL] to=Beka: Reminder: Team meeting at 10:00` |
| T3 | UrgentAlert + EmailChannel | `EMAIL<to=Beka; id=U-1; priority=URGENT> body="URGENT: Server is down" </EMAIL>` |
| T4 | UrgentAlert + SmsChannel | `SMS[id=U-1; priority=URGENT] to=Beka: URGENT: Server is down` |
| T5 | one Reminder: Email, then Sms | sameObject=true, stateUnchanged=true, before = T1 result, after = T2 result |
| T6 | Reminder + PushChannel | `PUSH{device=Beka; id=R-1; priority=NORMAL} alert="Reminder: Team meeting at 10:00"` |
| T7 | UrgentAlert + PushChannel | `PUSH{device=Beka; id=U-1; priority=URGENT} alert="URGENT: Server is down"` |

Final line: `SUMMARY: 7/7 PASS`. Captured output: demo-output.txt. Extension diff: extension.diff.
