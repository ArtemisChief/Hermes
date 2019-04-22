package controller;

import model.ConnectionModel;

import javax.net.ssl.*;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.security.SecureRandom;

public class ConnectionController {

    private ConnectionModel POP3Connection;
    private ConnectionModel SMTPConnection;

    private SSLContext sslContext;
    private TrustManager[] trustManagers = {new X509TrustManager() {
        public void checkClientTrusted(java.security.cert.X509Certificate[] certs, String authType) {
        }

        public void checkServerTrusted(java.security.cert.X509Certificate[] certs, String authType) {
        }

        public java.security.cert.X509Certificate[] getAcceptedIssuers() {
            return null;
        }
    }};

    public ConnectionController(String POP3, int POP3Port,String SMTP, int SMTPPort, String username, String password) {
        try {
            sslContext = SSLContext.getInstance("SSL");
            sslContext.init(null, trustManagers, new SecureRandom());
        } catch (Exception e) {
            e.printStackTrace();
        }

        POP3Connection = new ConnectionModel(0, POP3, POP3Port, username, password);
        SMTPConnection = new ConnectionModel(1, SMTP, SMTPPort, username, password);
    }

    private void initConnectionWithSSL(ConnectionModel connection) {
        SSLSocket sslSocket;

        try {
            sslSocket = (SSLSocket) SSLSocketFactory.getDefault().createSocket(connection.getHost(), connection.getPort());
            sslSocket.startHandshake();

            BufferedReader reader=new BufferedReader(new InputStreamReader(sslSocket.getInputStream()));
            reader.readLine();

            connection.setSSLSocket(sslSocket);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initConnectionWithTSL(ConnectionModel connection) {
        SSLSocket sslSocket;

        try {
            Socket socket = new Socket(connection.getHost(), connection.getPort());
            BufferedReader reader=new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter writer = new PrintWriter(socket.getOutputStream(), true);

            reader.readLine();
            writer.println("STARTTLS");
            reader.readLine();

            sslSocket = (SSLSocket) sslContext.getSocketFactory().createSocket(socket, connection.getHost(), connection.getPort(), true);
            sslSocket.startHandshake();

            connection.setSSLSocket(sslSocket);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void checkConnection(ConnectionModel connection) {
        SSLSocket sslSocket = connection.getSSLSocket();
        if (sslSocket==null || !sslSocket.isConnected()) {
            if (connection.getType() == 0)
                initConnectionWithSSL(connection);
            else
                initConnectionWithTSL(connection);
        }
    }

    public ConnectionModel getPOP3Connection() {
        return POP3Connection;
    }

    public ConnectionModel getSMTPConnection() {
        return SMTPConnection;
    }

}