package connection;

import javax.net.ssl.SSLSocket;
import java.io.BufferedReader;
import java.io.PrintWriter;

public class Connection {

    SSLSocket sslSocket;

    BufferedReader reader;
    PrintWriter writer;

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

    public String readLine() {
        try {
            return reader.readLine();
        } catch (Exception e) {
            return null;
        }
    }

    public String writeAndReadHead(String str) {
        writer.println(str);

        StringBuilder stringBuilder = new StringBuilder();

        try {
            String line;
            while ((line = reader.readLine()) != null && !line.equals("."))
                stringBuilder.append(line).append("\n");
        } catch (Exception e) {
            e.printStackTrace();
        }

        return stringBuilder.toString();
    }

    public String writeAndReadContent(String str) {
        writer.println(str);

        StringBuilder stringBuilder = new StringBuilder();

        try {
            String line;
            boolean emptyline = false;
            while ((line = reader.readLine()) != null) {
                stringBuilder.append(line).append("\n");
                if (emptyline && line.equals("."))
                    break;
                emptyline = line.equals("");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return stringBuilder.toString();
    }

    public void close() {
        try {
            write("QUIT");
            writer.close();
            reader.close();
            sslSocket.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}