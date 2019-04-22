package model;

import javax.net.ssl.SSLSocket;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;

public class ConnectionModel {

    // 0: SSL
    // 1: TSL
    private int type;

    private String host;
    private int port;
    private String username;
    private String password;

    private SSLSocket sslSocket;

    private BufferedReader reader;
    private PrintWriter writer;

    public ConnectionModel(int type, String host, int port, String username, String password) {
        this.type = type;
        this.host = host;
        this.port = port;
        this.username = username;
        this.password = password;
    }

    public int getType() {
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
            reader = new BufferedReader(new InputStreamReader(this.sslSocket.getInputStream()));
            writer = new PrintWriter(this.sslSocket.getOutputStream(), true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void write(String str) {
        writer.println(str);
    }

    public String writeAndReadLine(String str) {
        writer.println(str);

        String line = "";

        try {
            line = reader.readLine();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return line;
    }

    public String writeAndReadAll(String str){
        writer.println(str);

        StringBuilder stringBuilder = new StringBuilder();

        try {
            String line;
            while((line=reader.readLine())!=null)
                stringBuilder.append(line).append("\n");
        } catch (Exception e) {
            e.printStackTrace();
        }

        return stringBuilder.toString();
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