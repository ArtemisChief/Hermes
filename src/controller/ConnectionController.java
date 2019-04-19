package controller;

import model.ConnectionModel;

import javax.net.ssl.SSLSocket;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class ConnectionController {

    private ConnectionModel connection;

    private Socket socket;
    private SSLSocket sslSocket;

    private InputStream inputStream;
    private OutputStream outputStream;

    public ConnectionController(String host, int port, String username, String password) {
        connection = new ConnectionModel(host, port, username, password);
    }

    public Socket InitConnection(){
        //todo

        return socket;
    }

    public SSLSocket InitConnectionWithSSL(){
        //todo

        return sslSocket;
    }

    public void writeLine(){
        //todo
    }

    public void readLine(){
        //todo
    }

}