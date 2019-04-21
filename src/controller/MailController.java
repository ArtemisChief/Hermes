package controller;

import model.ConnectionModel;
import model.MailModel;
import sun.misc.BASE64Decoder;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MailController {

    private ArrayList<MailModel> mailBox;
    private final static byte TAB 	= 0x09;     // /t
    private final static byte LF 		= 0x0A;     // /n
    private final static byte CR 		= 0x0D;     // /r

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

            Matcher m=Pattern.compile("To: (.*\\r?\\n(\\s+.*\\r?\\n)*)").matcher(top);
            String to="",subject="",from="",sDate="";
            if(m.find()) {
                to = m.group(1).replaceAll("\\n\\s+", "");
                if (to.contains("=?"))
                    to = decode(to);
            }
            m=Pattern.compile("Subject: (.*\\r?\\n(\\s+.*\\r?\\n)*)").matcher(top);
            if(m.find()) {
                subject = m.group(1).replaceAll("\\n\\s+", "");
                if (subject.contains("=?"))
                    subject = decode(subject);
            }
            m=Pattern.compile("From: (.*\\r?\\n(\\s+.*\\r?\\n)*)").matcher(top);
            if(m.find()) {
                from = m.group(1).replaceAll("\\n\\s+", "");
                if (from.contains("=?"))
                    from = decode(from);
            }
            m=Pattern.compile("Date: (.*)\\n").matcher(top);
            if(m.find()) {
                sDate = m.group(1);
            }
            try {
                Date date = new SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss Z", Locale.ENGLISH).parse(sDate);
                MailModel mail = new MailModel(i, to, subject, from, date);
                mailBox.add(mail);
            }
            catch (Exception e){
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


            String type;
            if(content.contains("Content-Type: text"))
                type="Content-Type: text";
            else if(content.contains("Content-type: text"))
                type="Content-type: text";
            else {
                content="不支持的邮件正文格式（非纯文本或html）";
                mail.setContent(content);
                return mail;
            }

            Matcher m=Pattern.compile("boundary=\"(.*)\"\\n").matcher(content);
            String contentInfo;
            if(m.find()) {
                String boundary="--"+m.group(1);
                if(!content.contains(boundary+"\n"+type)) {
                    content = "不支持的邮件正文格式（非纯文本或html）";
                    mail.setContent(content);
                    return mail;
                }
                contentInfo=content.substring(content.indexOf(boundary+"\n"+type), content.indexOf("\n\n",content.indexOf(boundary+"\n"+type)));
                content = content.substring(content.indexOf(type));
                content = content.substring(content.indexOf("\n\n") + 2, content.indexOf(boundary)); //此时获得正文部分
            }
            else {
                contentInfo = content.substring(content.indexOf(type), content.indexOf("\n\n",content.indexOf(type)));
                content = content.substring(content.indexOf(type));
                content = content.substring(content.indexOf("\n\n") + 2);
                content = content.substring(0,content.indexOf("\n."));//此时获得正文部分
            }

            //进行解码
            if(contentInfo.contains("base64")){
                BASE64Decoder decoder = new BASE64Decoder();
                try {
                    byte[] b = decoder.decodeBuffer(content);
                    if(contentInfo.contains("GB")||contentInfo.contains("gb"))
                        content = new String(b, "GBK");
                    else if(contentInfo.contains("utf-8")||contentInfo.contains("UTF-8"))
                        content = new String(b, "UTF-8");
                    else if(contentInfo.contains("ISO-8859-1")||contentInfo.contains("iso-8859-1"))
                        content=new String(b,"ISO-8859-1");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            else if(contentInfo.contains("quoted-printable")){
                if(contentInfo.contains("GB")||contentInfo.contains("gb")) {
                    try {
                        byte[] b = content.getBytes("GBK");
                        content = Qdecode(b, "GBK");
                    }
                    catch (Exception e){
                        e.printStackTrace();
                    }
                }
                else if(contentInfo.contains("utf-8")||contentInfo.contains("UTF-8")){
                    try {
                        byte[] b = content.getBytes("UTF-8");
                        content = Qdecode(b, "UTF-8");
                    }
                    catch (Exception e){
                        e.printStackTrace();
                    }
                }
                else if(contentInfo.contains("ISO-8859-1")||contentInfo.contains("iso-8859-1")){
                    try {
                        byte[] b = content.getBytes("ISO-8859-1");
                        content = Qdecode(b, "ISO-8859-1");
                    }
                    catch (Exception e){
                        e.printStackTrace();
                    }
                }
            }

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
        while(s.contains("=?")) {
            int end = s.indexOf("?=");
            //base64解码
            if(s.contains("?B?")){
                int start=s.indexOf("?B?");
                String decodeString = s.substring(start + 3, end);
                BASE64Decoder decoder = new BASE64Decoder();
                try {
                    byte[] b = decoder.decodeBuffer(decodeString);
                    decodeString = new String(b, s.substring(s.indexOf("=?")+2, s.indexOf("?B?")).toUpperCase());
                    s=s.replaceFirst("=\\?.*\\?=", decodeString);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            //Quoted-Printable解码
            else if(s.contains("?Q?")){
                int start=s.indexOf("?Q?");
                String decodeString = s.substring(start + 3, end);
                try {
                    byte[] qp = decodeString.getBytes(s.substring(s.indexOf("=?") + 2, s.indexOf("?Q?")).toUpperCase());
                    decodeString = Qdecode(qp, s.substring(s.indexOf("=?") + 2, s.indexOf("?Q?")).toUpperCase());
                    s=s.replaceFirst("=\\?.*\\?=", decodeString);
                }
                catch (Exception e){
                    e.printStackTrace();
                }
            }
        }
        return s;
    }





    /**
     * A method to decode quoted printable encoded data.
     * It overrides the same input byte array to save memory. Can be done
     * because the result is surely smaller than the input.
     *
     * @param qp
     *         a byte array to decode.
     * @return the length of the decoded array.
     */
    public static int Qdecode(byte [] qp) {
        int qplen = qp.length;
        int retlen = 0;

        for (int i=0; i < qplen; i++) {
            // Handle encoded chars
            if (qp[i] == '=') {
                if (qplen - i > 2) {
                    // The sequence can be complete, check it
                    if (qp[i+1] == CR && qp[i+2] == LF) {
                        // soft line break, ignore it
                        i += 2;
                        continue;

                    } else if (isHexDigit(qp[i+1]) && isHexDigit(qp[i+2]) ) {
                        // convert the number into an integer, taking
                        // the ascii digits stored in the array.
                        qp[retlen++]=(byte)(getHexValue(qp[i+1])*16
                                + getHexValue(qp[i+2]));

                        i += 2;
                        continue;

                    }
                }
                // In all wrong cases leave the original bytes
                // (see RFC 2045). They can be incomplete sequence,
                // or a '=' followed by non hex digit.
            }

            // RFC 2045 says to exclude control characters mistakenly
            // present (unencoded) in the encoded stream.
            // As an exception, we keep unencoded tabs (0x09)
            if( (qp[i] >= 0x20 && qp[i] <= 0x7f) ||
                    qp[i] == TAB || qp[i] == CR || qp[i] == LF) {
                qp[retlen++] = qp[i];
            }
        }

        return retlen;
    }

    private static boolean isHexDigit(byte b) {
        return ( (b>=0x30 && b<=0x39) || (b>=0x41&&b<=0x46) );
    }

    private static byte getHexValue(byte b) {
        return (byte)Character.digit((char)b, 16);
    }

    /**
     *
     * @param qp Byte array to decode
     * @param enc The character encoding of the returned string
     * @return The decoded string.
     */
    public static String Qdecode(byte[] qp, String enc) {
        int len=Qdecode(qp);
        try {
            return new String(qp, 0, len, enc);
        } catch (UnsupportedEncodingException e) {
            return new String(qp, 0, len);
        }
    }

//    public void test(){
//        try {
//            File file = new File("D:\\test.txt");
//            InputStreamReader isr = new InputStreamReader(new FileInputStream(file), "utf-8");
//            BufferedReader br = new BufferedReader(isr);
//            String top = "";
//            String temp;
//            while((temp=br.readLine())!=null)
//                top+=temp+"\n";
//
//            String content=top;
//
//            String type;
//            if(content.contains("Content-Type: text"))
//                type="Content-Type: text";
//            else if(content.contains("Content-type: text"))
//                type="Content-type: text";
//            else {
//                content="不支持的邮件正文格式（非纯文本或html）";
//                return;
//            }
//
//                Matcher m=Pattern.compile("boundary=\"(.*)\"\\n").matcher(content);
//                String contentInfo;
//                if(m.find()) {
//                    String boundary="--"+m.group(1);
//                    if(!content.contains(boundary+"\n"+type)) {
//                        content = "不支持的邮件正文格式（非纯文本或html）";
//                        return;
//                    }
//                    contentInfo=content.substring(content.indexOf(boundary+"\n"+type), content.indexOf("\n\n",content.indexOf(boundary+"\n"+type)));
//                    content = content.substring(content.indexOf(type));
//                    content = content.substring(content.indexOf("\n\n") + 2, content.indexOf(boundary)); //此时获得正文部分
//                }
//                else {
//                    contentInfo = content.substring(content.indexOf(type), content.indexOf("\n\n",content.indexOf(type)));
//                    content = content.substring(content.indexOf(type));
//                    content = content.substring(content.indexOf("\n\n") + 2);
//                    content = content.substring(0,content.indexOf("\n."));//此时获得正文部分
//                }
//
//                //进行解码
//                if(contentInfo.contains("base64")){
//                    BASE64Decoder decoder = new BASE64Decoder();
//                    try {
//                        byte[] b = decoder.decodeBuffer(content);
//                        if(contentInfo.contains("GB")||contentInfo.contains("gb"))
//                            content = new String(b, "GBK");
//                        else if(contentInfo.contains("utf-8")||contentInfo.contains("UTF-8"))
//                            content = new String(b, "UTF-8");
//                        else if(contentInfo.contains("ISO-8859-1")||contentInfo.contains("iso-8859-1"))
//                            content=new String(b,"ISO-8859-1");
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }
//                }
//                else if(contentInfo.contains("quoted-printable")){
//                    if(contentInfo.contains("GB")||contentInfo.contains("gb")) {
//                        try {
//                            byte[] b = content.getBytes("GBK");
//                            content = Qdecode(b, "GBK");
//                        }
//                        catch (Exception e){
//                            e.printStackTrace();
//                        }
//                    }
//                    else if(contentInfo.contains("utf-8")||contentInfo.contains("UTF-8")){
//                        try {
//                            byte[] b = content.getBytes("UTF-8");
//                            content = Qdecode(b, "UTF-8");
//                        }
//                        catch (Exception e){
//                            e.printStackTrace();
//                        }
//                    }
//                    else if(contentInfo.contains("ISO-8859-1")||contentInfo.contains("iso-8859-1")){
//                        try {
//                            byte[] b = content.getBytes("ISO-8859-1");
//                            content = Qdecode(b, "ISO-8859-1");
//                        }
//                        catch (Exception e){
//                            e.printStackTrace();
//                        }
//                    }
//                }
//                System.out.println(content);
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }


}