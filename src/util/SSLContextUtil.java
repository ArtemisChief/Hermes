package util;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.security.SecureRandom;

public class SSLContextUtil {

    public static SSLContext sslContext;

    private static TrustManager[] trustManagers = {new X509TrustManager() {
        public void checkClientTrusted(java.security.cert.X509Certificate[] certs, String authType) {
        }

        public void checkServerTrusted(java.security.cert.X509Certificate[] certs, String authType) {
        }

        public java.security.cert.X509Certificate[] getAcceptedIssuers() {
            return null;
        }
    }};

    public static void Init() {
        try {
            sslContext = SSLContext.getInstance("SSL");
            sslContext.init(null, trustManagers, new SecureRandom());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}