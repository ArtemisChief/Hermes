package mail;

import connection.Connection;
import connection.POP3Connection;
import connection.SMTPConnection;
import util.DecodeUtil;
import util.EncodeUtil;

import java.nio.charset.StandardCharsets;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MailController {

    private String POP3Host;
    private int POP3Port;
    private String SMTPHost;
    private int SMTPPort;

    private String username;
    private String password;

    private BlockingQueue<MailModel> queue;
    private int mailAmount;
    private static AtomicInteger currentReading;

    public MailController(String POP3Host, int POP3Port, String SMTPHost, int SMTPPort, String username, String password) {
        this.POP3Host = POP3Host;
        this.POP3Port = POP3Port;
        this.SMTPHost = SMTPHost;
        this.SMTPPort = SMTPPort;
        this.username = username;
        this.password = password;
    }

    private Connection POP3Login() {
        Connection POP3Connection = new POP3Connection(POP3Host, POP3Port);

        POP3Connection.writeAndReadLine("user " + username);
        if (!POP3Connection.writeAndReadLine("pass " + password).contains("+OK"))
            return null;

        return POP3Connection;
    }

    private Connection SMTPLogin() {
        Connection SMTPConnection = new SMTPConnection(SMTPHost, SMTPPort);

        SMTPConnection.writeAndReadLine("HELO " + username);
        SMTPConnection.writeAndReadLine("auth login");

        SMTPConnection.writeAndReadLine(Base64.getEncoder().encodeToString(username.getBytes(StandardCharsets.UTF_8)));
        if (!SMTPConnection.writeAndReadLine(Base64.getEncoder().encodeToString(password.getBytes(StandardCharsets.UTF_8))).contains("235"))
            return null;

        return SMTPConnection;
    }

    public boolean receiveMailAmount() {
        Connection POP3Connection;

        if ((POP3Connection = POP3Login()) != null) {
            POP3Connection.write("list");

            StringBuilder stringBuilder = new StringBuilder();
            String line;

            while (!(line = POP3Connection.readLine()).equals(".")) {
                stringBuilder.append(line).append("\n");
            }
            line = stringBuilder.substring(stringBuilder.lastIndexOf("\n", stringBuilder.lastIndexOf(" ")) + 1, stringBuilder.lastIndexOf(" "));

            mailAmount = Integer.parseInt(line);

            currentReading = new AtomicInteger(mailAmount);

            POP3Connection.close();
            return true;
        } else
            return false;
    }

    public void receiveMail(int threadAmount) {
        queue = new LinkedBlockingQueue<>(threadAmount * 2);

        for (int i = 0; i < threadAmount; i++) {
            Thread receiveThread = new Thread(() -> {
                Connection POP3Connection = POP3Login();

                int count;

                while ((count = currentReading.getAndDecrement()) > 0) {

                    String top = POP3Connection.writeAndReadHead("top " + count + " 0");

                    String subject, from, sDate = "";

                    Matcher matcher = Pattern.compile("Subject: (.*\\r?\\n(\\s+.*\\r?\\n)*)").matcher(top);
                    subject = readMailHead(matcher);

                    matcher = Pattern.compile("From: (.*\\r?\\n(\\s+.*\\r?\\n)*)").matcher(top);
                    from = readMailHead(matcher);


                    matcher = Pattern.compile("\\nDate:\\s*(.*)(\\s*\\(.*\\))*\\n").matcher(top);
                    if (matcher.find()) {
                        sDate = matcher.group(1);
                    }

                    try {
                        Date date = new SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss Z", Locale.ENGLISH).parse(sDate);
                        MailModel mail = new MailModel(count, subject, from, date);
                        queue.offer(mail);
                    } catch (ParseException pe1) {
                        try {
                            Date date = new SimpleDateFormat("d MMM yyyy HH:mm:ss Z", Locale.ENGLISH).parse(sDate);
                            MailModel mail = new MailModel(count, subject, from, date);
                            queue.offer(mail);
                        } catch (ParseException pe2) {
                            try {
                                Date date = new SimpleDateFormat("EEE,MMM d yyyy HH:mm:ss Z", Locale.ENGLISH).parse(sDate);
                                MailModel mail = new MailModel(count, subject, from, date);
                                queue.offer(mail);
                            } catch (ParseException pe3) {
                                try {
                                    Date date = new SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss X", Locale.ENGLISH).parse(sDate);
                                    MailModel mail = new MailModel(count, subject, from, date);
                                    queue.offer(mail);
                                } catch (Exception e) {
                                    queue.offer((new MailModel(-1, "", "", null)));
                                }
                            }
                        }
                    }
                }
                Thread.currentThread().interrupt();
            });
            receiveThread.start();
        }
    }

    public String getMailContent(int idx) {
        String content;

        Connection POP3Connection;

        if ((POP3Connection = POP3Login()) != null) {
            content = POP3Connection.writeAndReadContent("RETR " + idx);

            // 正文类型
            String type;
            if (content.contains("Content-Type: text"))
                type = "Content-Type: text";
            else if (content.contains("Content-type: text"))
                type = "Content-type: text";
            else {
                content = "不支持的邮件正文格式（非纯文本或html）";
                POP3Connection.close();
                return content;
            }

            // 正文编码类型
            String contentInfo;

            // 获取正文编码类型与正文
            Matcher matcher = Pattern.compile("boundary=\"(.*)\"\\n").matcher(content);
            if (matcher.find()) {
                String boundary = "--" + matcher.group(1);
                if (!content.contains(boundary + "\n" + type)) {
                    content = "不支持的邮件正文格式（非纯文本或html）";
                    POP3Connection.close();
                    return content;
                }
                contentInfo = content.substring(content.indexOf(boundary + "\n" + type), content.indexOf("\n\n", content.indexOf(boundary + "\n" + type)));
                content = content.substring(content.indexOf(type));
                content = content.substring(content.indexOf("\n\n") + 2, content.indexOf(boundary));
            } else {
                contentInfo = content.substring(content.indexOf(type), content.indexOf("\n\n", content.indexOf(type)));
                content = content.substring(content.indexOf(type));
                content = content.substring(content.indexOf("\n\n") + 2);
                content = content.substring(0, content.indexOf("\n."));
            }

            // 进行解码
            content = DecodeUtil.ContentDecode(contentInfo, content);
            POP3Connection.close();
        } else
            return null;

        return content;
    }

    public boolean sendMail(String to, String subject, String from, String content) {
        Connection SMTPConnection;

        if ((SMTPConnection = SMTPLogin()) != null) {
            String date = new SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss Z", Locale.ENGLISH).format(new Date());

            SMTPConnection.writeAndReadLine("MAIL FROM:<" + from + ">");
            SMTPConnection.writeAndReadLine("RCPT TO:<" + to + ">");
            SMTPConnection.writeAndReadLine("DATA");

            SMTPConnection.write("From: \"" + EncodeUtil.BUEncode(from) + "\" <" + from + ">");
            SMTPConnection.write("To: \"" + EncodeUtil.BUEncode(to) + "\" <" + to + ">");
            SMTPConnection.write("Subject: " + EncodeUtil.BUEncode(subject));
            SMTPConnection.write("Mime-Version: 1.0");
            SMTPConnection.write("Content-Type: text/plain;");
            SMTPConnection.write(" charset=\"utf-8\"");
            SMTPConnection.write("Content-Transfer-Encoding: base64");
            SMTPConnection.write("Date: " + date);
            SMTPConnection.write("");
            SMTPConnection.write(EncodeUtil.BEncode(content));
            SMTPConnection.write("");
            SMTPConnection.writeAndReadLine(".");

            SMTPConnection.close();

            return true;
        } else
            return false;
    }

    private String readMailHead(Matcher m) {
        String[] headInfo;
        StringBuilder info = new StringBuilder();
        if (m.find()) {
            headInfo = m.group(1).split("\\n\\s+");
            for (String str : headInfo) {
                String temp = DecodeUtil.HeaderDecode(str.trim());
                info.append(temp);
            }
        }
        return info.toString();
    }

    public BlockingQueue<MailModel> getQueue() {
        return queue;
    }

}