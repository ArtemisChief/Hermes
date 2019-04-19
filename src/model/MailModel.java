package model;

import java.util.Date;

public class MailModel {
    private String to;
    private String subject;
    private String content;
    private String from;
    private Date date;

    public MailModel(String to, String subject, String content, String from, Date date) {
        this.to = to;
        this.subject = subject;
        this.content = content;
        this.from = from;
        this.date=date;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
