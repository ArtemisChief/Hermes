package mail;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Vector;

public class MailModel {

    private int idx;
    private String subject;
    private String from;
    private Date date;

    MailModel(int idx, String subject, String from, Date date) {
        this.idx = idx;
        this.subject = subject;
        this.from = from;
        this.date = date;
    }

    @SuppressWarnings("unchecked")
    public Vector getHeadInfo() {
        Vector v = new Vector();
        v.add(idx);
        v.add(from);
        v.add(subject);
        v.add(new SimpleDateFormat("yyyy-MMM-d HH:mm:ss Z", Locale.ENGLISH).format(date));
        return v;
    }

}