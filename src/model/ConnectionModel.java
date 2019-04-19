package model;

import java.io.*;
import javax.net.ssl.SSLSocket;

public class ConnectionModel {

    // 0: SSL
    // 1: TSL
    private byte type;

    private String host;
    private int port;
    private String username;
    private String password;

    private SSLSocket sslSocket;

    private BufferedReader reader;
    private PrintWriter writer;

    public ConnectionModel(byte type, String host, int port, String username, String password) {
        this.type = type;
        this.host = host;
        this.port = port;
        this.username = username;
        this.password = password;
    }

    public byte getType() {
        return type;
    }

    public String getHost() {
        return host;
    }

    public int getPort() {
        return port;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public SSLSocket getSSLSocket() {
        return sslSocket;
    }

    public void setSSLSocket(SSLSocket sslSocket) {
        this.sslSocket = sslSocket;

        try {
            reader = new BufferedReader(new InputStreamReader(sslSocket.getInputStream()));
            writer = new PrintWriter(sslSocket.getOutputStream(), true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void write(String str) {
        writer.write(str);
    }

    public String writeAndReadLine(String str) {
        writer.write(str);

        String line = "";

        try {
            line = reader.readLine();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return line;
    }

    public void close() {
        try {
            write("QUIT");
            sslSocket.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}