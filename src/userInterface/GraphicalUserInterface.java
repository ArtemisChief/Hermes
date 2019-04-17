/*
 * Created by JFormDesigner on Mon Apr 08 17:24:59 CST 2019
 */

package userInterface;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.Socket;
import java.security.SecureRandom;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import javax.swing.*;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

/**
 * @author Chief
 */
public class GraphicalUserInterface extends JFrame {

    InputStream inputStream;
    OutputStream outputStream;
    BufferedReader reader;
    PrintWriter writer;
    int status=0;

    public GraphicalUserInterface() {
        initComponents();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        try {
            Socket socket=new Socket("smtp.qq.com", 587);
            inputStream = socket.getInputStream();
            outputStream = socket.getOutputStream();
            reader = new BufferedReader(new InputStreamReader(inputStream));
            writer = new PrintWriter(outputStream, true);

            textArea1.setText(reader.readLine()+"\n\n"+"STARTTLS"+"\n");
            writer.println("STARTTLS");
            textArea1.setText(textArea1.getText()+reader.readLine()+"\n\n");
            textArea1.setCaretPosition(textArea1.getText().lastIndexOf("\n")+1);

            SSLContext context=SSLContext.getInstance("SSL");
            TrustManager[] tm = { new X509TrustManager() {
                public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                    return null;
                }
                public void checkClientTrusted(java.security.cert.X509Certificate[] certs, String authType) {
                }
                public void checkServerTrusted(java.security.cert.X509Certificate[] certs, String authType) {
                }
            } };

            context.init(null, tm, new SecureRandom());
            SSLSocket sslSocket =(SSLSocket) context.getSocketFactory().createSocket(socket,"smtp.qq.com", 587,true);
            sslSocket.startHandshake();

            inputStream=sslSocket.getInputStream();
            outputStream=sslSocket.getOutputStream();
            reader=new BufferedReader(new InputStreamReader(inputStream));
            writer=new PrintWriter(outputStream,true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void textArea1KeyPressed(KeyEvent e) {
        try {
            if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                String input = textArea1.getText();
                int last = input.lastIndexOf("\n") + 1;
                String lastLine = input.substring(last);

                if (lastLine.equals("DATA")) {
                    status = 1;
                } else if (lastLine.equals("."))
                    status = 0;

                writer.println(lastLine);
                if (status == 0) {
                    input += "\n" + reader.readLine() + "\n";
                    textArea1.setText(input);
                } else if (status == 1) {
                    input += "\n" + reader.readLine() + "\n";
                    textArea1.setText(input);
                    status = 2;
                }
            }
        } catch (Exception e1) {
            e1.printStackTrace();
        }
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        panel1 = new JPanel();
        scrollPane1 = new JScrollPane();
        textArea1 = new JTextArea();
        scrollPane2 = new JScrollPane();
        textArea2 = new JTextArea();

        //======== this ========
        setResizable(false);
        Container contentPane = getContentPane();
        contentPane.setLayout(new GridLayout());

        //======== panel1 ========
        {
            panel1.setLayout(new GridLayout());

            //======== scrollPane1 ========
            {

                //---- textArea1 ----
                textArea1.setFont(new Font("Microsoft YaHei", Font.PLAIN, 14));
                textArea1.setBorder(null);
                textArea1.addKeyListener(new KeyAdapter() {
                    @Override
                    public void keyPressed(KeyEvent e) {
                        textArea1KeyPressed(e);
                    }
                });
                scrollPane1.setViewportView(textArea1);
            }
            panel1.add(scrollPane1);

            //======== scrollPane2 ========
            {

                //---- textArea2 ----
                textArea2.setFont(new Font("Microsoft YaHei", Font.PLAIN, 14));
                textArea2.setBorder(null);
                scrollPane2.setViewportView(textArea2);
            }
            panel1.add(scrollPane2);
        }
        contentPane.add(panel1);
        setSize(690, 795);
        setLocationRelativeTo(getOwner());
        // JFormDesigner - End of component initialization  //GEN-END:initComponents
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
    private JPanel panel1;
    private JScrollPane scrollPane1;
    private JTextArea textArea1;
    private JScrollPane scrollPane2;
    private JTextArea textArea2;
    // JFormDesigner - End of variables declaration  //GEN-END:variables
}
