package connection;

import util.SSLContextUtil;

import javax.net.ssl.SSLSocket;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class SMTPConnection extends Connection {

    public SMTPConnection(String host, int port) {
        SSLSocket sslSocket;

        try {
            Socket socket = new Socket(host, port);
            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter writer = new PrintWriter(socket.getOutputStream(), true);

            reader.readLine();
            writer.println("STARTTLS");
            reader.readLine();

            sslSocket = (SSLSocket) SSLContextUtil.sslContext.getSocketFactory().createSocket(socket, host, port, true);
            sslSocket.startHandshake();

            this.sslSocket = sslSocket;

            this.reader = new BufferedReader(new InputStreamReader(this.sslSocket.getInputStream(), "GBK"));
            this.writer = new PrintWriter(this.sslSocket.getOutputStream(), true);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}