package util;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class Encode {

    public static String BUEncode(String str) {
        str = new String(Base64.getEncoder().encode(str.getBytes(StandardCharsets.UTF_8)));
        StringBuilder stringBuilder = new StringBuilder(str);
        stringBuilder.insert(0, "=?UTF-8?B?").append("?=");
        return stringBuilder.toString();
    }

    public static String BEncode(String str) {
        return new String(Base64.getEncoder().encode(str.getBytes(StandardCharsets.UTF_8)));
    }

}
