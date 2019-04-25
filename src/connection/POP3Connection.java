package connection;

import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;

public class POP3Connection extends Connection {

    public POP3Connection(String host, int port) {
        SSLSocket sslSocket;

        try {
            sslSocket = (SSLSocket) SSLSocketFactory.getDefault().createSocket(host, port);
            sslSocket.startHandshake();

            this.sslSocket = sslSocket;

            reader = new BufferedReader(new InputStreamReader(this.sslSocket.getInputStream(), "GBK"));
            writer = new PrintWriter(this.sslSocket.getOutputStream(), true);

            reader.readLine();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}