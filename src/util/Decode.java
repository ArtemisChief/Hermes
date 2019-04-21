package util;

import sun.misc.BASE64Decoder;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;

public class Decode {

    private final static byte TAB = 0x09;     // /t
    private final static byte LF = 0x0A;     // /n
    private final static byte CR = 0x0D;     // /r

    private static boolean IsHexDigit(byte b) {
        return ((b >= 0x30 && b <= 0x39) || (b >= 0x41 && b <= 0x46));
    }

    private static byte GetHexValue(byte b) {
        return (byte) Character.digit((char) b, 16);
    }

    /**
     * A method to HeaderDecode quoted printable encoded data.
     * It overrides the same input byte array to save memory. Can be done
     * because the result is surely smaller than the input.
     *
     * @param qp a byte array to HeaderDecode.
     * @return the length of the decoded array.
     */
    private static int Qdecode(byte[] qp) {
        int qplen = qp.length;
        int retlen = 0;

        for (int i = 0; i < qplen; i++) {
            // Handle encoded chars
            if (qp[i] == '=') {
                if (qplen - i > 2) {
                    // The sequence can be complete, check it
                    if (qp[i + 1] == CR && qp[i + 2] == LF) {
                        // soft line break, ignore it
                        i += 2;
                        continue;

                    } else if (IsHexDigit(qp[i + 1]) && IsHexDigit(qp[i + 2])) {
                        // convert the number into an integer, taking
                        // the ascii digits stored in the array.
                        qp[retlen++] = (byte) (GetHexValue(qp[i + 1]) * 16
                                + GetHexValue(qp[i + 2]));

                        i += 2;
                        continue;

                    }
                }
                // In all wrong cases leave the original bytes
                // (see RFC 2045). They can be incomplete sequence,
                // or a '=' followed by non hex digit.
            }

            // RFC 2045 says to exclude control characters mistakenly
            // present (unencoded) in the encoded stream.
            // As an exception, we keep unencoded tabs (0x09)
            if ((qp[i] >= 0x20 && qp[i] <= 0x7f) ||
                    qp[i] == TAB || qp[i] == CR || qp[i] == LF) {
                qp[retlen++] = qp[i];
            }
        }

        return retlen;
    }

    /**
     * @param qp  Byte array to HeaderDecode
     * @param enc The character encoding of the returned string
     * @return The decoded string.
     */
    private static String Qdecode(byte[] qp, String enc) {
        int len = Qdecode(qp);
        try {
            return new String(qp, 0, len, enc);
        } catch (UnsupportedEncodingException e) {
            return new String(qp, 0, len);
        }
    }

    public static String HeaderDecode(String str) {
        while (str.contains("=?")) {
            int end = str.indexOf("?=");
            //base64解码
            if (str.contains("?B?")) {
                int start = str.indexOf("?B?");
                String decodeString = str.substring(start + 3, end);
                BASE64Decoder decoder = new BASE64Decoder();
                try {
                    byte[] b = decoder.decodeBuffer(decodeString);
                    decodeString = new String(b, str.substring(str.indexOf("=?") + 2, str.indexOf("?B?")).toUpperCase());
                    str = str.replaceFirst("=\\?.*\\?=", decodeString);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            //Quoted-Printable解码
            else if (str.contains("?Q?")) {
                int start = str.indexOf("?Q?");
                String decodeString = str.substring(start + 3, end);
                try {
                    byte[] qp = decodeString.getBytes(str.substring(str.indexOf("=?") + 2, str.indexOf("?Q?")).toUpperCase());
                    decodeString = Qdecode(qp, str.substring(str.indexOf("=?") + 2, str.indexOf("?Q?")).toUpperCase());
                    str = str.replaceFirst("=\\?.*\\?=", decodeString);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return str;
    }

    public static String ContentDecode(String contentInfo, String content) {
        try {
            if (contentInfo.contains("base64")) {
                BASE64Decoder decoder = new BASE64Decoder();
                byte[] bytes = decoder.decodeBuffer(content);

                if (contentInfo.contains("GB") || contentInfo.contains("gb"))
                    content = new String(bytes, "GBK");

                else if (contentInfo.contains("utf-8") || contentInfo.contains("UTF-8"))
                    content = new String(bytes, StandardCharsets.UTF_8);

                else if (contentInfo.contains("ISO-8859-1") || contentInfo.contains("iso-8859-1"))
                    content = new String(bytes, StandardCharsets.ISO_8859_1);

            } else if (contentInfo.contains("quoted-printable")) {
                if (contentInfo.contains("GB") || contentInfo.contains("gb")) {
                    byte[] bytes = content.getBytes("GBK");
                    content = Qdecode(bytes, "GBK");

                } else if (contentInfo.contains("utf-8") || contentInfo.contains("UTF-8")) {
                    byte[] bytes = content.getBytes("UTF-8");
                    content = Qdecode(bytes, "UTF-8");

                } else if (contentInfo.contains("ISO-8859-1") || contentInfo.contains("iso-8859-1")) {
                    byte[] bytes = content.getBytes("ISO-8859-1");
                    content = Qdecode(bytes, "ISO-8859-1");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return content;
    }

}