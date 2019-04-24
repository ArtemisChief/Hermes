package controller;

import model.ConnectionModel;
import model.MailModel;
import util.Decode;
import util.Encode;

import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MailController {

    private ArrayList<MailModel> mailBox;

    private ConnectionModel POP3Connection;
    private ConnectionModel SMTPConnection;

    private int mailAmount;

    public int currentReading;

    public MailController(ConnectionModel POP3Connection, ConnectionModel SMTPConnection) {
        this.POP3Connection = POP3Connection;
        this.SMTPConnection = SMTPConnection;
    }

    private boolean mailLogin(int type) {
        if (type == 0) {
            POP3Connection.writeAndReadLine("user " + POP3Connection.getUsername());
            if (!POP3Connection.writeAndReadLine("pass " + POP3Connection.getPassword()).contains("+OK"))
                return false;
        } else {
            SMTPConnection.writeAndReadLine("auth login");

            SMTPConnection.writeAndReadLine(Base64.getEncoder().encodeToString(SMTPConnection.getUsername().getBytes(StandardCharsets.UTF_8)));
            if (!SMTPConnection.writeAndReadLine(Base64.getEncoder().encodeToString(SMTPConnection.getPassword().getBytes(StandardCharsets.UTF_8))).contains("235"))
                return false;
        }
        return true;
    }

    public boolean mailLogin() {
        POP3Connection.writeAndReadLine("user " + POP3Connection.getUsername());
        if (!POP3Connection.writeAndReadLine("pass " + POP3Connection.getPassword()).contains("+OK"))
            return false;
        return true;
    }

    public void mailLogout() {
        POP3Connection.close();
    }

    public boolean receiveMailAmount() {
        if (mailLogin(0)) {
            POP3Connection.write("list");

            StringBuilder stringBuilder = new StringBuilder();
            String line;

            while (!(line = POP3Connection.readLine()).equals(".")) {
                stringBuilder.append(line).append("\n");
            }
            line = stringBuilder.substring(stringBuilder.lastIndexOf("\n", stringBuilder.lastIndexOf(" ")) + 1, stringBuilder.lastIndexOf(" "));

            currentReading = mailAmount = Integer.parseInt(line);
            mailBox = new ArrayList<>(mailAmount);
            POP3Connection.close();
            return true;
        } else
            return false;
    }

    public synchronized boolean receiveMail() {
        System.out.println(currentReading);
        String top = POP3Connection.writeAndReadHead("top " + currentReading + " 0");

        String to = "", subject = "", from = "", sDate = "";

        Matcher matcher = Pattern.compile("To: (.*\\r?\\n(\\s+.*\\r?\\n)*)").matcher(top);
        if (matcher.find()) {
            to = matcher.group(1).replaceAll("\\n\\s+", "");
            if (to.contains("=?"))
                to = Decode.HeaderDecode(to);
        }

        matcher = Pattern.compile("Subject: (.*\\r?\\n(\\s+.*\\r?\\n)*)").matcher(top);
        if (matcher.find()) {
            subject = matcher.group(1).replaceAll("\\n\\s+", "");
            if (subject.contains("=?"))
                subject = Decode.HeaderDecode(subject);
        }

        matcher = Pattern.compile("From: (.*\\r?\\n(\\s+.*\\r?\\n)*)").matcher(top);
        if (matcher.find()) {
            from = matcher.group(1).replaceAll("\\n\\s+", "");
            if (from.contains("=?"))
                from = Decode.HeaderDecode(from);
        }

        matcher = Pattern.compile("(?:^|\\n)Date: (.*)\\n").matcher(top);
        if (matcher.find()) {
            sDate = matcher.group(1);
        }

        try {
            Date date = new SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss Z", Locale.ENGLISH).parse(sDate);
            MailModel mail = new MailModel(currentReading, to, subject, from, date);
            mailBox.add(mailAmount - currentReading, mail);
            currentReading--;
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public MailModel readMail(int idx) {
        String content;

        MailModel mail = mailBox.get(mailAmount - idx);

        if (mail.getContent().equals("")) {
            if (mailLogin(0)) {
                content = POP3Connection.writeAndReadContent("RETR " + idx);

                // 正文类型
                String type;
                if (content.contains("Content-Type: text"))
                    type = "Content-Type: text";
                else if (content.contains("Content-type: text"))
                    type = "Content-type: text";
                else {
                    content = "不支持的邮件正文格式（非纯文本或html）";
                    mail.setContent(content);
                    POP3Connection.close();
                    return mail;
                }

                // 正文编码类型
                String contentInfo;

                // 获取正文编码类型与正文
                Matcher matcher = Pattern.compile("boundary=\"(.*)\"\\n").matcher(content);
                if (matcher.find()) {
                    String boundary = "--" + matcher.group(1);
                    if (!content.contains(boundary + "\n" + type)) {
                        content = "不支持的邮件正文格式（非纯文本或html）";
                        mail.setContent(content);
                        POP3Connection.close();
                        return mail;
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
                content = Decode.ContentDecode(contentInfo, content);
                mail.setContent(content);
                POP3Connection.close();
            } else
                return null;
        }
        return mail;
    }

    private boolean sendMail(MailModel mail) {
        SMTPConnection.writeAndReadLine("HELO " + SMTPConnection.getUsername());

        if (mailLogin(1)) {
            String from = mail.getFrom(), to = mail.getTo(), subject = mail.getSubject(), content = mail.getContent();
            String date = new SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss Z", Locale.ENGLISH).format(mail.getDate());

            SMTPConnection.writeAndReadLine("MAIL FROM:<" + from + ">");
            SMTPConnection.writeAndReadLine("RCPT TO:<" + to + ">");
            SMTPConnection.writeAndReadLine("DATA");

            SMTPConnection.write("From: \"" + Encode.BUEncode(from) + "\" <" + from + ">");
            SMTPConnection.write("To: \"" + Encode.BUEncode(to) + "\" <" + to + ">");
            SMTPConnection.write("Subject: " + Encode.BUEncode(subject));
            SMTPConnection.write("Mime-Version: 1.0");
            SMTPConnection.write("Content-Type: text/plain;");
            SMTPConnection.write(" charset=\"utf-8\"");
            SMTPConnection.write("Content-Transfer-Encoding: base64");
            SMTPConnection.write("Date: " + date);
            SMTPConnection.write("");
            SMTPConnection.write(Encode.BEncode(content));
            SMTPConnection.write("");
            SMTPConnection.writeAndReadLine(".");

            SMTPConnection.close();

            return true;
        } else
            return false;
    }

    public boolean sendMail(String to, String subject, String from, String content) {
        return sendMail(new MailModel(mailBox.size() + 1, to, subject, from, content));
    }

//    public boolean deleteMail(int idx) {
//        if (mailLogin(0)) {
//            POP3Connection.write("DELE " + idx);
//            POP3Connection.close();
//            return true;
//        } else
//            return false;
//    }

    public synchronized ArrayList<MailModel> getMailBox() {
        return mailBox;
    }

    public int getCurrentReading() {
        return currentReading;
    }
}