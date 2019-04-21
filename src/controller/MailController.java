package controller;

import model.ConnectionModel;
import model.MailModel;
import util.Decode;

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

    public MailController(ConnectionModel POP3Connection, ConnectionModel SMTPConnection) {
        mailBox = new ArrayList<>();
        this.POP3Connection = POP3Connection;
        this.SMTPConnection = SMTPConnection;
    }

    public void receiveAllMail() {
        POP3Connection.write("user" + POP3Connection.getUsername());
        POP3Connection.write("pass" + POP3Connection.getPassword());

        for (int i = 0; ; i++) {
            String top = POP3Connection.writeAndReadLine("top " + i + " 0");
            if (top.contains("-ERR"))
                break;

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

            matcher = Pattern.compile("Date: (.*)\\n").matcher(top);
            if (matcher.find()) {
                sDate = matcher.group(1);
            }

            try {
                Date date = new SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss Z", Locale.ENGLISH).parse(sDate);
                MailModel mail = new MailModel(i, to, subject, from, date);
                mailBox.add(mail);
            } catch (Exception e) {
                e.printStackTrace();
            }

        }

        POP3Connection.close();
    }

    public MailModel readMail(int idx) {
        String content;

        MailModel mail = mailBox.get(idx);

        if (mail.getContent() == null) {
            POP3Connection.write("user" + POP3Connection.getUsername());
            POP3Connection.write("pass" + POP3Connection.getPassword());

            content = POP3Connection.writeAndReadLine("RETR " + idx);

            // 正文类型
            String type;
            if (content.contains("Content-Type: text"))
                type = "Content-Type: text";
            else if (content.contains("Content-type: text"))
                type = "Content-type: text";
            else {
                content = "不支持的邮件正文格式（非纯文本或html）";
                mail.setContent(content);
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
        }

        return mail;
    }

    public void sendMail(MailModel mail) {
        SMTPConnection.write("HELO " + SMTPConnection.getUsername());

        SMTPConnection.write("auth login");
        try {
            SMTPConnection.write(Base64.getEncoder().encodeToString(SMTPConnection.getUsername().getBytes(StandardCharsets.UTF_8)));
            SMTPConnection.write(Base64.getEncoder().encodeToString(SMTPConnection.getPassword().getBytes(StandardCharsets.UTF_8)));
        } catch (Exception e) {
            e.printStackTrace();
        }

        SMTPConnection.write("MAIL FROM:<" + mail.getFrom() + ">");
        SMTPConnection.write("RCPT TO:<" + mail.getTo() + ">");

        SMTPConnection.write("DATA");
        SMTPConnection.write("FROM:" + mail.getFrom());
        SMTPConnection.write("TO:" + mail.getTo());
        SMTPConnection.write("SUBJECT:" + mail.getSubject());
        SMTPConnection.write("");
        SMTPConnection.write(mail.getContent());
        SMTPConnection.write(".");

        SMTPConnection.close();
    }

    public void deleteMail(int idx) {
        POP3Connection.write("user" + POP3Connection.getUsername());
        POP3Connection.write("pass" + POP3Connection.getPassword());

        POP3Connection.write("DELE " + idx);

        POP3Connection.close();
    }

    public ArrayList<MailModel> getMailBox() {
        return mailBox;
    }

}