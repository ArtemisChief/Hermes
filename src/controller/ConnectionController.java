package controller;

import model.ConnectionModel;

import javax.net.ssl.*;
import java.io.*;
import java.net.Socket;
import java.security.SecureRandom;

public class ConnectionController {

    SSLContext sslContext;
    TrustManager[] trustManagers = {new X509TrustManager() {
        public void checkClientTrusted(java.security.cert.X509Certificate[] certs, String authType) {
        }

        public void checkServerTrusted(java.security.cert.X509Certificate[] certs, String authType) {
        }

        public java.security.cert.X509Certificate[] getAcceptedIssuers() {
            return null;
        }
    }};

    public ConnectionController() {
        try {
            sslContext = SSLContext.getInstance("SSL");
            sslContext.init(null, trustManagers, new SecureRandom());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public SSLSocket initConnectionWithSSL(ConnectionModel connection) {
        SSLSocket sslSocket;

        try {
            sslSocket = (SSLSocket) SSLSocketFactory.getDefault().createSocket(connection.getHost(), connection.getPort());
            sslSocket.startHandshake();

            connection.setSSLSocket(sslSocket);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

        return sslSocket;
    }

    public SSLSocket initConnectionWithTSL(ConnectionModel connection) {
        SSLSocket sslSocket;

        try {
            Socket socket = new Socket(connection.getHost(), connection.getPort());
            PrintWriter writer = new PrintWriter(socket.getOutputStream(), true);

            writer.println("STARTTLS");
            sslSocket = (SSLSocket) sslContext.getSocketFactory().createSocket(socket, "smtp.qq.com", 587, true);
            sslSocket.startHandshake();

            connection.setSSLSocket(sslSocket);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

        return sslSocket;
    }

    public void checkConnection(ConnectionModel connection) {
        SSLSocket sslSocket = connection.getSSLSocket();
        if (!sslSocket.isConnected()) {
            if (connection.getType() == 0)
                initConnectionWithSSL(connection);
            else
                initConnectionWithTSL(connection);
        }
    }

}