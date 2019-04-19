package controller;

import model.ConnectionModel;
import model.MailModel;
import sun.misc.BASE64Decoder;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MailController {

    private ArrayList<MailModel> mailBox;

    ConnectionModel POP3Connection;
    ConnectionModel SMTPConnection;

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

            String to = Pattern.compile("To: .*\\n").matcher(top).group().substring(4);
            if(to.contains("=?"))
                to=decode(to);
            String subject = Pattern.compile("Subject: .*\\n").matcher(top).group().substring(9);
            if(subject.contains("=?"))
                subject=decode(subject);
            String from = Pattern.compile("From: .*\\n").matcher(top).group().substring(6);
            if(from.contains("=?"))
                from=decode(from);
            String sDate=Pattern.compile("Date: .*\\n").matcher(top).group().substring(6);

            SimpleDateFormat formatter = new SimpleDateFormat("E, dd MMM yyyy HH:mm:ss X");
            try {
                Date date = formatter.parse(sDate);
                MailModel mail = new MailModel(i, to, subject, from, date);
                mailBox.add(mail);
            }
            catch (Exception e){
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
            //todo

            mail.setContent(content);

            POP3Connection.close();
        }

        return mail;
    }

    public void sendMail(MailModel mail) {
        SMTPConnection.write("HELO " + SMTPConnection.getUsername());

        SMTPConnection.write("auth login");
        try {
            SMTPConnection.write(Base64.getEncoder().encodeToString(SMTPConnection.getUsername().getBytes("UTF-8")));
            SMTPConnection.write(Base64.getEncoder().encodeToString(SMTPConnection.getPassword().getBytes("UTF-8")));
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
        POP3Connection.write("DELE " + idx);
        POP3Connection.close();
    }

    public ArrayList<MailModel> getMailBox() {
        return mailBox;
    }

    public String decode(String s){
        int start=s.indexOf("=?");
        int end=s.indexOf("?=");
        String gbkString=s.substring(start+8,end);
        BASE64Decoder decoder = new BASE64Decoder();
        try {
            byte[] b = decoder.decodeBuffer(gbkString);
            gbkString=new String(b,s.substring(start+2,s.indexOf("?B?")));
            s.replaceAll("=\\?.*\\?=",gbkString);
        }
        catch (Exception e){
        }
        return s;
    }

}